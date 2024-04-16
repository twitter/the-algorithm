package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.com rce

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.com rce.Com rceProductCand dateUrt emBu lder.Com rceProductCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.Com rceProductCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.com rce.Com rceProduct em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object Com rceProductCand dateUrt emBu lder {
  val Com rceProductCl entEvent nfoEle nt: Str ng = "com rce-product"
}

case class Com rceProductCand dateUrt emBu lder[-Query <: P pel neQuery](
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, Com rceProductCand date],
  feedbackAct on nfoBu lder: Opt on[BaseFeedbackAct on nfoBu lder[Query, Com rceProductCand date]])
    extends Cand dateUrtEntryBu lder[
      Query,
      Com rceProductCand date,
      Com rceProduct em
    ] {

  overr de def apply(
    query: Query,
    cand date: Com rceProductCand date,
    cand dateFeatures: FeatureMap
  ): Com rceProduct em =
    Com rceProduct em(
       d = cand date. d,
      sort ndex = None,
      cl entEvent nfo = cl entEvent nfoBu lder(
        query,
        cand date,
        cand dateFeatures,
        So (Com rceProductCl entEvent nfoEle nt)),
      feedbackAct on nfo =
        feedbackAct on nfoBu lder.flatMap(_.apply(query, cand date, cand dateFeatures))
    )
}
