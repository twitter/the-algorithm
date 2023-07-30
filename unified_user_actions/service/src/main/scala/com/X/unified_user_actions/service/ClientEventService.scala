package com.X.unified_user_actions.service

import com.X.clientapp.thriftscala.LogEvent
import com.X.finatra.decider.modules.DeciderModule
import com.X.finatra.kafka.serde.UnKeyed
import com.X.inject.server.XServer
import com.X.kafka.client.processor.AtLeastOnceProcessor
import com.X.unified_user_actions.service.module.KafkaProcessorClientEventModule

object ClientEventServiceMain extends ClientEventService

class ClientEventService extends XServer {

  override val modules = Seq(KafkaProcessorClientEventModule, DeciderModule)

  override protected def setup(): Unit = {}

  override protected def start(): Unit = {
    val processor = injector.instance[AtLeastOnceProcessor[UnKeyed, LogEvent]]
    closeOnExit(processor)
    processor.start()
  }
}
