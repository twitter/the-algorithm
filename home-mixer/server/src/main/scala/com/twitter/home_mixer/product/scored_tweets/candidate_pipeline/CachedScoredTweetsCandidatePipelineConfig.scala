package com.twitter.home_mixer.product.scored_tweets.candidate_pipeline

import com.twitter.home_mixer.model.HomeFeatures.CachedScoredTweetsFeature
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.CachedScoredTweetsCandidatePipelineConfig._
import com.twitter.home_mixer.product.scored_tweets.candidate_source.CachedScoredTweetsCandidateSource
import com.twitter.home_mixer.product.scored_tweets.gate.RecentFeedbackCheckGate
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.response_transformer.CachedScoredTweetsResponseFeatureTransformer
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.component_library.gate.NonEmptySeqFeatureGate
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Candidate Pipeline Config that fetches tweets from Scored Tweets Cache.
 */
@Singleton
class CachedScoredTweetsCandidatePipelineConfig @Inject() (
  cachedScoredTweetsCandidateSource: CachedScoredTweetsCandidateSource)
    extends CandidatePipelineConfig[
      ScoredTweetsQuery,
      ScoredTweetsQuery,
      hmt.ScoredTweet,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = Identifier

  override val gates: Seq[Gate[ScoredTweetsQuery]] = Seq(
    NonEmptySeqFeatureGate(CachedScoredTweetsFeature),
    RecentFeedbackCheckGate
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ScoredTweetsQuery,
    ScoredTweetsQuery
  ] = identity

  override val candidateSource: BaseCandidateSource[ScoredTweetsQuery, hmt.ScoredTweet] =
    cachedScoredTweetsCandidateSource

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[hmt.ScoredTweet]
  ] = Seq(CachedScoredTweetsResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    hmt.ScoredTweet,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.tweetId) }
}

object CachedScoredTweetsCandidatePipelineConfig {
  val Identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier("CachedScoredTweets")
}
