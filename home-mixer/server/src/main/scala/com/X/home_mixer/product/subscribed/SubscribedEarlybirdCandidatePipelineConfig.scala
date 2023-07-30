package com.X.home_mixer.product.subscribed

import com.google.inject.Inject
import com.X.home_mixer.functional_component.candidate_source.EarlybirdCandidateSource
import com.X.home_mixer.product.subscribed.model.SubscribedQuery
import com.X.product_mixer.component_library.feature_hydrator.query.social_graph.SGSSubscribedUsersFeature
import com.X.product_mixer.component_library.filter.TweetVisibilityFilter
import com.X.product_mixer.component_library.gate.NonEmptySeqFeatureGate
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.X.product_mixer.core.functional_component.filter.Filter
import com.X.product_mixer.core.functional_component.gate.Gate
import com.X.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.X.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.X.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.X.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.X.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.X.search.earlybird.{thriftscala => t}
import com.X.spam.rtf.thriftscala.SafetyLevel.TimelineHomeSubscribed
import com.X.stitch.tweetypie.{TweetyPie => TweetypieStitchClient}
import com.X.tweetypie.thriftscala.TweetVisibilityPolicy

class SubscribedEarlybirdCandidatePipelineConfig @Inject() (
  earlybirdCandidateSource: EarlybirdCandidateSource,
  tweetyPieStitchClient: TweetypieStitchClient,
  subscribedEarlybirdQueryTransformer: SubscribedEarlybirdQueryTransformer)
    extends CandidatePipelineConfig[
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
