package com.twitter.home_mitwitterer.product.scored_tweets.candidate_pipeline

import com.twitter.home_mitwitterer.product.scored_tweets.gate.MinCachedTweetsGate
import com.twitter.home_mitwitterer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mitwitterer.product.scored_tweets.param.ScoredTweetsParam.CachedScoredTweets
import com.twitter.home_mitwitterer.product.scored_tweets.param.ScoredTweetsParam.CandidatePipeline
import com.twitter.home_mitwitterer.product.scored_tweets.query_transformer.TimelineRankerUtegQueryTransformer
import com.twitter.home_mitwitterer.product.scored_tweets.response_transformer.ScoredTweetsUtegResponseFeatureTransformer
import com.twitter.product_mitwitterer.component_library.candidate_source.timeline_ranker.TimelineRankerUtegCandidateSource
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelineranker.{thriftscala => t}
import com.twitter.timelines.configapi.decider.DeciderParam
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Candidate Pipeline Config that fetches tweets from the Timeline Ranker UTEG Candidate Source
 */
@Singleton
class ScoredTweetsUtegCandidatePipelineConfig @Inject() (
  timelineRankerUtegCandidateSource: TimelineRankerUtegCandidateSource)
    etwittertends CandidatePipelineConfig[
      ScoredTweetsQuery,
      t.UtegLikedByTweetsQuery,
      t.CandidateTweet,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ScoredTweetsUteg")

  override val enabledDeciderParam: Option[DeciderParam[Boolean]] =
    Some(CandidatePipeline.EnableUtegParam)

  override val gates: Seq[Gate[ScoredTweetsQuery]] = Seq(
    MinCachedTweetsGate(identifier, CachedScoredTweets.MinCachedTweetsParam)
  )

  override val candidateSource: BaseCandidateSource[t.UtegLikedByTweetsQuery, t.CandidateTweet] =
    timelineRankerUtegCandidateSource

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ScoredTweetsQuery,
    t.UtegLikedByTweetsQuery
  ] = TimelineRankerUtegQueryTransformer(identifier)

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[t.CandidateTweet]
  ] = Seq(ScoredTweetsUtegResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    t.CandidateTweet,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.tweet.get.id) }
}
