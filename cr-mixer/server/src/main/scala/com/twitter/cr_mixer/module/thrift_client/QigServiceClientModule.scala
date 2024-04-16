package com.tw ter.cr_m xer.module.thr ft_cl ent

 mport com.tw ter.app.Flag
 mport com.tw ter.cr_m xer.module.core.T  outConf gModule.Q gRankerCl entT  outFlagNa 
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.mux.Cl entD scardedRequestExcept on
 mport com.tw ter.f nagle.serv ce.ReqRep
 mport com.tw ter.f nagle.serv ce.ResponseClass
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.q g_ranker.thr ftscala.Q gRanker
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Throw

object Q gServ ceCl entModule
    extends Thr ft thodBu lderCl entModule[
      Q gRanker.Serv cePerEndpo nt,
      Q gRanker. thodPerEndpo nt
    ]
    w h MtlsCl ent {
  overr de val label: Str ng = "q g-ranker"
  overr de val dest: Str ng = "/s/q g-shared/q g-ranker"
  pr vate val q gRankerCl entT  out: Flag[Durat on] =
    flag[Durat on](Q gRankerCl entT  outFlagNa , "rank ng t  out")

  overr de def requestT  out: Durat on = q gRankerCl entT  out()

  overr de def conf gureThr ftMuxCl ent(
     njector:  njector,
    cl ent: Thr ftMux.Cl ent
  ): Thr ftMux.Cl ent =
    super
      .conf gureThr ftMuxCl ent( njector, cl ent)
      .w hStatsRece ver( njector. nstance[StatsRece ver].scope("clnt"))
      .w hResponseClass f er {
        case ReqRep(_, Throw(_: Cl entD scardedRequestExcept on)) => ResponseClass. gnorable
      }
}
