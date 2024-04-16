package com.tw ter.product_m xer.component_l brary.scorer.cr_ml_ranker

 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query.cr_ml_ranker.CrMlRankerCommonFeatures
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query.cr_ml_ranker.CrMlRankerRank ngConf g
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.scorer.Scorer
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scorer dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

object CrMlRankerScore extends Feature[T etCand date, Double]

/**
 * Scorer that scores t ets us ng t  Content Recom nder ML L ght Ranker: http://go/cr-ml-ranker
 */
@S ngleton
class CrMlRankerScorer @ nject() (crMlRanker: CrMlRankerScoreSt chCl ent)
    extends Scorer[P pel neQuery, T etCand date] {

  overr de val  dent f er: Scorer dent f er = Scorer dent f er("CrMlRanker")

  overr de val features: Set[Feature[_, _]] = Set(CrMlRankerScore)

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = {
    val queryFeatureMap = query.features.getOrElse(FeatureMap.empty)
    val rank ngConf g = queryFeatureMap.get(CrMlRankerRank ngConf g)
    val commonFeatures = queryFeatureMap.get(CrMlRankerCommonFeatures)
    val user d = query.getRequ redUser d

    val scoresSt ch = St ch.collect(cand dates.map { cand dateW hFeatures =>
      crMlRanker
        .getScore(user d, cand dateW hFeatures.cand date, rank ngConf g, commonFeatures).map(
          _.score)
    })
    scoresSt ch.map { scores =>
      scores.map { score =>
        FeatureMapBu lder()
          .add(CrMlRankerScore, score)
          .bu ld()
      }
    }
  }
}
