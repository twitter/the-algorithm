package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.gener c_summary

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.gener c_summary.Gener cSummaryCand dateUrt emBu lder.Gener cSummaryCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.Gener cSummaryCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.r chtext.BaseR chTextBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.gener c_summary.Gener cSummary em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.gener c_summary.Gener cSummary emD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. d a. d a
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Promoted tadata
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.ut l.T  

object Gener cSummaryCand dateUrt emBu lder {
  val Gener cSummaryCl entEvent nfoEle nt: Str ng = "gener csummary"
}

case class Gener cSummaryCand dateUrt emBu lder[-Query <: P pel neQuery](
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, Gener cSummaryCand date],
   adl neR chTextBu lder: BaseR chTextBu lder[Query, Gener cSummaryCand date],
  d splayType: Gener cSummary emD splayType,
  gener cSummaryContextCand dateUrt emBu lder: Opt on[
    Gener cSummaryContextBu lder[Query, Gener cSummaryCand date]
  ] = None,
  gener cSummaryAct onCand dateUrt emBu lder: Opt on[
    Gener cSummaryAct onBu lder[Query, Gener cSummaryCand date]
  ] = None,
  t  stamp: Opt on[T  ] = None,
  userAttr but on ds: Opt on[Seq[Long]] = None,
   d a: Opt on[ d a] = None,
  promoted tadata: Opt on[Promoted tadata] = None,
  feedbackAct on nfoBu lder: Opt on[BaseFeedbackAct on nfoBu lder[Query, Gener cSummaryCand date]] =
    None)
    extends Cand dateUrtEntryBu lder[Query, Gener cSummaryCand date, Gener cSummary em] {

  overr de def apply(
    query: Query,
    gener cSummaryCand date: Gener cSummaryCand date,
    cand dateFeatures: FeatureMap
  ): Gener cSummary em = Gener cSummary em(
     d = gener cSummaryCand date. d,
    sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
    cl entEvent nfo = cl entEvent nfoBu lder(
      query,
      gener cSummaryCand date,
      cand dateFeatures,
      So (Gener cSummaryCl entEvent nfoEle nt)),
    feedbackAct on nfo =
      feedbackAct on nfoBu lder.flatMap(_.apply(query, gener cSummaryCand date, cand dateFeatures)),
     adl ne =  adl neR chTextBu lder.apply(query, gener cSummaryCand date, cand dateFeatures),
    d splayType = d splayType,
    userAttr but on ds = userAttr but on ds.getOrElse(Seq.empty),
     d a =  d a,
    context = gener cSummaryContextCand dateUrt emBu lder.map(
      _.apply(query, gener cSummaryCand date, cand dateFeatures)),
    t  stamp = t  stamp,
    onCl ckAct on = gener cSummaryAct onCand dateUrt emBu lder.map(
      _.apply(query, gener cSummaryCand date, cand dateFeatures)),
    promoted tadata = promoted tadata
  )
}
