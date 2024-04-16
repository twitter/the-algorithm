package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.t  l nes.suggests.common.dense_data_record.thr ftjava.DenseCompactDataRecord

pr vate[offl ne_aggregates] object Ut ls {

  /**
   * Selects only those values  n map that correspond to t  keys  n  ds and apply t  prov ded
   * transform to t  selected values. T   s a conven ence  thod for use by T  l nes Aggregat on
   * Fra work based features.
   *
   * @param  dsToSelect T  set of  ds to extract values for.
   * @param transform A transform to apply to t  selected values.
   * @param map Map[Long, DenseCompactDataRecord]
   */
  def selectAndTransform(
     dsToSelect: Seq[Long],
    transform: DenseCompactDataRecord => DataRecord,
    map: java.ut l.Map[java.lang.Long, DenseCompactDataRecord],
  ): Map[Long, DataRecord] = {
    val f ltered: Seq[(Long, DataRecord)] =
      for {
         d <-  dsToSelect  f map.conta nsKey( d)
      } y eld {
         d -> transform(map.get( d))
      }
    f ltered.toMap
  }

  def f lterDataRecord(dr: DataRecord, featureContext: FeatureContext): Un  = {
    new R chDataRecord(dr, featureContext).dropUnknownFeatures()
  }
}
