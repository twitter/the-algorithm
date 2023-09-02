package com.twitter.home_mixer.product.scored_tweets.scorer

import com.twitter.dal.personal_data.{thriftjava => pd}
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.Scoring.ModelWeights
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.datarecord.DataRecordOptionalFeature
import com.twitter.product_mixer.core.feature.datarecord.DoubleDataRecordCompatible
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.prediction.features.recap.RecapFeatures

sealed trait PredictedScoreFeature
    extends DataRecordOptionalFeature[TweetCandidate, Double]
    with DoubleDataRecordCompatible {

  override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  def statName: String
  def modelWeightParam: FSBoundedParam[Double]
  def extractScore: FeatureMap => Option[Double] = _.getOrElse(this, None)
}

object PredictedFavoriteScoreFeature extends PredictedScoreFeature {
  override val featureName: String = RecapFeatures.PREDICTED_IS_FAVORITED.getFeatureName
  override val statName = "fav"
  override val modelWeightParam = ModelWeights.FavParam
}

object PredictedReplyScoreFeature extends PredictedScoreFeature {
  override val featureName: String = RecapFeatures.PREDICTED_IS_REPLIED.getFeatureName
  override val statName = "reply"
  override val modelWeightParam = ModelWeights.ReplyParam
}

object PredictedRetweetScoreFeature extends PredictedScoreFeature {
  override val featureName: String = RecapFeatures.PREDICTED_IS_RETWEETED.getFeatureName
  override val statName = "retweet"
  override val modelWeightParam = ModelWeights.RetweetParam
}

object PredictedReplyEngagedByAuthorScoreFeature extends PredictedScoreFeature {
  override val featureName: String =
    RecapFeatures.PREDICTED_IS_REPLIED_REPLY_ENGAGED_BY_AUTHOR.getFeatureName
  override val statName = "reply_engaged_by_author"
  override val modelWeightParam = ModelWeights.ReplyEngagedByAuthorParam
}

object PredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature extends PredictedScoreFeature {
  override val featureName: String = RecapFeatures.PREDICTED_IS_GOOD_CLICKED_V1.getFeatureName
  override val statName = "good_click_convo_desc_favorited_or_replied"
  override val modelWeightParam = ModelWeights.GoodClickParam

  override def extractScore: FeatureMap => Option[Double] = { featureMap =>
    val goodClickV1Opt = featureMap.getOrElse(this, None)
    val goodClickV2Opt = featureMap.getOrElse(PredictedGoodClickConvoDescUamGt2ScoreFeature, None)

    (goodClickV1Opt, goodClickV2Opt) match {
      case (Some(v1Score), Some(v2Score)) => Some(Math.max(v1Score, v2Score))
      case _ => goodClickV1Opt.orElse(goodClickV2Opt)
    }
  }
}

object PredictedGoodClickConvoDescUamGt2ScoreFeature extends PredictedScoreFeature {
  override val featureName: String = RecapFeatures.PREDICTED_IS_GOOD_CLICKED_V2.getFeatureName
  override val statName = "good_click_convo_desc_uam_gt_2"
  override val modelWeightParam = ModelWeights.GoodClickV2Param
}

object PredictedGoodProfileClickScoreFeature extends PredictedScoreFeature {
  override val featureName: String =
    RecapFeatures.PREDICTED_IS_PROFILE_CLICKED_AND_PROFILE_ENGAGED.getFeatureName
  override val statName = "good_profile_click"
  override val modelWeightParam = ModelWeights.GoodProfileClickParam
}

object PredictedVideoPlayback50ScoreFeature extends PredictedScoreFeature {
  override val featureName: String = RecapFeatures.PREDICTED_IS_VIDEO_PLAYBACK_50.getFeatureName
  override val statName = "video_playback_50"
  override val modelWeightParam = ModelWeights.VideoPlayback50Param
}

