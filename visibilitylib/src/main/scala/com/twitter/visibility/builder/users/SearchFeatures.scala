package com.tw ter.v s b l y.bu lder.users

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.features._
 mport com.tw ter.v s b l y.context.thr ftscala.SearchContext

class SearchFeatures(statsRece ver: StatsRece ver) {
  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("search_features")
  pr vate[t ] val requests = scopedStatsRece ver.counter("requests")
  pr vate[t ] val rawQueryCounter =
    scopedStatsRece ver.scope(RawQuery.na ).counter("requests")

  def forSearchContext(
    searchContext: Opt on[SearchContext]
  ): FeatureMapBu lder => FeatureMapBu lder = { bu lder =>
    requests. ncr()
    searchContext match {
      case So (context: SearchContext) =>
        rawQueryCounter. ncr()
        bu lder
          .w hConstantFeature(RawQuery, context.rawQuery)
      case _ => bu lder
    }
  }
}
