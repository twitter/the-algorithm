package com.tw ter.un f ed_user_act ons.serv ce

 mport com.tw ter.f natra.dec der.modules.Dec derModule
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter. nject.server.Tw terServer
 mport com.tw ter.kafka.cl ent.processor.AtLeastOnceProcessor
 mport com.tw ter.t  l neserv ce.fanout.thr ftscala.Favor eArch valEvent
 mport com.tw ter.un f ed_user_act ons.serv ce.module.KafkaProcessorFavor eArch valEventsModule

object Favor eArch valEventsServ ceMa n extends Favor eArch valEventsServ ce

class Favor eArch valEventsServ ce extends Tw terServer {

  overr de val modules = Seq(
    KafkaProcessorFavor eArch valEventsModule,
    Dec derModule
  )

  overr de protected def setup(): Un  = {}

  overr de protected def start(): Un  = {
    val processor =  njector. nstance[AtLeastOnceProcessor[UnKeyed, Favor eArch valEvent]]
    closeOnEx (processor)
    processor.start()
  }
}
