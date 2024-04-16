package com.tw ter.ho _m xer.funct onal_component.scorer

 mport com.tw ter.ho _m xer.model.Ho Features. nNetworkFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ScoreFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.scorer.Scorer
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scorer dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * Scales scores of each out-of-network t et by t  spec f ed scale factor
 */
object OONT etScal ngScorer extends Scorer[P pel neQuery, T etCand date] {

  overr de val  dent f er: Scorer dent f er = Scorer dent f er("OONT etScal ng")

  overr de val features: Set[Feature[_, _]] = Set(ScoreFeature)

  pr vate val ScaleFactor = 0.75

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = {
    St ch.value {
      cand dates.map { cand date =>
        val score = cand date.features.getOrElse(ScoreFeature, None)
        val updatedScore =  f (selector(cand date)) score.map(_ * ScaleFactor) else score
        FeatureMapBu lder().add(ScoreFeature, updatedScore).bu ld()
      }
    }
  }

  /**
   *   should only be apply ng t  mult pl er to Out-Of-Network t ets.
   *  n-Network Ret ets of Out-Of-Network t ets should not have t  mult pl er appl ed
   */
  pr vate def selector(cand date: Cand dateW hFeatures[T etCand date]): Boolean = {
    !cand date.features.getOrElse( nNetworkFeature, false) &&
    !cand date.features.getOrElse( sRet etFeature, false)
  }
}
