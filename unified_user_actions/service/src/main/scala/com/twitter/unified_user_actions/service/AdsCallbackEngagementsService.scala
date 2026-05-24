package com.twitter.unified_user_actions.service

import com.twitter.ads.spendserver.thriftscala.SpendServerEvent
import com.twitter.finatra.decider.modules.DeciderModule
import com.twitter.finatra.kafka.serde.UnKeyed
import com.twitter.inject.server.TwitterServer
import com.twitter.kafka.client.processor.AtLeastOnceProcessor
import com.twitter.unified_user_actions.service.module.KafkaProcessorAdsCallbackEngagementsModule

object AdsCallbackEngagementsServiceMain extends AdsCallbackEngagementsService

class AdsCallbackEngagementsService extends TwitterServer {
  override val modules = Seq(
    KafkaProcessorAdsCallbackEngagementsModule,
    DeciderModule
  )

  override protected def setup(): Unit = {}

  override protected def start(): Unit = {
    val processor = injector.instance[AtLeastOnceProcessor[UnKeyed, SpendServerEvent]]
    closeOnExit(processor)
    processor.start()
  }
}
