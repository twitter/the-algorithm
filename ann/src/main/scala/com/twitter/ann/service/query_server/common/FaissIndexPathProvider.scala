package com.tw ter.ann.serv ce.query_server.common

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.search.common.f le.AbstractF le

case class Fa ss ndexPathProv der(
  overr de val m n ndexS zeBytes: Long,
  overr de val max ndexS zeBytes: Long,
  overr de val statsRece ver: StatsRece ver)
    extends Base ndexPathProv der {

  overr de val log = Logger.get("FA SS ndexPathProv der")

  overr de def  sVal d ndex(d r: AbstractF le): Boolean = {
    d r. sD rectory &&
    d r.hasSuccessF le &&
    d r.getCh ld("fa ss. ndex").ex sts()
  }
}
