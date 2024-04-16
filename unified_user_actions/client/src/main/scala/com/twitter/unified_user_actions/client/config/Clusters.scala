package com.tw ter.un f ed_user_act ons.cl ent.conf g

sealed tra  ClusterConf g {
  val na : Str ng
  val env ron nt: Env ron ntConf g
}

object Clusters {
  /*
   *   product on cluster for external consumpt on.   SLAs are enforced.
   */
  case object ProdCluster extends ClusterConf g {
    overr de val na : Str ng = Constants.UuaKafkaProdClusterNa 
    overr de val env ron nt: Env ron ntConf g = Env ron nts.Prod
  }

  /*
   *   stag ng cluster for external develop nt and pre-releases. No SLAs are enforced.
   */
  case object Stag ngCluster extends ClusterConf g {
    overr de val na : Str ng = Constants.UuaKafkaStag ngClusterNa 
    overr de val env ron nt: Env ron ntConf g = Env ron nts.Stag ng
  }
}
