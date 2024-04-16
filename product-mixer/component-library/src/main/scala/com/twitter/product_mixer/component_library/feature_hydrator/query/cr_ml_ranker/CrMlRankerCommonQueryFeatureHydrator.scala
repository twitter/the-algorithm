package com.tw ter.product_m xer.component_l brary.feature_hydrator.query.cr_ml_ranker

 mport com.tw ter.cr_ml_ranker.{thr ftscala => t}
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

object CrMlRankerCommonFeatures extends Feature[P pel neQuery, t.CommonFeatures]
object CrMlRankerRank ngConf g extends Feature[P pel neQuery, t.Rank ngConf g]

pr vate[cr_ml_ranker] class CrMlRankerCommonQueryFeatureHydrator(
  crMlRanker: t.CrMLRanker. thodPerEndpo nt,
  rank ngConf gSelector: Rank ngConf gBu lder)
    extends QueryFeatureHydrator[P pel neQuery] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("CrMlRanker")

  overr de val features: Set[Feature[_, _]] =
    Set(CrMlRankerCommonFeatures, CrMlRankerRank ngConf g)

  overr de def hydrate(query: P pel neQuery): St ch[FeatureMap] = {
    val rank ngConf g = rank ngConf gSelector.apply(query)
    St ch
      .callFuture(
        crMlRanker.getCommonFeatures(
          t.Rank ngRequestContext(query.getRequ redUser d, rank ngConf g))).map { commonFeatures =>
        FeatureMapBu lder()
          .add(CrMlRankerRank ngConf g, rank ngConf g)
          .add(CrMlRankerCommonFeatures, commonFeatures)
          .bu ld()
      }
  }
}
