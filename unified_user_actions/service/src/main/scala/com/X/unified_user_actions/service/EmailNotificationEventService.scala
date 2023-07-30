package com.X.unified_user_actions.service

import com.X.finatra.decider.modules.DeciderModule
import com.X.finatra.kafka.serde.UnKeyed
import com.X.ibis.thriftscala.NotificationScribe
import com.X.inject.server.XServer
import com.X.kafka.client.processor.AtLeastOnceProcessor
import com.X.unified_user_actions.service.module.KafkaProcessorEmailNotificationEventModule

object EmailNotificationEventServiceMain extends EmailNotificationEventService

class EmailNotificationEventService extends XServer {

  override val modules = Seq(
    KafkaProcessorEmailNotificationEventModule,
    DeciderModule
  )

  override protected def setup(): Unit = {}

  override protected def start(): Unit = {
    val processor = injector.instance[AtLeastOnceProcessor[UnKeyed, NotificationScribe]]
    closeOnExit(processor)
    processor.start()
  }
}
