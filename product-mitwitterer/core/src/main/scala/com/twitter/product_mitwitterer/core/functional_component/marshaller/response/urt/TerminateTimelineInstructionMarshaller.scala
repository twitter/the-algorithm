package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.BottomTermination
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TerminateTimelineInstruction
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TopTermination
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TopAndBottomTermination
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TerminateTimelineInstructionMarshaller @Inject() () {

  def apply(instruction: TerminateTimelineInstruction): urt.TerminateTimeline =
    urt.TerminateTimeline(
      direction = instruction.terminateTimelineDirection match {
        case TopTermination => urt.TimelineTerminationDirection.Top
        case BottomTermination => urt.TimelineTerminationDirection.Bottom
        case TopAndBottomTermination => urt.TimelineTerminationDirection.TopAndBottom
      }
    )
}
