package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.tw ter_l st

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.tw ter_l st.Tw terL stCand dateUrt emBu lder.L stCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.Tw terL stCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tw ter_l st.Tw terL stD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tw ter_l st.Tw terL st em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object Tw terL stCand dateUrt emBu lder {
  val L stCl entEvent nfoEle nt: Str ng = "l st"
}

case class Tw terL stCand dateUrt emBu lder[-Query <: P pel neQuery](
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, Tw terL stCand date],
  feedbackAct on nfoBu lder: Opt on[
    BaseFeedbackAct on nfoBu lder[Query, Tw terL stCand date]
  ] = None,
  d splayType: Opt on[Tw terL stD splayType] = None)
    extends Cand dateUrtEntryBu lder[Query, Tw terL stCand date, Tw terL st em] {

  overr de def apply(
    query: Query,
    tw terL stCand date: Tw terL stCand date,
    cand dateFeatures: FeatureMap
  ): Tw terL st em = Tw terL st em(
     d = tw terL stCand date. d,
    sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
    cl entEvent nfo = cl entEvent nfoBu lder(
      query,
      tw terL stCand date,
      cand dateFeatures,
      So (L stCl entEvent nfoEle nt)),
    feedbackAct on nfo =
      feedbackAct on nfoBu lder.flatMap(_.apply(query, tw terL stCand date, cand dateFeatures)),
    d splayType = d splayType
  )
}
