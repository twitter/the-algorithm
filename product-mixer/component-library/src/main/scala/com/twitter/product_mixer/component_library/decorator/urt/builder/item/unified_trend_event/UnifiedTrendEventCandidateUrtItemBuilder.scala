package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.un f ed_trend_event

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.event_summary.EventCand dateUrt emBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.trend.TrendCand dateUrt emBu lder
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.Un f edEventCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.Un f edTrendCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.Un f edTrendEventCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class Un f edTrendEventCand dateUrt emBu lder[Query <: P pel neQuery](
  eventCand dateUrt emBu lder: EventCand dateUrt emBu lder[Query],
  trendCand dateUrt emBu lder: TrendCand dateUrt emBu lder[Query])
    extends Cand dateUrtEntryBu lder[Query, Un f edTrendEventCand date[Any], T  l ne em] {

  overr de def apply(
    query: Query,
    cand date: Un f edTrendEventCand date[Any],
    cand dateFeatures: FeatureMap
  ): T  l ne em = {
    cand date match {
      case event: Un f edEventCand date =>
        eventCand dateUrt emBu lder(
          query = query,
          cand date = event,
          cand dateFeatures = cand dateFeatures)
      case trend: Un f edTrendCand date =>
        trendCand dateUrt emBu lder(
          query = query,
          cand date = trend,
          cand dateFeatures = cand dateFeatures)
    }
  }
}
