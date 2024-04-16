package com.tw ter.cr_m xer.module.thr ft_cl ent

 mport com.tw ter.app.Flag
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.cr_m xer.module.core.T  outConf gModule.FrsCl entT  outFlagNa 
 mport com.tw ter.f nagle.serv ce.RetryBudget
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter.follow_recom ndat ons.thr ftscala.FollowRecom ndat onsThr ftServ ce
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.ut l.Durat on

object FrsCl entModule
    extends Thr ft thodBu lderCl entModule[
      FollowRecom ndat onsThr ftServ ce.Serv cePerEndpo nt,
      FollowRecom ndat onsThr ftServ ce. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  overr de def label: Str ng = "follow-recom ndat ons-serv ce"
  overr de def dest: Str ng = "/s/follow-recom ndat ons/follow-recos-serv ce"

  pr vate val frsS gnalFetchT  out: Flag[Durat on] =
    flag[Durat on](FrsCl entT  outFlagNa , "FRS s gnal fetch cl ent t  out")
  overr de def requestT  out: Durat on = frsS gnalFetchT  out()

  overr de def retryBudget: RetryBudget = RetryBudget.Empty

  overr de def conf gureThr ftMuxCl ent(
     njector:  njector,
    cl ent: Thr ftMux.Cl ent
  ): Thr ftMux.Cl ent = {
    super
      .conf gureThr ftMuxCl ent( njector, cl ent)
      .w hStatsRece ver( njector. nstance[StatsRece ver].scope("clnt"))
      .w hSess onQual f er
      .successRateFa lureAccrual(successRate = 0.9, w ndow = 30.seconds)
  }
}
