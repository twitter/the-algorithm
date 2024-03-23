package com.ExTwitter.home_mixer.product.following

import com.ExTwitter.home_mixer.candidate_pipeline.FollowingEarlybirdResponseFeatureTransformer
import com.ExTwitter.home_mixer.functional_component.candidate_source.EarlybirdCandidateSource
import com.ExTwitter.home_mixer.product.following.model.FollowingQuery
import com.ExTwitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.ExTwitter.product_mixer.component_library.gate.NonEmptySeqFeatureGate
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.ExTwitter.product_mixer.core.functional_component.gate.Gate
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.ExTwitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.ExTwitter.search.earlybird.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowingEarlybirdCandidatePipelineConfig @Inject() (
  earlybirdCandidateSource: EarlybirdCandidateSource,
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
    earlybirdCandidateSource

  override val gates: Seq[Gate[FollowingQuery]] = Seq(
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
