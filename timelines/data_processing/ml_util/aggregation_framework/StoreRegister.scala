package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work

tra  StoreReg ster {
  def allStores: Set[StoreConf g[_]]

  lazy val storeMap: Map[AggregateType.Value, StoreConf g[_]] = allStores
    .map(store => (store.aggregateType, store))
    .toMap

  lazy val storeNa ToTypeMap: Map[Str ng, AggregateType.Value] = allStores
    .flatMap(store => store.storeNa s.map(na  => (na , store.aggregateType)))
    .toMap
}
