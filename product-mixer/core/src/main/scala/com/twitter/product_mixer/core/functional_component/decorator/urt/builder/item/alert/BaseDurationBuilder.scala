package com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.alert

 mport com.tw ter.product_m xer.component_l brary.model.cand date.ShowAlertCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.ut l.Durat on

tra  BaseDurat onBu lder[-Query <: P pel neQuery] {

  def apply(query: Query, cand date: ShowAlertCand date, features: FeatureMap): Opt on[Durat on]
}
