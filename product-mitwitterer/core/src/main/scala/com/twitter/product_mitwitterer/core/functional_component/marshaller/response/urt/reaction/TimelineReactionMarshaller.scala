package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.reaction

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.reaction.ImmediateTimelineReaction
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.reaction.RemoteTimelineReaction
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.reaction.TimelineReaction
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TimelineReactionMarshaller @Inject() () {
  def apply(timelineReaction: TimelineReaction): urt.TimelineReaction = {
    val etwitterecution = timelineReaction.etwitterecution match {
      case ImmediateTimelineReaction(key) =>
        urt.TimelineReactionEtwitterecution.Immediate(urt.ImmediateTimelineReaction(key))
      case RemoteTimelineReaction(requestParams, timeoutInSeconds) =>
        urt.TimelineReactionEtwitterecution.Remote(
          urt.RemoteTimelineReaction(
            requestParams,
            timeoutInSeconds
          ))
    }
    urt.TimelineReaction(
      etwitterecution = etwitterecution,
      matwitterEtwitterecutionCount = timelineReaction.matwitterEtwitterecutionCount
    )
  }
}
