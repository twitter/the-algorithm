package com.twitter.home_mixer.product.scored_tweets.candidate_pipeline

import com.twitter.home_mixer.functional_component.feature_hydrator.EarlybirdSearchResultFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.TweetEntityServiceFeatureHydrator
import com.twitter.home_mixer.functional_component.gate.AllowForYouRecommendationsGate
import com.twitter.home_mixer.model.HomeFeatures.CachedScoredTweetsFeature
import com.twitter.home_mixer.product.scored_tweets.filter.FollowedAuthorFilter
import com.twitter.home_mixer.product.scored_tweets.filter.OONReplyFilter
import com.twitter.home_mixer.product.scored_tweets.filter.UtegMinFavCountFilter
import com.twitter.home_mixer.product.scored_tweets.filter.UtegTopKFilter
import com.twitter.home_mixer.product.scored_tweets.gate.DenyLowSignalUserGate
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CandidateSourceParams
import com.twitter.home_mixer.product.scored_tweets.query_transformer.UtegQueryTransformer
import com.twitter.home_mixer.product.scored_tweets.response_transformer.ScoredTweetsDirectUtegResponseFeatureTransformer
import com.twitter.product_mixer.component_library.candidate_source.uteg.UtegCandidateSource
import com.twitter.product_mixer.component_library.gate.EmptySeqFeatureGate
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.recos.user_tweet_entity_graph.{thriftscala => uteg}
import com.twitter.timelines.configapi.FSParam
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoredTweetsDirectUtegCandidatePipelineConfig @Inject() (
  utegCandidateSource: UtegCandidateSource,
  tweetEntityServiceFeatureHydrator: TweetEntityServiceFeatureHydrator,
  earlybirdSearchResultFeatureHydrator: EarlybirdSearchResultFeatureHydrator)
    extends CandidatePipelineConfig[
      ScoredTweetsQuery,
      uteg.RecommendTweetEntityRequest,
      uteg.TweetRecommendation,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ScoredTweetsDirectUteg")

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(
    CandidateSourceParams.EnableUTEGCandidateSourceParam)

  override val gates: Seq[Gate[ScoredTweetsQuery]] = Seq(
    AllowForYouRecommendationsGate,
    DenyLowSignalUserGate,
    EmptySeqFeatureGate(CachedScoredTweetsFeature)
  )

  override val candidateSource: BaseCandidateSource[
    uteg.RecommendTweetEntityRequest,
    uteg.TweetRecommendation
  ] = utegCandidateSource

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ScoredTweetsQuery,
    uteg.RecommendTweetEntityRequest
  ] = UtegQueryTransformer(identifier)

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(
    tweetEntityServiceFeatureHydrator,
    earlybirdSearchResultFeatureHydrator
  )

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[uteg.TweetRecommendation]
  ] = Seq(ScoredTweetsDirectUtegResponseFeatureTransformer)

  override val filters: Seq[Filter[ScoredTweetsQuery, TweetCandidate]] = Seq(
    FollowedAuthorFilter,
    UtegMinFavCountFilter,
    OONReplyFilter,
    UtegTopKFilter,
  )

  override val resultTransformer: CandidatePipelineResultsTransformer[
    uteg.TweetRecommendation,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.tweetId) }
}
