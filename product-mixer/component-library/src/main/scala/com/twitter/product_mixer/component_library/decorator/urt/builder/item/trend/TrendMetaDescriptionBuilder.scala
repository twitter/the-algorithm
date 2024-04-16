package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.trend

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.str ngcenter.Str
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.PromotedTrendAdvert serNa Feature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.trends_events.TrendT etCount
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.trends.trend ng_content.ut l.Compact ngNumberLocal zer

case class Trend taDescr pt onBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]](
  promotedBy taDescr pt onStr: Str[P pel neQuery, Un versalNoun[Any]],
  t etCount taDescr pt onStr: Str[P pel neQuery, Un versalNoun[Any]],
  compact ngNumberLocal zer: Compact ngNumberLocal zer) {

  def apply(
    query: Query,
    cand date: Cand date,
    cand dateFeatures: FeatureMap
  ): Opt on[Str ng] = {
    val promoted taDescr pt on =
      cand dateFeatures.getOrElse(PromotedTrendAdvert serNa Feature, None).map { advert serNa  =>
        promotedBy taDescr pt onStr(query, cand date, cand dateFeatures).format(advert serNa )
      }

    val organ c taDescr pt on = cand dateFeatures.getOrElse(TrendT etCount, None).map {
      t etCount =>
        val compactedT etCount = compact ngNumberLocal zer.local zeAndCompact(
          query.getLanguageCode
            .getOrElse("en"),
          t etCount)
        t etCount taDescr pt onStr(query, cand date, cand dateFeatures).format(
          compactedT etCount)
    }

    promoted taDescr pt on.orElse(organ c taDescr pt on)
  }
}
