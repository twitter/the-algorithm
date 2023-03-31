package com.twitter.home_mixer.candidate_pipeline

import com.twitter.home_mixer.functional_component.feature_hydrator.NamesFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.SocialGraphServiceFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.TweetypieFeatureHydrator
import com.twitter.home_mixer.functional_component.filter.InvalidConversationModuleFilter
import com.twitter.home_mixer.functional_component.filter.PredicateFeatureFilter
import com.twitter.home_mixer.functional_component.filter.RetweetDeduplicationFilter
import com.twitter.home_mixer.model.HomeFeatures.IsHydratedFeature
import com.twitter.home_mixer.model.HomeFeatures.QuotedTweetDroppedFeature
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.product_mixer.component_library.candidate_source.tweetconvosvc.ConversationServiceCandidateSource
import com.twitter.product_mixer.component_library.candidate_source.tweetconvosvc.ConversationServiceCandidateSourceRequest
import com.twitter.product_mixer.component_library.candidate_source.tweetconvosvc.TweetWithConversationMetadata
import com.twitter.product_mixer.component_library.filter.FeatureFilter
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.gate.BaseGate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.functional_component.transformer.DependentCandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.DependentCandidatePipelineConfig

/**
 * Candidate Pipeline Config that fetches tweets from the Conversation Service Candidate Source
 */
class ConversationServiceCandidatePipelineConfig[Query <: PipelineQuery](
  conversationServiceCandidateSource: ConversationServiceCandidateSource,
  tweetypieFeatureHydrator: TweetypieFeatureHydrator,
  socialGraphServiceFeatureHydrator: SocialGraphServiceFeatureHydrator,
  namesFeatureHydrator: NamesFeatureHydrator,
  override val gates: Seq[BaseGate[Query]],
  override val decorator: Option[CandidateDecorator[Query, TweetCandidate]])
    extends DependentCandidatePipelineConfig[
      Query,
      ConversationServiceCandidateSourceRequest,
      TweetWithConversationMetadata,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ConversationService")

  private val TweetypieHydratedFilterId = "TweetypieHydrated"
  private val QuotedTweetDroppedFilterId = "QuotedTweetDropped"

  override val candidateSource: BaseCandidateSource[
    ConversationServiceCandidateSourceRequest,
    TweetWithConversationMetadata
  ] = conversationServiceCandidateSource

  override val queryTransformer: DependentCandidatePipelineQueryTransformer[
    Query,
    ConversationServiceCandidateSourceRequest
  ] = { (_, candidates) =>
    val tweetsWithConversationMetadata = candidates.map { candidate =>
      TweetWithConversationMetadata(
        tweetId = candidate.candidateIdLong,
        userId = None,
        sourceTweetId = None,
        sourceUserId = None,
        inReplyToTweetId = None,
        conversationId = None,
        ancestors = Seq.empty
      )
    }
    ConversationServiceCandidateSourceRequest(tweetsWithConversationMetadata)
  }

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[TweetWithConversationMetadata]
  ] = Seq(ConversationServiceResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TweetWithConversationMetadata,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.tweetId) }

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[Query, TweetCandidate, _]
  ] = Seq(tweetypieFeatureHydrator, socialGraphServiceFeatureHydrator)

  override def filters: Seq[Filter[Query, TweetCandidate]] = Seq(
    RetweetDeduplicationFilter,
    FeatureFilter.fromFeature(FilterIdentifier(TweetypieHydratedFilterId), IsHydratedFeature),
    PredicateFeatureFilter.fromPredicate(
      FilterIdentifier(QuotedTweetDroppedFilterId),
      shouldKeepCandidate = { features => !features.getOrElse(QuotedTweetDroppedFeature, false) }
    ),
    InvalidConversationModuleFilter
  )

  override val postFilterFeatureHydration: Seq[
    BaseCandidateFeatureHydrator[Query, TweetCandidate, _]
  ] = Seq(namesFeatureHydrator)

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(),
    HomeMixerAlertConfig.BusinessHours.defaultEmptyResponseRateAlert()
  )
}
