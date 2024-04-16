package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.com rce

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.com rce.Com rceProductGroupCand dateUrt emBu lder.Com rceProductGroupCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.Com rceProductGroupCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.com rce.Com rceProductGroup em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object Com rceProductGroupCand dateUrt emBu lder {
  val Com rceProductGroupCl entEvent nfoEle nt: Str ng = "com rce-product-group"
}

case class Com rceProductGroupCand dateUrt emBu lder[-Query <: P pel neQuery](
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, Com rceProductGroupCand date],
  feedbackAct on nfoBu lder: Opt on[
    BaseFeedbackAct on nfoBu lder[Query, Com rceProductGroupCand date]
  ]) extends Cand dateUrtEntryBu lder[
      Query,
      Com rceProductGroupCand date,
      Com rceProductGroup em
    ] {

  overr de def apply(
    query: Query,
    cand date: Com rceProductGroupCand date,
    cand dateFeatures: FeatureMap
  ): Com rceProductGroup em =
    Com rceProductGroup em(
       d = cand date. d,
      sort ndex = None,
      cl entEvent nfo = cl entEvent nfoBu lder(
        query,
        cand date,
        cand dateFeatures,
        So (Com rceProductGroupCl entEvent nfoEle nt)),
      feedbackAct on nfo =
        feedbackAct on nfoBu lder.flatMap(_.apply(query, cand date, cand dateFeatures))
    )
}
