package com.twitter.product_mitwitterer.core.feature.featurestorev1.featurevalue

import com.fastertwitterml.jackson.annotation.JsonIgnoreProperties
import com.fastertwitterml.jackson.annotation.JsonProperty
import com.twitter.ml.api.DataRecordMerger
import com.twitter.ml.api.util.SRichDataRecord
import com.twitter.ml.featurestore.lib.data.HydrationError
import com.twitter.product_mitwitterer.core.feature.Feature

private[product_mitwitterer] object FeatureStoreV1ResponseFeature
    etwittertends Feature[Any, FeatureStoreV1Response]

@JsonIgnoreProperties(Array("richDataRecord", "failedFeatures"))
private[product_mitwitterer] case class FeatureStoreV1Response(
  @JsonProperty("richDataRecord") richDataRecord: SRichDataRecord,
  @JsonProperty("failedFeatures") failedFeatures: Map[_ <: Feature[_, _], Set[HydrationError]]) {
  // Since RichDataRecord is Java, we need to override this.
  override def equals(obj: Any): Boolean = obj match {
    case that: FeatureStoreV1Response =>
      failedFeatures == that.failedFeatures && richDataRecord.getRecord.equals(
        that.richDataRecord.getRecord)
    case _ => false
  }
}

private[product_mitwitterer] object FeatureStoreV1Response {
  val dataRecordMerger = new DataRecordMerger
  def merge(
    left: FeatureStoreV1Response,
    right: FeatureStoreV1Response
  ): FeatureStoreV1Response = {
    val newDataRecord = left.richDataRecord.getRecord.deepCopy()
    dataRecordMerger.merge(newDataRecord, right.richDataRecord.getRecord)
    FeatureStoreV1Response(
      richDataRecord = SRichDataRecord(newDataRecord),
      left.failedFeatures ++ right.failedFeatures
    )
  }
}
