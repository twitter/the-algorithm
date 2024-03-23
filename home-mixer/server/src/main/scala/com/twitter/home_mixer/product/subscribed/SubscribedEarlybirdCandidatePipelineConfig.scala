package com.ExTwitter.home_mixer.product.subscribed

import com.google.inject.Inject
import com.ExTwitter.home_mixer.functional_component.candidate_source.EarlybirdCandidateSource
import com.ExTwitter.home_mixer.product.subscribed.model.SubscribedQuery
import com.ExTwitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSSubscribedUsersFeature
import com.ExTwitter.product_mixer.component_library.filter.TweetVisibilityFilter
import com.ExTwitter.product_mixer.component_library.gate.NonEmptySeqFeatureGate
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.ExTwitter.product_mixer.core.functional_component.filter.Filter
import com.ExTwitter.product_mixer.core.functional_component.gate.Gate
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.ExTwitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.ExTwitter.search.earlybird.{thriftscala => t}
import com.ExTwitter.spam.rtf.thriftscala.SafetyLevel.TimelineHomeSubscribed
import com.ExTwitter.stitch.tweetypie.{TweetyPie => TweetypieStitchClient}
import com.ExTwitter.tweetypie.thriftscala.TweetVisibilityPolicy

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
