package com.X.home_mixer.candidate_pipeline

import com.X.home_mixer.functional_component.feature_hydrator.InNetworkFeatureHydrator
import com.X.home_mixer.functional_component.feature_hydrator.NamesFeatureHydrator
import com.X.home_mixer.functional_component.feature_hydrator.TweetypieFeatureHydrator
import com.X.home_mixer.functional_component.filter.InvalidConversationModuleFilter
import com.X.home_mixer.functional_component.filter.InvalidSubscriptionTweetFilter
import com.X.home_mixer.functional_component.filter.RetweetDeduplicationFilter
import com.X.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.X.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.X.home_mixer.model.HomeFeatures.IsHydratedFeature
import com.X.home_mixer.model.HomeFeatures.QuotedTweetDroppedFeature
import com.X.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.X.home_mixer.model.HomeFeatures.SourceUserIdFeature
import com.X.home_mixer.service.HomeMixerAlertConfig
import com.X.product_mixer.component_library.candidate_source.tweetconvosvc.ConversationServiceCandidateSource
import com.X.product_mixer.component_library.candidate_source.tweetconvosvc.ConversationServiceCandidateSourceRequest
import com.X.product_mixer.component_library.candidate_source.tweetconvosvc.TweetWithConversationMetadata
import com.X.product_mixer.component_library.filter.FeatureFilter
import com.X.product_mixer.component_library.filter.PredicateFeatureFilter
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.X.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.X.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.X.product_mixer.core.functional_component.filter.Filter
import com.X.product_mixer.core.functional_component.gate.BaseGate
import com.X.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.X.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.X.product_mixer.core.functional_component.transformer.DependentCandidatePipelineQueryTransformer
import com.X.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.X.product_mixer.core.model.common.identifier.FilterIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.pipeline.candidate.DependentCandidatePipelineConfig

/**
 * Candidate Pipeline Config that fetches tweets from the Conversation Service Candidate Source
 */
class ConversationServiceCandidatePipelineConfig[Query <: PipelineQuery](
  conversationServiceCandidateSource: ConversationServiceCandidateSource,
  tweetypieFeatureHydrator: TweetypieFeatureHydrator,
  namesFeatureHydrator: NamesFeatureHydrator,
  invalidSubscriptionTweetFilter: InvalidSubscriptionTweetFilter,
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
        userId = candidate.features.getOrElse(AuthorIdFeature, None),
        sourceTweetId = candidate.features.getOrElse(SourceTweetIdFeature, None),
        sourceUserId = candidate.features.getOrElse(SourceUserIdFeature, None),
        inReplyToTweetId = candidate.features.getOrElse(InReplyToTweetIdFeature, None),
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
  ] = Seq(
    tweetypieFeatureHydrator,
    InNetworkFeatureHydrator,
  )

  override def filters: Seq[Filter[Query, TweetCandidate]] = Seq(
    RetweetDeduplicationFilter,
    FeatureFilter.fromFeature(FilterIdentifier(TweetypieHydratedFilterId), IsHydratedFeature),
    PredicateFeatureFilter.fromPredicate(
      FilterIdentifier(QuotedTweetDroppedFilterId),
      shouldKeepCandidate = { features => !features.getOrElse(QuotedTweetDroppedFeature, false) }
    ),
    invalidSubscriptionTweetFilter,
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
