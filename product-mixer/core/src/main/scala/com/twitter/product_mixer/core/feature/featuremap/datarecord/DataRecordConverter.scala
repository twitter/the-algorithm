package com.tw ter.product_m xer.core.feature.featuremap.datarecord

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .DataRecord rger
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.datarecord._
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap

object DataRecordConverter {
  val  rger = new DataRecord rger
}

/**
 * Constructs a FeatureMap from a DataRecord, g ven a predef ned set of features from a FeaturesScope.
 *
 * @param featuresScope scope of predef ned set of BaseDataRecordFeatures that should be  ncluded  n t  output FeatureMap.
 */
class DataRecordConverter[DRFeature <: BaseDataRecordFeature[_, _]](
  featuresScope: FeaturesScope[DRFeature]) {
   mport DataRecordConverter._

  def toDataRecord(featureMap: FeatureMap): DataRecord = {
    //  n  al ze a DataRecord w h t  Feature Store features  n   and t n add all t 
    // non-Feature Store features that support DataRecords to DataRecord.   don't
    // need to add Feature Store features because t y're already  n t   n  al DataRecord.
    //  f t re are any pre-bu lt DataRecords,    rge those  n.
    val r chDataRecord = featuresScope.getFeatureStoreFeaturesDataRecord(featureMap)
    val features = featuresScope.getNonFeatureStoreDataRecordFeatures(featureMap)
    features.foreach {
      case _: FeatureStoreDataRecordFeature[_, _] =>
      case requ redFeature: DataRecordFeature[_, _] w h DataRecordCompat ble[_] =>
        r chDataRecord.setFeatureValue(
          requ redFeature.mlFeature,
          requ redFeature.toDataRecordFeatureValue(
            featureMap.get(requ redFeature).as nstanceOf[requ redFeature.FeatureType]))
      case opt onalFeature: DataRecordOpt onalFeature[_, _] w h DataRecordCompat ble[_] =>
        featureMap
          .get(
            opt onalFeature.as nstanceOf[Feature[_, Opt on[opt onalFeature.FeatureType]]]).foreach {
            value =>
              r chDataRecord
                .setFeatureValue(
                  opt onalFeature.mlFeature,
                  opt onalFeature.toDataRecordFeatureValue(value))
          }
      case dataRecord nAFeature: DataRecord nAFeature[_] =>
         rger. rge(r chDataRecord.getRecord, featureMap.get(dataRecord nAFeature))
    }
    r chDataRecord.getRecord
  }
}
