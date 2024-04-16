package com.tw ter.un f ed_user_act ons.cl ent.conf g

sealed tra  Cl entConf g {
  val cluster: ClusterConf g
  val top c: Str ng
  val env ron nt: Env ron ntConf g
}

class AbstractCl entConf g( sEngage ntOnly: Boolean, env: Env ron ntConf g) extends Cl entConf g {
  overr de val cluster: ClusterConf g = {
    env match {
      case Env ron nts.Prod => Clusters.ProdCluster
      case Env ron nts.Stag ng => Clusters.Stag ngCluster
      case _ => Clusters.ProdCluster
    }
  }

  overr de val top c: Str ng = {
     f ( sEngage ntOnly) Constants.UuaEngage ntOnlyKafkaTop cNa 
    else Constants.UuaKafkaTop cNa 
  }

  overr de val env ron nt: Env ron ntConf g = env
}

object KafkaConf gs {

  /*
   * Un f ed User Act ons Kafka conf g w h all events (engage nts and  mpress ons).
   * Use t  conf g w n   ma nly need  mpress on data and data volu   s not an  ssue.
   */
  case object ProdUn f edUserAct ons
      extends AbstractCl entConf g( sEngage ntOnly = false, env = Env ron nts.Prod)

  /*
   * Un f ed User Act ons Kafka conf g w h engage nts events only.
   * Use t  conf g w n   only need engage nt data. T  data volu  should be a lot smaller
   * than   ma n conf g.
   */
  case object ProdUn f edUserAct onsEngage ntOnly
      extends AbstractCl entConf g( sEngage ntOnly = true, env = Env ron nts.Prod)

  /*
   * Stag ng Env ron nt for  ntegrat on and test ng. T   s not a product on conf g.
   *
   * Un f ed User Act ons Kafka conf g w h all events (engage nts and  mpress ons).
   * Use t  conf g w n   ma nly need  mpress on data and data volu   s not an  ssue.
   */
  case object Stag ngUn f edUserAct ons
      extends AbstractCl entConf g( sEngage ntOnly = false, env = Env ron nts.Stag ng)

  /*
   * Stag ng Env ron nt for  ntegrat on and test ng. T   s not a product on conf g.
   *
   * Un f ed User Act ons Kafka conf g w h engage nts events only.
   * Use t  conf g w n   only need engage nt data. T  data volu  should be a lot smaller
   * than   ma n conf g.
   */
  case object Stag ngUn f edUserAct onsEngage ntOnly
      extends AbstractCl entConf g( sEngage ntOnly = true, env = Env ron nts.Stag ng)
}
