package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.reaction

import com.twitter.product_mixer.core.model.marshalling.response.urt.reaction.ImmediateTimelineReaction
import com.twitter.product_mixer.core.model.marshalling.response.urt.reaction.RemoteTimelineReaction
import com.twitter.product_mixer.core.model.marshalling.response.urt.reaction.TimelineReaction
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimelineReactionMarshaller @Inject() () {
  def apply(timelineReaction: TimelineReaction): urt.TimelineReaction = {
    val execution = timelineReaction.execution match {
      case ImmediateTimelineReaction(key) =>
        urt.TimelineReactionExecution.Immediate(urt.ImmediateTimelineReaction(key))
      case RemoteTimelineReaction(requestParams, timeoutInSeconds) =>
        urt.TimelineReactionExecution.Remote(
          urt.RemoteTimelineReaction(
            requestParams,
            timeoutInSeconds
          ))
    }
    urt.TimelineReaction(
      execution = execution,
      maxExecutionCount = timelineReaction.maxExecutionCount
    )
  }
}
