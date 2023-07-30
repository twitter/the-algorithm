package com.X.unified_user_actions.service

import com.X.finatra.decider.modules.DeciderModule
import com.X.finatra.kafka.serde.UnKeyed
import com.X.gizmoduck.thriftscala.UserModification
import com.X.inject.server.XServer
import com.X.kafka.client.processor.AtLeastOnceProcessor
import com.X.unified_user_actions.service.module.KafkaProcessorUserModificationModule

object UserModificationServiceMain extends UserModificationService

class UserModificationService extends XServer {
  override val modules = Seq(
    KafkaProcessorUserModificationModule,
    DeciderModule
  )

  override protected def setup(): Unit = {}

  override protected def start(): Unit = {
    val processor = injector.instance[AtLeastOnceProcessor[UnKeyed, UserModification]]
    closeOnExit(processor)
    processor.start()
  }
}
