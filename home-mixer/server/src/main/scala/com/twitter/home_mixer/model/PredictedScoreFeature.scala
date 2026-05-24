package com.twitter.home_mixer.model

import com.twitter.dal.personal_data.thriftjava.PersonalDataType
import com.twitter.dal.personal_data.{thriftjava => pd}
import com.twitter.home_mixer.model.HomeFeatures.HasVideoFeature
import com.twitter.home_mixer.model.HomeFeatures.VideoDurationMsFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnableImmersiveVQV
import com.twitter.home_mixer.param.HomeGlobalParams.EnableTenSecondsLogicForVQV
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.ScoreThresholdForVQVParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.EnableBinarySchemeForVQVParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.BinarySchemeConstantForVQVParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.ScoreThresholdForDwellParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.EnableDwellOrVQVParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.EnableBinarySchemeForDwellParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.ModelBiases
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.ModelDebiases
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.ModelWeights
import com.twitter.ml.api.DataType
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.datarecord.DataRecordOptionalFeature
import com.twitter.product_mixer.core.feature.datarecord.DoubleDataRecordCompatible
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.prediction.features.recap.RecapFeatures
import com.twitter.ml.api.thriftscala.GeneralTensor
import com.twitter.product_mixer.core.feature.datarecord.GeneralTensorDataRecordCompatible

sealed trait PredictedScoreFeature
    extends DataRecordOptionalFeature[TweetCandidate, Double]
    with DoubleDataRecordCompatible {

  override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty

  // Note: Determine whether the score prediction should contribute to the weighted score
  // Currently, it is being used for PredictedVideoQualityViewScoreFeature
  def isEligible(
    features: FeatureMap,
    query: PipelineQuery
  ): Boolean = true
  def statName: String
  def modelWeightParam: FSBoundedParam[Double]
  def modelBiasParam: Option[FSBoundedParam[Double]] = None
  def modelDebiasParam: Option[FSBoundedParam[Double]] = None
  def extractScore(features: FeatureMap, query: PipelineQuery): Option[Double] =
    features.getOrElse(this, None)

  def weightQueryFeatureName: String
  lazy val weightQueryFeature: Feature[PipelineQuery, Option[Double]] =
    PredictedScoreFeature.getDataRecordFeatureFromName(weightQueryFeatureName)
  def biasQueryFeatureName: Option[String] = None
  lazy val biasQueryFeature: Option[Feature[PipelineQuery, Option[Double]]] =
    biasQueryFeatureName.map(PredictedScoreFeature.getDataRecordFeatureFromName)

  def debiasQueryFeatureName: Option[String] = None

  lazy val debiasQueryFeature: Option[Feature[PipelineQuery, Option[GeneralTensor]]] =
    debiasQueryFeatureName.map(PredictedScoreFeature.getGeneralTensorFeatureFromName)
}

object PredictedFavoriteScoreFeature extends PredictedScoreFeature {
  override val featureName: String = RecapFeatures.PREDICTED_IS_FAVORITED.getFeatureName
  override val statName = "fav"
  override val modelWeightParam = ModelWeights.FavParam
  override val weightQueryFeatureName = RecapFeatures.WEIGHT_IS_FAVORITED.getFeatureName
  override val modelDebiasParam = Some(ModelDebiases.FavParam)
  override val debiasQueryFeatureName = Some(RecapFeatures.DEBIAS_IS_FAVORITED.getFeatureName)
}

object PredictedReplyScoreFeature extends PredictedScoreFeature {
  override val featureName: String = RecapFeatures.PREDICTED_IS_REPLIED.getFeatureName
  override val statName = "reply"
  override val modelWeightParam = ModelWeights.ReplyParam
  override val weightQueryFeatureName = RecapFeatures.WEIGHT_IS_REPLIED.getFeatureName
  override val modelDebiasParam = Some(ModelDebiases.ReplyParam)
  override val debiasQueryFeatureName = Some(RecapFeatures.DEBIAS_IS_REPLIED.getFeatureName)
}

object PredictedRetweetScoreFeature extends PredictedScoreFeature {
  override val featureName: String = RecapFeatures.PREDICTED_IS_RETWEETED.getFeatureName
  override val statName = "retweet"
  override val modelWeightParam = ModelWeights.RetweetParam
  override val weightQueryFeatureName = RecapFeatures.WEIGHT_IS_RETWEETED.getFeatureName
  override val modelDebiasParam = Some(ModelDebiases.RetweetParam)
  override val debiasQueryFeatureName = Some(RecapFeatures.DEBIAS_IS_RETWEETED.getFeatureName)
}

