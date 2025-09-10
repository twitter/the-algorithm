package com.twitter.home_mixer.product.scored_tweets.candidate_pipeline

import com.twitter.home_mixer.functional_component.feature_hydrator.TweetEntityServiceFeatureHydrator
import com.twitter.home_mixer.functional_component.gate.AllowForYouRecommendationsGate
import com.twitter.home_mixer.product.scored_tweets.filter.ExtendedDirectedAtFilter
import com.twitter.home_mixer.product.scored_tweets.filter.QualifiedRepliesFilter
import com.twitter.tweet_mixer.{thriftscala => t}
import com.twitter.home_mixer.product.scored_tweets.gate.MinCachedTweetsGate
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CachedScoredTweets
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FetchParams
import com.twitter.home_mixer.product.scored_tweets.response_transformer.ScoredTweetsTweetMixerResponseFeatureTransformer
import com.twitter.home_mixer.util.CachedScoredTweetsHelper
import com.twitter.product_mixer.component_library.candidate_source.tweet_mixer.TweetMixerCandidateSource
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.module.TestUserMapper
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.marshaller.request.ClientContextMarshaller
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.ClientContext
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Candidate Pipeline Config that fetches tweets from TweetMixer.
 */
@Singleton
class ScoredTweetsTweetMixerCandidatePipelineConfig @Inject() (
  tweetMixerCandidateSource: TweetMixerCandidateSource,
  tweetEntityServiceFeatureHydrator: TweetEntityServiceFeatureHydrator,
  testUserMapper: TestUserMapper)
    extends CandidatePipelineConfig[
      ScoredTweetsQuery,
      t.TweetMixerRequest,
      t.TweetResult,
      TweetCandidate
    ] {

  import ScoredTweetsTweetMixerCandidatePipelineConfig._
  override val identifier: CandidatePipelineIdentifier = Identifier

  override val gates: Seq[Gate[ScoredTweetsQuery]] = Seq(
    AllowForYouRecommendationsGate,
    MinCachedTweetsGate(identifier, CachedScoredTweets.MinCachedTweetsParam)
  )

  override val candidateSource: BaseCandidateSource[t.TweetMixerRequest, t.TweetResult] =
    tweetMixerCandidateSource

  def getClientContext(
    query: ScoredTweetsQuery
  ): ClientContext = {
    if (testUserMapper.isTestUser(query.clientContext)) testUserMapper(query.clientContext)
    else query.clientContext
  }

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ScoredTweetsQuery,
    t.TweetMixerRequest
  ] = { query =>
    val excludedTweetIds = query.features.map(
      CachedScoredTweetsHelper.tweetImpressionsAndCachedScoredTweets(_, identifier))

    t.TweetMixerRequest(
      clientContext = ClientContextMarshaller(getClientContext(query)),
      product = t.Product.HomeRecommendedTweets,
      productContext = Some(
        t.ProductContext.HomeRecommendedTweetsProductContext(
          t.HomeRecommendedTweetsProductContext(excludedTweetIds = excludedTweetIds.map(_.toSet)))),
      maxResults = Some(query.params(FetchParams.TweetMixerMaxTweetsToFetchParam))
    )
  }

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(tweetEntityServiceFeatureHydrator)

  override val filters: Seq[Filter[ScoredTweetsQuery, TweetCandidate]] = Seq(
    QualifiedRepliesFilter,
    ExtendedDirectedAtFilter
  )

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[t.TweetResult]
  ] = Seq(ScoredTweetsTweetMixerResponseFeatureTransformer())

  override val resultTransformer: CandidatePipelineResultsTransformer[
    t.TweetResult,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.tweetId) }
}

object ScoredTweetsTweetMixerCandidatePipelineConfig {
  val Identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ScoredTweetsTweetMixer")
}
