package com.tw ter.ho _m xer.funct onal_component.decorator.bu lder

 mport com.tw ter.ho _m xer.model.Ho Features.ScoreFeature
 mport com.tw ter.ho _m xer.param.Ho GlobalParams.EnableSendScoresToCl ent
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.t et.BaseT  l nesScore nfoBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T  l nesScore nfo
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object Ho T  l nesScore nfoBu lder
    extends BaseT  l nesScore nfoBu lder[P pel neQuery, T etCand date] {

  pr vate val Undef nedT etScore = -1.0

  overr de def apply(
    query: P pel neQuery,
    cand date: T etCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[T  l nesScore nfo] = {
     f (query.params(EnableSendScoresToCl ent)) {
      val score = cand dateFeatures.getOrElse(ScoreFeature, None).getOrElse(Undef nedT etScore)
      So (T  l nesScore nfo(score))
    } else None
  }
}
