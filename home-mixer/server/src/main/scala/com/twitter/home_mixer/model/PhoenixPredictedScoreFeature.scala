package com.twitter.home_mixer.model

import com.twitter.home_mixer.model.HomeFeatures.HasVideoFeature
import com.twitter.home_mixer.model.HomeFeatures.VideoDurationMsFeature
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.ModelWeights
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.UseProdInPhoenixParams
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.x.user_action_sequence.ActionName
import com.x.user_action_sequence.ActionName._

sealed trait PhoenixPredictedScoreFeature extends Feature[TweetCandidate, Option[Double]] {
  def featureName: String
  def modelWeightParam: FSBoundedParam[Double]
  def actions: Seq[ActionName]
  def isEligible(features: FeatureMap): Boolean = true
  def prodScoreFeature: PredictedScoreFeature
  def useProdFeatureParam: FSParam[Boolean]
  def extractScore(features: FeatureMap, query: PipelineQuery): Option[Double] = {
    if (query.params(useProdFeatureParam))
      prodScoreFeature.extractScore(features, query)
    else features.getOrElse(this, None).orElse(prodScoreFeature.extractScore(features, query))
  }
}

object PhoenixPredictedFavoriteScoreFeature extends PhoenixPredictedScoreFeature {
  override val featureName = "fav"
  override val modelWeightParam = ModelWeights.FavParam
  override val actions = Seq(SERVER_TWEET_FAV)

  override def prodScoreFeature = PredictedFavoriteScoreFeature
  override def useProdFeatureParam = UseProdInPhoenixParams.EnableProdFavForPhoenixParam
}

object PhoenixPredictedReplyScoreFeature extends PhoenixPredictedScoreFeature {
  override val featureName = "reply"
  override val modelWeightParam = ModelWeights.ReplyParam
  override val actions = Seq(SERVER_TWEET_REPLY)

  override def prodScoreFeature = PredictedReplyScoreFeature
  override def useProdFeatureParam = UseProdInPhoenixParams.EnableProdReplyForPhoenixParam
}

object PhoenixPredictedRetweetScoreFeature extends PhoenixPredictedScoreFeature {
  override val featureName = "retweet"
  override val modelWeightParam = ModelWeights.RetweetParam
  override val actions = Seq(SERVER_TWEET_QUOTE, SERVER_TWEET_RETWEET)

  override def prodScoreFeature = PredictedRetweetScoreFeature
  override def useProdFeatureParam = UseProdInPhoenixParams.EnableProdRetweetForPhoenixParam
}

object PhoenixPredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature
    extends PhoenixPredictedScoreFeature {
  override val featureName = "click_engage"
  override val modelWeightParam = ModelWeights.GoodClickV1Param
  override val actions = Seq(CLIENT_TWEET_PHOTO_EXPAND)

  override def prodScoreFeature = PredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature
  override def useProdFeatureParam = UseProdInPhoenixParams.EnableProdGoodClickV1ForPhoenixParam
}

object PhoenixPredictedGoodClickConvoDescUamGt2ScoreFeature extends PhoenixPredictedScoreFeature {
  override val featureName = "click_dwell"
  override val modelWeightParam = ModelWeights.GoodClickV2Param
  override val actions = Seq(CLIENT_TWEET_CLICK)

  override def prodScoreFeature = PredictedGoodClickConvoDescUamGt2ScoreFeature
  override def useProdFeatureParam = UseProdInPhoenixParams.EnableProdGoodClickV2ForPhoenixParam
}

object PhoenixPredictedGoodProfileClickScoreFeature extends PhoenixPredictedScoreFeature {
  override val featureName = "good_profile_click"
  override val modelWeightParam = ModelWeights.GoodProfileClickParam
  override val actions = Seq(CLIENT_TWEET_CLICK_PROFILE)

  override def prodScoreFeature = PredictedGoodProfileClickScoreFeature
  override def useProdFeatureParam = UseProdInPhoenixParams.EnableProdProfileClickForPhoenixParam
}

object PhoenixPredictedVideoQualityViewScoreFeature extends PhoenixPredictedScoreFeature {
  override val featureName = "vqv"
  override val modelWeightParam = ModelWeights.VideoQualityViewParam
  override val actions = Seq(CLIENT_TWEET_VIDEO_QUALITY_VIEW)

  override def isEligible(features: FeatureMap): Boolean = {
    val isVideoDurationGte10Seconds =
      (features.getOrElse(VideoDurationMsFeature, None).getOrElse(0) / 1000.0) >= 10
    val hasVideoFeature = features.getOrElse(HasVideoFeature, false)
    hasVideoFeature && isVideoDurationGte10Seconds
  }
  override def prodScoreFeature = PredictedVideoQualityViewScoreFeature
  override def useProdFeatureParam = UseProdInPhoenixParams.EnableProdVQVForPhoenixParam
}

