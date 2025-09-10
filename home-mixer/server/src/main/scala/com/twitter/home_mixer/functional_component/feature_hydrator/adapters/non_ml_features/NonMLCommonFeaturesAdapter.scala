package com.twitter.home_mixer.functional_component.feature_hydrator.adapters.non_ml_features

import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.RichDataRecord
import com.twitter.ml.api.constant.SharedFeatures
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures
import com.twitter.timelines.prediction.features.request_context.RequestContextFeatures
import java.lang.{Long => JLong}
import java.lang.{String => JString}

case class NonMLCommonFeatures(
  userId: Long,
  guestId: Option[Long],
  clientId: Option[Long],
  countryCode: Option[String],
  predictionRequestId: Option[Long],
  productSurface: String,
  servedTimestamp: Long,
)

/**
 * define non ml features adapter to create a data record which includes many non ml features
 * e.g. predictionRequestId, userId, tweetId to be used as joined key in batch pipeline.
 */
object NonMLCommonFeaturesAdapter extends TimelinesMutatingAdapterBase[NonMLCommonFeatures] {

  private val featureContext = new FeatureContext(
    SharedFeatures.USER_ID,
    SharedFeatures.GUEST_ID,
    SharedFeatures.CLIENT_ID,
    TimelinesSharedFeatures.PREDICTION_REQUEST_ID,
    TimelinesSharedFeatures.PRODUCT_SURFACE,
    TimelinesSharedFeatures.SERVED_TIMESTAMP,
    RequestContextFeatures.COUNTRY_CODE,
  )

  override def getFeatureContext: FeatureContext = featureContext

  override val commonFeatures: Set[Feature[_]] = Set(
    SharedFeatures.USER_ID,
    SharedFeatures.GUEST_ID,
    SharedFeatures.CLIENT_ID,
    TimelinesSharedFeatures.PREDICTION_REQUEST_ID,
    TimelinesSharedFeatures.PRODUCT_SURFACE,
    TimelinesSharedFeatures.SERVED_TIMESTAMP,
    RequestContextFeatures.COUNTRY_CODE,
  )

  override def setFeatures(
    nonMLCommonFeatures: NonMLCommonFeatures,
    richDataRecord: RichDataRecord
  ): Unit = {
    richDataRecord.setFeatureValue[JLong](SharedFeatures.USER_ID, nonMLCommonFeatures.userId)
    nonMLCommonFeatures.guestId.foreach(
      richDataRecord.setFeatureValue[JLong](SharedFeatures.GUEST_ID, _))
    nonMLCommonFeatures.clientId.foreach(
      richDataRecord.setFeatureValue[JLong](SharedFeatures.CLIENT_ID, _))
    nonMLCommonFeatures.predictionRequestId.foreach(
      richDataRecord.setFeatureValue[JLong](TimelinesSharedFeatures.PREDICTION_REQUEST_ID, _))
    nonMLCommonFeatures.countryCode.foreach(
      richDataRecord.setFeatureValue[JString](RequestContextFeatures.COUNTRY_CODE, _))
    richDataRecord.setFeatureValue(
      TimelinesSharedFeatures.PRODUCT_SURFACE,
      nonMLCommonFeatures.productSurface)
    richDataRecord.setFeatureValue[JLong](
      TimelinesSharedFeatures.SERVED_TIMESTAMP,
      nonMLCommonFeatures.servedTimestamp)
  }
}
