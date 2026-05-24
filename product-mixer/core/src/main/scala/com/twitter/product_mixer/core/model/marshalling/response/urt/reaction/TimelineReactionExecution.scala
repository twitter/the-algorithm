package com.twitter.product_mixer.core.model.marshalling.response.urt.reaction

sealed abstract class TimelineReactionExecution

case class ImmediateTimelineReaction(key: String) extends TimelineReactionExecution

case class RemoteTimelineReaction(
  requestParams: Map[String, String],
  timeoutInSeconds: Option[Short])
    extends TimelineReactionExecution
