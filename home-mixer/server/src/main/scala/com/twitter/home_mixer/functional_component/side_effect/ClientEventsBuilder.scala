package com.twitter.home_mixer.functional_component.side_effect

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.functional_component.decorator.HomeQueryTypePredicates
import com.twitter.home_mixer.functional_component.decorator.builder.HomeTweetTypePredicates
import com.twitter.home_mixer.model.HomeFeatures.AccountAgeFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetTypeMetricsFeature
import com.twitter.home_mixer.model.HomeFeatures.VideoDurationMsFeature
import com.twitter.home_mixer.model.request.FollowingProduct
import com.twitter.home_mixer.model.request.ForYouProduct
import com.twitter.home_mixer.model.request.SubscribedProduct
import com.twitter.product_mixer.component_library.side_effect.ScribeClientEventSideEffect.ClientEvent
import com.twitter.product_mixer.component_library.side_effect.ScribeClientEventSideEffect.EventNamespace
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.suggests.controller_data.Home

private[side_effect] sealed trait ClientEventsBuilder {
  private val FollowingSection = Some("latest")
  private val ForYouSection = Some("home")
  private val SubscribedSection = Some("subscribed")

  protected def section(query: PipelineQuery): Option[String] = {
    query.product match {
      case FollowingProduct => FollowingSection
      case ForYouProduct => ForYouSection
      case SubscribedProduct => SubscribedSection
      case other => throw new UnsupportedOperationException(s"Unknown product: $other")
    }
  }

  protected def count(
    candidates: Seq[CandidateWithDetails],
    predicate: FeatureMap => Boolean = _ => true,
    queryFeatures: FeatureMap = FeatureMap.empty
  ): Option[Long] = Some(candidates.view.count(item => predicate(item.features ++ queryFeatures)))

  protected def sum(
    candidates: Seq[CandidateWithDetails],
    predicate: FeatureMap => Option[Int],
    queryFeatures: FeatureMap = FeatureMap.empty
  ): Option[Long] =
    Some(candidates.view.flatMap(item => predicate(item.features ++ queryFeatures)).sum)
}

private[side_effect] object ServedEventsBuilder extends ClientEventsBuilder {

  private val ServedTweetsAction = Some("served_tweets")
  private val ServedUsersAction = Some("served_users")
  private val ServedCommunitiesAction = Some("served_communities")
  private val ServedPromptsAction = Some("served_prompts")
  private val InjectedComponent = Some("injected")
  private val PromotedComponent = Some("promoted")
  private val WhoToFollowComponent = Some("who_to_follow")
  private val WhoToSubscribeComponent = Some("who_to_subscribe")
  private val CommunitiesToJoinComponent = Some("communities_to_join")
  private val RelevancePromptComponent = Some("for_you_survey_feed")
  private val WithVideoDurationComponent = Some("with_video_duration")
  private val VideoDurationSumElement = Some("video_duration_sum")
  private val NumVideosElement = Some("num_videos")

  def tweetTypePredicate(predicateName: String): FeatureMap => Boolean = {
    val predicateIdxOpt = Home.ItemTypeIdxMap.get(predicateName)
    if (predicateIdxOpt.nonEmpty) {
      val predicateIdx = predicateIdxOpt.get
      featureMap: FeatureMap => {
        featureMap
          .getOrElse(TweetTypeMetricsFeature, None)
          .map { tweetTypeMetrics =>
            java.util.BitSet
              .valueOf(tweetTypeMetrics.toArray)
              .get(predicateIdx)
          }.getOrElse(false)
      }
    } else _ => false
  }
  def build(
    query: PipelineQuery,
    injectedTweets: Seq[ItemCandidateWithDetails],
    promotedTweets: Seq[ItemCandidateWithDetails],
    whoToFollowUsers: Seq[ItemCandidateWithDetails],
    whoToSubscribeUsers: Seq[ItemCandidateWithDetails],
    communititesToJoin: Seq[ItemCandidateWithDetails],
    relevancePrompt: Seq[ItemCandidateWithDetails]
  ): Seq[ClientEvent] = {
    val baseEventNamespace = EventNamespace(
      section = section(query),
      action = ServedTweetsAction
    )
    val overallServedEvents = Seq(
      ClientEvent(baseEventNamespace, eventValue = count(injectedTweets ++ promotedTweets)),
      ClientEvent(
        baseEventNamespace.copy(component = InjectedComponent),
        eventValue = count(injectedTweets)),
      ClientEvent(
        baseEventNamespace.copy(component = PromotedComponent),
        eventValue = count(promotedTweets)),
      ClientEvent(
        baseEventNamespace.copy(component = WhoToFollowComponent, action = ServedUsersAction),
        eventValue = count(whoToFollowUsers)),
      ClientEvent(
        baseEventNamespace.copy(component = WhoToSubscribeComponent, action = ServedUsersAction),
        eventValue = count(whoToSubscribeUsers)),
      ClientEvent(
        baseEventNamespace
          .copy(component = CommunitiesToJoinComponent, action = ServedCommunitiesAction),
        eventValue = count(communititesToJoin)),
      ClientEvent(
        baseEventNamespace
          .copy(component = RelevancePromptComponent, action = ServedPromptsAction),
        eventValue = count(relevancePrompt)),
    )

    val tweetTypeServedEvents = HomeTweetTypePredicates.PredicateMap.map {
      case (tweetType, predicate) =>
        ClientEvent(
          baseEventNamespace.copy(component = InjectedComponent, element = Some(tweetType)),
          eventValue = count(
            injectedTweets,
            tweetTypePredicate(tweetType),
            query.features.getOrElse(FeatureMap.empty))
        )
    }.toSeq

    val servedTypeServedEvents = injectedTweets
      .map(_.features.get(ServedTypeFeature))
      .groupBy(identity).map {
        case (servedType, group) =>
          ClientEvent(
            baseEventNamespace.copy(component = Some(servedType.originalName)),
            eventValue = Some(group.size.toLong))
      }.toSeq

    // Video duration events
    val numVideosEvent = ClientEvent(
      baseEventNamespace.copy(component = WithVideoDurationComponent, element = NumVideosElement),
      eventValue = count(injectedTweets, _.getOrElse(VideoDurationMsFeature, None).nonEmpty)
    )
    val videoDurationSumEvent = ClientEvent(
      baseEventNamespace
        .copy(component = WithVideoDurationComponent, element = VideoDurationSumElement),
      eventValue = sum(injectedTweets, _.getOrElse(VideoDurationMsFeature, None))
    )
    val videoEvents = Seq(numVideosEvent, videoDurationSumEvent)

    overallServedEvents ++ tweetTypeServedEvents ++ servedTypeServedEvents ++ videoEvents
  }
}

