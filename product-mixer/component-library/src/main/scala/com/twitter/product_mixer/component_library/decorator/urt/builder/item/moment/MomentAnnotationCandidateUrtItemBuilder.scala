package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.mo nt

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.mo nt.Mo ntAnnotat onCand dateUrt emBu lder.Mo ntAnnotat on emCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.Mo ntAnnotat onCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.r chtext.BaseR chTextBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.mo nt.Mo ntAnnotat on em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object Mo ntAnnotat onCand dateUrt emBu lder {
  val Mo ntAnnotat on emCl entEvent nfoEle nt = " tadata"
}

case class Mo ntAnnotat onCand dateUrt emBu lder[Query <: P pel neQuery](
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, Mo ntAnnotat onCand date],
  annotat onTextR chTextBu lder: BaseR chTextBu lder[Query, Mo ntAnnotat onCand date],
  annotat on aderR chTextBu lder: BaseR chTextBu lder[Query, Mo ntAnnotat onCand date],
  feedbackAct on nfoBu lder: Opt on[
    BaseFeedbackAct on nfoBu lder[Query, Mo ntAnnotat onCand date]
  ] = None,
) extends Cand dateUrtEntryBu lder[Query, Mo ntAnnotat onCand date, Mo ntAnnotat on em] {

  overr de def apply(
    query: Query,
    cand date: Mo ntAnnotat onCand date,
    cand dateFeatures: FeatureMap
  ): Mo ntAnnotat on em = Mo ntAnnotat on em(
     d = cand date. d,
    sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
    cl entEvent nfo = cl entEvent nfoBu lder(
      query,
      cand date,
      cand dateFeatures,
      So (Mo ntAnnotat on emCl entEvent nfoEle nt)),
    feedbackAct on nfo =
      feedbackAct on nfoBu lder.flatMap(_.apply(query, cand date, cand dateFeatures)),
     sP nned = None,
    text =
      cand date.text.map(_ => annotat onTextR chTextBu lder(query, cand date, cand dateFeatures)),
     ader = cand date. ader.map(_ =>
      annotat on aderR chTextBu lder(query, cand date, cand dateFeatures)),
  )
}
