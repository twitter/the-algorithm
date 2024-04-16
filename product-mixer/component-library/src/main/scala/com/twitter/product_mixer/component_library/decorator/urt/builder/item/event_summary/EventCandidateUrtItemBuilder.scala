package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.event_summary

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.event_summary.EventCand dateUrt emBu lder.EventCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.EventD splayType
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.Event mage
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.EventT  Str ng
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.EventT leFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.EventUrl
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.Un f edEventCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.event.EventSummary em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object EventCand dateUrt emBu lder {
  val EventCl entEvent nfoEle nt = "event"
}

case class EventCand dateUrt emBu lder[Query <: P pel neQuery](
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, Un f edEventCand date],
  feedbackAct on nfoBu lder: Opt on[BaseFeedbackAct on nfoBu lder[Query, Un f edEventCand date]] =
    None)
    extends Cand dateUrtEntryBu lder[Query, Un f edEventCand date, T  l ne em] {

  overr de def apply(
    query: Query,
    cand date: Un f edEventCand date,
    cand dateFeatures: FeatureMap
  ): T  l ne em = {
    EventSummary em(
       d = cand date. d,
      sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
      cl entEvent nfo = cl entEvent nfoBu lder(
        query = query,
        cand date = cand date,
        cand dateFeatures = cand dateFeatures,
        ele nt = So (EventCl entEvent nfoEle nt)
      ),
      feedbackAct on nfo =
        feedbackAct on nfoBu lder.flatMap(_.apply(query, cand date, cand dateFeatures)),
      t le = cand dateFeatures.get(EventT leFeature),
      d splayType = cand dateFeatures.get(EventD splayType),
      url = cand dateFeatures.get(EventUrl),
       mage = cand dateFeatures.getOrElse(Event mage, None),
      t  Str ng = cand dateFeatures.getOrElse(EventT  Str ng, None)
    )
  }
}
