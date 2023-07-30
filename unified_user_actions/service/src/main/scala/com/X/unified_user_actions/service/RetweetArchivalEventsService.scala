package com.X.unified_user_actions.service

import com.X.finatra.decider.modules.DeciderModule
import com.X.finatra.kafka.serde.UnKeyed
import com.X.inject.server.XServer
import com.X.kafka.client.processor.AtLeastOnceProcessor
import com.X.tweetypie.thriftscala.RetweetArchivalEvent
import com.X.unified_user_actions.service.module.KafkaProcessorRetweetArchivalEventsModule

object RetweetArchivalEventsServiceMain extends RetweetArchivalEventsService

class RetweetArchivalEventsService extends XServer {

  override val modules = Seq(
    KafkaProcessorRetweetArchivalEventsModule,
    DeciderModule
  )

  override protected def setup(): Unit = {}

  override protected def start(): Unit = {
    val processor = injector.instance[AtLeastOnceProcessor[UnKeyed, RetweetArchivalEvent]]
    closeOnExit(processor)
    processor.start()
  }
}
