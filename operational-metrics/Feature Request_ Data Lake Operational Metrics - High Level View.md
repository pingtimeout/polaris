# Data Lake Operational Metrics \- High-level view

## Introduction

Polaris acts as the gatekeeper for all interactions with the storage layer that powers the Data Lake, through features like RBAC and credential vending, for example.  However, it currently does not offer any facility regarding the collection of Data Lake operational metrics that could be used by external systems to perform certain functions.

The purpose of this document is to explore the possibility to collect and expose Data Lake operational metrics.

## Use Cases

The following use cases would benefit from having operational metrics maintained and served by Polaris.

### Table Maintenance Services

Table Maintenance Services (TMS) perform Iceberg table compaction as well as snapshot expiration operations.  TMS would benefit from operational metrics in that it would make it easier to identify whether a particular table is eligible for maintenance.

### Engine specific optimization

*Note: this section means to cover reflections.  But as this document should eventually be published on the Polaris Dev ML, it should be phrased to encompass more than just Dremio.*

Query engines may use different strategies depending on the shape and size of frequently used tables and views.  Leveraging the pre-computed storage-level metrics in Polaris could help them deliver better data lake performance.

### Billing services?

*Note: given that some Polaris users are service providers, and that one aspect of Polaris development is multi-tenancy, we might want to bring that use case?*

Service Vendors who deploy Polaris may also leverage certain operational metrics for billing purposes.  For example, Data Lake Catalogs offering could be billed based on the metadata size and/or shape, or on the number of reads/writes.

### Anything else?

## Example of collected data

This section shows examples of low level metrics as well as higher level business metrics that could be collected and exposed.

* Storage-level metrics (for table-like entities):  
  * Total number of files  
  * Total number of small files  
  * Total number of partitions  
  * Total table size  
  * Histogram of partition sizes  
  * Storage utilization  
* Access metrics (for table-like entities):  
  * Total number of reads  
  * Total number of writes  
* Data Health metrics  
  * Data Skew (partition imbalance)