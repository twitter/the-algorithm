package com.tw ter.follow_recom ndat ons.common.cl ents.adserver

 mport com.tw ter.adserver.thr ftscala.NewAdServer
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.common.BaseCl entModule

object AdserverModule extends BaseCl entModule[NewAdServer. thodPerEndpo nt] w h MtlsCl ent {
  overr de val label = "adserver"
  overr de val dest = "/s/ads/adserver"

  overr de def conf gureThr ftMuxCl ent(cl ent: Thr ftMux.Cl ent): Thr ftMux.Cl ent =
    cl ent.w hRequestT  out(500.m ll s)
}
