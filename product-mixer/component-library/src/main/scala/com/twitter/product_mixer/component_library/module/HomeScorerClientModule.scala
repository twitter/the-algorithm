package com.tw ter.product_m xer.component_l brary.module

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.convers ons.PercentOps._
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.ho _scorer.{thr ftscala => t}
 mport com.tw ter.ut l.Durat on

object Ho ScorerCl entModule
    extends Thr ft thodBu lderCl entModule[
      t.Ho Scorer.Serv cePerEndpo nt,
      t.Ho Scorer. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  overr de val label = "ho -scorer"
  overr de val dest = "/s/ho -scorer/ho -scorer"

  overr de protected def conf gure thodBu lder(
     njector:  njector,
     thodBu lder:  thodBu lder
  ):  thodBu lder = {
     thodBu lder
      .w hT  outPerRequest(1200.m ll s)
      .w hT  outTotal(2400.m ll s)
      . dempotent(1.percent)
  }

  overr de protected def sess onAcqu s  onT  out: Durat on = 500.m ll seconds
}
