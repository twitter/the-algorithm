package com.tw ter.follow_recom ndat ons.common.cl ents.geoduck

 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.common.BaseCl entModule
 mport com.tw ter.geoduck.thr ftscala.Locat onServ ce

object Locat onServ ceModule
    extends BaseCl entModule[Locat onServ ce. thodPerEndpo nt]
    w h MtlsCl ent {
  overr de val label = "geoduck_locat onserv ce"
  overr de val dest = "/s/geo/geoduck_locat onserv ce"
}
