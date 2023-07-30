package com.X.home_mixer.product.scored_tweets.candidate_pipeline

import com.X.home_mixer.product.scored_tweets.feature_hydrator.IsExtendedReplyFeatureHydrator
import com.X.home_mixer.product.scored_tweets.feature_hydrator.ReplyFeatureHydrator
import com.X.home_mixer.product.scored_tweets.feature_hydrator.RetweetSourceTweetFeatureHydrator
import com.X.home_mixer.product.scored_tweets.filter.RetweetSourceTweetRemovingFilter
import com.X.home_mixer.product.scored_tweets.gate.MinCachedTweetsGate
import com.X.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.X.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CachedScoredTweets
import com.X.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CandidatePipeline
import com.X.home_mixer.product.scored_tweets.query_transformer.TimelineRankerInNetworkQueryTransformer
import com.X.home_mixer.product.scored_tweets.response_transformer.ScoredTweetsInNetworkResponseFeatureTransformer
import com.X.product_mixer.component_library.candidate_source.timeline_ranker.TimelineRankerInNetworkCandidateSource
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.X.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.X.product_mixer.core.functional_component.filter.Filter
import com.X.product_mixer.core.functional_component.gate.Gate
import com.X.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.X.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.X.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.X.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.X.timelineranker.{thriftscala => t}
import com.X.timelines.configapi.decider.DeciderParam
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Candidate Pipeline Config to fetch in-network tweets from Timeline Ranker's Recycled source
 */
@Singleton
class ScoredTweetsInNetworkCandidatePipelineConfig @Inject() (
  timelineRankerInNetworkCandidateSource: TimelineRankerInNetworkCandidateSource,
  replyFeatureHydrator: ReplyFeatureHydrator)
    extends CandidatePipelineConfig[
      ScoredTweetsQuery,
      t.RecapQuery,
      t.CandidateTweet,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ScoredTweetsInNetwork")

  override val enabledDeciderParam: Option[DeciderParam[Boolean]] =
    Some(CandidatePipeline.EnableInNetworkParam)

  override val gates: Seq[Gate[ScoredTweetsQuery]] = Seq(
    MinCachedTweetsGate(identifier, CachedScoredTweets.MinCachedTweetsParam)
  )

  override val candidateSource: BaseCandidateSource[t.RecapQuery, t.CandidateTweet] =
    timelineRankerInNetworkCandidateSource

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ScoredTweetsQuery,
    t.RecapQuery
  ] = TimelineRankerInNetworkQueryTransformer(identifier)

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[PipelineQuery, TweetCandidate, _]
  ] = Seq(RetweetSourceTweetFeatureHydrator)

  override def filters: Seq[Filter[ScoredTweetsQuery, TweetCandidate]] = Seq(
    RetweetSourceTweetRemovingFilter
  )

  override val postFilterFeatureHydration: Seq[
    BaseCandidateFeatureHydrator[PipelineQuery, TweetCandidate, _]
  ] = Seq(IsExtendedReplyFeatureHydrator, replyFeatureHydrator)

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[t.CandidateTweet]
  ] = Seq(ScoredTweetsInNetworkResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    t.CandidateTweet,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.tweet.get.id) }
}
