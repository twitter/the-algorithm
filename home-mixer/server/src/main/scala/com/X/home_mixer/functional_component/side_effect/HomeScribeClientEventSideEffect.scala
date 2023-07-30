package com.X.home_mixer.functional_component.side_effect

import com.X.clientapp.thriftscala.LogEvent
import com.X.home_mixer.service.HomeMixerAlertConfig
import com.X.home_mixer.util.CandidatesUtil
import com.X.logpipeline.client.common.EventPublisher
import com.X.product_mixer.component_library.side_effect.ScribeClientEventSideEffect
import com.X.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.X.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.X.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.X.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.X.product_mixer.core.model.marshalling.response.urt.Timeline
import com.X.product_mixer.core.pipeline.PipelineQuery

/**
 * Side effect that logs served tweet metrics to Scribe as client events.
 */
case class HomeScribeClientEventSideEffect(
  enableScribeClientEvents: Boolean,
  override val logPipelinePublisher: EventPublisher[LogEvent],
  injectedTweetsCandidatePipelineIdentifiers: Seq[CandidatePipelineIdentifier],
  adsCandidatePipelineIdentifier: Option[CandidatePipelineIdentifier] = None,
  whoToFollowCandidatePipelineIdentifier: Option[CandidatePipelineIdentifier] = None,
  whoToSubscribeCandidatePipelineIdentifier: Option[CandidatePipelineIdentifier] = None)
    extends ScribeClientEventSideEffect[PipelineQuery, Timeline]
    with PipelineResultSideEffect.Conditionally[
      PipelineQuery,
      Timeline
    ] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("HomeScribeClientEvent")

  override val page = "timelinemixer"

  override def onlyIf(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: Timeline
  ): Boolean = enableScribeClientEvents

  override def buildClientEvents(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: Timeline
  ): Seq[ScribeClientEventSideEffect.ClientEvent] = {

    val itemCandidates = CandidatesUtil.getItemCandidates(selectedCandidates)
    val sources = itemCandidates.groupBy(_.source)
    val injectedTweets =
      injectedTweetsCandidatePipelineIdentifiers.flatMap(sources.getOrElse(_, Seq.empty))
    val promotedTweets = adsCandidatePipelineIdentifier.flatMap(sources.get).toSeq.flatten

    // WhoToFollow and WhoToSubscribe modules are not required for all home-mixer products, e.g. list tweets timeline.
    val whoToFollowUsers = whoToFollowCandidatePipelineIdentifier.flatMap(sources.get).toSeq.flatten
    val whoToSubscribeUsers =
      whoToSubscribeCandidatePipelineIdentifier.flatMap(sources.get).toSeq.flatten

    val servedEvents = ServedEventsBuilder
      .build(query, injectedTweets, promotedTweets, whoToFollowUsers, whoToSubscribeUsers)

    val emptyTimelineEvents = EmptyTimelineEventsBuilder.build(query, injectedTweets)

    val queryEvents = QueryEventsBuilder.build(query, injectedTweets)

    (servedEvents ++ emptyTimelineEvents ++ queryEvents).filter(_.eventValue.forall(_ > 0))
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.9)
  )
}
