package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.trend

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.trend.TrendCand dateUrt emBu lder.TrendsCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.TrendDescr pt on
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.TrendDoma nContext
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.TrendGroupedTrends
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.TrendNormal zedTrendNa 
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.TrendTrendNa 
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.TrendT etCount
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.TrendUrl
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.Un f edTrendCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.promoted.BasePromoted tadataBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.trend.Trend em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object TrendCand dateUrt emBu lder {
  f nal val TrendsCl entEvent nfoEle nt = "trend"
}

case class TrendCand dateUrt emBu lder[Query <: P pel neQuery](
  trend taDescr pt onBu lder: Trend taDescr pt onBu lder[Query, Un f edTrendCand date],
  promoted tadataBu lder: BasePromoted tadataBu lder[Query, Un f edTrendCand date],
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, Un f edTrendCand date],
  feedbackAct on nfoBu lder: Opt on[BaseFeedbackAct on nfoBu lder[Query, Un f edTrendCand date]] =
    None)
    extends Cand dateUrtEntryBu lder[Query, Un f edTrendCand date, T  l ne em] {

  overr de def apply(
    query: Query,
    cand date: Un f edTrendCand date,
    cand dateFeatures: FeatureMap
  ): T  l ne em = {
    Trend em(
       d = cand date. d,
      sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
      cl entEvent nfo = cl entEvent nfoBu lder(
        query = query,
        cand date = cand date,
        cand dateFeatures = cand dateFeatures,
        ele nt = So (TrendsCl entEvent nfoEle nt)
      ),
      feedbackAct on nfo = None,
      normal zedTrendNa  = cand dateFeatures.get(TrendNormal zedTrendNa ),
      trendNa  = cand dateFeatures.get(TrendTrendNa ),
      url = cand dateFeatures.get(TrendUrl),
      descr pt on = cand dateFeatures.getOrElse(TrendDescr pt on, None),
       taDescr pt on = trend taDescr pt onBu lder(query, cand date, cand dateFeatures),
      t etCount = cand dateFeatures.getOrElse(TrendT etCount, None),
      doma nContext = cand dateFeatures.getOrElse(TrendDoma nContext, None),
      promoted tadata = promoted tadataBu lder(
        query = query,
        cand date = cand date,
        cand dateFeatures = cand dateFeatures
      ),
      groupedTrends = cand dateFeatures.getOrElse(TrendGroupedTrends, None)
    )
  }
}
