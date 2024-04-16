package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.eventbus.cl ent.EventBusPubl s r
 mport com.tw ter.eventbus.cl ent.EventBusPubl s rBu lder
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.t  l nes.conf g.Conf gUt ls
 mport com.tw ter.t  l nes.conf g.Env
 mport com.tw ter.t  l nes. mpress onstore.thr ftscala.Publ s d mpress onL st
 mport javax. nject.S ngleton

object Cl entSent mpress onsPubl s rModule extends Tw terModule w h Conf gUt ls {
  pr vate val serv ceNa  = "ho -m xer"

  @S ngleton
  @Prov des
  def prov desCl entSent mpress onsPubl s r(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): EventBusPubl s r[Publ s d mpress onL st] = {
    val env = serv ce dent f er.env ron nt.toLo rCase match {
      case "prod" => Env.prod
      case "stag ng" => Env.stag ng
      case "local" => Env.local
      case _ => Env.devel
    }

    val streamNa  = env match {
      case Env.prod => "t  l nem xer_cl ent_sent_ mpress ons_prod"
      case _ => "t  l nem xer_cl ent_sent_ mpress ons_devel"
    }

    EventBusPubl s rBu lder()
      .cl ent d(cl ent dW hScopeOpt(serv ceNa , env))
      .serv ce dent f er(serv ce dent f er)
      .streamNa (streamNa )
      .statsRece ver(statsRece ver.scope("eventbus"))
      .thr ftStruct(Publ s d mpress onL st)
      .tcpConnectT  out(20.m ll seconds)
      .connectT  out(100.m ll seconds)
      .requestT  out(1.second)
      .publ shT  out(1.second)
      .bu ld()
  }
}
