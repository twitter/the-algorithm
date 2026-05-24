package com.twitter.timelines.data_processing.ml_util.aggregation_framework

import com.twitter.ml.api.DataRecord

/**
 * Keyed record that is used to reprsent the aggregation type and its corresponding data record.
 *
 * @constructor creates a new keyed record.
 *
 * @param aggregateType the aggregate type
 * @param record the data record associated with the key
  **/
case class KeyedRecord(aggregateType: AggregateType.Value, record: DataRecord)

/**
 * Keyed record map with multiple data record.
 *
 * @constructor creates a new keyed record map.
 *
 * @param aggregateType the aggregate type
 * @param recordMap a map with key of type Long and value of type DataRecord
 *  where the key indicates the index and the value indicating the record
 *
  **/
case class KeyedRecordMap(
  aggregateType: AggregateType.Value,
  recordMap: scala.collection.Map[Long, DataRecord])
