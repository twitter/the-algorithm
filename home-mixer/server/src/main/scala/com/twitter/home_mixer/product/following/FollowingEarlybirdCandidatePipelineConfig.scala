package com.twitter.home_mixer.product.following

import com.twitter.home_mixer.candidate_pipeline.FollowingEarlybirdResponseFeatureTransformer
import com.twitter.home_mixer.functional_component.gate.RateLimitGate
import com.twitter.home_mixer.product.following.model.FollowingQuery
import com.twitter.product_mixer.component_library.candidate_source.earlybird.EarlybirdTweetCandidateSource
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.twitter.product_mixer.component_library.gate.NonEmptySeqFeatureGate
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.search.earlybird.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowingEarlybirdCandidatePipelineConfig @Inject() (
  earlybirdTweetCandidateSource: EarlybirdTweetCandidateSource,
  followingEarlybirdQueryTransformer: FollowingEarlybirdQueryTransformer)
    extends CandidatePipelineConfig[
      FollowingQuery,
      t.EarlybirdRequest,
      t.ThriftSearchResult,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("FollowingEarlybird")

  override val candidateSource: BaseCandidateSource[t.EarlybirdRequest, t.ThriftSearchResult] =
    earlybirdTweetCandidateSource

  override val gates: Seq[Gate[FollowingQuery]] = Seq(
    RateLimitGate,
    NonEmptySeqFeatureGate(SGSFollowedUsersFeature)
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    FollowingQuery,
    t.EarlybirdRequest
  ] = followingEarlybirdQueryTransformer

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[t.ThriftSearchResult]
  ] = Seq(FollowingEarlybirdResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    t.ThriftSearchResult,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.id) }
}
