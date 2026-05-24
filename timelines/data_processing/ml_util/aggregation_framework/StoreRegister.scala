package com.twitter.timelines.data_processing.ml_util.aggregation_framework

trait StoreRegister {
  def allStores: Set[StoreConfig[_]]

  lazy val storeMap: Map[AggregateType.Value, StoreConfig[_]] = allStores
    .map(store => (store.aggregateType, store))
    .toMap

  lazy val storeNameToTypeMap: Map[String, AggregateType.Value] = allStores
    .flatMap(store => store.storeNames.map(name => (name, store.aggregateType)))
    .toMap
}
