package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.trend

 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.PromotedTrendDescr pt onFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.PromotedTrendD sclosureTypeFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.PromotedTrend dFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.PromotedTrend mpress on dFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.PromotedTrendNa Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.promoted.BasePromoted tadataBu lder
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Promoted tadata
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object TrendPromoted tadataBu lder
    extends BasePromoted tadataBu lder[P pel neQuery, Un versalNoun[Any]] {

  overr de def apply(
    query: P pel neQuery,
    cand date: Un versalNoun[Any],
    cand dateFeatures: FeatureMap
  ): Opt on[Promoted tadata] = {
    //  f a promoted trend na  ex sts, t n t   s a promoted trend
    cand dateFeatures.getOrElse(PromotedTrendNa Feature, None).map { promotedTrendNa  =>
      Promoted tadata(
        // T   s t  current product behav or that advert ser d  s always set to 0L.
        // Correct advert ser na  co s from Trend's trend tadata. taDescr pt on.
        advert ser d = 0L,
        d sclosureType = cand dateFeatures.getOrElse(PromotedTrendD sclosureTypeFeature, None),
        exper  ntValues = None,
        promotedTrend d = cand dateFeatures.getOrElse(PromotedTrend dFeature, None),
        promotedTrendNa  = So (promotedTrendNa ),
        promotedTrendQueryTerm = None,
        ad tadataConta ner = None,
        promotedTrendDescr pt on =
          cand dateFeatures.getOrElse(PromotedTrendDescr pt onFeature, None),
         mpress onStr ng = cand dateFeatures.getOrElse(PromotedTrend mpress on dFeature, None),
        cl ckTrack ng nfo = None
      )
    }
  }
}
