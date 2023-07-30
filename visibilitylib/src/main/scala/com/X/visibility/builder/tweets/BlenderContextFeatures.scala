package com.X.visibility.builder.tweets

import com.X.finagle.stats.StatsReceiver
import com.X.search.common.constants.thriftscala.ThriftQuerySource
import com.X.visibility.builder.FeatureMapBuilder
import com.X.visibility.features.SearchCandidateCount
import com.X.visibility.features.SearchQueryHasUser
import com.X.visibility.features.SearchQuerySource
import com.X.visibility.features.SearchResultsPageNumber
import com.X.visibility.interfaces.common.blender.BlenderVFRequestContext

@Deprecated
class BlenderContextFeatures(
  statsReceiver: StatsReceiver) {
  private[this] val scopedStatsReceiver = statsReceiver.scope("blender_context_features")
  private[this] val requests = scopedStatsReceiver.counter("requests")
  private[this] val searchResultsPageNumber =
    scopedStatsReceiver.scope(SearchResultsPageNumber.name).counter("requests")
  private[this] val searchCandidateCount =
    scopedStatsReceiver.scope(SearchCandidateCount.name).counter("requests")
  private[this] val searchQuerySource =
    scopedStatsReceiver.scope(SearchQuerySource.name).counter("requests")
  private[this] val searchQueryHasUser =
    scopedStatsReceiver.scope(SearchQueryHasUser.name).counter("requests")

  def forBlenderContext(
    blenderContext: BlenderVFRequestContext
  ): FeatureMapBuilder => FeatureMapBuilder = {
    requests.incr()
    searchResultsPageNumber.incr()
    searchCandidateCount.incr()
    searchQuerySource.incr()
    searchQueryHasUser.incr()

    _.withConstantFeature(SearchResultsPageNumber, blenderContext.resultsPageNumber)
      .withConstantFeature(SearchCandidateCount, blenderContext.candidateCount)
      .withConstantFeature(
        SearchQuerySource,
        blenderContext.querySourceOption match {
          case Some(querySource) => querySource
          case _ => ThriftQuerySource.Unknown
        })
      .withConstantFeature(SearchQueryHasUser, blenderContext.queryHasUser)
  }
}
