package com.twitter.unified_user_actions.service

import com.twitter.finatra.decider.modules.DeciderModule
import com.twitter.finatra.kafka.serde.UnKeyed
import com.twitter.ibis.thriftscala.NotificationScribe
import com.twitter.inject.server.TwitterServer
import com.twitter.kafka.client.processor.AtLeastOnceProcessor
import com.twitter.unified_user_actions.service.module.KafkaProcessorEmailNotificationEventModule

object EmailNotificationEventServiceMain extends EmailNotificationEventService

class EmailNotificationEventService extends TwitterServer {

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
