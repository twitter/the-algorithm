package com.X.unified_user_actions.service

import com.X.finatra.decider.modules.DeciderModule
import com.X.finatra.kafka.serde.UnKeyed
import com.X.inject.server.XServer
import com.X.kafka.client.processor.AtLeastOnceProcessor
import com.X.unified_user_actions.service.module.KafkaProcessorRekeyUuaModule
import com.X.unified_user_actions.thriftscala.UnifiedUserAction

object RekeyUuaServiceMain extends RekeyUuaService

class RekeyUuaService extends XServer {

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
