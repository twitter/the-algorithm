package com.tw ter.product_m xer.component_l brary.module.cr_ml_ranker

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.cr_ml_ranker.thr ftscala.CrMLRanker
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule
 mport com.tw ter.product_m xer.component_l brary.scorer.cr_ml_ranker.CrMlRankerScoreSt chCl ent
 mport com.tw ter.ut l.Durat on
 mport javax. nject.S ngleton

case class CrMLRankerModule(totalT  out: Durat on = 100.m ll seconds, batchS ze:  nt = 50)
    extends Thr ft thodBu lderCl entModule[
      CrMLRanker.Serv cePerEndpo nt,
      CrMLRanker. thodPerEndpo nt
    ]
    w h MtlsCl ent {
  overr de val label = "cr-ml-ranker"
  overr de val dest = "/s/cr-ml-ranker/cr-ml-ranker"

  overr de protected def conf gure thodBu lder(
     njector:  njector,
     thodBu lder:  thodBu lder
  ):  thodBu lder = {
     thodBu lder
      .w hT  outTotal(totalT  out)
  }

  @Prov des
  @S ngleton
  def prov desSt chCl ent(
    crMlRankerThr ftCl ent: CrMLRanker. thodPerEndpo nt
  ): CrMlRankerScoreSt chCl ent = new CrMlRankerScoreSt chCl ent(
    crMlRankerThr ftCl ent,
    maxBatchS ze = batchS ze
  )
}
