package com.twitter.unified_user_actions.service

import com.twitter.finatra.decider.modules.DeciderModule
import com.twitter.finatra.kafka.serde.UnKeyed
import com.twitter.inject.server.TwitterServer
import com.twitter.kafka.client.processor.AtLeastOnceProcessor
import com.twitter.timelineservice.fanout.thriftscala.FavoriteArchivalEvent
import com.twitter.unified_user_actions.service.module.KafkaProcessorFavoriteArchivalEventsModule

object FavoriteArchivalEventsServiceMain extends FavoriteArchivalEventsService

class FavoriteArchivalEventsService extends TwitterServer {

  override val modules = Seq(
    KafkaProcessorFavoriteArchivalEventsModule,
    DeciderModule
  )

  override protected def setup(): Unit = {}

  override protected def start(): Unit = {
    val processor = injector.instance[AtLeastOnceProcessor[UnKeyed, FavoriteArchivalEvent]]
    closeOnExit(processor)
    processor.start()
  }
}
