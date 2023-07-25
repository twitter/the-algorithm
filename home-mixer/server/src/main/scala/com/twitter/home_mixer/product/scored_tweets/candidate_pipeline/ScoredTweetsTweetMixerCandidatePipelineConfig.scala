package com.twitter.home_mixer.product.scored_tweets.candidate_pipeline

import com.twitter.tweet_mixer.{thriftscala => t}
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.TweetypieStaticEntitiesFeatureHydrator
import com.twitter.home_mixer.product.scored_tweets.gate.MinCachedTweetsGate
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CachedScoredTweets
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CandidatePipeline
import com.twitter.home_mixer.product.scored_tweets.response_transformer.ScoredTweetsTweetMixerResponseFeatureTransformer
import com.twitter.home_mixer.util.CachedScoredTweetsHelper
import com.twitter.product_mixer.component_library.candidate_source.tweet_mixer.TweetMixerCandidateSource
import com.twitter.product_mixer.component_library.filter.PredicateFeatureFilter
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.marshaller.request.ClientContextMarshaller
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.decider.DeciderParam

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Candidate Pipeline Config that fetches tweets from TweetMixer.
 */
@Singleton
class ScoredTweetsTweetMixerCandidatePipelineConfig @Inject() (
  tweetMixerCandidateSource: TweetMixerCandidateSource,
  tweetypieStaticEntitiesFeatureHydrator: TweetypieStaticEntitiesFeatureHydrator)
    extends CandidatePipelineConfig[
      ScoredTweetsQuery,
      t.TweetMixerRequest,
      t.TweetResult,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ScoredTweetsTweetMixer")

  val HasAuthorFilterId = "HasAuthor"

  override val enabledDeciderParam: Option[DeciderParam[Boolean]] =
    Some(CandidatePipeline.EnableTweetMixerParam)

  override val gates: Seq[Gate[ScoredTweetsQuery]] = Seq(
    MinCachedTweetsGate(identifier, CachedScoredTweets.MinCachedTweetsParam),
  )

  override val candidateSource: BaseCandidateSource[t.TweetMixerRequest, t.TweetResult] =
    tweetMixerCandidateSource

  private val MaxTweetsToFetch = 400

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ScoredTweetsQuery,
    t.TweetMixerRequest
  ] = { query =>
    val maxCount = (query.getQualityFactorCurrentValue(identifier) * MaxTweetsToFetch).toInt

    val excludedTweetIds = query.features.map(
      CachedScoredTweetsHelper.tweetImpressionsAndCachedScoredTweets(_, identifier))

    t.TweetMixerRequest(
      clientContext = ClientContextMarshaller(query.clientContext),
      product = t.Product.HomeRecommendedTweets,
      productContext = Some(
        t.ProductContext.HomeRecommendedTweetsProductContext(
          t.HomeRecommendedTweetsProductContext(excludedTweetIds = excludedTweetIds.map(_.toSet)))),
      maxResults = Some(maxCount)
    )
  }

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(tweetypieStaticEntitiesFeatureHydrator)

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[t.TweetResult]
  ] = Seq(ScoredTweetsTweetMixerResponseFeatureTransformer)

  override val filters: Seq[Filter[ScoredTweetsQuery, TweetCandidate]] = Seq(
    PredicateFeatureFilter.fromPredicate(
      FilterIdentifier(HasAuthorFilterId),
      shouldKeepCandidate = _.getOrElse(AuthorIdFeature, None).isDefined
    )
  )

  override val resultTransformer: CandidatePipelineResultsTransformer[
    t.TweetResult,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.tweetId) }
}
