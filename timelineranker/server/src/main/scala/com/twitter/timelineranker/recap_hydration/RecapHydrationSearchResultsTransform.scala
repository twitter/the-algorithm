package com.twitter.timelineranker.recap_hydration

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.timelineranker.common.RecapHydrationSearchResultsTransformBase
import com.twitter.timelineranker.core.CandidateEnvelope
import com.twitter.timelines.clients.relevance_search.SearchClient
import com.twitter.timelines.model.TweetId

class RecapHydrationSearchResultsTransform(
  override protected val searchClient: SearchClient,
  override protected val statsReceiver: StatsReceiver)
    extends RecapHydrationSearchResultsTransformBase {
  override def tweetIdsToHydrate(envelope: CandidateEnvelope): Seq[TweetId] =
    envelope.query.tweetIds.get
}
