package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.suggest on

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.suggest on.Spell ngSuggest onCand dateUrt emBu lder.Spell ng emCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.suggest on.Spell ngSuggest onCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.suggest on.Spell ng em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object Spell ngSuggest onCand dateUrt emBu lder {
  val Spell ng emCl entEvent nfoEle nt: Str ng = "spell ng"
}

case class Spell ngSuggest onCand dateUrt emBu lder[Query <: P pel neQuery](
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, Spell ngSuggest onCand date],
  feedbackAct on nfoBu lder: Opt on[
    BaseFeedbackAct on nfoBu lder[Query, Spell ngSuggest onCand date]
  ] = None,
) extends Cand dateUrtEntryBu lder[Query, Spell ngSuggest onCand date, Spell ng em] {

  overr de def apply(
    query: Query,
    cand date: Spell ngSuggest onCand date,
    cand dateFeatures: FeatureMap
  ): Spell ng em = Spell ng em(
     d = cand date. d,
    sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
    cl entEvent nfo = cl entEvent nfoBu lder(
      query,
      cand date,
      cand dateFeatures,
      So (Spell ng emCl entEvent nfoEle nt)),
    feedbackAct on nfo =
      feedbackAct on nfoBu lder.flatMap(_.apply(query, cand date, cand dateFeatures)),
    textResult = cand date.textResult,
    spell ngAct onType = cand date.spell ngAct onType,
    or g nalQuery = cand date.or g nalQuery
  )
}
