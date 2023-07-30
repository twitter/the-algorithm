package com.X.unified_user_actions.service

import com.X.ads.spendserver.thriftscala.SpendServerEvent
import com.X.finatra.decider.modules.DeciderModule
import com.X.finatra.kafka.serde.UnKeyed
import com.X.inject.server.XServer
import com.X.kafka.client.processor.AtLeastOnceProcessor
import com.X.unified_user_actions.service.module.KafkaProcessorAdsCallbackEngagementsModule

object AdsCallbackEngagementsServiceMain extends AdsCallbackEngagementsService

class AdsCallbackEngagementsService extends XServer {
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
