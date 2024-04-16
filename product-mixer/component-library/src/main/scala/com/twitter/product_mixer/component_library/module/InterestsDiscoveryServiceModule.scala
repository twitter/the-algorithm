package com.tw ter.product_m xer.component_l brary.module

 mport com.tw ter. nterests_d scovery.thr ftscala. nterestsD scoveryServ ce
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.convers ons.PercentOps._
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.ut l.Durat on

object  nterestsD scoveryServ ceModule
    extends Thr ft thodBu lderCl entModule[
       nterestsD scoveryServ ce.Serv cePerEndpo nt,
       nterestsD scoveryServ ce. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  overr de val label: Str ng = " nterests-d scovery-serv ce"

  overr de val dest: Str ng = "/s/ nterests_d scovery/ nterests_d scovery"

  overr de protected def conf gure thodBu lder(
     njector:  njector,
     thodBu lder:  thodBu lder
  ):  thodBu lder = {
     thodBu lder
      .w hT  outPerRequest(500.m ll s)
      .w hT  outTotal(1000.m ll s)
      . dempotent(5.percent)
  }

  overr de protected def sess onAcqu s  onT  out: Durat on = 500.m ll seconds
}
