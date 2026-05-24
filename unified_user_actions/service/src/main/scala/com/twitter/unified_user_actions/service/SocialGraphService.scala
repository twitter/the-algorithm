package com.twitter.unified_user_actions.service

import com.twitter.finatra.decider.modules.DeciderModule
import com.twitter.finatra.kafka.serde.UnKeyed
import com.twitter.inject.server.TwitterServer
import com.twitter.kafka.client.processor.AtLeastOnceProcessor
import com.twitter.socialgraph.thriftscala.WriteEvent
import com.twitter.unified_user_actions.service.module.KafkaProcessorSocialGraphModule

object SocialGraphServiceMain extends SocialGraphService

class SocialGraphService extends TwitterServer {
  override val modules = Seq(
    KafkaProcessorSocialGraphModule,
    DeciderModule
  )

  override protected def setup(): Unit = {}

  override protected def start(): Unit = {
    val processor = injector.instance[AtLeastOnceProcessor[UnKeyed, WriteEvent]]
    closeOnExit(processor)
    processor.start()
  }
}
