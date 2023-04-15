package com.twitter.unified_user_actions.service

import com.twitter.finatra.decider.modules.DeciderModule
import com.twitter.finatra.kafka.serde.UnKeyed
import com.twitter.inject.server.TwitterServer
import com.twitter.kafka.client.processor.AtLeastOnceProcessor
import com.twitter.tweetypie.thriftscala.RetweetArchivalEvent
import com.twitter.unified_user_actions.service.module.KafkaProcessorRetweetArchivalEventsModule

object RetweetArchivalEventsServiceMain extends RetweetArchivalEventsService

class RetweetArchivalEventsService extends TwitterServer {

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
