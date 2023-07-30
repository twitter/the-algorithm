package com.X.unified_user_actions.service

import com.X.finatra.decider.modules.DeciderModule
import com.X.finatra.kafka.serde.UnKeyed
import com.X.inject.server.XServer
import com.X.kafka.client.processor.AtLeastOnceProcessor
import com.X.timelineservice.thriftscala.ContextualizedFavoriteEvent
import com.X.unified_user_actions.service.module.KafkaProcessorTlsFavsModule

object TlsFavsServiceMain extends TlsFavsService

class TlsFavsService extends XServer {

  override val modules = Seq(
    KafkaProcessorTlsFavsModule,
    DeciderModule
  )

  override protected def setup(): Unit = {}

  override protected def start(): Unit = {
    val processor = injector.instance[AtLeastOnceProcessor[UnKeyed, ContextualizedFavoriteEvent]]
    closeOnExit(processor)
    processor.start()
  }
}
