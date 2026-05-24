package com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.earlybird

import com.twitter.home_mixer.functional_component.feature_hydrator.TweetEntityServiceFeatureHydrator
import com.twitter.home_mixer.model.HomeFeatures.CachedScoredTweetsFeature
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CandidateSourceParams
import com.twitter.home_mixer.product.scored_tweets.query_transformer.earlybird.CommunitiesEarlybirdQueryTransformer
import com.twitter.home_mixer.product.scored_tweets.response_transformer.earlybird.ScoredTweetsCommunitiesResponseFeatureTransformer
import com.twitter.product_mixer.component_library.candidate_source.earlybird.EarlybirdTweetCandidateSource
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.communities.CommunityNamesFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.communities.CommunityMembershipsFeature
import com.twitter.product_mixer.component_library.gate.EmptySeqFeatureGate
import com.twitter.product_mixer.component_library.gate.NonEmptySeqFeatureGate
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.search.earlybird.{thriftscala => t}
import com.twitter.timelines.configapi.FSParam
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoredTweetsCommunitiesCandidatePipelineConfig @Inject() (
  earlybirdTweetCandidateSource: EarlybirdTweetCandidateSource,
  communityNamesFeatureHydrator: CommunityNamesFeatureHydrator,
  communitiesEarlybirdQueryTransformer: CommunitiesEarlybirdQueryTransformer,
  tweetEntityServiceFeatureHydrator: TweetEntityServiceFeatureHydrator)
    extends CandidatePipelineConfig[
      ScoredTweetsQuery,
      t.EarlybirdRequest,
      t.ThriftSearchResult,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ScoredTweetsCommunities")

  override val supportedClientParam: Option[FSParam[Boolean]] =
    Some(CandidateSourceParams.EnableCommunitiesCandidateSourceParam)

  override val gates: Seq[Gate[ScoredTweetsQuery]] = Seq(
    NonEmptySeqFeatureGate(CommunityMembershipsFeature),
    EmptySeqFeatureGate(CachedScoredTweetsFeature)
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ScoredTweetsQuery,
    t.EarlybirdRequest
  ] = communitiesEarlybirdQueryTransformer

  override def candidateSource: BaseCandidateSource[t.EarlybirdRequest, t.ThriftSearchResult] =
    earlybirdTweetCandidateSource

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[t.ThriftSearchResult]
  ] = Seq(ScoredTweetsCommunitiesResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    t.ThriftSearchResult,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.id) }

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(tweetEntityServiceFeatureHydrator)

  override val postFilterFeatureHydration: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(communityNamesFeatureHydrator)
}
