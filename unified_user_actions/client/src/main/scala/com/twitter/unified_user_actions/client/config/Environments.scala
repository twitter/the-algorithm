package com.tw ter.un f ed_user_act ons.cl ent.conf g

sealed tra  Env ron ntConf g {
  val na : Str ng
}

object Env ron nts {
  case object Prod extends Env ron ntConf g {
    overr de val na : Str ng = Constants.UuaProdEnv
  }

  case object Stag ng extends Env ron ntConf g {
    overr de val na : Str ng = Constants.UuaStag ngEnv
  }
}
