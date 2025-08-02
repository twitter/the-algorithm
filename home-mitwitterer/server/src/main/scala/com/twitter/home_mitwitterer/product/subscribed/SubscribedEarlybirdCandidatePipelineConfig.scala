package com.twitter.home_mitwitterer.product.subscribed

import com.google.inject.Inject
import com.twitter.home_mitwitterer.functional_component.candidate_source.EarlybirdCandidateSource
import com.twitter.home_mitwitterer.product.subscribed.model.SubscribedQuery
import com.twitter.product_mitwitterer.component_library.feature_hydrator.query.social_graph.SGSSubscribedUsersFeature
import com.twitter.product_mitwitterer.component_library.filter.TweetVisibilityFilter
import com.twitter.product_mitwitterer.component_library.gate.NonEmptySeqFeatureGate
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.search.earlybird.{thriftscala => t}
import com.twitter.spam.rtf.thriftscala.SafetyLevel.TimelineHomeSubscribed
import com.twitter.stitch.tweetypie.{TweetyPie => TweetypieStitchClient}
import com.twitter.tweetypie.thriftscala.TweetVisibilityPolicy

class SubscribedEarlybirdCandidatePipelineConfig @Inject() (
  earlybirdCandidateSource: EarlybirdCandidateSource,
  tweetyPieStitchClient: TweetypieStitchClient,
  subscribedEarlybirdQueryTransformer: SubscribedEarlybirdQueryTransformer)
    etwittertends CandidatePipelineConfig[
      SubscribedQuery,
      t.EarlybirdRequest,
      t.ThriftSearchResult,
      TweetCandidate
    ] {
  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("SubscribedEarlybird")

  override val candidateSource: BaseCandidateSource[t.EarlybirdRequest, t.ThriftSearchResult] =
    earlybirdCandidateSource

  override val gates: Seq[Gate[SubscribedQuery]] = Seq(
    NonEmptySeqFeatureGate(SGSSubscribedUsersFeature)
  )

  override def filters: Seq[Filter[SubscribedQuery, TweetCandidate]] = Seq(
    new TweetVisibilityFilter(
      tweetyPieStitchClient,
      TweetVisibilityPolicy.UserVisible,
      TimelineHomeSubscribed
    )
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    SubscribedQuery,
    t.EarlybirdRequest
  ] = subscribedEarlybirdQueryTransformer

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[t.ThriftSearchResult]
  ] = Seq(SubscribedEarlybirdResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    t.ThriftSearchResult,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.id) }
}
