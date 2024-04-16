package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.alert

 mport com.tw ter.product_m xer.component_l brary.model.cand date.ShowAlertCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.alert.BaseShowAlert conD splay nfoBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.ShowAlert conD splay nfo
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class Stat cShowAlert conD splay nfoBu lder[-Query <: P pel neQuery](
   conD splay nfo: ShowAlert conD splay nfo)
    extends BaseShowAlert conD splay nfoBu lder[Query] {

  def apply(
    query: Query,
    cand date: ShowAlertCand date,
    features: FeatureMap
  ): Opt on[ShowAlert conD splay nfo] = So ( conD splay nfo)
}
