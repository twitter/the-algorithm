package com.tw ter.product_m xer.component_l brary.module

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.convers ons.PercentOps._
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.t  l nescorer.{thr ftscala => t}
 mport com.tw ter.ut l.Durat on

object T  l neScorerCl entModule
    extends Thr ft thodBu lderCl entModule[
      t.T  l neScorer.Serv cePerEndpo nt,
      t.T  l neScorer. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  overr de val label = "t  l ne-scorer"
  overr de val dest = "/s/t  l nescorer/t  l nescorer"

  overr de protected def conf gure thodBu lder(
     njector:  njector,
     thodBu lder:  thodBu lder
  ):  thodBu lder = {
     thodBu lder
      .w hT  outPerRequest(2000.m ll s)
      .w hT  outTotal(4000.m ll s)
      . dempotent(1.percent)
  }

  overr de protected def sess onAcqu s  onT  out: Durat on = 500.m ll seconds
}
