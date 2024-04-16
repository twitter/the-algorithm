package com.tw ter.cr_m xer.module.thr ft_cl ent

 mport com.tw ter.app.Flag
 mport com.tw ter.cr_m xer.module.core.T  outConf gModule.UserAdGraphCl entT  outFlagNa 
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent.MtlsThr ftMuxCl entSyntax
 mport com.tw ter.f nagle.mux.Cl entD scardedRequestExcept on
 mport com.tw ter.f nagle.serv ce.ReqRep
 mport com.tw ter.f nagle.serv ce.ResponseClass
 mport com.tw ter.f nagle.serv ce.RetryBudget
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.recos.user_ad_graph.thr ftscala.UserAdGraph
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Throw

object UserAdGraphCl entModule
    extends Thr ft thodBu lderCl entModule[
      UserAdGraph.Serv cePerEndpo nt,
      UserAdGraph. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  overr de val label = "user-ad-graph"
  overr de val dest = "/s/user-t et-graph/user-ad-graph"
  pr vate val userAdGraphCl entT  out: Flag[Durat on] =
    flag[Durat on](UserAdGraphCl entT  outFlagNa , "userAdGraph cl ent t  out")
  overr de def requestT  out: Durat on = userAdGraphCl entT  out()

  overr de def retryBudget: RetryBudget = RetryBudget.Empty

  overr de def conf gureThr ftMuxCl ent(
     njector:  njector,
    cl ent: Thr ftMux.Cl ent
  ): Thr ftMux.Cl ent =
    super
      .conf gureThr ftMuxCl ent( njector, cl ent)
      .w hMutualTls( njector. nstance[Serv ce dent f er])
      .w hStatsRece ver( njector. nstance[StatsRece ver].scope("clnt"))
      .w hResponseClass f er {
        case ReqRep(_, Throw(_: Cl entD scardedRequestExcept on)) => ResponseClass. gnorable
      }

}