object PredictedReplyEngagedByAuthorScoreFeature extends PredictedScoreFeature {
  override val featureName: String =
    RecapFeatures.PREDICTED_IS_REPLIED_REPLY_ENGAGED_BY_AUTHOR.getFeatureName
  override val statName = "reply_engaged_by_author"
  override val modelWeightParam = ModelWeights.ReplyEngagedByAuthorParam
  override val weightQueryFeatureName =
    RecapFeatures.WEIGHT_IS_REPLIED_REPLY_ENGAGED_BY_AUTHOR.getFeatureName
  override val modelDebiasParam = Some(ModelDebiases.ReplyEngagedByAuthorParam)
  override val debiasQueryFeatureName =
    Some(RecapFeatures.DEBIAS_IS_REPLIED_REPLY_ENGAGED_BY_AUTHOR.getFeatureName)
}

object PredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature extends PredictedScoreFeature {
  override val featureName: String = RecapFeatures.PREDICTED_IS_GOOD_CLICKED_V1.getFeatureName
  override val statName = "click_engaged" // click_convo_desc_favorited_or_replied
  override val modelWeightParam = ModelWeights.GoodClickV1Param
  override val weightQueryFeatureName = RecapFeatures.WEIGHT_IS_GOOD_CLICKED_V1.getFeatureName
  override val modelDebiasParam = Some(ModelDebiases.GoodClickV1Param)
  override val debiasQueryFeatureName = Some(RecapFeatures.DEBIAS_IS_GOOD_CLICKED_V1.getFeatureName)
}

object PredictedGoodClickConvoDescUamGt2ScoreFeature extends PredictedScoreFeature {
  override val featureName: String = RecapFeatures.PREDICTED_IS_GOOD_CLICKED_V2.getFeatureName
  override val statName = "click_dwell" // good_click_convo_desc_uam_gt_2
  override val modelWeightParam = ModelWeights.GoodClickV2Param
  override val weightQueryFeatureName = RecapFeatures.WEIGHT_IS_GOOD_CLICKED_V2.getFeatureName
  override val modelDebiasParam = Some(ModelDebiases.GoodClickV2Param)
  override val debiasQueryFeatureName = Some(RecapFeatures.DEBIAS_IS_GOOD_CLICKED_V2.getFeatureName)
}

object PredictedGoodProfileClickScoreFeature extends PredictedScoreFeature {
  override val featureName: String =
    RecapFeatures.PREDICTED_IS_PROFILE_CLICKED_AND_PROFILE_ENGAGED.getFeatureName
  override val statName = "good_profile_click"
  override val modelWeightParam = ModelWeights.GoodProfileClickParam
  override val weightQueryFeatureName =
    RecapFeatures.WEIGHT_IS_PROFILE_CLICKED_AND_PROFILE_ENGAGED.getFeatureName
  override val modelDebiasParam = Some(ModelDebiases.GoodProfileClickParam)
  override val debiasQueryFeatureName =
    Some(RecapFeatures.DEBIAS_IS_PROFILE_CLICKED_AND_PROFILE_ENGAGED.getFeatureName)
}

object PredictedVideoQualityViewImmersiveScoreFeature extends PredictedScoreFeature {
  override def isEligible(
    features: FeatureMap,
    query: PipelineQuery
  ): Boolean = {
    query.params(EnableImmersiveVQV)
  }
  override val featureName: String =
    RecapFeatures.PREDICTED_IS_VIDEO_QUALITY_VIEWED_IMMERSIVE.getFeatureName
  override val statName = "vqv_immersive" // video_quality_viewed_immersive
  override val modelWeightParam = ModelWeights.VideoQualityViewImmersiveParam
  override val weightQueryFeatureName =
    RecapFeatures.WEIGHT_IS_VIDEO_QUALITY_VIEWED_IMMERSIVE.getFeatureName
  override val modelBiasParam = Some(ModelBiases.VideoQualityViewImmersiveParam)
  override val biasQueryFeatureName = Some(
    RecapFeatures.BIAS_IS_VIDEO_QUALITY_VIEWED_IMMERSIVE.getFeatureName)
  override val modelDebiasParam = Some(ModelDebiases.VideoQualityViewImmersiveParam)
  override val debiasQueryFeatureName =
    Some(RecapFeatures.DEBIAS_IS_VIDEO_QUALITY_VIEWED_IMMERSIVE.getFeatureName)

}

