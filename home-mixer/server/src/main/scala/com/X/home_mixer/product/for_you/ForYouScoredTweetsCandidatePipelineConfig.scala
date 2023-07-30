package com.X.home_mixer.product.for_you

import com.X.home_mixer.functional_component.decorator.builder.HomeClientEventInfoBuilder
import com.X.home_mixer.functional_component.decorator.builder.HomeConversationModuleMetadataBuilder
import com.X.home_mixer.functional_component.decorator.builder.HomeTimelinesScoreInfoBuilder
import com.X.home_mixer.functional_component.decorator.urt.builder.HomeFeedbackActionInfoBuilder
import com.X.home_mixer.functional_component.decorator.urt.builder.HomeTweetSocialContextBuilder
import com.X.home_mixer.functional_component.feature_hydrator.InNetworkFeatureHydrator
import com.X.home_mixer.functional_component.feature_hydrator.NamesFeatureHydrator
import com.X.home_mixer.functional_component.feature_hydrator.TweetypieFeatureHydrator
import com.X.home_mixer.functional_component.filter.InvalidConversationModuleFilter
import com.X.home_mixer.functional_component.filter.InvalidSubscriptionTweetFilter
import com.X.home_mixer.functional_component.gate.SupportedLanguagesGate
import com.X.home_mixer.model.HomeFeatures.ConversationModuleFocalTweetIdFeature
import com.X.home_mixer.model.HomeFeatures.InNetworkFeature
import com.X.home_mixer.model.HomeFeatures.IsHydratedFeature
import com.X.home_mixer.model.HomeFeatures.IsNsfwFeature
import com.X.home_mixer.model.HomeFeatures.QuotedTweetDroppedFeature
import com.X.home_mixer.product.for_you.candidate_source.ScoredTweetWithConversationMetadata
import com.X.home_mixer.product.for_you.candidate_source.ScoredTweetsProductCandidateSource
import com.X.home_mixer.product.for_you.feature_hydrator.FocalTweetFeatureHydrator
import com.X.home_mixer.product.for_you.filter.SocialContextFilter
import com.X.home_mixer.product.for_you.model.ForYouQuery
import com.X.home_mixer.product.for_you.param.ForYouParam.EnableScoredTweetsCandidatePipelineParam
import com.X.home_mixer.service.HomeMixerAlertConfig
import com.X.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.X.product_mixer.component_library.decorator.urt.UrtMultipleModulesDecorator
import com.X.product_mixer.component_library.decorator.urt.builder.item.tweet.TweetCandidateUrtItemBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.timeline_module.ManualModuleId
import com.X.product_mixer.component_library.decorator.urt.builder.timeline_module.StaticModuleDisplayTypeBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.timeline_module.TimelineModuleBuilder
import com.X.product_mixer.component_library.filter.FeatureFilter
import com.X.product_mixer.component_library.filter.PredicateFeatureFilter
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.X.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.X.product_mixer.core.functional_component.filter.Filter
import com.X.product_mixer.core.functional_component.gate.Gate
import com.X.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.X.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.X.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.X.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.X.product_mixer.core.model.common.identifier.FilterIdentifier
import com.X.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.VerticalConversation
import com.X.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.X.timelines.configapi.decider.DeciderParam
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForYouScoredTweetsCandidatePipelineConfig @Inject() (
  scoredTweetsProductCandidateSource: ScoredTweetsProductCandidateSource,
  focalTweetFeatureHydrator: FocalTweetFeatureHydrator,
  namesFeatureHydrator: NamesFeatureHydrator,
  tweetypieFeatureHydrator: TweetypieFeatureHydrator,
  invalidSubscriptionTweetFilter: InvalidSubscriptionTweetFilter,
  homeFeedbackActionInfoBuilder: HomeFeedbackActionInfoBuilder,
  homeTweetSocialContextBuilder: HomeTweetSocialContextBuilder)
    extends CandidatePipelineConfig[
      ForYouQuery,
      ForYouQuery,
      ScoredTweetWithConversationMetadata,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ForYouScoredTweets")

  private val TweetypieHydratedFilterId = "TweetypieHydrated"
  private val QuotedTweetDroppedFilterId = "QuotedTweetDropped"
  private val OutOfNetworkNSFWFilterId = "OutOfNetworkNSFW"
  private val ConversationModuleNamespace = EntryNamespace("home-conversation")

  override val gates: Seq[Gate[ForYouQuery]] = Seq(SupportedLanguagesGate)

  override val candidateSource: CandidateSource[ForYouQuery, ScoredTweetWithConversationMetadata] =
    scoredTweetsProductCandidateSource

  override val enabledDeciderParam: Option[DeciderParam[Boolean]] =
    Some(EnableScoredTweetsCandidatePipelineParam)

  override val queryTransformer: CandidatePipelineQueryTransformer[ForYouQuery, ForYouQuery] =
    identity

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[ScoredTweetWithConversationMetadata]
  ] = Seq(ForYouScoredTweetsResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    ScoredTweetWithConversationMetadata,
    TweetCandidate
  ] = { sourceResults => TweetCandidate(sourceResults.tweetId) }

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ForYouQuery, TweetCandidate, _]
  ] = Seq(InNetworkFeatureHydrator, namesFeatureHydrator, tweetypieFeatureHydrator)

  override val filters: Seq[Filter[ForYouQuery, TweetCandidate]] = Seq(
    FeatureFilter.fromFeature(FilterIdentifier(TweetypieHydratedFilterId), IsHydratedFeature),
    PredicateFeatureFilter.fromPredicate(
      FilterIdentifier(QuotedTweetDroppedFilterId),
      shouldKeepCandidate = { features => !features.getOrElse(QuotedTweetDroppedFeature, false) }
    ),
    PredicateFeatureFilter.fromPredicate(
      FilterIdentifier(OutOfNetworkNSFWFilterId),
      shouldKeepCandidate = { features =>
        features.getOrElse(InNetworkFeature, false) ||
        !features.getOrElse(IsNsfwFeature, false)
      }
    ),
    SocialContextFilter,
    invalidSubscriptionTweetFilter,
    InvalidConversationModuleFilter
  )

  override val postFilterFeatureHydration: Seq[
    BaseCandidateFeatureHydrator[ForYouQuery, TweetCandidate, _]
  ] = Seq(focalTweetFeatureHydrator)

  override val decorator: Option[CandidateDecorator[ForYouQuery, TweetCandidate]] = {
    val clientEventInfoBuilder = HomeClientEventInfoBuilder()

    val tweetItemBuilder = TweetCandidateUrtItemBuilder(
      clientEventInfoBuilder = clientEventInfoBuilder,
      socialContextBuilder = Some(homeTweetSocialContextBuilder),
      timelinesScoreInfoBuilder = Some(HomeTimelinesScoreInfoBuilder),
      feedbackActionInfoBuilder = Some(homeFeedbackActionInfoBuilder)
    )

    val tweetDecorator = UrtItemCandidateDecorator(tweetItemBuilder)

    val moduleBuilder = TimelineModuleBuilder(
      entryNamespace = ConversationModuleNamespace,
      clientEventInfoBuilder = clientEventInfoBuilder,
      moduleIdGeneration = ManualModuleId(0L),
      displayTypeBuilder = StaticModuleDisplayTypeBuilder(VerticalConversation),
      metadataBuilder = Some(HomeConversationModuleMetadataBuilder())
    )

    Some(
      UrtMultipleModulesDecorator(
        urtItemCandidateDecorator = tweetDecorator,
        moduleBuilder = moduleBuilder,
        groupByKey = (_, _, candidateFeatures) =>
          candidateFeatures.getOrElse(ConversationModuleFocalTweetIdFeature, None)
      ))
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(),
    HomeMixerAlertConfig.BusinessHours.defaultEmptyResponseRateAlert(10, 20)
  )
}
