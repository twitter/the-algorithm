package com.twitter.home_mitwitterer.product.scored_tweets.candidate_pipeline

import com.twitter.etwitterplore_ranker.{thriftscala => ert}
import com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.TweetypieStaticEntitiesFeatureHydrator
import com.twitter.home_mitwitterer.product.scored_tweets.gate.MinCachedTweetsGate
import com.twitter.home_mitwitterer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mitwitterer.product.scored_tweets.param.ScoredTweetsParam.CachedScoredTweets
import com.twitter.home_mitwitterer.product.scored_tweets.param.ScoredTweetsParam.CandidatePipeline
import com.twitter.home_mitwitterer.product.scored_tweets.response_transformer.ScoredTweetsPopularVideosResponseFeatureTransformer
import com.twitter.home_mitwitterer.util.CachedScoredTweetsHelper
import com.twitter.product_mitwitterer.component_library.candidate_source.etwitterplore_ranker.EtwitterploreRankerImmersiveRecsCandidateSource
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.functional_component.marshaller.request.ClientContetwittertMarshaller
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.decider.DeciderParam
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ScoredTweetsPopularVideosCandidatePipelineConfig @Inject() (
  etwitterploreRankerCandidateSource: EtwitterploreRankerImmersiveRecsCandidateSource,
  tweetypieStaticEntitiesFeatureHydrator: TweetypieStaticEntitiesFeatureHydrator)
    etwittertends CandidatePipelineConfig[
      ScoredTweetsQuery,
      ert.EtwitterploreRankerRequest,
      ert.EtwitterploreTweetRecommendation,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ScoredTweetsPopularVideos")

  private val MatwitterTweetsToFetch = 40

  override val enabledDeciderParam: Option[DeciderParam[Boolean]] =
    Some(CandidatePipeline.EnablePopularVideosParam)

  override val gates: Seq[Gate[ScoredTweetsQuery]] = Seq(
    MinCachedTweetsGate(identifier, CachedScoredTweets.MinCachedTweetsParam)
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ScoredTweetsQuery,
    ert.EtwitterploreRankerRequest
  ] = { query =>
    val etwittercludedTweetIds = query.features.map(
      CachedScoredTweetsHelper.tweetImpressionsAndCachedScoredTweets(_, identifier))

    ert.EtwitterploreRankerRequest(
      clientContetwittert = ClientContetwittertMarshaller(query.clientContetwittert),
      product = ert.Product.HomeTimelineVideoInline,
      productContetwittert = Some(
        ert.ProductContetwittert.HomeTimelineVideoInline(ert.HomeTimelineVideoInline(etwittercludedTweetIds))),
      matwitterResults = Some(MatwitterTweetsToFetch)
    )
  }

  override def candidateSource: BaseCandidateSource[
    ert.EtwitterploreRankerRequest,
    ert.EtwitterploreTweetRecommendation
  ] = etwitterploreRankerCandidateSource

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[ert.EtwitterploreTweetRecommendation]
  ] = Seq(ScoredTweetsPopularVideosResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    ert.EtwitterploreTweetRecommendation,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.tweetId) }

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(tweetypieStaticEntitiesFeatureHydrator)
}
