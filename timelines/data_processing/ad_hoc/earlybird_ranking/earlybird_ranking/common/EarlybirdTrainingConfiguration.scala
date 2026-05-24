package com.twitter.timelines.data_processing.ad_hoc.earlybird_ranking.common

import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.ITransform
import com.twitter.ml.api.transform.CascadeTransform
import com.twitter.ml.api.transform.TransformFactory
import com.twitter.ml.api.util.SRichDataRecord
import com.twitter.ml.api.constant.SharedFeatures
import com.twitter.search.common.features.SearchResultFeature
import com.twitter.search.common.features.ExternalTweetFeature
import com.twitter.search.common.features.TweetFeature
import com.twitter.timelines.prediction.features.recap.RecapFeatures
import com.twitter.timelines.prediction.features.request_context.RequestContextFeatures
import com.twitter.timelines.prediction.features.time_features.TimeDataRecordFeatures
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures
import com.twitter.timelines.prediction.features.real_graph.RealGraphDataRecordFeatures
import scala.collection.JavaConverters._
import java.lang.{Boolean => JBoolean}

case class LabelInfo(name: String, downsampleFraction: Double, importance: Double)

case class LabelInfoWithFeature(info: LabelInfo, feature: Feature[JBoolean])

trait EarlybirdTrainingConfiguration {

  protected def labels: Map[String, Feature.Binary]

  protected def weights: Map[String, Double] = Map(
    "detail_expanded" -> 0.3,
    "favorited" -> 1.0,
    "open_linked" -> 0.1,
    "photo_expanded" -> 0.03,
    "profile_clicked" -> 1.0,
    "replied" -> 9.0,
    "retweeted" -> 1.0,
    "video_playback50" -> 0.01
  )

  // we basically should not downsample any of the precious positive data.
  // importance are currently set to match the full model's weights.
  protected def PositiveSamplingRate: Double = 1.0
  private def NegativeSamplingRate: Double = PositiveSamplingRate * 0.08

  // we basically should not downsample any of the precious positive data.
  // importance are currently set to match the full model's weights.
  final lazy val LabelInfos: List[LabelInfoWithFeature] = {
    assert(labels.keySet == weights.keySet)
    labels.keySet.map(makeLabelInfoWithFeature).toList
  }

  def makeLabelInfoWithFeature(labelName: String): LabelInfoWithFeature = {
    LabelInfoWithFeature(
      LabelInfo(labelName, PositiveSamplingRate, weights(labelName)),
      labels(labelName))
  }

  final lazy val NegativeInfo: LabelInfo = LabelInfo("negative", NegativeSamplingRate, 1.0)

