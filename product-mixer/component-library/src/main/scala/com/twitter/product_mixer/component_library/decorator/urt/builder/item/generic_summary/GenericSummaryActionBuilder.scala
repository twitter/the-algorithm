package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.gener c_summary

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.gener c_summary.Gener cSummaryAct onBu lder.Gener cSummaryAct onCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseUrlBu lder
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.gener c_summary.Gener cSummaryAct on
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object Gener cSummaryAct onBu lder {
  val Gener cSummaryAct onCl entEvent nfoEle nt: Str ng = "gener csummary-act on"
}

case class Gener cSummaryAct onBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]](
  urlBu lder: BaseUrlBu lder[Query, Cand date],
  cl entEvent nfoBu lder: Opt on[BaseCl entEvent nfoBu lder[Query, Cand date]] = None) {

  def apply(
    query: Query,
    cand date: Cand date,
    cand dateFeatures: FeatureMap
  ): Gener cSummaryAct on = Gener cSummaryAct on(
    url = urlBu lder.apply(query, cand date, cand dateFeatures),
    cl entEvent nfo = cl entEvent nfoBu lder.flatMap(
      _.apply(
        query,
        cand date,
        cand dateFeatures,
        So (Gener cSummaryAct onCl entEvent nfoEle nt)))
  )
}
