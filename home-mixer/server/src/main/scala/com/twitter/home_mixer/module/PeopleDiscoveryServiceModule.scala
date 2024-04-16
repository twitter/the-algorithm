package com.tw ter.ho _m xer.module

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.peopled scovery.ap .thr ftscala.Thr ftPeopleD scoveryServ ce
 mport com.tw ter.ut l.Durat on

/**
 * Copy of com.tw ter.product_m xer.component_l brary.module.PeopleD scoveryServ ceModule
 */
object PeopleD scoveryServ ceModule
    extends Thr ft thodBu lderCl entModule[
      Thr ftPeopleD scoveryServ ce.Serv cePerEndpo nt,
      Thr ftPeopleD scoveryServ ce. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  overr de val label: Str ng = "people-d scovery-ap "

  overr de val dest: Str ng = "/s/people-d scovery-ap /people-d scovery-ap :thr ft"

  overr de protected def conf gure thodBu lder(
     njector:  njector,
     thodBu lder:  thodBu lder
  ):  thodBu lder = {
     thodBu lder
      .w hT  outPerRequest(350.m ll s)
      .w hT  outTotal(350.m ll s)
  }

  overr de protected def sess onAcqu s  onT  out: Durat on = 500.m ll seconds
}
