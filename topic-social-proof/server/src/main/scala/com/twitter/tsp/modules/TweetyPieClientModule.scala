package com.tw ter.tsp
package modules

 mport com.google. nject.Module
 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps.r chDurat onFrom nt
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent.MtlsThr ftMuxCl entSyntax
 mport com.tw ter.f nagle.mux.Cl entD scardedRequestExcept on
 mport com.tw ter.f nagle.serv ce.ReqRep
 mport com.tw ter.f nagle.serv ce.ResponseClass
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.t etyp e.thr ftscala.T etServ ce
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Throw
 mport com.tw ter.st ch.t etyp e.{T etyP e => ST etyP e}
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport javax. nject.S ngleton

object T etyP eCl entModule
    extends Thr ft thodBu lderCl entModule[
      T etServ ce.Serv cePerEndpo nt,
      T etServ ce. thodPerEndpo nt
    ]
    w h MtlsCl ent {
  overr de val label = "t etyp e"
  overr de val dest = "/s/t etyp e/t etyp e"
  overr de val requestT  out: Durat on = 450.m ll seconds

  overr de val modules: Seq[Module] = Seq(TSPCl ent dModule)

  //   bump t  success rate from t  default of 0.8 to 0.9 s nce  're dropp ng t 
  // consecut ve fa lures part of t  default pol cy.
  overr de def conf gureThr ftMuxCl ent(
     njector:  njector,
    cl ent: Thr ftMux.Cl ent
  ): Thr ftMux.Cl ent =
    super
      .conf gureThr ftMuxCl ent( njector, cl ent)
      .w hMutualTls( njector. nstance[Serv ce dent f er])
      .w hStatsRece ver( njector. nstance[StatsRece ver].scope("clnt"))
      .w hCl ent d( njector. nstance[Cl ent d])
      .w hResponseClass f er {
        case ReqRep(_, Throw(_: Cl entD scardedRequestExcept on)) => ResponseClass. gnorable
      }
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
