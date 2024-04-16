package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work

 mport com.tw ter.dal.cl ent.dataset.T  Part  onedDALDataset
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .Feature
 mport java.lang.{Long => JLong}

case class Offl neAggregateS ce(
  overr de val na : Str ng,
  overr de val t  stampFeature: Feature[JLong],
  scald ngHdfsPath: Opt on[Str ng] = None,
  scald ngSuff xType: Opt on[Str ng] = None,
  dalDataSet: Opt on[T  Part  onedDALDataset[DataRecord]] = None,
  w hVal dat on: Boolean = true) // context: https://j ra.tw ter.b z/browse/TQ-10618
    extends AggregateS ce {
  /*
   * Th  lp trans  on callers to use DAL.read,   c ck that e  r t  HDFS
   * path  s def ned, or t  dalDataset. Both opt ons cannot be set at t  sa  t  .
   */
  assert(!(scald ngHdfsPath. sDef ned && dalDataSet. sDef ned))
}
