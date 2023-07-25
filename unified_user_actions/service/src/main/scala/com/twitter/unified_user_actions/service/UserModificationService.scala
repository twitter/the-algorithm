package com.twitter.unified_user_actions.service

import com.twitter.finatra.decider.modules.DeciderModule
import com.twitter.finatra.kafka.serde.UnKeyed
import com.twitter.gizmoduck.thriftscala.UserModification
import com.twitter.inject.server.TwitterServer
import com.twitter.kafka.client.processor.AtLeastOnceProcessor
import com.twitter.unified_user_actions.service.module.KafkaProcessorUserModificationModule

object UserModificationServiceMain extends UserModificationService

class UserModificationService extends TwitterServer {
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
