package com.tw ter.un f ed_user_act ons.serv ce

 mport com.tw ter.f natra.dec der.modules.Dec derModule
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter. nject.server.Tw terServer
 mport com.tw ter.kafka.cl ent.processor.AtLeastOnceProcessor
 mport com.tw ter.t etyp e.thr ftscala.T etEvent
 mport com.tw ter.un f ed_user_act ons.serv ce.module.KafkaProcessorT etyp eEventModule

object T etyp eEventServ ceMa n extends T etyp eEventServ ce

class T etyp eEventServ ce extends Tw terServer {

  overr de val modules = Seq(
    KafkaProcessorT etyp eEventModule,
    Dec derModule
  )

  overr de protected def setup(): Un  = {}

  overr de protected def start(): Un  = {
    val processor =  njector. nstance[AtLeastOnceProcessor[UnKeyed, T etEvent]]
    closeOnEx (processor)
    processor.start()
  }

}
