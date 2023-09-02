package com.twitter.home_mixer.product.scored_tweets.feature_hydrator.adapters.non_ml_features

import com.twitter.ml.api.constant.SharedFeatures
import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.RichDataRecord
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures
import java.lang.{Long => JLong}

case class NonMLCommonFeatures(
  userId: Long,
  predictionRequestId: Option[Long],
  servedTimestamp: Long,
)

/**
 * define non ml features adapter to create a data record which includes many non ml features
 * e.g. predictionRequestId, userId, tweetId to be used as joined key in batch pipeline.
 */
object NonMLCommonFeaturesAdapter extends TimelinesMutatingAdapterBase[NonMLCommonFeatures] {

  private val featureContext = new FeatureContext(
    SharedFeatures.USER_ID,
    TimelinesSharedFeatures.PREDICTION_REQUEST_ID,
    TimelinesSharedFeatures.SERVED_TIMESTAMP,
  )

  override def getFeatureContext: FeatureContext = featureContext

  override val commonFeatures: Set[Feature[_]] = Set(
    SharedFeatures.USER_ID,
    TimelinesSharedFeatures.PREDICTION_REQUEST_ID,
    TimelinesSharedFeatures.SERVED_TIMESTAMP,
  )

  override def setFeatures(
    nonMLCommonFeatures: NonMLCommonFeatures,
    richDataRecord: RichDataRecord
  ): Unit = {
    richDataRecord.setFeatureValue[JLong](SharedFeatures.USER_ID, nonMLCommonFeatures.userId)
    nonMLCommonFeatures.predictionRequestId.foreach(
      richDataRecord.setFeatureValue[JLong](TimelinesSharedFeatures.PREDICTION_REQUEST_ID, _))
    richDataRecord.setFeatureValue[JLong](
      TimelinesSharedFeatures.SERVED_TIMESTAMP,
      nonMLCommonFeatures.servedTimestamp)
  }
}
