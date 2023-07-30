package com.X.timelineranker.observe

import com.X.servo.util.Gate
import com.X.timelineranker.model.TimelineQuery
import com.X.timelines.features.Features
import com.X.timelines.features.UserList
import com.X.timelines.observe.DebugObserver
import com.X.timelineranker.{thriftscala => thrift}

/**
 * Builds the DebugObserver that is attached to thrift requests.
 * This class exists to centralize the gates that determine whether or not
 * to enable debug transcripts for a particular request.
 */
class DebugObserverBuilder(whitelist: UserList) {

  lazy val observer: DebugObserver = build()

  private[this] def build(): DebugObserver = {
    new DebugObserver(queryGate)
  }

  private[observe] def queryGate: Gate[Any] = {
    val shouldEnableDebug = whitelist.userIdGate(Features.DebugTranscript)

    Gate { a: Any =>
      a match {
        case q: thrift.EngagedTweetsQuery => shouldEnableDebug(q.userId)
        case q: thrift.RecapHydrationQuery => shouldEnableDebug(q.userId)
        case q: thrift.RecapQuery => shouldEnableDebug(q.userId)
        case q: TimelineQuery => shouldEnableDebug(q.userId)
        case _ => false
      }
    }
  }
}
