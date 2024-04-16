package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.alert

 mport com.tw ter.product_m xer.component_l brary.model.cand date.ShowAlertCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.alert.BaseDurat onBu lder
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on

case class Durat onParamBu lder(
  durat onParam: Param[Durat on])
    extends BaseDurat onBu lder[P pel neQuery] {

  def apply(
    query: P pel neQuery,
    cand date: ShowAlertCand date,
    features: FeatureMap
  ): Opt on[Durat on] =
    So (query.params(durat onParam))
}