  // example of features available in schema based namespace:
  protected def featureToSearchResultFeatureMap: Map[Feature[_], SearchResultFeature] = Map(
    RecapFeatures.TEXT_SCORE -> TweetFeature.TEXT_SCORE,
    RecapFeatures.REPLY_COUNT -> TweetFeature.REPLY_COUNT,
    RecapFeatures.RETWEET_COUNT -> TweetFeature.RETWEET_COUNT,
    RecapFeatures.FAV_COUNT -> TweetFeature.FAVORITE_COUNT,
    RecapFeatures.HAS_CARD -> TweetFeature.HAS_CARD_FLAG,
    RecapFeatures.HAS_CONSUMER_VIDEO -> TweetFeature.HAS_CONSUMER_VIDEO_FLAG,
    RecapFeatures.HAS_PRO_VIDEO -> TweetFeature.HAS_PRO_VIDEO_FLAG,
    // no corresponding HAS_NATIVE_VIDEO feature in TweetFeature
    RecapFeatures.HAS_VINE -> TweetFeature.HAS_VINE_FLAG,
    RecapFeatures.HAS_PERISCOPE -> TweetFeature.HAS_PERISCOPE_FLAG,
    RecapFeatures.HAS_NATIVE_IMAGE -> TweetFeature.HAS_NATIVE_IMAGE_FLAG,
    RecapFeatures.HAS_IMAGE -> TweetFeature.HAS_IMAGE_URL_FLAG,
    RecapFeatures.HAS_NEWS -> TweetFeature.HAS_NEWS_URL_FLAG,
    RecapFeatures.HAS_VIDEO -> TweetFeature.HAS_VIDEO_URL_FLAG,
    RecapFeatures.HAS_TREND -> TweetFeature.HAS_TREND_FLAG,
    RecapFeatures.HAS_MULTIPLE_HASHTAGS_OR_TRENDS -> TweetFeature.HAS_MULTIPLE_HASHTAGS_OR_TRENDS_FLAG,
    RecapFeatures.IS_OFFENSIVE -> TweetFeature.IS_OFFENSIVE_FLAG,
    RecapFeatures.IS_REPLY -> TweetFeature.IS_REPLY_FLAG,
    RecapFeatures.IS_RETWEET -> TweetFeature.IS_RETWEET_FLAG,
    RecapFeatures.IS_AUTHOR_BOT -> TweetFeature.IS_USER_BOT_FLAG,
    RecapFeatures.FROM_VERIFIED_ACCOUNT -> TweetFeature.FROM_VERIFIED_ACCOUNT_FLAG,
    RecapFeatures.USER_REP -> TweetFeature.USER_REPUTATION,
    RecapFeatures.EMBEDS_IMPRESSION_COUNT -> TweetFeature.EMBEDS_IMPRESSION_COUNT,
    RecapFeatures.EMBEDS_URL_COUNT -> TweetFeature.EMBEDS_URL_COUNT,
    // RecapFeatures.VIDEO_VIEW_COUNT deprecated
    RecapFeatures.FAV_COUNT_V2 -> TweetFeature.FAVORITE_COUNT_V2,
    RecapFeatures.RETWEET_COUNT_V2 -> TweetFeature.RETWEET_COUNT_V2,
    RecapFeatures.REPLY_COUNT_V2 -> TweetFeature.REPLY_COUNT_V2,
    RecapFeatures.IS_SENSITIVE -> TweetFeature.IS_SENSITIVE_CONTENT,
    RecapFeatures.HAS_MULTIPLE_MEDIA -> TweetFeature.HAS_MULTIPLE_MEDIA_FLAG,
    RecapFeatures.IS_AUTHOR_PROFILE_EGG -> TweetFeature.PROFILE_IS_EGG_FLAG,
    RecapFeatures.IS_AUTHOR_NEW -> TweetFeature.IS_USER_NEW_FLAG,
    RecapFeatures.NUM_MENTIONS -> TweetFeature.NUM_MENTIONS,
    RecapFeatures.NUM_HASHTAGS -> TweetFeature.NUM_HASHTAGS,
    RecapFeatures.HAS_VISIBLE_LINK -> TweetFeature.HAS_VISIBLE_LINK_FLAG,
    RecapFeatures.HAS_LINK -> TweetFeature.HAS_LINK_FLAG,
    //note: DISCRETE features are not supported by the modelInterpreter tool.
    // for the following features, we will create separate CONTINUOUS features instead of renaming
    //RecapFeatures.LINK_LANGUAGE
    //RecapFeatures.LANGUAGE
    TimelinesSharedFeatures.HAS_QUOTE -> TweetFeature.HAS_QUOTE_FLAG,
    TimelinesSharedFeatures.QUOTE_COUNT -> TweetFeature.QUOTE_COUNT,
    TimelinesSharedFeatures.WEIGHTED_FAV_COUNT -> TweetFeature.WEIGHTED_FAVORITE_COUNT,
    TimelinesSharedFeatures.WEIGHTED_QUOTE_COUNT -> TweetFeature.WEIGHTED_QUOTE_COUNT,
    TimelinesSharedFeatures.WEIGHTED_REPLY_COUNT -> TweetFeature.WEIGHTED_REPLY_COUNT,
    TimelinesSharedFeatures.WEIGHTED_RETWEET_COUNT -> TweetFeature.WEIGHTED_RETWEET_COUNT,
    TimelinesSharedFeatures.DECAYED_FAVORITE_COUNT -> TweetFeature.DECAYED_FAVORITE_COUNT,
    TimelinesSharedFeatures.DECAYED_RETWEET_COUNT -> TweetFeature.DECAYED_RETWEET_COUNT,
    TimelinesSharedFeatures.DECAYED_REPLY_COUNT -> TweetFeature.DECAYED_RETWEET_COUNT,
    TimelinesSharedFeatures.DECAYED_QUOTE_COUNT -> TweetFeature.DECAYED_QUOTE_COUNT,
    TimelinesSharedFeatures.FAKE_FAVORITE_COUNT -> TweetFeature.FAKE_FAVORITE_COUNT,
    TimelinesSharedFeatures.FAKE_RETWEET_COUNT -> TweetFeature.FAKE_RETWEET_COUNT,
    TimelinesSharedFeatures.FAKE_REPLY_COUNT -> TweetFeature.FAKE_REPLY_COUNT,
    TimelinesSharedFeatures.FAKE_QUOTE_COUNT -> TweetFeature.FAKE_QUOTE_COUNT,
    TimelinesSharedFeatures.EMBEDS_IMPRESSION_COUNT_V2 -> TweetFeature.EMBEDS_IMPRESSION_COUNT_V2,
    TimelinesSharedFeatures.EMBEDS_URL_COUNT_V2 -> TweetFeature.EMBEDS_URL_COUNT_V2,
    TimelinesSharedFeatures.LABEL_ABUSIVE_FLAG -> TweetFeature.LABEL_ABUSIVE_FLAG,
    TimelinesSharedFeatures.LABEL_ABUSIVE_HI_RCL_FLAG -> TweetFeature.LABEL_ABUSIVE_HI_RCL_FLAG,
    TimelinesSharedFeatures.LABEL_DUP_CONTENT_FLAG -> TweetFeature.LABEL_DUP_CONTENT_FLAG,
    TimelinesSharedFeatures.LABEL_NSFW_HI_PRC_FLAG -> TweetFeature.LABEL_NSFW_HI_PRC_FLAG,
    TimelinesSharedFeatures.LABEL_NSFW_HI_RCL_FLAG -> TweetFeature.LABEL_NSFW_HI_RCL_FLAG,
    TimelinesSharedFeatures.LABEL_SPAM_FLAG -> TweetFeature.LABEL_SPAM_FLAG,
    TimelinesSharedFeatures.LABEL_SPAM_HI_RCL_FLAG -> TweetFeature.LABEL_SPAM_HI_RCL_FLAG
  )

