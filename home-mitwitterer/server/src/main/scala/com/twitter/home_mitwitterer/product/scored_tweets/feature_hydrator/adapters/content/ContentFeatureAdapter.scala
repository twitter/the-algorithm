package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.adapters.content

import com.twitter.home_mitwitterer.model.ContentFeatures
import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContetwittert
import com.twitter.ml.api.RichDataRecord
import com.twitter.ml.api.util.DataRecordConverters._
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.twitter.timelines.prediction.common.adapters.TweetLengthType
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures
import com.twitter.timelines.prediction.features.conversation_features.ConversationFeatures
import scala.collection.JavaConverters._

object ContentFeatureAdapter etwittertends TimelinesMutatingAdapterBase[Option[ContentFeatures]] {

  override val getFeatureContetwittert: FeatureContetwittert = new FeatureContetwittert(
    ConversationFeatures.IS_SELF_THREAD_TWEET,
    ConversationFeatures.IS_LEAF_IN_SELF_THREAD,
    TimelinesSharedFeatures.ASPECT_RATIO_DEN,
    TimelinesSharedFeatures.ASPECT_RATIO_NUM,
    TimelinesSharedFeatures.BIT_RATE,
    TimelinesSharedFeatures.CLASSIFICATION_LABELS,
    TimelinesSharedFeatures.COLOR_1_BLUE,
    TimelinesSharedFeatures.COLOR_1_GREEN,
    TimelinesSharedFeatures.COLOR_1_PERCENTAGE,
    TimelinesSharedFeatures.COLOR_1_RED,
    TimelinesSharedFeatures.FACE_AREAS,
    TimelinesSharedFeatures.HAS_APP_INSTALL_CALL_TO_ACTION,
    TimelinesSharedFeatures.HAS_DESCRIPTION,
    TimelinesSharedFeatures.HAS_QUESTION,
    TimelinesSharedFeatures.HAS_SELECTED_PREVIEW_IMAGE,
    TimelinesSharedFeatures.HAS_TITLE,
    TimelinesSharedFeatures.HAS_VISIT_SITE_CALL_TO_ACTION,
    TimelinesSharedFeatures.HAS_WATCH_NOW_CALL_TO_ACTION,
    TimelinesSharedFeatures.HEIGHT_1,
    TimelinesSharedFeatures.HEIGHT_2,
    TimelinesSharedFeatures.HEIGHT_3,
    TimelinesSharedFeatures.HEIGHT_4,
    TimelinesSharedFeatures.IS_360,
    TimelinesSharedFeatures.IS_EMBEDDABLE,
    TimelinesSharedFeatures.IS_MANAGED,
    TimelinesSharedFeatures.IS_MONETIZABLE,
    TimelinesSharedFeatures.MEDIA_PROVIDERS,
    TimelinesSharedFeatures.NUM_CAPS,
    TimelinesSharedFeatures.NUM_COLOR_PALLETTE_ITEMS,
    TimelinesSharedFeatures.NUM_FACES,
    TimelinesSharedFeatures.NUM_MEDIA_TAGS,
    TimelinesSharedFeatures.NUM_NEWLINES,
    TimelinesSharedFeatures.NUM_STICKERS,
    TimelinesSharedFeatures.NUM_WHITESPACES,
    TimelinesSharedFeatures.RESIZE_METHOD_1,
    TimelinesSharedFeatures.RESIZE_METHOD_2,
    TimelinesSharedFeatures.RESIZE_METHOD_3,
    TimelinesSharedFeatures.RESIZE_METHOD_4,
    TimelinesSharedFeatures.TWEET_LENGTH,
    TimelinesSharedFeatures.TWEET_LENGTH_TYPE,
    TimelinesSharedFeatures.VIDEO_DURATION,
    TimelinesSharedFeatures.VIEW_COUNT,
    TimelinesSharedFeatures.WIDTH_1,
    TimelinesSharedFeatures.WIDTH_2,
    TimelinesSharedFeatures.WIDTH_3,
    TimelinesSharedFeatures.WIDTH_4,
  )

  override val commonFeatures: Set[Feature[_]] = Set.empty

  private def getTweetLengthType(tweetLength: Int): Long = {
    tweetLength match {
      case twitter if 0 > twitter || 280 < twitter => TweetLengthType.INVALID
      case twitter if 0 <= twitter && twitter <= 30 => TweetLengthType.VERY_SHORT
      case twitter if 30 < twitter && twitter <= 60 => TweetLengthType.SHORT
      case twitter if 60 < twitter && twitter <= 90 => TweetLengthType.MEDIUM
      case twitter if 90 < twitter && twitter <= 140 => TweetLengthType.LENGTHY
      case twitter if 140 < twitter && twitter <= 210 => TweetLengthType.VERY_LENGTHY
      case twitter if twitter > 210 => TweetLengthType.MAtwitterIMUM_LENGTH
    }
  }

