package com.tw ter.users gnalserv ce.module

 mport com.tw ter. nject. njector
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle._
 mport com.tw ter.f nagle.mux.Cl entD scardedRequestExcept on
 mport com.tw ter.f nagle.serv ce.ReqRep
 mport com.tw ter.f nagle.serv ce.ResponseClass
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Throw
 mport com.tw ter.soc algraph.thr ftscala.Soc alGraphServ ce

object Soc alGraphServ ceCl entModule
    extends Thr ft thodBu lderCl entModule[
      Soc alGraphServ ce.Serv cePerEndpo nt,
      Soc alGraphServ ce. thodPerEndpo nt
    ]
    w h MtlsCl ent {
  overr de val label = "soc algraph"
  overr de val dest = "/s/soc algraph/soc algraph"
  overr de val requestT  out: Durat on = 30.m ll seconds

  overr de def conf gureThr ftMuxCl ent(
     njector:  njector,
    cl ent: Thr ftMux.Cl ent
  ): Thr ftMux.Cl ent = {
    super
      .conf gureThr ftMuxCl ent( njector, cl ent)
      .w hStatsRece ver( njector. nstance[StatsRece ver].scope("clnt"))
      .w hSess onQual f er
      .successRateFa lureAccrual(successRate = 0.9, w ndow = 30.seconds)
      .w hResponseClass f er {
        case ReqRep(_, Throw(_: Cl entD scardedRequestExcept on)) => ResponseClass. gnorable
      }
  }

}
