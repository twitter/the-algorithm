package com.tw ter.product_m xer.core.feature.featurestorev1.featurevalue

 mport com.fasterxml.jackson.annotat on.Json gnorePropert es
 mport com.fasterxml.jackson.annotat on.JsonProperty
 mport com.tw ter.ml.ap .DataRecord rger
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.ml.featurestore.l b.data.Hydrat onError
 mport com.tw ter.product_m xer.core.feature.Feature

pr vate[product_m xer] object FeatureStoreV1ResponseFeature
    extends Feature[Any, FeatureStoreV1Response]

@Json gnorePropert es(Array("r chDataRecord", "fa ledFeatures"))
pr vate[product_m xer] case class FeatureStoreV1Response(
  @JsonProperty("r chDataRecord") r chDataRecord: SR chDataRecord,
  @JsonProperty("fa ledFeatures") fa ledFeatures: Map[_ <: Feature[_, _], Set[Hydrat onError]]) {
  // S nce R chDataRecord  s Java,   need to overr de t .
  overr de def equals(obj: Any): Boolean = obj match {
    case that: FeatureStoreV1Response =>
      fa ledFeatures == that.fa ledFeatures && r chDataRecord.getRecord.equals(
        that.r chDataRecord.getRecord)
    case _ => false
  }
}

pr vate[product_m xer] object FeatureStoreV1Response {
  val dataRecord rger = new DataRecord rger
  def  rge(
    left: FeatureStoreV1Response,
    r ght: FeatureStoreV1Response
  ): FeatureStoreV1Response = {
    val newDataRecord = left.r chDataRecord.getRecord.deepCopy()
    dataRecord rger. rge(newDataRecord, r ght.r chDataRecord.getRecord)
    FeatureStoreV1Response(
      r chDataRecord = SR chDataRecord(newDataRecord),
      left.fa ledFeatures ++ r ght.fa ledFeatures
    )
  }
}
