package com.tw ter.tsp.modules

 mport com.google. nject.Module
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter.g zmoduck.thr ftscala.UserServ ce
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule

object G zmoduckUserModule
    extends Thr ft thodBu lderCl entModule[
      UserServ ce.Serv cePerEndpo nt,
      UserServ ce. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  overr de val label: Str ng = "g zmoduck"
  overr de val dest: Str ng = "/s/g zmoduck/g zmoduck"
  overr de val modules: Seq[Module] = Seq(TSPCl ent dModule)

  overr de def conf gureThr ftMuxCl ent(
     njector:  njector,
    cl ent: Thr ftMux.Cl ent
  ): Thr ftMux.Cl ent = {
    super
      .conf gureThr ftMuxCl ent( njector, cl ent)
      .w hMutualTls( njector. nstance[Serv ce dent f er])
      .w hCl ent d( njector. nstance[Cl ent d])
      .w hStatsRece ver( njector. nstance[StatsRece ver].scope("g z"))
  }
}
