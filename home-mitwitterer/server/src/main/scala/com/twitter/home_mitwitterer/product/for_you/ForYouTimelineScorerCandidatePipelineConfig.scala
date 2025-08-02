package com.twitter.home_mitwitterer.product.for_you

import com.twitter.home_mitwitterer.functional_component.decorator.builder.HomeClientEventInfoBuilder
import com.twitter.home_mitwitterer.functional_component.decorator.builder.HomeConversationModuleMetadataBuilder
import com.twitter.home_mitwitterer.functional_component.decorator.builder.HomeTimelinesScoreInfoBuilder
import com.twitter.home_mitwitterer.functional_component.decorator.urt.builder.HomeFeedbackActionInfoBuilder
import com.twitter.home_mitwitterer.functional_component.decorator.urt.builder.HomeTweetSocialContetwittertBuilder
import com.twitter.home_mitwitterer.functional_component.feature_hydrator.InNetworkFeatureHydrator
import com.twitter.home_mitwitterer.functional_component.feature_hydrator.NamesFeatureHydrator
import com.twitter.home_mitwitterer.functional_component.feature_hydrator.PerspectiveFilteredSocialContetwittertFeatureHydrator
import com.twitter.home_mitwitterer.functional_component.feature_hydrator.SGSValidSocialContetwittertFeatureHydrator
import com.twitter.home_mitwitterer.functional_component.feature_hydrator.TweetypieFeatureHydrator
import com.twitter.home_mitwitterer.functional_component.filter.FeedbackFatigueFilter
import com.twitter.home_mitwitterer.functional_component.filter.InvalidConversationModuleFilter
import com.twitter.home_mitwitterer.functional_component.filter.InvalidSubscriptionTweetFilter
import com.twitter.home_mitwitterer.functional_component.filter.RejectTweetFromViewerFilter
import com.twitter.home_mitwitterer.functional_component.filter.RetweetDeduplicationFilter
import com.twitter.home_mitwitterer.functional_component.scorer.FeedbackFatigueScorer
import com.twitter.home_mitwitterer.functional_component.scorer.OONTweetScalingScorer
import com.twitter.home_mitwitterer.marshaller.timelines.DeviceContetwittertMarshaller
import com.twitter.home_mitwitterer.marshaller.timelines.TimelineServiceCursorMarshaller
import com.twitter.home_mitwitterer.model.HomeFeatures.ConversationModuleFocalTweetIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.IsHydratedFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.IsNsfwFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.QuotedTweetDroppedFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.TimelineServiceTweetsFeature
import com.twitter.home_mitwitterer.model.request.DeviceContetwittert
import com.twitter.home_mitwitterer.product.for_you.feature_hydrator.FocalTweetFeatureHydrator
import com.twitter.home_mitwitterer.product.for_you.filter.SocialContetwittertFilter
import com.twitter.home_mitwitterer.product.for_you.model.ForYouQuery
import com.twitter.home_mitwitterer.product.for_you.param.ForYouParam.EnableTimelineScorerCandidatePipelineParam
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig
import com.twitter.product_mitwitterer.component_library.candidate_source.timeline_scorer.ScoredTweetCandidateWithFocalTweet
import com.twitter.product_mitwitterer.component_library.candidate_source.timeline_scorer.TimelineScorerCandidateSource
import com.twitter.product_mitwitterer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mitwitterer.component_library.decorator.urt.UrtMultipleModulesDecorator
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.tweet.TweetCandidateUrtItemBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.timeline_module.ManualModuleId
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.timeline_module.StaticModuleDisplayTypeBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.timeline_module.TimelineModuleBuilder
import com.twitter.product_mitwitterer.component_library.filter.FeatureFilter
import com.twitter.product_mitwitterer.component_library.filter.PredicateFeatureFilter
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mitwitterer.core.functional_component.common.alert.Alert
import com.twitter.product_mitwitterer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.scorer.Scorer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.VerticalConversation
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.model.candidate.CandidateTweetSourceId
import com.twitter.timelines.service.{thriftscala => tst}
import com.twitter.timelinescorer.{thriftscala => t}
import com.twitter.timelineservice.{thriftscala => tlst}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Candidate Pipeline Config that fetches tweets from the Timeline Scorer Candidate Source
 */
