package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.offl ne_aggregates

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.convers on.Comb neCountsPol cy
 mport com.tw ter.t  l nes.pred ct on.common.adapters.T  l nes RecordAdapter

class SparseAggregatesToDenseAdapter(pol cy: Comb neCountsPol cy)
    extends T  l nes RecordAdapter[Seq[DataRecord]] {

  overr de def setFeatures( nput: Seq[DataRecord], mutableDataRecord: R chDataRecord): Un  =
    pol cy.default rgeRecord(mutableDataRecord.getRecord,  nput.toL st)

  overr de val getFeatureContext: FeatureContext =
    new FeatureContext(pol cy.outputFeaturesPost rge.toSeq: _*)
}
