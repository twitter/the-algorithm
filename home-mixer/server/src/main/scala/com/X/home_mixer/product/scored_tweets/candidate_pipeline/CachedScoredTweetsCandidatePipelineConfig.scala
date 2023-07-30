package com.X.home_mixer.product.scored_tweets.candidate_pipeline

import com.X.home_mixer.product.scored_tweets.candidate_pipeline.CachedScoredTweetsCandidatePipelineConfig._
import com.X.home_mixer.product.scored_tweets.candidate_source.CachedScoredTweetsCandidateSource
import com.X.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.X.home_mixer.product.scored_tweets.response_transformer.CachedScoredTweetsResponseFeatureTransformer
import com.X.home_mixer.{thriftscala => hmt}
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.X.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.X.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.X.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.X.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.X.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
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
