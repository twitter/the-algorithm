package com.twitter.home_mixer.product.scored_tweets.param

import com.twitter.home_mixer.param.decider.DeciderKey
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam._
import com.twitter.product_mixer.core.product.ProductParamConfig
import com.twitter.servo.decider.DeciderKeyName
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoredTweetsParamConfig @Inject() () extends ProductParamConfig {
  override val enabledDeciderKey: DeciderKeyName = DeciderKey.EnableScoredTweetsProduct
  override val supportedClientFSName: String = SupportedClientFSName

  override val booleanDeciderOverrides = Seq(
    CandidatePipeline.EnableBackfillParam,
    CandidatePipeline.EnableTweetMixerParam,
    CandidatePipeline.EnableFrsParam,
    CandidatePipeline.EnableInNetworkParam,
    CandidatePipeline.EnableListsParam,
    CandidatePipeline.EnablePopularVideosParam,
    CandidatePipeline.EnableUtegParam,
    ScoredTweetsParam.EnableSimClustersSimilarityFeatureHydrationDeciderParam
  )

  override val booleanFSOverrides = Seq(
    EnableBackfillCandidatePipelineParam,
    EnableScribeScoredCandidatesParam
  )

  override val boundedIntFSOverrides = Seq(
    CachedScoredTweets.MinCachedTweetsParam,
    MaxInNetworkResultsParam,
    MaxOutOfNetworkResultsParam,
    QualityFactor.BackfillMaxTweetsToScoreParam,
    QualityFactor.TweetMixerMaxTweetsToScoreParam,
    QualityFactor.FrsMaxTweetsToScoreParam,
    QualityFactor.InNetworkMaxTweetsToScoreParam,
    QualityFactor.ListsMaxTweetsToScoreParam,
    QualityFactor.PopularVideosMaxTweetsToScoreParam,
    QualityFactor.UtegMaxTweetsToScoreParam,
    ServerMaxResultsParam
  )

  override val boundedDurationFSOverrides = Seq(
    CachedScoredTweets.TTLParam
  )

  override val stringFSOverrides = Seq(
    Scoring.HomeModelParam,
    EarlybirdTensorflowModel.InNetworkParam,
    EarlybirdTensorflowModel.FrsParam,
    EarlybirdTensorflowModel.UtegParam
  )

  override val boundedDoubleFSOverrides = Seq(
    BlueVerifiedAuthorInNetworkMultiplierParam,
    BlueVerifiedAuthorOutOfNetworkMultiplierParam,
    CreatorInNetworkMultiplierParam,
    CreatorOutOfNetworkMultiplierParam,
    OutOfNetworkScaleFactorParam,
    // Model Weights
    Scoring.ModelWeights.FavParam,
    Scoring.ModelWeights.ReplyParam,
    Scoring.ModelWeights.RetweetParam,
    Scoring.ModelWeights.GoodClickParam,
    Scoring.ModelWeights.GoodClickV2Param,
    Scoring.ModelWeights.GoodProfileClickParam,
    Scoring.ModelWeights.ReplyEngagedByAuthorParam,
    Scoring.ModelWeights.VideoPlayback50Param,
    Scoring.ModelWeights.ReportParam,
    Scoring.ModelWeights.NegativeFeedbackV2Param,
    Scoring.ModelWeights.TweetDetailDwellParam,
    Scoring.ModelWeights.ProfileDwelledParam,
    Scoring.ModelWeights.BookmarkParam,
    Scoring.ModelWeights.ShareParam,
    Scoring.ModelWeights.ShareMenuClickParam,
    Scoring.ModelWeights.StrongNegativeFeedbackParam,
    Scoring.ModelWeights.WeakNegativeFeedbackParam
  )

  override val longSetFSOverrides = Seq(
    CompetitorSetParam
  )

  override val stringSeqFSOverrides = Seq(
    CompetitorURLSeqParam
  )
}
