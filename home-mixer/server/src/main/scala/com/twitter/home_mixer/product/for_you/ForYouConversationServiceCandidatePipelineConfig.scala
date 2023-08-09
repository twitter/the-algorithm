package com.twitter.home_mixer.product.for_you

import com.twitter.home_mixer.candidate_pipeline.ConversationServiceResponseFeatureTransformer
import com.twitter.home_mixer.functional_component.decorator.HomeConversationServiceCandidateDecorator
import com.twitter.home_mixer.functional_component.decorator.urt.builder.HomeFeedbackActionInfoBuilder
import com.twitter.home_mixer.functional_component.feature_hydrator.InNetworkFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.NamesFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.TweetypieFeatureHydrator
import com.twitter.home_mixer.functional_component.filter.InvalidConversationModuleFilter
import com.twitter.home_mixer.functional_component.filter.InvalidSubscriptionTweetFilter
import com.twitter.home_mixer.functional_component.filter.PreviouslyServedTweetsFilter
import com.twitter.home_mixer.functional_component.filter.RetweetDeduplicationFilter
import com.twitter.home_mixer.model.HomeFeatures.IsHydratedFeature
import com.twitter.home_mixer.model.HomeFeatures.QuotedTweetDroppedFeature
import com.twitter.home_mixer.model.HomeFeatures.TimelineServiceTweetsFeature
import com.twitter.home_mixer.product.for_you.model.ForYouQuery
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.product_mixer.component_library.candidate_source.tweetconvosvc.ConversationServiceCandidateSource
import com.twitter.product_mixer.component_library.candidate_source.tweetconvosvc.ConversationServiceCandidateSourceRequest
import com.twitter.product_mixer.component_library.candidate_source.tweetconvosvc.TweetWithConversationMetadata
import com.twitter.product_mixer.component_library.filter.FeatureFilter
import com.twitter.product_mixer.component_library.filter.PredicateFeatureFilter
import com.twitter.product_mixer.component_library.gate.NoCandidatesGate
import com.twitter.product_mixer.component_library.gate.NonEmptySeqFeatureGate
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.common.SpecificPipelines
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.gate.BaseGate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.functional_component.transformer.DependentCandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.candidate.DependentCandidatePipelineConfig
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Candidate Pipeline Config that fetches Tweet ancestors from Conversation Service Candidate Source
 */
@Singleton
class ForYouConversationServiceCandidatePipelineConfig @Inject() (
  forYouScoredTweetsCandidatePipelineConfig: ForYouScoredTweetsCandidatePipelineConfig,
  forYouTimelineScorerCandidatePipelineConfig: ForYouTimelineScorerCandidatePipelineConfig,
  conversationServiceCandidateSource: ConversationServiceCandidateSource,
  tweetypieFeatureHydrator: TweetypieFeatureHydrator,
  namesFeatureHydrator: NamesFeatureHydrator,
  invalidSubscriptionTweetFilter: InvalidSubscriptionTweetFilter,
  homeFeedbackActionInfoBuilder: HomeFeedbackActionInfoBuilder)
    extends DependentCandidatePipelineConfig[
      ForYouQuery,
      ConversationServiceCandidateSourceRequest,
      TweetWithConversationMetadata,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ForYouConversationService")

  override val gates: Seq[BaseGate[ForYouQuery]] = Seq(
    NoCandidatesGate(
      SpecificPipelines(
        forYouTimelineScorerCandidatePipelineConfig.identifier,
        forYouScoredTweetsCandidatePipelineConfig.identifier
      )
    ),
    NonEmptySeqFeatureGate(TimelineServiceTweetsFeature)
  )

  override val candidateSource: BaseCandidateSource[
    ConversationServiceCandidateSourceRequest,
    TweetWithConversationMetadata
  ] = conversationServiceCandidateSource

  override val queryTransformer: DependentCandidatePipelineQueryTransformer[
    ForYouQuery,
    ConversationServiceCandidateSourceRequest
  ] = { (query, candidates) =>
    val timelineServiceTweets = query.features
      .map(_.getOrElse(TimelineServiceTweetsFeature, Seq.empty)).getOrElse(Seq.empty)

    val tweetsWithConversationMetadata = timelineServiceTweets.map { id =>
      TweetWithConversationMetadata(
        tweetId = id,
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
    BaseCandidateFeatureHydrator[ForYouQuery, TweetCandidate, _]
  ] = Seq(
    InNetworkFeatureHydrator,
    tweetypieFeatureHydrator
  )

  override def filters: Seq[Filter[ForYouQuery, TweetCandidate]] = Seq(
    PreviouslyServedTweetsFilter,
    RetweetDeduplicationFilter,
    FeatureFilter.fromFeature(FilterIdentifier("TweetypieHydrated"), IsHydratedFeature),
    PredicateFeatureFilter.fromPredicate(
      FilterIdentifier("QuotedTweetDropped"),
      shouldKeepCandidate = { features => !features.getOrElse(QuotedTweetDroppedFeature, false) }
    ),
    invalidSubscriptionTweetFilter,
    InvalidConversationModuleFilter
  )

  override val postFilterFeatureHydration: Seq[
    BaseCandidateFeatureHydrator[ForYouQuery, TweetCandidate, _]
  ] = Seq(namesFeatureHydrator)

  override val decorator: Option[CandidateDecorator[ForYouQuery, TweetCandidate]] =
    HomeConversationServiceCandidateDecorator(homeFeedbackActionInfoBuilder)

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(),
    HomeMixerAlertConfig.BusinessHours.defaultEmptyResponseRateAlert()
  )
}
