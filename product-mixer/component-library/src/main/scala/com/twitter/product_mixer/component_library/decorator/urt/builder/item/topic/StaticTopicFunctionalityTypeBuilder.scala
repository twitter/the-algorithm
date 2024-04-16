package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.top c

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseTop cCand date
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.top c.BaseTop cFunct onal yTypeBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.Top cFunct onal yType
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class Stat cTop cFunct onal yTypeBu lder(
  funct onal yType: Top cFunct onal yType)
    extends BaseTop cFunct onal yTypeBu lder[P pel neQuery, BaseTop cCand date] {

  overr de def apply(
    query: P pel neQuery,
    cand date: BaseTop cCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[Top cFunct onal yType] = So (funct onal yType)
}
