package com.tw ter.un f ed_user_act ons.serv ce

 mport com.tw ter.f natra.dec der.modules.Dec derModule
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter. nject.server.Tw terServer
 mport com.tw ter.kafka.cl ent.processor.AtLeastOnceProcessor
 mport com.tw ter.t etyp e.thr ftscala.Ret etArch valEvent
 mport com.tw ter.un f ed_user_act ons.serv ce.module.KafkaProcessorRet etArch valEventsModule

object Ret etArch valEventsServ ceMa n extends Ret etArch valEventsServ ce

class Ret etArch valEventsServ ce extends Tw terServer {

  overr de val modules = Seq(
    KafkaProcessorRet etArch valEventsModule,
    Dec derModule
  )

  overr de protected def setup(): Un  = {}

  overr de protected def start(): Un  = {
    val processor =  njector. nstance[AtLeastOnceProcessor[UnKeyed, Ret etArch valEvent]]
    closeOnEx (processor)
    processor.start()
  }
}
