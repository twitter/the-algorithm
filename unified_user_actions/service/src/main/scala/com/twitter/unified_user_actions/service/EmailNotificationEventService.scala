package com.tw ter.un f ed_user_act ons.serv ce

 mport com.tw ter.f natra.dec der.modules.Dec derModule
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter. b s.thr ftscala.Not f cat onScr be
 mport com.tw ter. nject.server.Tw terServer
 mport com.tw ter.kafka.cl ent.processor.AtLeastOnceProcessor
 mport com.tw ter.un f ed_user_act ons.serv ce.module.KafkaProcessorEma lNot f cat onEventModule

object Ema lNot f cat onEventServ ceMa n extends Ema lNot f cat onEventServ ce

class Ema lNot f cat onEventServ ce extends Tw terServer {

  overr de val modules = Seq(
    KafkaProcessorEma lNot f cat onEventModule,
    Dec derModule
  )

  overr de protected def setup(): Un  = {}

  overr de protected def start(): Un  = {
    val processor =  njector. nstance[AtLeastOnceProcessor[UnKeyed, Not f cat onScr be]]
    closeOnEx (processor)
    processor.start()
  }
}
