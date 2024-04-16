package com.tw ter.product_m xer.component_l brary.module

 mport com.tw ter.follow_recom ndat ons.thr ftscala.FollowRecom ndat onsThr ftServ ce
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.convers ons.PercentOps._
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.ut l.Durat on

object FollowRecom nderServ ceModule
    extends Thr ft thodBu lderCl entModule[
      FollowRecom ndat onsThr ftServ ce.Serv cePerEndpo nt,
      FollowRecom ndat onsThr ftServ ce. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  overr de val label: Str ng = "follow-recom ndat ons-serv ce"

  overr de val dest: Str ng = "/s/follow-recom ndat ons/follow-recos-serv ce"

  overr de protected def conf gure thodBu lder(
     njector:  njector,
     thodBu lder:  thodBu lder
  ):  thodBu lder = {
     thodBu lder
      .w hT  outPerRequest(400.m ll s)
      .w hT  outTotal(800.m ll s)
      . dempotent(5.percent)
  }

  overr de protected def sess onAcqu s  onT  out: Durat on = 500.m ll seconds
}
