package com.tw ter.un f ed_user_act ons.serv ce

 mport com.tw ter.f natra.dec der.modules.Dec derModule
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter. nject.server.Tw terServer
 mport com.tw ter.kafka.cl ent.processor.AtLeastOnceProcessor
 mport com.tw ter.un f ed_user_act ons.serv ce.module.KafkaProcessorRekeyUuaModule
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on

object RekeyUuaServ ceMa n extends RekeyUuaServ ce

class RekeyUuaServ ce extends Tw terServer {

  overr de val modules = Seq(
    KafkaProcessorRekeyUuaModule,
    Dec derModule
  )

  overr de protected def setup(): Un  = {}

  overr de protected def start(): Un  = {
    val processor =  njector. nstance[AtLeastOnceProcessor[UnKeyed, Un f edUserAct on]]
    closeOnEx (processor)
    processor.start()
  }
}
