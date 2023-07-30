package com.X.timelineranker.common

import com.X.finagle.stats.StatsReceiver
import com.X.servo.util.FutureArrow
import com.X.timelineranker.core.CandidateEnvelope
import com.X.timelines.clients.relevance_search.SearchClient
import com.X.timelines.model.TweetId
import com.X.util.Future

trait RecapHydrationSearchResultsTransformBase
    extends FutureArrow[CandidateEnvelope, CandidateEnvelope] {
  protected def statsReceiver: StatsReceiver
  protected def searchClient: SearchClient
  private[this] val numResultsFromSearchStat = statsReceiver.stat("numResultsFromSearch")

  def tweetIdsToHydrate(envelope: CandidateEnvelope): Seq[TweetId]

  override def apply(envelope: CandidateEnvelope): Future[CandidateEnvelope] = {
    searchClient
      .getTweetsScoredForRecap(
        envelope.query.userId,
        tweetIdsToHydrate(envelope),
        envelope.query.earlybirdOptions
      ).map { results =>
        numResultsFromSearchStat.add(results.size)
        envelope.copy(searchResults = results)
      }
  }
}