  override def setFeatures(
    contentFeatures: Option[ContentFeatures],
    richDataRecord: RichDataRecord
  ): Unit = {
    if (contentFeatures.nonEmpty) {
      val features = contentFeatures.get
      // Conversation Features
      richDataRecord.setFeatureValueFromOption(
        ConversationFeatures.IS_SELF_THREAD_TWEET,
        Some(features.selfThreadMetadata.nonEmpty)
      )
      richDataRecord.setFeatureValueFromOption(
        ConversationFeatures.IS_LEAF_IN_SELF_THREAD,
        features.selfThreadMetadata.map(_.isLeaf)
      )

      // Media Features
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.ASPECT_RATIO_DEN,
        features.aspectRatioDen.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.ASPECT_RATIO_NUM,
        features.aspectRatioNum.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.BIT_RATE,
        features.bitRate.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.HEIGHT_1,
        features.heights.flatMap(_.lift(0)).map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.HEIGHT_2,
        features.heights.flatMap(_.lift(1)).map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.HEIGHT_3,
        features.heights.flatMap(_.lift(2)).map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.HEIGHT_4,
        features.heights.flatMap(_.lift(3)).map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.NUM_MEDIA_TAGS,
        features.numMediaTags.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.RESIZE_METHOD_1,
        features.resizeMethods.flatMap(_.lift(0)).map(_.toLong)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.RESIZE_METHOD_2,
        features.resizeMethods.flatMap(_.lift(1)).map(_.toLong)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.RESIZE_METHOD_3,
        features.resizeMethods.flatMap(_.lift(2)).map(_.toLong)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.RESIZE_METHOD_4,
        features.resizeMethods.flatMap(_.lift(3)).map(_.toLong)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.VIDEO_DURATION,
        features.videoDurationMs.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.WIDTH_1,
        features.widths.flatMap(_.lift(0)).map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.WIDTH_2,
        features.widths.flatMap(_.lift(1)).map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.WIDTH_3,
        features.widths.flatMap(_.lift(2)).map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.WIDTH_4,
        features.widths.flatMap(_.lift(3)).map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.NUM_COLOR_PALLETTE_ITEMS,
        features.numColors.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.COLOR_1_RED,
        features.dominantColorRed.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.COLOR_1_BLUE,
        features.dominantColorBlue.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.COLOR_1_GREEN,
        features.dominantColorGreen.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.COLOR_1_PERCENTAGE,
        features.dominantColorPercentage
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.MEDIA_PROVIDERS,
        features.mediaOriginProviders.map(_.toSet.asJava)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.IS_360,
        features.is360
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.VIEW_COUNT,
        features.viewCount.map(_.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.IS_MANAGED,
        features.isManaged
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.IS_MONETIZABLE,
        features.isMonetizable
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.IS_EMBEDDABLE,
        features.isEmbeddable
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.NUM_STICKERS,
        features.stickerIds.map(_.length.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.NUM_FACES,
        features.faceAreas.map(_.length.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.FACE_AREAS,
        // guard for etwitterception from matwitter on empty seq
        features.faceAreas.map(faceAreas =>
          faceAreas.map(_.toDouble).reduceOption(_ matwitter _).getOrElse(0.0))
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.HAS_SELECTED_PREVIEW_IMAGE,
        features.hasSelectedPreviewImage
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.HAS_TITLE,
        features.hasTitle
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.HAS_DESCRIPTION,
        features.hasDescription
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.HAS_VISIT_SITE_CALL_TO_ACTION,
        features.hasVisitSiteCallToAction
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.HAS_APP_INSTALL_CALL_TO_ACTION,
        features.hasAppInstallCallToAction
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.HAS_WATCH_NOW_CALL_TO_ACTION,
        features.hasWatchNowCallToAction
      )
      // tetwittert features
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.NUM_CAPS,
        Some(features.numCaps.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.TWEET_LENGTH,
        Some(features.length.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.TWEET_LENGTH_TYPE,
        Some(getTweetLengthType(features.length.toInt))
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.NUM_WHITESPACES,
        Some(features.numWhiteSpaces.toDouble)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.HAS_QUESTION,
        Some(features.hasQuestion)
      )
      richDataRecord.setFeatureValueFromOption(
        TimelinesSharedFeatures.NUM_NEWLINES,
        features.numNewlines.map(_.toDouble)
      )
    }
  }
}