private[side_effect] object EmptyTimelineEventsBuilder extends ClientEventsBuilder {
  private val EmptyAction = Some("empty")
  private val AccountAgeLessThan30MinutesComponent = Some("account_age_less_than_30_minutes")
  private val ServedNonPromotedTweetElement = Some("served_non_promoted_tweet")

  def build(
    query: PipelineQuery,
    injectedTweets: Seq[ItemCandidateWithDetails]
  ): Seq[ClientEvent] = {
    val baseEventNamespace = EventNamespace(
      section = section(query),
      action = EmptyAction
    )

    // Empty timeline events
    val accountAgeLessThan30Minutes = query.features
      .flatMap(_.getOrElse(AccountAgeFeature, None))
      .exists(_.untilNow < 30.minutes)
    val isEmptyTimeline = count(injectedTweets).contains(0L)
    val predicates = Seq(
      None -> isEmptyTimeline,
      AccountAgeLessThan30MinutesComponent -> (isEmptyTimeline && accountAgeLessThan30Minutes)
    )
    for {
      (component, predicate) <- predicates
      if predicate
    } yield ClientEvent(
      baseEventNamespace.copy(component = component, element = ServedNonPromotedTweetElement))
  }
}

private[side_effect] object QueryEventsBuilder extends ClientEventsBuilder {

  private val ServedSizePredicateMap: Map[String, Int => Boolean] = Map(
    ("size_is_empty", _ <= 0),
    ("size_at_most_5", _ <= 5),
    ("size_at_most_10", _ <= 10),
    ("size_at_most_35", _ <= 35)
  )

  def build(
    query: PipelineQuery,
    injectedTweets: Seq[ItemCandidateWithDetails]
  ): Seq[ClientEvent] = {
    val baseEventNamespace = EventNamespace(
      section = section(query)
    )
    val queryFeatureMap = query.features.getOrElse(FeatureMap.empty)
    val servedSizeQueryEvents =
      for {
        (queryPredicateName, queryPredicate) <- HomeQueryTypePredicates.PredicateMap
        if queryPredicate(queryFeatureMap)
        (servedSizePredicateName, servedSizePredicate) <- ServedSizePredicateMap
        if servedSizePredicate(injectedTweets.size)
      } yield ClientEvent(
        baseEventNamespace
          .copy(component = Some(servedSizePredicateName), action = Some(queryPredicateName)))
    servedSizeQueryEvents.toSeq
  }
}
