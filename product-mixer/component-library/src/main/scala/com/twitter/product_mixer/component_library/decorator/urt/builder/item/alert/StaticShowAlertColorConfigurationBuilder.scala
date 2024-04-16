package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.alert

 mport com.tw ter.product_m xer.component_l brary.model.cand date.ShowAlertCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.alert.BaseShowAlertColorConf gurat onBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.ShowAlertColorConf gurat on
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class Stat cShowAlertColorConf gurat onBu lder[-Query <: P pel neQuery](
  conf gurat on: ShowAlertColorConf gurat on)
    extends BaseShowAlertColorConf gurat onBu lder[Query] {

  def apply(
    query: Query,
    cand date: ShowAlertCand date,
    features: FeatureMap
  ): ShowAlertColorConf gurat on = conf gurat on
}
