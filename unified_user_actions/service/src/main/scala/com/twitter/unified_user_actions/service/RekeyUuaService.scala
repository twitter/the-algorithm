package com.twitter.unified_user_actions.service

import com.twitter.finatra.decider.modules.DeciderModule
import com.twitter.finatra.kafka.serde.UnKeyed
import com.twitter.inject.server.TwitterServer
import com.twitter.kafka.client.processor.AtLeastOnceProcessor
import com.twitter.unified_user_actions.service.module.KafkaProcessorRekeyUuaModule
import com.twitter.unified_user_actions.thriftscala.UnifiedUserAction

object RekeyUuaServiceMain extends RekeyUuaService

class RekeyUuaService extends TwitterServer {

  override val modules = Seq(
    KafkaProcessorRekeyUuaModule,
    DeciderModule
  )

  override protected def setup(): Unit = {}

  override protected def start(): Unit = {
    val processor = injector.instance[AtLeastOnceProcessor[UnKeyed, UnifiedUserAction]]
    closeOnExit(processor)
    processor.start()
  }
}
