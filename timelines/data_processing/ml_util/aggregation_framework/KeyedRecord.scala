package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work

 mport com.tw ter.ml.ap .DataRecord

/**
 * Keyed record that  s used to reprsent t  aggregat on type and  s correspond ng data record.
 *
 * @constructor creates a new keyed record.
 *
 * @param aggregateType t  aggregate type
 * @param record t  data record assoc ated w h t  key
  **/
case class KeyedRecord(aggregateType: AggregateType.Value, record: DataRecord)

/**
 * Keyed record map w h mult ple data record.
 *
 * @constructor creates a new keyed record map.
 *
 * @param aggregateType t  aggregate type
 * @param recordMap a map w h key of type Long and value of type DataRecord
 *  w re t  key  nd cates t   ndex and t  value  nd cat ng t  record
 *
  **/
case class KeyedRecordMap(
  aggregateType: AggregateType.Value,
  recordMap: scala.collect on.Map[Long, DataRecord])
