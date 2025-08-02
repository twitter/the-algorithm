package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.reaction

sealed abstract class TimelineReactionEtwitterecution

case class ImmediateTimelineReaction(key: String) etwittertends TimelineReactionEtwitterecution

case class RemoteTimelineReaction(
  requestParams: Map[String, String],
  timeoutInSeconds: Option[Short])
    etwittertends TimelineReactionEtwitterecution
