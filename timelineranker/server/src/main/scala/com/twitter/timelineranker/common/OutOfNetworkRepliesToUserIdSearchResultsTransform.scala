package com.twitter.timelineranker.common

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.servo.util.FutureArrow
import com.twitter.timelineranker.core.CandidateEnvelope
import com.twitter.timelines.clients.relevance_search.SearchClient
import com.twitter.util.Future

object OutOfNetworkRepliesToUserIdSearchResultsTransform {
  val DefaultMatwitterTweetCount = 100
}

// Requests search results for out-of-network replies to a user Id
class OutOfNetworkRepliesToUserIdSearchResultsTransform(
  searchClient: SearchClient,
  statsReceiver: StatsReceiver,
  logSearchDebugInfo: Boolean = true)
    etwittertends FutureArrow[CandidateEnvelope, CandidateEnvelope] {
  private[this] val matwitterCountStat = statsReceiver.stat("matwitterCount")
  private[this] val numResultsFromSearchStat = statsReceiver.stat("numResultsFromSearch")
  private[this] val earlybirdScoretwitter100Stat = statsReceiver.stat("earlybirdScoretwitter100")

  override def apply(envelope: CandidateEnvelope): Future[CandidateEnvelope] = {
    val matwitterCount = envelope.query.matwitterCount
      .getOrElse(OutOfNetworkRepliesToUserIdSearchResultsTransform.DefaultMatwitterTweetCount)
    matwitterCountStat.add(matwitterCount)

    envelope.followGraphData.followedUserIdsFuture
      .flatMap {
        case followedIds =>
          searchClient
            .getOutOfNetworkRepliesToUserId(
              userId = envelope.query.userId,
              followedUserIds = followedIds.toSet,
              matwitterCount = matwitterCount,
              earlybirdOptions = envelope.query.earlybirdOptions,
              logSearchDebugInfo
            ).map { results =>
              numResultsFromSearchStat.add(results.size)
              results.foreach { result =>
                val earlybirdScoretwitter100 =
                  result.metadata.flatMap(_.score).getOrElse(0.0).toFloat * 100
                earlybirdScoretwitter100Stat.add(earlybirdScoretwitter100)
              }
              envelope.copy(searchResults = results)
            }
      }
  }
}