object PredictedVideoQualityViewScoreFeature extends PredictedScoreFeature {
  override def isEligible(
    features: FeatureMap,
    query: PipelineQuery
  ): Boolean = {
    val isTenSecondsLogicEnabled = query.params(EnableTenSecondsLogicForVQV)
    val isVideoDurationGte10Seconds =
      (features.getOrElse(VideoDurationMsFeature, None).getOrElse(0) / 1000.0) >= 10
    val hasVideoFeature = features.getOrElse(HasVideoFeature, false)

    hasVideoFeature && (!isTenSecondsLogicEnabled || isVideoDurationGte10Seconds)
  }
  override val featureName: String = RecapFeatures.PREDICTED_IS_VIDEO_QUALITY_VIEWED.getFeatureName
  override val statName = "vqv" // video_quality_viewed
  override val modelWeightParam = ModelWeights.VideoQualityViewParam
  override val weightQueryFeatureName = RecapFeatures.WEIGHT_IS_VIDEO_QUALITY_VIEWED.getFeatureName
  override val modelBiasParam = Some(ModelBiases.VideoQualityViewParam)
  override val biasQueryFeatureName = Some(
    RecapFeatures.BIAS_IS_VIDEO_QUALITY_VIEWED.getFeatureName)
  override val modelDebiasParam = Some(ModelDebiases.VideoQualityViewParam)
  override val debiasQueryFeatureName = Some(
    RecapFeatures.DEBIAS_IS_VIDEO_QUALITY_VIEWED.getFeatureName)

  override def extractScore(features: FeatureMap, query: PipelineQuery): Some[Double] = {
    // For VQV, if the score is below a threshold, we return 0
    val vqvScore = features.getOrElse(this, None).getOrElse(0.0)
    if (vqvScore < query.params(ScoreThresholdForVQVParam)) {
      // the default threshold is 0.0, vqvScore should be always non-negative
      Some(0.0)
    } else if (query.params(EnableBinarySchemeForVQVParam)) {
      // If the binary scheme is enabled, we return a constant
      Some(query.params(BinarySchemeConstantForVQVParam))
    } else {
      Some(vqvScore)
    }
  }

}

object PredictedBookmarkScoreFeature extends PredictedScoreFeature {
  override val featureName: String = RecapFeatures.PREDICTED_IS_BOOKMARKED.getFeatureName
  override val statName = "bookmark"
  override val modelWeightParam = ModelWeights.BookmarkParam
  override val weightQueryFeatureName = RecapFeatures.WEIGHT_IS_BOOKMARKED.getFeatureName
  override val modelDebiasParam = Some(ModelDebiases.BookmarkParam)
  override val debiasQueryFeatureName = Some(RecapFeatures.DEBIAS_IS_BOOKMARKED.getFeatureName)
}

object PredictedShareScoreFeature extends PredictedScoreFeature {
  override val featureName: String =
    RecapFeatures.PREDICTED_IS_SHARED.getFeatureName
  override val statName = "share"
  override val modelWeightParam = ModelWeights.ShareParam
  override val weightQueryFeatureName = RecapFeatures.WEIGHT_IS_SHARED.getFeatureName
  override val modelDebiasParam = Some(ModelDebiases.ShareParam)
  override val debiasQueryFeatureName = Some(RecapFeatures.DEBIAS_IS_SHARED.getFeatureName)
}

object PredictedDwellScoreFeature extends PredictedScoreFeature {
  override val featureName: String =
    RecapFeatures.PREDICTED_IS_DWELLED.getFeatureName
  override val statName = "dwell"
  override val modelWeightParam = ModelWeights.DwellParam
  override val weightQueryFeatureName = RecapFeatures.WEIGHT_IS_DWELLED.getFeatureName
  override val modelDebiasParam = Some(ModelDebiases.DwellParam)
  override val debiasQueryFeatureName = Some(RecapFeatures.DEBIAS_IS_DWELLED.getFeatureName)

  override def isEligible(
    features: FeatureMap,
    query: PipelineQuery
  ): Boolean = {
    val isTenSecondsLogicEnabled = query.params(EnableTenSecondsLogicForVQV)
    val isVideoDurationGte10Seconds =
      (features.getOrElse(VideoDurationMsFeature, None).getOrElse(0) / 1000.0) >= 10
    val hasVideoFeature = features.getOrElse(HasVideoFeature, false)

    val isEligibleForVqv =
      hasVideoFeature && (!isTenSecondsLogicEnabled || isVideoDurationGte10Seconds)
    !(query.params(EnableDwellOrVQVParam) && isEligibleForVqv)
  }

  override def extractScore(features: FeatureMap, query: PipelineQuery): Some[Double] = {
    // For Dwell, if the score is below a threshold, we return 0
    val dwellScore = features.getOrElse(this, None).getOrElse(0.0)
    if (dwellScore < query.params(ScoreThresholdForDwellParam)) {
      // the default threshold is 0.0, dwellScore should be always non-negative
      Some(0.0)
    } else if (query.params(EnableBinarySchemeForDwellParam)) {
      // If the binary scheme is enabled, we return a constant
      Some(query.params(ScoreThresholdForDwellParam))
    } else {
      Some(dwellScore)
    }
  }
}

