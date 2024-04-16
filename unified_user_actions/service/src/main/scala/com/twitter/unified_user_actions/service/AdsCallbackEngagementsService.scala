package com.tw ter.un f ed_user_act ons.serv ce

 mport com.tw ter.ads.spendserver.thr ftscala.SpendServerEvent
 mport com.tw ter.f natra.dec der.modules.Dec derModule
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter. nject.server.Tw terServer
 mport com.tw ter.kafka.cl ent.processor.AtLeastOnceProcessor
 mport com.tw ter.un f ed_user_act ons.serv ce.module.KafkaProcessorAdsCallbackEngage ntsModule

object AdsCallbackEngage ntsServ ceMa n extends AdsCallbackEngage ntsServ ce

class AdsCallbackEngage ntsServ ce extends Tw terServer {
  overr de val modules = Seq(
    KafkaProcessorAdsCallbackEngage ntsModule,
    Dec derModule
  )

  overr de protected def setup(): Un  = {}

  overr de protected def start(): Un  = {
    val processor =  njector. nstance[AtLeastOnceProcessor[UnKeyed, SpendServerEvent]]
    closeOnEx (processor)
    processor.start()
  }
}
