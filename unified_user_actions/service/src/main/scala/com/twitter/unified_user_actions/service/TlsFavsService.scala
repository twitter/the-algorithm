package com.twitter.unified_user_actions.service

import com.twitter.finatra.decider.modules.DeciderModule
import com.twitter.finatra.kafka.serde.UnKeyed
import com.twitter.inject.server.TwitterServer
import com.twitter.kafka.client.processor.AtLeastOnceProcessor
import com.twitter.timelineservice.thriftscala.ContextualizedFavoriteEvent
import com.twitter.unified_user_actions.service.module.KafkaProcessorTlsFavsModule

object TlsFavsServiceMain extends TlsFavsService

class TlsFavsService extends TwitterServer {

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
