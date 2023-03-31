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
    CrMixerSource.EnableCandidatePipelineParam,
    FrsTweetSource.EnableCandidatePipelineParam,
    InNetworkSource.EnableCandidatePipelineParam,
    UtegSource.EnableCandidatePipelineParam,
    ScoredTweetsParam.EnableSimClustersSimilarityFeatureHydrationDeciderParam
  )

  override val boundedIntFSOverrides = Seq(
    CachedScoredTweets.MinCachedTweetsParam,
    QualityFactor.CrMixerMaxTweetsToScoreParam,
    QualityFactor.MaxTweetsToScoreParam,
    ServerMaxResultsParam
  )

  override val boundedDurationFSOverrides = Seq(
    CachedScoredTweets.TTLParam
  )

  override val stringFSOverrides = Seq(
    Scoring.HomeModelParam
  )

  override val boundedDoubleFSOverrides = Seq(
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
  )

  override val longSetFSOverrides = Seq(
    CompetitorSetParam,
  )

  override val stringSeqFSOverrides = Seq(
    CompetitorURLSeqParam
  )
}
