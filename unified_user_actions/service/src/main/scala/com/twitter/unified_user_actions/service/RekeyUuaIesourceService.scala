package com.twitter.unified_user_actions.service

import com.twitter.finatra.decider.modules.DeciderModule
import com.twitter.finatra.kafka.serde.UnKeyed
import com.twitter.iesource.thriftscala.InteractionEvent
import com.twitter.inject.server.TwitterServer
import com.twitter.kafka.client.processor.AtLeastOnceProcessor
import com.twitter.unified_user_actions.service.module.KafkaProcessorRekeyUuaIesourceModule

object RekeyUuaIesourceServiceMain extends RekeyUuaIesourceService

class RekeyUuaIesourceService extends TwitterServer {

  override val modules = Seq(
    KafkaProcessorRekeyUuaIesourceModule,
    DeciderModule
  )

  override protected def setup(): Unit = {}

  override protected def start(): Unit = {
    val processor = injector.instance[AtLeastOnceProcessor[UnKeyed, InteractionEvent]]
    closeOnExit(processor)
    processor.start()
  }
}
