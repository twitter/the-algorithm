package com.twitter.unified_user_actions.service;

import com.twitter.finatra.decider.modules.DeciderModule
import com.twitter.finatra.kafka.serde.UnKeyed
import com.twitter.inject.server.TwitterServer
import com.twitter.kafka.client.processor.AtLeastOnceProcessor
import com.twitter.storage.behavioral_event.thriftscala.FlattenedEventLog
import com.twitter.unified_user_actions.service.module.KafkaProcessorBehavioralClientEventModule

object BehavioralClientEventServiceMain extends BehavioralClientEventService

class BehavioralClientEventService extends TwitterServer {
  override val modules = Seq(
    KafkaProcessorBehavioralClientEventModule,
    DeciderModule
  )

  override protected def setup(): Unit = {}

  override protected def start(): Unit = {
    val processor = injector.instance[AtLeastOnceProcessor[UnKeyed, FlattenedEventLog]]
    closeOnExit(processor)
    processor.start()
  }
}
