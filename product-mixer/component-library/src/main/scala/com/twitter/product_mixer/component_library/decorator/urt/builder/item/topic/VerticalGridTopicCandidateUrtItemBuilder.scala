package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.top c

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.top c.Top cCand dateUrt emBu lder.Top cCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.Top cCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseUrlBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.vert cal_gr d_ em.Vert calGr d em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.vert cal_gr d_ em.Vert calGr d emT leStyle
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.vert cal_gr d_ em.Vert calGr d emTop cFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.vert cal_gr d_ em.Vert calGr d emTop cT le
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class Vert calGr dTop cCand dateUrt emBu lder[-Query <: P pel neQuery](
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, Top cCand date],
  vert calGr d emTop cFunct onal yType: Vert calGr d emTop cFunct onal yType,
  vert calGr d emT leStyle: Vert calGr d emT leStyle,
  urlBu lder: Opt on[BaseUrlBu lder[Query, Top cCand date]] = None,
  feedbackAct on nfoBu lder: Opt on[
    BaseFeedbackAct on nfoBu lder[Query, Top cCand date]
  ] = None)
    extends Cand dateUrtEntryBu lder[Query, Top cCand date, Vert calGr d em] {

  overr de def apply(
    query: Query,
    top cCand date: Top cCand date,
    cand dateFeatures: FeatureMap
  ): Vert calGr d em = {
    Vert calGr d emTop cT le(
       d = top cCand date. d,
      sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
      cl entEvent nfo = cl entEvent nfoBu lder(
        query,
        top cCand date,
        cand dateFeatures,
        So (Top cCl entEvent nfoEle nt)),
      feedbackAct on nfo =
        feedbackAct on nfoBu lder.flatMap(_.apply(query, top cCand date, cand dateFeatures)),
      style = So (vert calGr d emT leStyle),
      funct onal yType = So (vert calGr d emTop cFunct onal yType),
      url = urlBu lder.map(_.apply(query, top cCand date, cand dateFeatures))
    )
  }
}
