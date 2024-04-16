package com.tw ter.product_m xer.component_l brary.feature_hydrator.query.cr_ml_ranker

 mport com.tw ter.cr_ml_ranker.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Bu lds a query hydrator that hydrates Common Features for t  g ven Query from CR ML Ranker
 * to be later used to call CR ML Ranker for scor ng us ng t  des red [[Rank ngConf gBu lder]]
 * for bu ld ng t  rank ng conf g.
 */
@S ngleton
class CrMlRankerCommonQueryFeatureHydratorBu lder @ nject() (
  crMlRanker: t.CrMLRanker. thodPerEndpo nt) {

  def bu ld(rank ngConf gSelector: Rank ngConf gBu lder): CrMlRankerCommonQueryFeatureHydrator =
    new CrMlRankerCommonQueryFeatureHydrator(crMlRanker, rank ngConf gSelector)
}
