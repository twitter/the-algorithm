package com.tw ter.ho _m xer.ut l

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.ml.ap .Feature
 mport java.lang.{Double => JDouble}

object DataRecordUt l {
  def applyRena (
    dataRecord: DataRecord,
    featureContext: FeatureContext,
    rena dFeatureContext: FeatureContext,
    featureRenam ngMap: Map[Feature[_], Feature[_]]
  ): DataRecord = {
    val r chFullDr = new SR chDataRecord(dataRecord, featureContext)
    val r chNewDr = new SR chDataRecord(new DataRecord, rena dFeatureContext)
    val feature erator = featureContext. erator()
    feature erator.forEachRema n ng { feature =>
       f (r chFullDr.hasFeature(feature)) {
        val rena dFeature = featureRenam ngMap.getOrElse(feature, feature)

        val typedFeature = feature.as nstanceOf[Feature[JDouble]]
        val typedRena dFeature = rena dFeature.as nstanceOf[Feature[JDouble]]

        r chNewDr.setFeatureValue(typedRena dFeature, r chFullDr.getFeatureValue(typedFeature))
      }
    }
    r chNewDr.getRecord
  }
}
