package com.twitter.unified_user_actions.kafka.serde

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import scala.collection.mutable.ArrayBuffer

class TestLogAppender extends AppenderBase[ILoggingEvent] {
  import TestLogAppender._

  override def append(eventObject: ILoggingEvent): Unit =
    recordLog(eventObject)
}

object TestLogAppender {
  val events: ArrayBuffer[ILoggingEvent] = ArrayBuffer()

  def recordLog(event: ILoggingEvent): Unit =
    events += event
}
