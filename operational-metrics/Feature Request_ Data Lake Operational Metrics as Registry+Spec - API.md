# Data Lake Operational Metrics \- API

This document contains the possible API endpoints that would be served by the Polaris Operational Metrics component.

## Base path

New API endpoints will be added to allow external systems to fetch the operational metrics associated with a given entity.  In order to distinguish the new API from the Iceberg REST API , while maintaining consistency, all new endpoints will be located under `/api/operational-metrics`.

## Registry related endpoints

### Get the list of supported operational metrics for tables

| Endpoint | `/v1/{prefix}/definitions/tables` |
| :---- | :---- |
| **Method** | GET |
| **Summary** | Retrieve the list of supported operational metrics for tables |
| **Parameters** | None |
| **Response** | A `200` status response with a JSON array of supported metric definitions. |

The response is a JSON object whose body contains the metric name, type and description for each supported metric.  The following are examples of valid responses:

```json
{
  "data-file-count": {
    "type": "point",
    "description": "Total number of data files in the table"
  },
  "data-file-size-stats": {
    "type": "distribution",
    "description": "Data file size distribution statistics"
  }
}
```

## Table related endpoints

#### Submit new metrics

| Endpoint | `/v1/{prefix}/namespaces/{namespace}/tables/{table}` |
| :---- | :---- |
| **Method** | PUT |
| **Summary** | Submit new metric values for a specific table |
| **Request** | JSON object containing metric value and metadata. The metadata for each metric SHOULD contain the timestamp of the last table modification included in the metric computation.  It CAN also include other metadata, like the last Iceberg sequence number included in the metric computation. |
| **Response** | A `204` status response if the operation is successful.  |

Below is an example of a JSON payload that contains two metrics:

```json
{
  "total-number-of-files": {
    "metadata": {
      "last-updated-timestamp": 1756827441,
      "last-sequence-number": 123
    },
    "metric": {
      "type": "point",
      "value": 14827
    }
  },
  "partition-sizes": {
    "metadata": {
      "last-updated-timestamp": 1756820479,
      "last-sequence-number": 42
    },
    "metric": {
      "type": "distribution",
      "value": {
        "min": 1024,
        "p10": 2048,
        "p25": 4096,
        "p50": 8192,
        "p75": 16384,
        "p90": 32768,
        "p99": 65536,
        "p999": 131072,
        "p9999": 262144,
        "max": 524288,
        "average": 12345.67,
        "standard-deviation": 8765.43,
        "total": 67118765.43
      }
    }
  }
}
```

#### Get a given metric

| Endpoint | `/v1/{prefix}/namespaces/{namespace}/tables/{table}/metrics/{metric}` |
| :---- | :---- |
| **Method** | GET |
| **Summary** | Fetch a given operational metrics associated with a given table. |
| **Parameters** | None |
| **Response** | A `200` status response with a json-encoded object if the operation is successful.  A `404` status response if the metric does not exist. |

The response is a JSON object whose body contains the metric name, and value(s).  The following are examples of valid responses:

```json
{
  "metadata": {
    "last-updated-timestamp": 1756827441,
    "last-sequence-number": 123
  },
  "metric": {
    "type": "point",
    "value": 14827
  }
}
```

```json
{
  "metadata": {
    "last-updated-timestamp": 1756820479,
    "last-sequence-number": 42
  },
  "metric": {
    "type": "distribution",
    "value": {
      "min": 1024,
      "p10": 2048,
      "p25": 4096,
      "p50": 8192,
      "p75": 16384,
      "p90": 32768,
      "p99": 65536,
      "p999": 131072,
      "p9999": 262144,
      "max": 524288,
      "average": 12345.67,
      "standard-deviation": 8765.43,
      "total": 67118765.43
    }
  }
}
```

#### Get all metrics

| Endpoint | `/v1/{prefix}/namespaces/{namespace}/tables/{table}` |
| :---- | :---- |
| **Method** | GET |
| **Summary** | Fetch all the operational metrics associated with a given table. |
| **Parameters** | None |
| **Response** | A `200` status response with a json-encoded object if the operation is successful.  A `404` status response if no metric can be found for the table. |

The response is a JSON object whose body contains an array associating each metric name with its value(s).  The following is an example of a valid response:

```json
{
  "total-number-of-files": {
    "metadata": {
      "last-updated-timestamp": 1756827441,
      "last-sequence-number": 123
    },
    "metric": {
      "type": "point",
      "value": 14827
    }
  },
  "partition-sizes": {
    "metadata": {
      "last-updated-timestamp": 1756820479,
      "last-sequence-number": 42
    },
    "metric": {
      "type": "distribution",
      "value": {
        "min": 1024,
        "p10": 2048,
        "p25": 4096,
        "p50": 8192,
        "p75": 16384,
        "p90": 32768,
        "p99": 65536,
        "p999": 131072,
        "p9999": 262144,
        "max": 524288,
        "average": 12345.67,
        "standard-deviation": 8765.43,
        "total": 67118765.43
      }
    }
  }
}
```