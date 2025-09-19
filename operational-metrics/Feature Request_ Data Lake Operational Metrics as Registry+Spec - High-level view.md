# Data Lake Operational Metrics as Registry+Spec \- High-level view

This document is a second iteration of the initial [Data Lake Operational Metrics design document](https://docs.google.com/document/d/1yHvLwqNVD3Z84KYcc_m3c4M8bMijTXg9iP1CR0JXxCc/edit?tab=t.1l3uwb7vlwqw).  It presents a completely different approach where the computation of Operational Metrics is performed by Trusted Engines, outside of Polaris.

## Introduction

Polaris acts as the gatekeeper for all interactions with the storage layer that powers the Data Lake, through features like RBAC and credential vending, for example.  However, it currently does not offer any facility regarding the collection of Data Lake operational metrics that could be used by external systems to perform certain functions.

The purpose of this document is to explore the possibility to collect and expose Data Lake operational metrics.

## Use Cases

The following use cases would benefit from having operational metrics maintained and served by Polaris.

### Table Maintenance Services

Table Maintenance Services (TMS) perform Iceberg table compaction as well as snapshot expiration operations.  TMS would benefit from operational metrics in that it would make it easier to identify whether a particular table is eligible for maintenance.

### Engine specific optimization

Query engines may use different strategies depending on the shape and size of frequently used tables and views.  Leveraging the pre-computed storage-level metrics in Polaris could help them deliver better Data Lake performance.

### Data Lake Monitoring

Service Vendors who deploy Polaris may also leverage certain operational metrics for monitoring purposes. Typically, total storage would be a straightforward candidate for such a use case.

### Data Lake cleanup

Iceberg orphan removal is an expensive operation that requires scanning the entirety of a table storage location(s).  When Polaris Events API supports notifying about failures (e.g. table commit failure), new operational metrics could be added to track the number of failed creations/updates against a given table.

This could become a useful heuristic for Data Lake cleanup operations.  It would hint to tables that may contain a large number of orphaned files.  This would help reduce the Data Lake size and cost.

## Example of collected data

This section shows examples of low level metrics as well as higher level business metrics that could be computed and exposed.

* Storage-level metrics (for table-like entities):  
  * Total number of files  
  * Histogram of file sizes  
  * Total table size  
  * Total number of partitions  
  * Histogram of partition sizes  
  * Total Storage utilization  
* Data Health metrics  
  * Data Skew (partition imbalance)