package com.tw ter.un f ed_user_act ons.serv ce

 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.f natra.dec der.modules.Dec derModule
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter. nject.server.Tw terServer
 mport com.tw ter.kafka.cl ent.processor.AtLeastOnceProcessor
 mport com.tw ter.un f ed_user_act ons.serv ce.module.KafkaProcessorCl entEventModule

object Cl entEventServ ceMa n extends Cl entEventServ ce

class Cl entEventServ ce extends Tw terServer {

  overr de val modules = Seq(KafkaProcessorCl entEventModule, Dec derModule)

  overr de protected def setup(): Un  = {}

  overr de protected def start(): Un  = {
    val processor =  njector. nstance[AtLeastOnceProcessor[UnKeyed, LogEvent]]
    closeOnEx (processor)
    processor.start()
  }
}
