package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.t le

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.t le.T leCand dateUrt emBu lder.Top cT leCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.PromptCarouselT leCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t le.StandardT leContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t le.T le em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object T leCand dateUrt emBu lder {
  val Top cT leCl entEvent nfoEle nt: Str ng = "t le"
}

case class T leCand dateUrt emBu lder[-Query <: P pel neQuery](
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, PromptCarouselT leCand date],
  feedbackAct on nfoBu lder: Opt on[
    BaseFeedbackAct on nfoBu lder[Query, PromptCarouselT leCand date]
  ] = None)
    extends Cand dateUrtEntryBu lder[Query, PromptCarouselT leCand date, T le em] {

  overr de def apply(
    query: Query,
    t leCand date: PromptCarouselT leCand date,
    cand dateFeatures: FeatureMap
  ): T le em = T le em(
     d = t leCand date. d,
    sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
    cl entEvent nfo = cl entEvent nfoBu lder(
      query,
      t leCand date,
      cand dateFeatures,
      So (Top cT leCl entEvent nfoEle nt)),
    t le = "", //T  data  s  gnored do
    support ngText = "",
    feedbackAct on nfo =
      feedbackAct on nfoBu lder.flatMap(_.apply(query, t leCand date, cand dateFeatures)),
     mage = None,
    url = None,
    content = StandardT leContent(
      t le = "",
      support ngText = "",
      badge = None
    )
  )
}
