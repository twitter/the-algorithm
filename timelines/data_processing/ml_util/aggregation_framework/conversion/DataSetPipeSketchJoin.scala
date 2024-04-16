package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.convers on

 mport com.tw ter.b ject on. nject on
 mport com.tw ter.ml.ap ._
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.scald ng.TypedP pe

object DataSetP peSketchJo n {
  val DefaultSketchNumReducers = 500
  val dataRecord rger: DataRecord rger = new DataRecord rger
   mpl c  val str2Byte: Str ng => Array[Byte] =
     mpl c ly[ nject on[Str ng, Array[Byte]]].toFunct on

  /* Computes a left sketch jo n on a set of ske d keys. */
  def apply(
     nputDataSet: DataSetP pe,
    ske dJo nKeys: Product,
    jo nFeaturesDataSet: DataSetP pe,
    sketchNumReducers:  nt = DefaultSketchNumReducers
  ): DataSetP pe = {
    val jo nKeyL st = ske dJo nKeys.product erator.toL st.as nstanceOf[L st[Feature[_]]]

    def makeKey(record: DataRecord): Str ng =
      jo nKeyL st
        .map(SR chDataRecord(record).getFeatureValue(_))
        .toStr ng

    def byKey(p pe: DataSetP pe): TypedP pe[(Str ng, DataRecord)] =
      p pe.records.map(record => (makeKey(record), record))

    val jo nedRecords = byKey( nputDataSet)
      .sketch(sketchNumReducers)
      .leftJo n(byKey(jo nFeaturesDataSet))
      .values
      .map {
        case ( nputRecord, jo nFeaturesOpt) =>
          jo nFeaturesOpt.foreach { jo nRecord => dataRecord rger. rge( nputRecord, jo nRecord) }
           nputRecord
      }

    DataSetP pe(
      jo nedRecords,
      FeatureContext. rge( nputDataSet.featureContext, jo nFeaturesDataSet.featureContext)
    )
  }
}
