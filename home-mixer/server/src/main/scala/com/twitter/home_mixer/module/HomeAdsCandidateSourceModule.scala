package com.tw ter.ho _m xer.module

 mport com.tw ter.adserver.thr ftscala.NewAdServer
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.ut l.Durat on

object Ho AdsCand dateS ceModule
    extends Thr ft thodBu lderCl entModule[
      NewAdServer.Serv cePerEndpo nt,
      NewAdServer. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  overr de val label = "adserver"
  overr de val dest = "/s/ads/adserver"

  overr de protected def conf gure thodBu lder(
     njector:  njector,
     thodBu lder:  thodBu lder
  ):  thodBu lder = {
     thodBu lder
      .w hT  outPerRequest(1200.m ll seconds)
      .w hT  outTotal(1200.m ll seconds)
      .w hMaxRetr es(2)
  }

  overr de protected def sess onAcqu s  onT  out: Durat on = 150.m ll seconds
}
