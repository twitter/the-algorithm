package com.tw ter.cr_m xer.module.thr ft_cl ent

 mport com.tw ter.app.Flag
 mport com.tw ter.cr_m xer.module.core.T  outConf gModule.UserT etGraphPlusCl entT  outFlagNa 
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.mux.Cl entD scardedRequestExcept on
 mport com.tw ter.f nagle.serv ce.ReqRep
 mport com.tw ter.f nagle.serv ce.ResponseClass
 mport com.tw ter.f nagle.serv ce.RetryBudget
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.recos.user_t et_graph_plus.thr ftscala.UserT etGraphPlus
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Throw

object UserT etGraphPlusCl entModule
    extends Thr ft thodBu lderCl entModule[
      UserT etGraphPlus.Serv cePerEndpo nt,
      UserT etGraphPlus. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  overr de val label = "user-t et-graph-plus"
  overr de val dest = "/s/user-t et-graph/user-t et-graph-plus"
  pr vate val userT etGraphPlusCl entT  out: Flag[Durat on] =
    flag[Durat on](
      UserT etGraphPlusCl entT  outFlagNa ,
      "userT etGraphPlus cl ent t  out"
    )
  overr de def requestT  out: Durat on = userT etGraphPlusCl entT  out()

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
