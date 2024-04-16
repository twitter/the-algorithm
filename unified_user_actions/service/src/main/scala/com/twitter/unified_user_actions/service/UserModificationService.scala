package com.tw ter.un f ed_user_act ons.serv ce

 mport com.tw ter.f natra.dec der.modules.Dec derModule
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter.g zmoduck.thr ftscala.UserMod f cat on
 mport com.tw ter. nject.server.Tw terServer
 mport com.tw ter.kafka.cl ent.processor.AtLeastOnceProcessor
 mport com.tw ter.un f ed_user_act ons.serv ce.module.KafkaProcessorUserMod f cat onModule

object UserMod f cat onServ ceMa n extends UserMod f cat onServ ce

class UserMod f cat onServ ce extends Tw terServer {
  overr de val modules = Seq(
    KafkaProcessorUserMod f cat onModule,
    Dec derModule
  )

  overr de protected def setup(): Un  = {}

  overr de protected def start(): Un  = {
    val processor =  njector. nstance[AtLeastOnceProcessor[UnKeyed, UserMod f cat on]]
    closeOnEx (processor)
    processor.start()
  }
}
