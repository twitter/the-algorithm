package com.tw ter.representat on_manager.modules

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent.MtlsThr ftMuxCl entSyntax
 mport com.tw ter.f nagle.mux.Cl entD scardedRequestExcept on
 mport com.tw ter.f nagle.serv ce.ReqRep
 mport com.tw ter.f nagle.serv ce.ResponseClass
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nterests.thr ftscala. nterestsThr ftServ ce
 mport com.tw ter.ut l.Throw
 mport javax. nject.S ngleton

object  nterestsThr ftCl entModule extends Tw terModule {

  @S ngleton
  @Prov des
  def prov des nterestsThr ftCl ent(
    cl ent d: Cl ent d,
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ):  nterestsThr ftServ ce. thodPerEndpo nt = {
    Thr ftMux.cl ent
      .w hCl ent d(cl ent d)
      .w hMutualTls(serv ce dent f er)
      .w hRequestT  out(450.m ll seconds)
      .w hStatsRece ver(statsRece ver.scope(" nterestsThr ftCl ent"))
      .w hResponseClass f er {
        case ReqRep(_, Throw(_: Cl entD scardedRequestExcept on)) => ResponseClass. gnorable
      }
      .bu ld[ nterestsThr ftServ ce. thodPerEndpo nt](
        dest = "/s/ nterests-thr ft-serv ce/ nterests-thr ft-serv ce",
        label = " nterests_thr ft_serv ce"
      )
  }
}
