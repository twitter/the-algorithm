package com.tw ter.s mclustersann.except ons

case class M ss ngClusterConf gForS mClustersAnnVar antExcept on(sannServ ceNa : Str ng)
    extends  llegalStateExcept on(
      s"No cluster conf gurat on found for serv ce ($sannServ ceNa )",
      null)
