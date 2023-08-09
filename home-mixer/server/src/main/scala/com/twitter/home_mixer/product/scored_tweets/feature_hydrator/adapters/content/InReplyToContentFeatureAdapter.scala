package com.twitter.home_mixer.product.scored_tweets.feature_hydrator.adapters.content

import com.twitter.home_mixer.model.ContentFeatures
import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.RichDataRecord
import com.twitter.ml.api.util.DataRecordConverters.RichDataRecordWrapper
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.twitter.timelines.prediction.features.common.InReplyToTweetTimelinesSharedFeatures

object InReplyToContentFeatureAdapter
    extends TimelinesMutatingAdapterBase[Option[ContentFeatures]] {

  override val getFeatureContext: FeatureContext = new FeatureContext(
    // Media Features
    InReplyToTweetTimelinesSharedFeatures.ASPECT_RATIO_DEN,
    InReplyToTweetTimelinesSharedFeatures.ASPECT_RATIO_NUM,
    InReplyToTweetTimelinesSharedFeatures.HEIGHT_1,
    InReplyToTweetTimelinesSharedFeatures.HEIGHT_2,
    InReplyToTweetTimelinesSharedFeatures.VIDEO_DURATION,
    // TextFeatures
    InReplyToTweetTimelinesSharedFeatures.NUM_CAPS,
    InReplyToTweetTimelinesSharedFeatures.TWEET_LENGTH,
    InReplyToTweetTimelinesSharedFeatures.HAS_QUESTION,
  )

  override val commonFeatures: Set[Feature[_]] = Set.empty

  override def setFeatures(
    contentFeatures: Option[ContentFeatures],
    richDataRecord: RichDataRecord
  ): Unit = {
    if (contentFeatures.nonEmpty) {
      val features = contentFeatures.get
      richDataRecord.setFeatureValueFromOption(
        InReplyToTweetTimelinesSharedFeatures.ASPECT_RATIO_DEN,
        features.aspectRatioNum.map(_.toDouble)
      )

      richDataRecord.setFeatureValueFromOption(
        InReplyToTweetTimelinesSharedFeatures.ASPECT_RATIO_NUM,
        features.aspectRatioNum.map(_.toDouble)
      )

      richDataRecord.setFeatureValueFromOption(
        InReplyToTweetTimelinesSharedFeatures.HEIGHT_1,
        features.heights.flatMap(_.lift(0)).map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        InReplyToTweetTimelinesSharedFeatures.HEIGHT_2,
        features.heights.flatMap(_.lift(1)).map(_.toDouble)
      )

      richDataRecord.setFeatureValueFromOption(
        InReplyToTweetTimelinesSharedFeatures.VIDEO_DURATION,
        features.videoDurationMs.map(_.toDouble)
      )

      richDataRecord.setFeatureValueFromOption(
        InReplyToTweetTimelinesSharedFeatures.NUM_CAPS,
        Some(features.numCaps.toDouble)
      )

      richDataRecord.setFeatureValueFromOption(
        InReplyToTweetTimelinesSharedFeatures.TWEET_LENGTH,
        Some(features.length.toDouble)
      )

      richDataRecord.setFeatureValueFromOption(
        InReplyToTweetTimelinesSharedFeatures.HAS_QUESTION,
        Some(features.hasQuestion)
      )
    }
  }
}
