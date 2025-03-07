import io.gatling.core.Predef._
import io.gatling.http.Predef._

import java.util.concurrent.atomic.AtomicInteger

class IcebergRestSimulation extends Simulation {
  // --------------------------------------------------------------------------------
  // Read parameters from system properties, with default values when possible
  // --------------------------------------------------------------------------------

  // The CLIENT_ID and CLIENT_SECRET must be provided otherwise we cannot authenticate
  private val clientId: String = sys.env("CLIENT_ID")
  private val clientSecret: String = sys.env("CLIENT_SECRET")

  // By default, we point to localhost
  private val baseUrl: String = sys.env.getOrElse("BASE_URL", "http://localhost:8181")

  // --------------------------------------------------------------------------------
  // Feeders
  // --------------------------------------------------------------------------------
  private val authenticationFeeder = Iterator.continually(
    Map(
      "clientId" -> clientId,
      "clientSecret" -> clientSecret
    )
  )
  private val catalogFeeder = Iterator.from(0).map(i =>
    Map(
      "catalogName" -> s"C_$i",
      "defaultBaseLocation" -> s"file:///tmp/polaris2/C_$i"
    )
  )
  private val namespaceFeeder = Iterator.from(0).map(i =>
    Map(
      "namespaceName" -> s"NS_$i"
    )
  )


  private val scn = scenario("Create catalog and namespaces using the Iceberg REST API")
    // Authenticate using the /api/catalog/v1/oauth/tokens endpoint
    .feed(authenticationFeeder)
    .exec(
      http("Authenticate")
        .post("/api/catalog/v1/oauth/tokens")
        .header("Content-Type", "application/x-www-form-urlencoded")
        .formParam("grant_type", "client_credentials")
        .formParam("client_id", "#{clientId}")
        .formParam("client_secret", "#{clientSecret}")
        .formParam("scope", "PRINCIPAL_ROLE:ALL")
        .check(status.is(200))
        .check(jsonPath("$.access_token").saveAs("accessToken"))
    )
    // Then create the catalog
    .feed(catalogFeeder)
    .exec(
      http("Create Catalog")
        .post("/api/management/v1/catalogs")
        .header("Authorization", "Bearer #{accessToken}")
        .header("Content-Type", "application/json")
        .body(
          StringBody(
            """{
              |  "catalog": {
              |    "type": "INTERNAL",
              |    "name": "#{catalogName}",
              |    "properties": {
              |      "default-base-location": "#{defaultBaseLocation}"
              |    },
              |    "storageConfigInfo": {
              |      "storageType": "FILE"
              |    }
              |  }
              |}""".stripMargin
          )
        )
        .check(status.is(201))
    )
    // Then create the namespaces
    .repeat(5) {
      feed(namespaceFeeder)
        .exec(
          http("Create Namespace")
            .post("/api/catalog/v1/#{catalogName}/namespaces")
            .header("Authorization", "Bearer #{accessToken}")
            .header("Content-Type", "application/json")
            .body(
              StringBody(
                """{
                  |  "namespace": ["#{namespaceName}"]
                  |}""".stripMargin
              )
            )
            .check(status.is(200))
        )
    }

  // --------------------------------------------------------------------------------
  // Build up the HTTP protocol configuration and set up the simulation
  // --------------------------------------------------------------------------------
  private val httpProtocol = http
    .baseUrl(baseUrl)
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")

  setUp(
    scn.inject(atOnceUsers(5))
  ).protocols(httpProtocol)
}
