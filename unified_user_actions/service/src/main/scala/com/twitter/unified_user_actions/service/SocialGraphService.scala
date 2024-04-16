package com.tw ter.un f ed_user_act ons.serv ce

 mport com.tw ter.f natra.dec der.modules.Dec derModule
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter. nject.server.Tw terServer
 mport com.tw ter.kafka.cl ent.processor.AtLeastOnceProcessor
 mport com.tw ter.soc algraph.thr ftscala.Wr eEvent
 mport com.tw ter.un f ed_user_act ons.serv ce.module.KafkaProcessorSoc alGraphModule

object Soc alGraphServ ceMa n extends Soc alGraphServ ce

class Soc alGraphServ ce extends Tw terServer {
  overr de val modules = Seq(
    KafkaProcessorSoc alGraphModule,
    Dec derModule
  )

  overr de protected def setup(): Un  = {}

  overr de protected def start(): Un  = {
    val processor =  njector. nstance[AtLeastOnceProcessor[UnKeyed, Wr eEvent]]
    closeOnEx (processor)
    processor.start()
  }
}
