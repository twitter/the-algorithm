package com.tw ter.un f ed_user_act ons.kafka.serde

 mport ch.qos.logback.class c.sp . Logg ngEvent
 mport ch.qos.logback.core.AppenderBase
 mport scala.collect on.mutable.ArrayBuffer

class TestLogAppender extends AppenderBase[ Logg ngEvent] {
   mport TestLogAppender._

  overr de def append(eventObject:  Logg ngEvent): Un  =
    recordLog(eventObject)
}

object TestLogAppender {
  val events: ArrayBuffer[ Logg ngEvent] = ArrayBuffer()

  def recordLog(event:  Logg ngEvent): Un  =
    events += event
}
