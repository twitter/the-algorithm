package com.tw ter.ho _m xer.funct onal_component.feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features.RealGraph nNetworkScoresFeature
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.RealGraph nNetworkScores
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.wtf.cand date.{thr ftscala => wtf}
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

@S ngleton
case class RealGraph nNetworkScoresQueryFeatureHydrator @ nject() (
  @Na d(RealGraph nNetworkScores) store: ReadableStore[Long, Seq[wtf.Cand date]])
    extends QueryFeatureHydrator[P pel neQuery] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("RealGraph nNetworkScores")

  overr de val features: Set[Feature[_, _]] = Set(RealGraph nNetworkScoresFeature)

  pr vate val RealGraphCand dateCount = 1000

  overr de def hydrate(query: P pel neQuery): St ch[FeatureMap] = {
    St ch.callFuture(store.get(query.getRequ redUser d)).map { realGraphFollo dUsers =>
      val realGraphScoresFeatures = realGraphFollo dUsers
        .getOrElse(Seq.empty)
        .sortBy(-_.score)
        .map(cand date => cand date.user d -> scaleScore(cand date.score))
        .take(RealGraphCand dateCount)
        .toMap

      FeatureMapBu lder().add(RealGraph nNetworkScoresFeature, realGraphScoresFeatures).bu ld()
    }
  }

  // Rescale Real Graph v2 scores from [0,1] to t  v1 scores d str but on [1,2.97]
  pr vate def scaleScore(score: Double): Double =
     f (score >= 0.0 && score <= 1.0) score * 1.97 + 1.0 else score
}
