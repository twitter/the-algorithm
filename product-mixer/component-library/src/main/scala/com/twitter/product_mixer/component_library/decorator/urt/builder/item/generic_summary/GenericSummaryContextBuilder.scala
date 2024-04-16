package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.gener c_summary

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.r chtext.BaseR chTextBu lder
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. con.Hor zon con
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.gener c_summary.Gener cSummaryContext
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class Gener cSummaryContextBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]](
  r chTextBu lder: BaseR chTextBu lder[Query, Cand date],
   con: Opt on[Hor zon con] = None) {

  def apply(
    query: Query,
    cand date: Cand date,
    cand dateFeatures: FeatureMap
  ): Gener cSummaryContext = Gener cSummaryContext(
    r chTextBu lder.apply(query, cand date, cand dateFeatures),
     con
  )
}
