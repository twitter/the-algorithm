package com.X.timelineranker.uteg_liked_by_tweets

import com.X.finagle.stats.StatsReceiver
import com.X.timelineranker.common.RecapHydrationSearchResultsTransformBase
import com.X.timelineranker.core.CandidateEnvelope
import com.X.timelineranker.model.RecapQuery.DependencyProvider
import com.X.timelines.clients.relevance_search.SearchClient
import com.X.timelines.model.TweetId
import com.X.util.Future

class UtegLikedByTweetsSearchResultsTransform(
  override protected val searchClient: SearchClient,
  override protected val statsReceiver: StatsReceiver,
  relevanceSearchProvider: DependencyProvider[Boolean])
    extends RecapHydrationSearchResultsTransformBase {

  private[this] val numResultsFromSearchStat = statsReceiver.stat("numResultsFromSearch")

  override def tweetIdsToHydrate(envelope: CandidateEnvelope): Seq[TweetId] =
    envelope.utegResults.keys.toSeq

  override def apply(envelope: CandidateEnvelope): Future[CandidateEnvelope] = {
    searchClient
      .getTweetsScoredForRecap(
        userId = envelope.query.userId,
        tweetIds = tweetIdsToHydrate(envelope),
        earlybirdOptions = envelope.query.earlybirdOptions,
        logSearchDebugInfo = false,
        searchClientId = None,
        relevanceSearch = relevanceSearchProvider(envelope.query)
      ).map { results =>
        numResultsFromSearchStat.add(results.size)
        envelope.copy(searchResults = results)
      }
  }
}
