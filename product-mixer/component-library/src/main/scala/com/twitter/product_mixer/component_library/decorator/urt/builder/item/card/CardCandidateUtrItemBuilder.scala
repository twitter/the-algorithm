package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.card

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.card.CardCand dateUtr emBu lder.CardCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.CardCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseStr
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseUrlBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.card.CardD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.card.Card em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object CardCand dateUtr emBu lder {
  val CardCl entEvent nfoEle nt: Str ng = "card"
}

case class CardCand dateUtr emBu lder[-Query <: P pel neQuery](
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, CardCand date],
  cardUrlBu lder: BaseStr[Query, CardCand date],
  textBu lder: Opt on[BaseStr[Query, CardCand date]],
  subtextBu lder: Opt on[BaseStr[Query, CardCand date]],
  urlBu lder: Opt on[BaseUrlBu lder[Query, CardCand date]],
  cardD splayType: Opt on[CardD splayType],
  feedbackAct on nfoBu lder: Opt on[
    BaseFeedbackAct on nfoBu lder[Query, CardCand date],
  ] = None)
    extends Cand dateUrtEntryBu lder[Query, CardCand date, Card em] {

  overr de def apply(
    query: Query,
    cardCand date: CardCand date,
    cand dateFeatures: FeatureMap
  ): Card em = Card em(
     d = cardCand date. d,
    sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
    cl entEvent nfo = cl entEvent nfoBu lder(
      query,
      cardCand date,
      cand dateFeatures,
      So (CardCl entEvent nfoEle nt)),
    feedbackAct on nfo =
      feedbackAct on nfoBu lder.flatMap(_.apply(query, cardCand date, cand dateFeatures)),
    cardUrl = cardUrlBu lder(query, cardCand date, cand dateFeatures),
    text = textBu lder.map(_.apply(query, cardCand date, cand dateFeatures)),
    subtext = textBu lder.map(_.apply(query, cardCand date, cand dateFeatures)),
    url = urlBu lder.map(_.apply(query, cardCand date, cand dateFeatures)),
    d splayType = cardD splayType
  )
}
