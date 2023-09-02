package com.twitter.home_mixer.product.scored_tweets.candidate_pipeline

import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.IsExtendedReplyFeatureHydrator
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.ReplyFeatureHydrator
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.RetweetSourceTweetFeatureHydrator
import com.twitter.home_mixer.product.scored_tweets.filter.RetweetSourceTweetRemovingFilter
import com.twitter.home_mixer.product.scored_tweets.gate.MinCachedTweetsGate
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CachedScoredTweets
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CandidatePipeline
import com.twitter.home_mixer.product.scored_tweets.query_transformer.TimelineRankerInNetworkQueryTransformer
import com.twitter.home_mixer.product.scored_tweets.response_transformer.ScoredTweetsInNetworkResponseFeatureTransformer
import com.twitter.product_mixer.component_library.candidate_source.timeline_ranker.TimelineRankerInNetworkCandidateSource
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelineranker.{thriftscala => t}
import com.twitter.timelines.configapi.decider.DeciderParam
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
