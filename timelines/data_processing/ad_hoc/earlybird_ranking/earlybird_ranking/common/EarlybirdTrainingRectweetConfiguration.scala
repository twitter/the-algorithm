package com.twitter.timelines.data_processing.ad_hoc.earlybird_ranking.common

import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.ITransform
import com.twitter.ml.api.transform.CascadeTransform
import com.twitter.ml.api.util.SRichDataRecord
import com.twitter.search.common.features.SearchResultFeature
import com.twitter.search.common.features.TweetFeature
import com.twitter.timelines.prediction.features.itl.ITLFeatures._
import scala.collection.JavaConverters._

class EarlybirdTrainingRectweetConfiguration extends EarlybirdTrainingConfiguration {

  override val labels: Map[String, Feature.Binary] = Map(
    "detail_expanded" -> IS_CLICKED,
    "favorited" -> IS_FAVORITED,
    "open_linked" -> IS_OPEN_LINKED,
    "photo_expanded" -> IS_PHOTO_EXPANDED,
    "profile_clicked" -> IS_PROFILE_CLICKED,
    "replied" -> IS_REPLIED,
    "retweeted" -> IS_RETWEETED,
    "video_playback50" -> IS_VIDEO_PLAYBACK_50
  )

  override val PositiveSamplingRate: Double = 0.5

  override def featureToSearchResultFeatureMap: Map[Feature[_], SearchResultFeature] =
    super.featureToSearchResultFeatureMap ++ Map(
      TEXT_SCORE -> TweetFeature.TEXT_SCORE,
      REPLY_COUNT -> TweetFeature.REPLY_COUNT,
      RETWEET_COUNT -> TweetFeature.RETWEET_COUNT,
      FAV_COUNT -> TweetFeature.FAVORITE_COUNT,
      HAS_CARD -> TweetFeature.HAS_CARD_FLAG,
      HAS_CONSUMER_VIDEO -> TweetFeature.HAS_CONSUMER_VIDEO_FLAG,
      HAS_PRO_VIDEO -> TweetFeature.HAS_PRO_VIDEO_FLAG,
      HAS_VINE -> TweetFeature.HAS_VINE_FLAG,
      HAS_PERISCOPE -> TweetFeature.HAS_PERISCOPE_FLAG,
      HAS_NATIVE_IMAGE -> TweetFeature.HAS_NATIVE_IMAGE_FLAG,
      HAS_IMAGE -> TweetFeature.HAS_IMAGE_URL_FLAG,
      HAS_NEWS -> TweetFeature.HAS_NEWS_URL_FLAG,
      HAS_VIDEO -> TweetFeature.HAS_VIDEO_URL_FLAG,
      // some features that exist for recap are not available in rectweet
      //    HAS_TREND
      //    HAS_MULTIPLE_HASHTAGS_OR_TRENDS
      //    IS_OFFENSIVE
      //    IS_REPLY
      //    IS_RETWEET
      IS_AUTHOR_BOT -> TweetFeature.IS_USER_BOT_FLAG,
      IS_AUTHOR_SPAM -> TweetFeature.IS_USER_SPAM_FLAG,
      IS_AUTHOR_NSFW -> TweetFeature.IS_USER_NSFW_FLAG,
      //    FROM_VERIFIED_ACCOUNT
      USER_REP -> TweetFeature.USER_REPUTATION,
      //    EMBEDS_IMPRESSION_COUNT
      //    EMBEDS_URL_COUNT
      //    VIDEO_VIEW_COUNT
      FAV_COUNT_V2 -> TweetFeature.FAVORITE_COUNT_V2,
      RETWEET_COUNT_V2 -> TweetFeature.RETWEET_COUNT_V2,
      REPLY_COUNT_V2 -> TweetFeature.REPLY_COUNT_V2,
      IS_SENSITIVE -> TweetFeature.IS_SENSITIVE_CONTENT,
      HAS_MULTIPLE_MEDIA -> TweetFeature.HAS_MULTIPLE_MEDIA_FLAG,
      IS_AUTHOR_PROFILE_EGG -> TweetFeature.PROFILE_IS_EGG_FLAG,
      IS_AUTHOR_NEW -> TweetFeature.IS_USER_NEW_FLAG,
      NUM_MENTIONS -> TweetFeature.NUM_MENTIONS,
      NUM_HASHTAGS -> TweetFeature.NUM_HASHTAGS,
      HAS_VISIBLE_LINK -> TweetFeature.HAS_VISIBLE_LINK_FLAG,
      HAS_LINK -> TweetFeature.HAS_LINK_FLAG
    )

  override def derivedFeaturesAdder: CascadeTransform = {
    // only LINK_LANGUAGE availabe in rectweet. no LANGUAGE feature
    val linkLanguageTransform = new ITransform {
      private val linkLanguageFeature = new Feature.Continuous(TweetFeature.LINK_LANGUAGE.getName)

      override def transformContext(featureContext: FeatureContext): FeatureContext =
        featureContext.addFeatures(
          linkLanguageFeature
        )

      override def transform(record: DataRecord): Unit = {
        val srecord = SRichDataRecord(record)

        srecord.getFeatureValueOpt(LINK_LANGUAGE).map { link_language =>
          srecord.setFeatureValue(
            linkLanguageFeature,
            link_language.toDouble
          )
        }
      }
    }

    new CascadeTransform(
      List(
        super.derivedFeaturesAdder,
        linkLanguageTransform
      ).asJava
    )
  }
}
