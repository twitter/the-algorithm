package com.twitter.home_mixer.product.scored_tweets.candidate_pipeline

import com.twitter.cr_mixer.{thriftscala => t}
import com.twitter.home_mixer.functional_component.feature_hydrator.TweetypieStaticEntitiesFeatureHydrator
import com.twitter.home_mixer.functional_component.filter.PredicateFeatureFilter
import com.twitter.home_mixer.functional_component.gate.MinCachedTweetsGate
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CachedScoredTweets
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CrMixerSource
import com.twitter.home_mixer.product.scored_tweets.response_transformer.ScoredTweetsCrMixerResponseFeatureTransformer
import com.twitter.home_mixer.util.CachedScoredTweetsHelper
import com.twitter.product_mixer.component_library.candidate_source.cr_mixer.CrMixerTweetRecommendationsCandidateSource
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
 * Candidate Pipeline Config that fetches tweets from CrMixer.
 */
@Singleton
class ScoredTweetsCrMixerCandidatePipelineConfig @Inject() (
  crMixerTweetRecommendationsCandidateSource: CrMixerTweetRecommendationsCandidateSource,
  tweetypieStaticEntitiesFeatureHydrator: TweetypieStaticEntitiesFeatureHydrator)
    extends CandidatePipelineConfig[
      ScoredTweetsQuery,
      t.CrMixerTweetRequest,
      t.TweetRecommendation,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ScoredTweetsCrMixer")

  val HasAuthorFilterId = "HasAuthor"

  override val enabledDeciderParam: Option[DeciderParam[Boolean]] =
    Some(CrMixerSource.EnableCandidatePipelineParam)

  override val gates: Seq[Gate[ScoredTweetsQuery]] = Seq(
    MinCachedTweetsGate(identifier, CachedScoredTweets.MinCachedTweetsParam)
  )

  override val candidateSource: BaseCandidateSource[t.CrMixerTweetRequest, t.TweetRecommendation] =
    crMixerTweetRecommendationsCandidateSource

  private val MaxTweetsToFetch = 500

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ScoredTweetsQuery,
    t.CrMixerTweetRequest
  ] = { query =>
    val maxCount = (query.getQualityFactorCurrentValue(identifier) * MaxTweetsToFetch).toInt

    val excludedTweetIds = query.features.map(
      CachedScoredTweetsHelper.tweetImpressionsAndCachedScoredTweets(_, identifier))

    t.CrMixerTweetRequest(
      clientContext = ClientContextMarshaller(query.clientContext),
      product = t.Product.Home,
      productContext =
        Some(t.ProductContext.HomeContext(t.HomeContext(maxResults = Some(maxCount)))),
      excludedTweetIds = excludedTweetIds
    )
  }

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(tweetypieStaticEntitiesFeatureHydrator)

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[t.TweetRecommendation]
  ] = Seq(ScoredTweetsCrMixerResponseFeatureTransformer)

  override val filters: Seq[Filter[ScoredTweetsQuery, TweetCandidate]] = Seq(
    PredicateFeatureFilter.fromPredicate(
      FilterIdentifier(HasAuthorFilterId),
      shouldKeepCandidate = _.getOrElse(AuthorIdFeature, None).isDefined
    )
  )

  override val resultTransformer: CandidatePipelineResultsTransformer[
    t.TweetRecommendation,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.tweetId) }
}