object PhoenixPredictedShareScoreFeature extends PhoenixPredictedScoreFeature {
  override val featureName = "share"
  override val modelWeightParam = ModelWeights.ShareParam
  override val actions = Seq(
    CLIENT_TWEET_SHARE_VIA_COPY_LINK,
    CLIENT_TWEET_CLICK_SEND_VIA_DIRECT_MESSAGE,
    CLIENT_TWEET_SHARE
  )

  override def prodScoreFeature = PredictedShareScoreFeature
  override def useProdFeatureParam = UseProdInPhoenixParams.EnableProdShareForPhoenixParam
}

object PhoenixPredictedDwellScoreFeature extends PhoenixPredictedScoreFeature {
  override val featureName = "dwell"
  override val modelWeightParam = ModelWeights.DwellParam
  override val actions = Seq(CLIENT_TWEET_RECAP_DWELLED)

  override def isEligible(features: FeatureMap): Boolean = {
    val isVideoDurationGte10Seconds =
      (features.getOrElse(VideoDurationMsFeature, None).getOrElse(0) / 1000.0) >= 10
    val hasVideoFeature = features.getOrElse(HasVideoFeature, false)
    !(hasVideoFeature && isVideoDurationGte10Seconds)
  }
  override def prodScoreFeature = PredictedDwellScoreFeature
  override def useProdFeatureParam = UseProdInPhoenixParams.EnableProdDwellForPhoenixParam
}

object PhoenixPredictedOpenLinkScoreFeature extends PhoenixPredictedScoreFeature {
  override val featureName = "open_link"
  override val modelWeightParam = ModelWeights.OpenLinkParam
  override val actions = Seq(CLIENT_TWEET_OPEN_LINK)

  // Placeholder prod score feature. This should not be used.
  override def prodScoreFeature = PredictedShareScoreFeature
  override def useProdFeatureParam = UseProdInPhoenixParams.EnableProdOpenLinkForPhoenixParam
}

object PhoenixPredictedScreenshotScoreFeature extends PhoenixPredictedScoreFeature {
  override val featureName = "screenshot"
  override val modelWeightParam = ModelWeights.OpenLinkParam
  override val actions = Seq(CLIENT_TWEET_TAKE_SCREENSHOT)

  // Placeholder prod score feature. This should not be used.
  override def prodScoreFeature = PredictedShareScoreFeature
  override def useProdFeatureParam = UseProdInPhoenixParams.EnableProdScreenshotForPhoenixParam
}

object PhoenixPredictedBookmarkScoreFeature extends PhoenixPredictedScoreFeature {
  override val featureName = "bookmark"
  override val modelWeightParam = ModelWeights.BookmarkParam
  override val actions = Seq(CLIENT_TWEET_BOOKMARK)

  // Placeholder prod score feature. This should not be used.
  override def prodScoreFeature = PredictedBookmarkScoreFeature
  override def useProdFeatureParam = UseProdInPhoenixParams.EnableProdBookmarkForPhoenixParam
}

// Negative Engagements
object PhoenixPredictedNegativeFeedbackV2ScoreFeature extends PhoenixPredictedScoreFeature {
  override val featureName = "negative_feedback_v2"
  override val modelWeightParam = ModelWeights.NegativeFeedbackV2Param
  override val actions = Seq(
    CLIENT_TWEET_NOT_INTERESTED_IN,
    CLIENT_TWEET_BLOCK_AUTHOR,
    CLIENT_TWEET_MUTE_AUTHOR,
    CLIENT_TWEET_REPORT,
  )

  override def prodScoreFeature = PredictedNegativeFeedbackV2ScoreFeature
  override def useProdFeatureParam = UseProdInPhoenixParams.EnableProdNegForPhoenixParam
}

object PhoenixPredictedScoreFeature {
  val PhoenixPredictedScoreFeatures: Seq[PhoenixPredictedScoreFeature] = Seq(
    PhoenixPredictedFavoriteScoreFeature,
    PhoenixPredictedReplyScoreFeature,
    PhoenixPredictedRetweetScoreFeature,
    PhoenixPredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature,
    PhoenixPredictedGoodClickConvoDescUamGt2ScoreFeature,
    PhoenixPredictedGoodProfileClickScoreFeature,
    PhoenixPredictedVideoQualityViewScoreFeature,
    PhoenixPredictedShareScoreFeature,
    PhoenixPredictedDwellScoreFeature,
    PhoenixPredictedOpenLinkScoreFeature,
    PhoenixPredictedScreenshotScoreFeature,
    PhoenixPredictedBookmarkScoreFeature,
    // Negative Engagements
    PhoenixPredictedNegativeFeedbackV2ScoreFeature,
  )

  val PhoenixPredictedScoreFeatureSet: Set[PhoenixPredictedScoreFeature] =
    PhoenixPredictedScoreFeatures.toSet
}
