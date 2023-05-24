## Timelines Aggregation Jobs

This directory contains the specific definition of aggregate jobs that generate features used by the Heavy Ranker. 
The primary files of interest are [`TimelinesAggregationConfigDetails.scala`](TimelinesAggregationConfigDetails.scala), which contains the defintion for the batch aggregate jobs and [`real_time/TimelinesOnlineAggregationConfigBase.scala`](real_time/TimelinesOnlineAggregationConfigBase.scala) which contains the definitions for the real time aggregate jobs. 

The aggregation framework that these jobs are based on is [here](../../../../../../../../timelines/data_processing/ml_util/aggregation_framework).