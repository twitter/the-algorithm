package com.tw ter.v s b l y.bu lder.t ets

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.search.common.constants.thr ftscala.Thr ftQueryS ce
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.features.SearchCand dateCount
 mport com.tw ter.v s b l y.features.SearchQueryHasUser
 mport com.tw ter.v s b l y.features.SearchQueryS ce
 mport com.tw ter.v s b l y.features.SearchResultsPageNumber
 mport com.tw ter.v s b l y. nterfaces.common.blender.BlenderVFRequestContext

@Deprecated
class BlenderContextFeatures(
  statsRece ver: StatsRece ver) {
  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("blender_context_features")
  pr vate[t ] val requests = scopedStatsRece ver.counter("requests")
  pr vate[t ] val searchResultsPageNumber =
    scopedStatsRece ver.scope(SearchResultsPageNumber.na ).counter("requests")
  pr vate[t ] val searchCand dateCount =
    scopedStatsRece ver.scope(SearchCand dateCount.na ).counter("requests")
  pr vate[t ] val searchQueryS ce =
    scopedStatsRece ver.scope(SearchQueryS ce.na ).counter("requests")
  pr vate[t ] val searchQueryHasUser =
    scopedStatsRece ver.scope(SearchQueryHasUser.na ).counter("requests")

  def forBlenderContext(
    blenderContext: BlenderVFRequestContext
  ): FeatureMapBu lder => FeatureMapBu lder = {
    requests. ncr()
    searchResultsPageNumber. ncr()
    searchCand dateCount. ncr()
    searchQueryS ce. ncr()
    searchQueryHasUser. ncr()

    _.w hConstantFeature(SearchResultsPageNumber, blenderContext.resultsPageNumber)
      .w hConstantFeature(SearchCand dateCount, blenderContext.cand dateCount)
      .w hConstantFeature(
        SearchQueryS ce,
        blenderContext.queryS ceOpt on match {
          case So (queryS ce) => queryS ce
          case _ => Thr ftQueryS ce.Unknown
        })
      .w hConstantFeature(SearchQueryHasUser, blenderContext.queryHasUser)
  }
}
