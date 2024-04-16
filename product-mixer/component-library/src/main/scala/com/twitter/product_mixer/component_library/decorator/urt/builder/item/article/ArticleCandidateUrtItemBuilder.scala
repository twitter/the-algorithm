package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.art cle

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.art cle.Art cleCand dateUrt emBu lder.Art cleCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseArt cleCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.soc al_context.BaseSoc alContextBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.art cle.Art cleD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.art cle.Art cle em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.art cle.Art cleSeedType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.art cle.Follow ngL stSeed
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object Art cleCand dateUrt emBu lder {
  val Art cleCl entEvent nfoEle nt: Str ng = "art cle"
}

case class Art cleCand dateUrt emBu lder[
  -Query <: P pel neQuery,
  Cand date <: BaseArt cleCand date
](
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, Cand date],
  art cleSeedType: Art cleSeedType = Follow ngL stSeed,
  feedbackAct on nfoBu lder: Opt on[
    BaseFeedbackAct on nfoBu lder[Query, Cand date]
  ] = None,
  d splayType: Opt on[Art cleD splayType] = None,
  soc alContextBu lder: Opt on[BaseSoc alContextBu lder[Query, Cand date]] = None,
) extends Cand dateUrtEntryBu lder[Query, Cand date, Art cle em] {

  overr de def apply(
    query: Query,
    art cleCand date: Cand date,
    cand dateFeatures: FeatureMap
  ): Art cle em = Art cle em(
     d = art cleCand date. d,
    sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
    cl entEvent nfo = cl entEvent nfoBu lder(
      query,
      art cleCand date,
      cand dateFeatures,
      So (Art cleCl entEvent nfoEle nt)),
    feedbackAct on nfo =
      feedbackAct on nfoBu lder.flatMap(_.apply(query, art cleCand date, cand dateFeatures)),
    d splayType = d splayType,
    soc alContext =
      soc alContextBu lder.flatMap(_.apply(query, art cleCand date, cand dateFeatures)),
    art cleSeedType = art cleSeedType
  )
}