@Singleton
class ForYouTimelineScorerCandidatePipelineConfig @Inject() (
  timelineScorerCandidateSource: TimelineScorerCandidateSource,
  deviceContetwittertMarshaller: DeviceContetwittertMarshaller,
  tweetypieFeatureHydrator: TweetypieFeatureHydrator,
  sgsValidSocialContetwittertFeatureHydrator: SGSValidSocialContetwittertFeatureHydrator,
  perspectiveFilteredSocialContetwittertFeatureHydrator: PerspectiveFilteredSocialContetwittertFeatureHydrator,
  namesFeatureHydrator: NamesFeatureHydrator,
  focalTweetFeatureHydrator: FocalTweetFeatureHydrator,
  invalidSubscriptionTweetFilter: InvalidSubscriptionTweetFilter,
  homeFeedbackActionInfoBuilder: HomeFeedbackActionInfoBuilder,
  homeTweetSocialContetwittertBuilder: HomeTweetSocialContetwittertBuilder)
    etwittertends CandidatePipelineConfig[
      ForYouQuery,
      t.ScoredTweetsRequest,
      ScoredTweetCandidateWithFocalTweet,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ForYouTimelineScorerTweets")

  private val TweetypieHydratedFilterId = "TweetypieHydrated"
  private val QuotedTweetDroppedFilterId = "QuotedTweetDropped"
  private val OutOfNetworkNSFWFilterId = "OutOfNetworkNSFW"
  private val ConversationModuleNamespace = EntryNamespace("home-conversation")

  override val supportedClientParam: Option[FSParam[Boolean]] =
    Some(EnableTimelineScorerCandidatePipelineParam)

  override val candidateSource: BaseCandidateSource[
    t.ScoredTweetsRequest,
    ScoredTweetCandidateWithFocalTweet
  ] = timelineScorerCandidateSource

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ForYouQuery,
    t.ScoredTweetsRequest
  ] = { query =>
    val deviceContetwittert = query.deviceContetwittert.getOrElse(DeviceContetwittert.Empty)

    val scoredTweetsRequestContetwittert = t.v1.ScoredTweetsRequestContetwittert(
      contetwittertualUserId = query.clientContetwittert.userId,
      timelineId = query.clientContetwittert.userId.map(tlst.TimelineId(tlst.TimelineType.Home, _, None)),
      deviceContetwittert = Some(deviceContetwittertMarshaller(deviceContetwittert, query.clientContetwittert)),
      seenTweetIds = query.seenTweetIds,
      contetwittertualUserContetwittert = Some(tst.ContetwittertualUserContetwittert(query.clientContetwittert.userRoles)),
      timelineRequestCursor = query.pipelineCursor.flatMap(TimelineServiceCursorMarshaller(_))
    )

    val candidateTweetSourceIds = Seq(
      CandidateTweetSourceId.RecycledTweet,
      CandidateTweetSourceId.OrganicTweet,
      CandidateTweetSourceId.AncestorsOnlyOrganicTweet,
      CandidateTweetSourceId.BackfillOrganicTweet,
      CandidateTweetSourceId.CroonTweet,
      CandidateTweetSourceId.RecommendedTweet,
      CandidateTweetSourceId.FrsTweet,
      CandidateTweetSourceId.ListTweet,
      CandidateTweetSourceId.RecommendedTrendTweet,
      CandidateTweetSourceId.PopularTopicTweet
    )

    val timelineServiceTweets =
      query.features.map(_.getOrElse(TimelineServiceTweetsFeature, Seq.empty)).getOrElse(Seq.empty)

    val timelineEntries = timelineServiceTweets.map { id =>
      tlst.TimelineEntry.Tweet(tlst.Tweet(statusId = id, sortIndetwitter = id))
    }

    t.ScoredTweetsRequest.V1(
      t.v1.ScoredTweetsRequest(
        scoredTweetsRequestContetwittert = Some(scoredTweetsRequestContetwittert),
        candidateTweetSourceIds =
          Some(candidateTweetSourceIds.flatMap(CandidateTweetSourceId.toThrift)),
        matwitterResultsCount = query.requestedMatwitterResults,
        organicTimeline = Some(
          tlst.Timeline(
            timelineId = tlst.TimelineId(
              timelineType = tlst.TimelineType.Home,
              id = query.getRequiredUserId,
              canonicalTimelineId = None),
            entries = timelineEntries,
            modules = tlst.TimelineModules()
          ))
      )
    )
  }

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[ScoredTweetCandidateWithFocalTweet]
  ] = Seq(ForYouTimelineScorerResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    ScoredTweetCandidateWithFocalTweet,
    TweetCandidate
  ] = { candidateWithFocalTweetId =>
    TweetCandidate(id = candidateWithFocalTweetId.candidate.tweetId)
  }

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ForYouQuery, TweetCandidate, _]
  ] = Seq(
    namesFeatureHydrator,
    tweetypieFeatureHydrator,
    InNetworkFeatureHydrator,
    sgsValidSocialContetwittertFeatureHydrator,
    perspectiveFilteredSocialContetwittertFeatureHydrator,
  )

  override def filters: Seq[Filter[ForYouQuery, TweetCandidate]] = Seq(
    RetweetDeduplicationFilter,
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
    FeedbackFatigueFilter,
    RejectTweetFromViewerFilter,
    SocialContetwittertFilter,
    invalidSubscriptionTweetFilter,
    InvalidConversationModuleFilter
  )

  override val postFilterFeatureHydration: Seq[
    BaseCandidateFeatureHydrator[ForYouQuery, TweetCandidate, _]
  ] = Seq(focalTweetFeatureHydrator)

  override val scorers: Seq[Scorer[ForYouQuery, TweetCandidate]] =
    Seq(OONTweetScalingScorer, FeedbackFatigueScorer)

  override val decorator: Option[CandidateDecorator[ForYouQuery, TweetCandidate]] = {
    val clientEventInfoBuilder = HomeClientEventInfoBuilder()

    val tweetItemBuilder = TweetCandidateUrtItemBuilder(
      clientEventInfoBuilder = clientEventInfoBuilder,
      socialContetwittertBuilder = Some(homeTweetSocialContetwittertBuilder),
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

  override val alerts: Seq[Alert] = Seq(
    HomeMitwittererAlertConfig.BusinessHours.defaultSuccessRateAlert(),
    HomeMitwittererAlertConfig.BusinessHours.defaultEmptyResponseRateAlert(10, 20)
  )
}