  protected def derivedFeaturesAdder: ITransform =
    new ITransform {
      private val hasEnglishTweetDiffUiLangFeature =
        featureInstanceFromSearchResultFeature(ExternalTweetFeature.HAS_ENGLISH_TWEET_DIFF_UI_LANG)
          .asInstanceOf[Feature.Binary]
      private val hasEnglishUiDiffTweetLangFeature =
        featureInstanceFromSearchResultFeature(ExternalTweetFeature.HAS_ENGLISH_UI_DIFF_TWEET_LANG)
          .asInstanceOf[Feature.Binary]
      private val hasDiffLangFeature =
        featureInstanceFromSearchResultFeature(ExternalTweetFeature.HAS_DIFF_LANG)
          .asInstanceOf[Feature.Binary]
      private val isSelfTweetFeature =
        featureInstanceFromSearchResultFeature(ExternalTweetFeature.IS_SELF_TWEET)
          .asInstanceOf[Feature.Binary]
      private val tweetAgeInSecsFeature =
        featureInstanceFromSearchResultFeature(ExternalTweetFeature.TWEET_AGE_IN_SECS)
          .asInstanceOf[Feature.Continuous]
      private val authorSpecificScoreFeature =
        featureInstanceFromSearchResultFeature(ExternalTweetFeature.AUTHOR_SPECIFIC_SCORE)
          .asInstanceOf[Feature.Continuous]

      // see comments above
      private val linkLanguageFeature = new Feature.Continuous(TweetFeature.LINK_LANGUAGE.getName)
      private val languageFeature = new Feature.Continuous(TweetFeature.LANGUAGE.getName)

      override def transformContext(featureContext: FeatureContext): FeatureContext =
        featureContext.addFeatures(
          authorSpecificScoreFeature,
          // used when training against the full scoreEarlybirdModelEvaluationJob.scala
          // TimelinesSharedFeatures.PREDICTED_SCORE_LOG,
          hasEnglishTweetDiffUiLangFeature,
          hasEnglishUiDiffTweetLangFeature,
          hasDiffLangFeature,
          isSelfTweetFeature,
          tweetAgeInSecsFeature,
          linkLanguageFeature,
          languageFeature
        )

      override def transform(record: DataRecord): Unit = {
        val srecord = SRichDataRecord(record)

        srecord.getFeatureValueOpt(RealGraphDataRecordFeatures.WEIGHT).map { realgraphWeight =>
          srecord.setFeatureValue(authorSpecificScoreFeature, realgraphWeight)
        }

        // use this when training against the log of the full score
        // srecord.getFeatureValueOpt(TimelinesSharedFeatures.PREDICTED_SCORE).map { score =>
        //   if (score > 0.0) {
        //     srecord.setFeatureValue(TimelinesSharedFeatures.PREDICTED_SCORE_LOG, Math.log(score))
        //   }
        // }

        if (srecord.hasFeature(RequestContextFeatures.LANGUAGE_CODE) && srecord.hasFeature(
            RecapFeatures.LANGUAGE)) {
          val uilangIsEnglish = srecord
            .getFeatureValue(RequestContextFeatures.LANGUAGE_CODE).toString == "en"
          val tweetIsEnglish = srecord.getFeatureValue(RecapFeatures.LANGUAGE) == 5
          srecord.setFeatureValue(
            hasEnglishTweetDiffUiLangFeature,
            tweetIsEnglish && !uilangIsEnglish
          )
          srecord.setFeatureValue(
            hasEnglishUiDiffTweetLangFeature,
            uilangIsEnglish && !tweetIsEnglish
          )
        }
        srecord.getFeatureValueOpt(RecapFeatures.MATCH_UI_LANG).map { match_ui_lang =>
          srecord.setFeatureValue(
            hasDiffLangFeature,
            !match_ui_lang
          )
        }

        for {
          author_id <- srecord.getFeatureValueOpt(SharedFeatures.AUTHOR_ID)
          user_id <- srecord.getFeatureValueOpt(SharedFeatures.USER_ID)
        } srecord.setFeatureValue(
          isSelfTweetFeature,
          author_id == user_id
        )

        srecord.getFeatureValueOpt(TimeDataRecordFeatures.TIME_SINCE_TWEET_CREATION).map {
          time_since_tweet_creation =>
            srecord.setFeatureValue(
              tweetAgeInSecsFeature,
              time_since_tweet_creation / 1000.0
            )
        }

        srecord.getFeatureValueOpt(RecapFeatures.LINK_LANGUAGE).map { link_language =>
          srecord.setFeatureValue(
            linkLanguageFeature,
            link_language.toDouble
          )
        }
        srecord.getFeatureValueOpt(RecapFeatures.LANGUAGE).map { language =>
          srecord.setFeatureValue(
            languageFeature,
            language.toDouble
          )
        }
      }
    }