object PredictedTweetDetailDwellScoreFeature extends PredictedScoreFeature {
  override val featureName: String =
    RecapFeatures.PREDICTED_IS_TWEET_DETAIL_DWELLED_15_SEC.getFeatureName
  override val statName = "tweet_detail_dwell"
  override val modelWeightParam = ModelWeights.TweetDetailDwellParam
}

object PredictedProfileDwelledScoreFeature extends PredictedScoreFeature {
  override val featureName: String =
    RecapFeatures.PREDICTED_IS_PROFILE_DWELLED_20_SEC.getFeatureName
  override val statName = "profile_dwell"
  override val modelWeightParam = ModelWeights.ProfileDwelledParam
}

object PredictedBookmarkScoreFeature extends PredictedScoreFeature {
  override val featureName: String = RecapFeatures.PREDICTED_IS_BOOKMARKED.getFeatureName
  override val statName = "bookmark"
  override val modelWeightParam = ModelWeights.BookmarkParam
}

object PredictedShareScoreFeature extends PredictedScoreFeature {
  override val featureName: String =
    RecapFeatures.PREDICTED_IS_SHARED.getFeatureName
  override val statName = "share"
  override val modelWeightParam = ModelWeights.ShareParam
}

object PredictedShareMenuClickScoreFeature extends PredictedScoreFeature {
  override val featureName: String =
    RecapFeatures.PREDICTED_IS_SHARE_MENU_CLICKED.getFeatureName
  override val statName = "share_menu_click"
  override val modelWeightParam = ModelWeights.ShareMenuClickParam
}

// Negative Engagements
object PredictedNegativeFeedbackV2ScoreFeature extends PredictedScoreFeature {
  override val featureName: String =
    RecapFeatures.PREDICTED_IS_NEGATIVE_FEEDBACK_V2.getFeatureName
  override val statName = "negative_feedback_v2"
  override val modelWeightParam = ModelWeights.NegativeFeedbackV2Param
}

object PredictedReportedScoreFeature extends PredictedScoreFeature {
  override val featureName: String =
    RecapFeatures.PREDICTED_IS_REPORT_TWEET_CLICKED.getFeatureName
  override val statName = "reported"
  override val modelWeightParam = ModelWeights.ReportParam
}

object PredictedStrongNegativeFeedbackScoreFeature extends PredictedScoreFeature {
  override val featureName: String =
    RecapFeatures.PREDICTED_IS_STRONG_NEGATIVE_FEEDBACK.getFeatureName
  override val statName = "strong_negative_feedback"
  override val modelWeightParam = ModelWeights.StrongNegativeFeedbackParam
}

object PredictedWeakNegativeFeedbackScoreFeature extends PredictedScoreFeature {
  override val featureName: String =
    RecapFeatures.PREDICTED_IS_WEAK_NEGATIVE_FEEDBACK.getFeatureName
  override val statName = "weak_negative_feedback"
  override val modelWeightParam = ModelWeights.WeakNegativeFeedbackParam
}

object PredictedScoreFeature {
  val PredictedScoreFeatures: Set[PredictedScoreFeature] = Set(
    PredictedFavoriteScoreFeature,
    PredictedReplyScoreFeature,
    PredictedRetweetScoreFeature,
    PredictedReplyEngagedByAuthorScoreFeature,
    PredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature,
    PredictedGoodClickConvoDescUamGt2ScoreFeature,
    PredictedGoodProfileClickScoreFeature,
    PredictedVideoPlayback50ScoreFeature,
    PredictedTweetDetailDwellScoreFeature,
    PredictedProfileDwelledScoreFeature,
    PredictedBookmarkScoreFeature,
    PredictedShareScoreFeature,
    PredictedShareMenuClickScoreFeature,
    // Negative Engagements
    PredictedNegativeFeedbackV2ScoreFeature,
    PredictedReportedScoreFeature,
    PredictedStrongNegativeFeedbackScoreFeature,
    PredictedWeakNegativeFeedbackScoreFeature,
  )
}
