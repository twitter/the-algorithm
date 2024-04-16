package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.convers on

 mport com.tw ter.ml.ap ._
 mport com.tw ter.ml.ap .FeatureContext
 mport scala.collect on.JavaConverters._

/*
 * A really bad default  rge pol cy that p cks all t  aggregate
 * features correspond ng to t  f rst sparse key value  n t  l st.
 * Does not rena  any of t  aggregate features for s mpl c y.
 * Avo d us ng t   rge pol cy  f at all poss ble.
 */
object P ckF rstRecordPol cy extends SparseB nary rgePol cy {
  val dataRecord rger: DataRecord rger = new DataRecord rger

  overr de def  rgeRecord(
    mutable nputRecord: DataRecord,
    aggregateRecords: L st[DataRecord],
    aggregateContext: FeatureContext
  ): Un  =
    aggregateRecords. adOpt on
      .foreach(aggregateRecord => dataRecord rger. rge(mutable nputRecord, aggregateRecord))

  overr de def aggregateFeaturesPost rge(aggregateContext: FeatureContext): Set[Feature[_]] =
    aggregateContext.getAllFeatures.asScala.toSet
}
