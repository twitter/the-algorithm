package com.tw ter.un f ed_user_act ons.serv ce

 mport com.tw ter.f natra.dec der.modules.Dec derModule
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter. nject.server.Tw terServer
 mport com.tw ter.kafka.cl ent.processor.AtLeastOnceProcessor
 mport com.tw ter.t  l neserv ce.thr ftscala.Contextual zedFavor eEvent
 mport com.tw ter.un f ed_user_act ons.serv ce.module.KafkaProcessorTlsFavsModule

object TlsFavsServ ceMa n extends TlsFavsServ ce

class TlsFavsServ ce extends Tw terServer {

  overr de val modules = Seq(
    KafkaProcessorTlsFavsModule,
    Dec derModule
  )

  overr de protected def setup(): Un  = {}

  overr de protected def start(): Un  = {
    val processor =  njector. nstance[AtLeastOnceProcessor[UnKeyed, Contextual zedFavor eEvent]]
    closeOnEx (processor)
    processor.start()
  }
}
