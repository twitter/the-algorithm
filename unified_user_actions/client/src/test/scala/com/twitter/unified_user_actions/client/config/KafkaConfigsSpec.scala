package com.tw ter.un f ed_user_act ons.cl ent.conf g

 mport com.tw ter. nject.Test

class KafkaConf gsSpec extends Test {
  test("conf gs should be correct") {
    val states = Seq(
      (
        KafkaConf gs.ProdUn f edUserAct ons,
        Constants.UuaProdEnv,
        Constants.UuaKafkaTop cNa ,
        Constants.UuaKafkaProdClusterNa ),
      (
        KafkaConf gs.ProdUn f edUserAct onsEngage ntOnly,
        Constants.UuaProdEnv,
        Constants.UuaEngage ntOnlyKafkaTop cNa ,
        Constants.UuaKafkaProdClusterNa ),
      (
        KafkaConf gs.Stag ngUn f edUserAct ons,
        Constants.UuaStag ngEnv,
        Constants.UuaKafkaTop cNa ,
        Constants.UuaKafkaStag ngClusterNa ),
      (
        KafkaConf gs.Stag ngUn f edUserAct onsEngage ntOnly,
        Constants.UuaStag ngEnv,
        Constants.UuaEngage ntOnlyKafkaTop cNa ,
        Constants.UuaKafkaStag ngClusterNa )
    )

    states.foreach {
      case (actual, expectedEnv, expectedTop c, expectedClusterNa ) =>
        assert(expectedEnv == actual.env ron nt.na , s" n $actual")
        assert(expectedTop c == actual.top c, s" n $actual")
        assert(expectedClusterNa  == actual.cluster.na , s" n $actual")
      case _ =>
    }
  }
}
