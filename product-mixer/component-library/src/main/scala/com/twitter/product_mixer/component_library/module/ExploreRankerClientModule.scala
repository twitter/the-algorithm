package com.tw ter.product_m xer.component_l brary.module

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.explore_ranker.thr ftscala.ExploreRanker
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject.annotat ons.Flags
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.ut l.Durat on

object ExploreRankerCl entModule
    extends Thr ft thodBu lderCl entModule[
      ExploreRanker.Serv cePerEndpo nt,
      ExploreRanker. thodPerEndpo nt
    ]
    w h MtlsCl ent {

  overr de val label: Str ng = "explore-ranker"
  overr de val dest: Str ng = "/s/explore-ranker/explore-ranker"

  pr vate f nal val ExploreRankerT  outTotal = "explore_ranker.t  out_total"

  flag[Durat on](
    na  = ExploreRankerT  outTotal,
    default = 800.m ll seconds,
     lp = "T  out total for ExploreRanker")

  overr de protected def conf gure thodBu lder(
     njector:  njector,
     thodBu lder:  thodBu lder
  ):  thodBu lder = {
    val t  outTotal: Durat on =  njector. nstance[Durat on](Flags.na d(ExploreRankerT  outTotal))
     thodBu lder
      .w hT  outTotal(t  outTotal)
      .non dempotent
  }
}
