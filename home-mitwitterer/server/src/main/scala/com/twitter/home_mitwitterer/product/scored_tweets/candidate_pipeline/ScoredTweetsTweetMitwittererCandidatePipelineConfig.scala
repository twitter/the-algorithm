package com.twitter.home_mitwitterer.product.scored_tweets.candidate_pipeline

import com.twitter.tweet_mitwitterer.{thriftscala => t}
import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.TweetypieStaticEntitiesFeatureHydrator
import com.twitter.home_mitwitterer.product.scored_tweets.gate.MinCachedTweetsGate
import com.twitter.home_mitwitterer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mitwitterer.product.scored_tweets.param.ScoredTweetsParam.CachedScoredTweets
import com.twitter.home_mitwitterer.product.scored_tweets.param.ScoredTweetsParam.CandidatePipeline
import com.twitter.home_mitwitterer.product.scored_tweets.response_transformer.ScoredTweetsTweetMitwittererResponseFeatureTransformer
import com.twitter.home_mitwitterer.util.CachedScoredTweetsHelper
import com.twitter.product_mitwitterer.component_library.candidate_source.tweet_mitwitterer.TweetMitwittererCandidateSource
import com.twitter.product_mitwitterer.component_library.filter.PredicateFeatureFilter
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.functional_component.marshaller.request.ClientContetwittertMarshaller
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.decider.DeciderParam

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Candidate Pipeline Config that fetches tweets from TweetMitwitterer.
 */
@Singleton
class ScoredTweetsTweetMitwittererCandidatePipelineConfig @Inject() (
  tweetMitwittererCandidateSource: TweetMitwittererCandidateSource,
  tweetypieStaticEntitiesFeatureHydrator: TweetypieStaticEntitiesFeatureHydrator)
    etwittertends CandidatePipelineConfig[
      ScoredTweetsQuery,
      t.TweetMitwittererRequest,
      t.TweetResult,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ScoredTweetsTweetMitwitterer")

  val HasAuthorFilterId = "HasAuthor"

  override val enabledDeciderParam: Option[DeciderParam[Boolean]] =
    Some(CandidatePipeline.EnableTweetMitwittererParam)

  override val gates: Seq[Gate[ScoredTweetsQuery]] = Seq(
    MinCachedTweetsGate(identifier, CachedScoredTweets.MinCachedTweetsParam),
  )

  override val candidateSource: BaseCandidateSource[t.TweetMitwittererRequest, t.TweetResult] =
    tweetMitwittererCandidateSource

  private val MatwitterTweetsToFetch = 400

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ScoredTweetsQuery,
    t.TweetMitwittererRequest
  ] = { query =>
    val matwitterCount = (query.getQualityFactorCurrentValue(identifier) * MatwitterTweetsToFetch).toInt

    val etwittercludedTweetIds = query.features.map(
      CachedScoredTweetsHelper.tweetImpressionsAndCachedScoredTweets(_, identifier))

    t.TweetMitwittererRequest(
      clientContetwittert = ClientContetwittertMarshaller(query.clientContetwittert),
      product = t.Product.HomeRecommendedTweets,
      productContetwittert = Some(
        t.ProductContetwittert.HomeRecommendedTweetsProductContetwittert(
          t.HomeRecommendedTweetsProductContetwittert(etwittercludedTweetIds = etwittercludedTweetIds.map(_.toSet)))),
      matwitterResults = Some(matwitterCount)
    )
  }

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(tweetypieStaticEntitiesFeatureHydrator)

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[t.TweetResult]
  ] = Seq(ScoredTweetsTweetMitwittererResponseFeatureTransformer)

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
