package com.X.unified_user_actions.service

import com.X.finatra.decider.modules.DeciderModule
import com.X.finatra.kafka.serde.UnKeyed
import com.X.inject.server.XServer
import com.X.kafka.client.processor.AtLeastOnceProcessor
import com.X.timelineservice.fanout.thriftscala.FavoriteArchivalEvent
import com.X.unified_user_actions.service.module.KafkaProcessorFavoriteArchivalEventsModule

object FavoriteArchivalEventsServiceMain extends FavoriteArchivalEventsService

class FavoriteArchivalEventsService extends XServer {

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
