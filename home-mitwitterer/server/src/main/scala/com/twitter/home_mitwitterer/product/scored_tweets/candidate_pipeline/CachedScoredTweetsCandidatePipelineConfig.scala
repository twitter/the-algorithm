package com.twitter.home_mitwitterer.product.scored_tweets.candidate_pipeline

import com.twitter.home_mitwitterer.product.scored_tweets.candidate_pipeline.CachedScoredTweetsCandidatePipelineConfig._
import com.twitter.home_mitwitterer.product.scored_tweets.candidate_source.CachedScoredTweetsCandidateSource
import com.twitter.home_mitwitterer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mitwitterer.product.scored_tweets.response_transformer.CachedScoredTweetsResponseFeatureTransformer
import com.twitter.home_mitwitterer.{thriftscala => hmt}
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineConfig
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Candidate Pipeline Config that fetches tweets from Scored Tweets Cache.
 */
@Singleton
class CachedScoredTweetsCandidatePipelineConfig @Inject() (
  cachedScoredTweetsCandidateSource: CachedScoredTweetsCandidateSource)
    etwittertends CandidatePipelineConfig[
      ScoredTweetsQuery,
      ScoredTweetsQuery,
      hmt.ScoredTweet,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = Identifier

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
