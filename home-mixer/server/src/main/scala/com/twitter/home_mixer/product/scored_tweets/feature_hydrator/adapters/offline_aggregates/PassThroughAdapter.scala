package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.offl ne_aggregates

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap . RecordOneToOneAdapter

object PassThroughAdapter extends  RecordOneToOneAdapter[Seq[DataRecord]] {
  overr de def adaptToDataRecord(record: Seq[DataRecord]): DataRecord =
    record. adOpt on.getOrElse(new DataRecord)

  // T   s not necessary and should not be used.
  overr de def getFeatureContext = ???
}
