package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.top c

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.top c.Top cCand dateUrt emBu lder.Top cCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseTop cCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.top c.BaseTop cD splayTypeBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.top c.BaseTop cFunct onal yTypeBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.Top c em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object Top cCand dateUrt emBu lder {
  val Top cCl entEvent nfoEle nt: Str ng = "top c"
}

case class Top cCand dateUrt emBu lder[-Query <: P pel neQuery, Cand date <: BaseTop cCand date](
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, Cand date],
  top cFunct onal yTypeBu lder: Opt on[BaseTop cFunct onal yTypeBu lder[Query, Cand date]] = None,
  top cD splayTypeBu lder: Opt on[BaseTop cD splayTypeBu lder[Query, Cand date]] = None,
  feedbackAct on nfoBu lder: Opt on[
    BaseFeedbackAct on nfoBu lder[Query, Cand date]
  ] = None)
    extends Cand dateUrtEntryBu lder[Query, Cand date, Top c em] {

  overr de def apply(
    query: Query,
    top cCand date: Cand date,
    cand dateFeatures: FeatureMap
  ): Top c em =
    Top c em(
       d = top cCand date. d,
      sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
      cl entEvent nfo = cl entEvent nfoBu lder(
        query,
        top cCand date,
        cand dateFeatures,
        So (Top cCl entEvent nfoEle nt)),
      feedbackAct on nfo =
        feedbackAct on nfoBu lder.flatMap(_.apply(query, top cCand date, cand dateFeatures)),
      top cFunct onal yType =
        top cFunct onal yTypeBu lder.flatMap(_.apply(query, top cCand date, cand dateFeatures)),
      top cD splayType =
        top cD splayTypeBu lder.flatMap(_.apply(query, top cCand date, cand dateFeatures))
    )
}