object PredictedVideoWatchTimeScoreFeature extends PredictedScoreFeature {
  override val featureName: String =
    RecapFeatures.PREDICTED_VIDEO_WATCH_TIME_MS.getFeatureName
  override val statName = "video_watch_time_ms"
  override val modelWeightParam = ModelWeights.VideoWatchTimeMsParam
  override val weightQueryFeatureName = RecapFeatures.WEIGHT_VIDEO_WATCH_TIME_MS.getFeatureName
  override val modelDebiasParam = Some(ModelDebiases.VideoWatchTimeMsParam)
  override val debiasQueryFeatureName = Some(
    RecapFeatures.DEBIAS_VIDEO_WATCH_TIME_MS.getFeatureName)
}

// Negative Engagements
object PredictedNegativeFeedbackV2ScoreFeature extends PredictedScoreFeature {
  override val featureName: String =
    RecapFeatures.PREDICTED_IS_NEGATIVE_FEEDBACK_V2.getFeatureName
  override val statName = "negative_feedback_v2"
  override val modelWeightParam = ModelWeights.NegativeFeedbackV2Param
  override val weightQueryFeatureName = RecapFeatures.WEIGHT_IS_NEGATIVE_FEEDBACK_V2.getFeatureName
  override val modelDebiasParam = Some(ModelDebiases.NegativeFeedbackV2Param)
  override val debiasQueryFeatureName = Some(
    RecapFeatures.DEBIAS_IS_NEGATIVE_FEEDBACK_V2.getFeatureName)
}

object PredictedVideoQualityWatchScoreFeature extends PredictedScoreFeature {
  override def isEligible(
    features: FeatureMap,
    query: PipelineQuery
  ) = {
    features.getOrElse(HasVideoFeature, false) && (features
      .getOrElse(VideoDurationMsFeature, None).getOrElse(0) / 1000.0) >= 10
  }
  override val featureName: String =
    RecapFeatures.PREDICTED_IS_VIDEO_QUALITY_WATCH.getFeatureName
  override val statName = "video_quality_watched"
  override val modelWeightParam = ModelWeights.VideoQualityWatchParam
  override val weightQueryFeatureName = RecapFeatures.WEIGHT_IS_VIDEO_QUALITY_WATCHED.getFeatureName
  override val modelBiasParam = Some(ModelBiases.VideoQualityWatchParam)
  override val biasQueryFeatureName = Some(
    RecapFeatures.BIAS_IS_VIDEO_QUALITY_WATCHED.getFeatureName)
  override val modelDebiasParam = Some(ModelDebiases.VideoQualityWatchParam)
  override val debiasQueryFeatureName = Some(
    RecapFeatures.DEBIAS_IS_VIDEO_QUALITY_WATCHED.getFeatureName)
}

object PredictedScoreFeature {
  val PredictedScoreFeatures: Seq[PredictedScoreFeature] = Seq(
    PredictedFavoriteScoreFeature,
    PredictedReplyScoreFeature,
    PredictedRetweetScoreFeature,
    PredictedReplyEngagedByAuthorScoreFeature,
    PredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature,
    PredictedGoodClickConvoDescUamGt2ScoreFeature,
    PredictedGoodProfileClickScoreFeature,
    PredictedVideoQualityViewScoreFeature,
    PredictedVideoQualityViewImmersiveScoreFeature,
    PredictedBookmarkScoreFeature,
    PredictedShareScoreFeature,
    PredictedDwellScoreFeature,
    PredictedVideoQualityWatchScoreFeature,
    PredictedVideoWatchTimeScoreFeature,
    // Negative Engagements
    PredictedNegativeFeedbackV2ScoreFeature,
  )

  val PredictedScoreFeatureSet: Set[PredictedScoreFeature] = PredictedScoreFeatures.toSet

  def getGeneralTensorFeatureFromName(
    name: String
  ): Feature[PipelineQuery, Option[GeneralTensor]] = {
    new DataRecordOptionalFeature[PipelineQuery, GeneralTensor]
      with GeneralTensorDataRecordCompatible {
      override val featureName: String = name
      override val personalDataTypes: Set[PersonalDataType] = Set.empty
      override def toString: String = name
      override def dataType: DataType = DataType.DOUBLE
    }
  }

  def getDataRecordFeatureFromName(
    name: String
  ): Feature[PipelineQuery, Option[Double]] = {
    new DataRecordOptionalFeature[PipelineQuery, Double] with DoubleDataRecordCompatible {
      override val featureName: String = name
      override val personalDataTypes: Set[PersonalDataType] = Set.empty
      override def toString: String = name
    }
  }
}
