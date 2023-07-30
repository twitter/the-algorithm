package com.X.timelineranker.recap_hydration

import com.X.finagle.stats.StatsReceiver
import com.X.timelineranker.common.RecapHydrationSearchResultsTransformBase
import com.X.timelineranker.core.CandidateEnvelope
import com.X.timelines.clients.relevance_search.SearchClient
import com.X.timelines.model.TweetId

class RecapHydrationSearchResultsTransform(
  override protected val searchClient: SearchClient,
  override protected val statsReceiver: StatsReceiver)
    extends RecapHydrationSearchResultsTransformBase {
  override def tweetIdsToHydrate(envelope: CandidateEnvelope): Seq[TweetId] =
    envelope.query.tweetIds.get
}