  protected def featureInstanceFromSearchResultFeature(
    tweetFeature: SearchResultFeature
  ): Feature[_] = {
    val featureType = tweetFeature.getType
    val featureName = tweetFeature.getName

    require(
      !tweetFeature.isDiscrete && (
        featureType == com.twitter.search.common.features.thrift.ThriftSearchFeatureType.BOOLEAN_VALUE ||
          featureType == com.twitter.search.common.features.thrift.ThriftSearchFeatureType.DOUBLE_VALUE ||
          featureType == com.twitter.search.common.features.thrift.ThriftSearchFeatureType.INT32_VALUE
      )
    )

    if (featureType == com.twitter.search.common.features.thrift.ThriftSearchFeatureType.BOOLEAN_VALUE)
      new Feature.Binary(featureName)
    else
      new Feature.Continuous(featureName)
  }

  lazy val EarlybirdFeatureRenamer: ITransform = {
    val earlybirdFeatureRenameMap: Map[Feature[_], Feature[_]] =
      featureToSearchResultFeatureMap.map {
        case (originalFeature, tweetFeature) =>
          originalFeature -> featureInstanceFromSearchResultFeature(tweetFeature)
      }.toMap

    new CascadeTransform(
      List(
        derivedFeaturesAdder,
        TransformFactory.produceTransform(
          TransformFactory.produceFeatureRenameTransformSpec(
            earlybirdFeatureRenameMap.asJava
          )
        )
      ).asJava
    )
  }
}
