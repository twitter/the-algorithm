package com.tw ter.cr_m xer.module.thr ft_cl ent

 mport com.tw ter.app.Flag
 mport com.tw ter.cr_m xer.module.core.T  outConf gModule.UtegCl entT  outFlagNa 
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.mux.Cl entD scardedRequestExcept on
 mport com.tw ter.f nagle.serv ce.ReqRep
 mport com.tw ter.f nagle.serv ce.ResponseClass
 mport com.tw ter.f nagle.serv ce.RetryBudget
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.UserT etEnt yGraph
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Throw

object UserT etEnt yGraphCl entModule
    extends Thr ft thodBu lderCl entModule[
      UserT etEnt yGraph.Serv cePerEndpo nt,
      UserT etEnt yGraph. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  overr de val label = "user-t et-ent y-graph"
  overr de val dest = "/s/cassowary/user_t et_ent y_graph"
  pr vate val userT etEnt yGraphCl entT  out: Flag[Durat on] =
    flag[Durat on](UtegCl entT  outFlagNa , "user t et ent y graph cl ent t  out")
  overr de def requestT  out: Durat on = userT etEnt yGraphCl entT  out()

  overr de def retryBudget: RetryBudget = RetryBudget.Empty

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
