package com.tw ter.product_m xer.core.feature.featuremap.datarecord

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.datarecord._
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure. llegalStateFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport scala.collect on.JavaConverters._

/**
 * Constructs a DataRecord from a FeatureMap, g ven a predef ned set of features.
 *
 * @param features predef ned set of BaseDataRecordFeatures that should be  ncluded  n t  output DataRecord.
 */
class DataRecordExtractor[DRFeature <: BaseDataRecordFeature[_, _]](
  features: Set[DRFeature]) {

  pr vate val featureContext = new FeatureContext(features.collect {
    case dataRecordCompat ble: DataRecordCompat ble[_] => dataRecordCompat ble.mlFeature
  }.asJava)

  def fromDataRecord(dataRecord: DataRecord): FeatureMap = {
    val featureMapBu lder = FeatureMapBu lder()
    val r chDataRecord = SR chDataRecord(dataRecord, featureContext)
    features.foreach {
      // FeatureStoreDataRecordFeature  s currently not supported
      case _: FeatureStoreDataRecordFeature[_, _] =>
        throw new UnsupportedOperat onExcept on(
          "FeatureStoreDataRecordFeature cannot be extracted from a DataRecord")
      case feature: DataRecordFeature[_, _] w h DataRecordCompat ble[_] =>
        // Java AP  w ll return null, so use Opt on to convert   to Scala Opt on wh ch  s None w n null.
        r chDataRecord.getFeatureValueOpt(feature.mlFeature)(
          feature.fromDataRecordFeatureValue) match {
          case So (value) =>
            featureMapBu lder.add(feature.as nstanceOf[Feature[_, feature.FeatureType]], value)
          case None =>
            featureMapBu lder.addFa lure(
              feature,
              P pel neFa lure(
                 llegalStateFa lure,
                s"Requ red DataRecord feature  s m ss ng: ${feature.mlFeature.getFeatureNa }")
            )
        }
      case feature: DataRecordOpt onalFeature[_, _] w h DataRecordCompat ble[_] =>
        val featureValue =
          r chDataRecord.getFeatureValueOpt(feature.mlFeature)(feature.fromDataRecordFeatureValue)
        featureMapBu lder
          .add(feature.as nstanceOf[Feature[_, Opt on[feature.FeatureType]]], featureValue)
      // DataRecord nAFeature  s currently not supported
      case _: DataRecord nAFeature[_] =>
        throw new UnsupportedOperat onExcept on(
          "DataRecord nAFeature cannot be extracted from a DataRecord")
    }
    featureMapBu lder.bu ld()
  }
}
