package com.tw ter.cr_m xer.module.thr ft_cl ent

 mport com.google. nject.Prov des
 mport com.tw ter.app.Flag
 mport com.tw ter.convers ons.Durat onOps.r chDurat onFrom nt
 mport com.tw ter.cr_m xer.module.core.T  outConf gModule.T etyp eCl entT  outFlagNa 
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.mux.Cl entD scardedRequestExcept on
 mport com.tw ter.f nagle.serv ce.ReqRep
 mport com.tw ter.f nagle.serv ce.ResponseClass
 mport com.tw ter.f nagle.serv ce.RetryBudget
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.st ch.t etyp e.{T etyP e => ST etyP e}
 mport com.tw ter.t etyp e.thr ftscala.T etServ ce
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Throw
 mport javax. nject.S ngleton

object T etyP eCl entModule
    extends Thr ft thodBu lderCl entModule[
      T etServ ce.Serv cePerEndpo nt,
      T etServ ce. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  overr de val label = "t etyp e"
  overr de val dest = "/s/t etyp e/t etyp e"

  pr vate val t etyp eCl entT  out: Flag[Durat on] =
    flag[Durat on](T etyp eCl entT  outFlagNa , "t etyp e cl ent t  out")
  overr de def requestT  out: Durat on = t etyp eCl entT  out()

  overr de def retryBudget: RetryBudget = RetryBudget.Empty

  //   bump t  success rate from t  default of 0.8 to 0.9 s nce  're dropp ng t 
  // consecut ve fa lures part of t  default pol cy.
  overr de def conf gureThr ftMuxCl ent(
     njector:  njector,
    cl ent: Thr ftMux.Cl ent
  ): Thr ftMux.Cl ent =
    super
      .conf gureThr ftMuxCl ent( njector, cl ent)
      .w hStatsRece ver( njector. nstance[StatsRece ver].scope("clnt"))
      .w hSess onQual f er
      .successRateFa lureAccrual(successRate = 0.9, w ndow = 30.seconds)
      .w hResponseClass f er {
        case ReqRep(_, Throw(_: Cl entD scardedRequestExcept on)) => ResponseClass. gnorable
      }

  @Prov des
  @S ngleton
  def prov desT etyP e(
    t etyP eServ ce: T etServ ce. thodPerEndpo nt
  ): ST etyP e = {
    ST etyP e(t etyP eServ ce)
  }
}
