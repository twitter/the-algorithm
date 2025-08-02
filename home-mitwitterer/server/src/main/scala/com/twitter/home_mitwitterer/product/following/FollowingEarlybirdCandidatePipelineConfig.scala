package com.twitter.home_mitwitterer.product.following

import com.twitter.home_mitwitterer.candidate_pipeline.FollowingEarlybirdResponseFeatureTransformer
import com.twitter.home_mitwitterer.functional_component.candidate_source.EarlybirdCandidateSource
import com.twitter.home_mitwitterer.product.following.model.FollowingQuery
import com.twitter.product_mitwitterer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.twitter.product_mitwitterer.component_library.gate.NonEmptySeqFeatureGate
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.search.earlybird.{thriftscala => t}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class FollowingEarlybirdCandidatePipelineConfig @Inject() (
  earlybirdCandidateSource: EarlybirdCandidateSource,
  followingEarlybirdQueryTransformer: FollowingEarlybirdQueryTransformer)
    etwittertends CandidatePipelineConfig[
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
