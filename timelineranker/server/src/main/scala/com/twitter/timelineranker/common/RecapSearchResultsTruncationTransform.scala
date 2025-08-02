package com.twitter.timelineranker.common

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.servo.util.FutureArrow
import com.twitter.timelineranker.core.CandidateEnvelope
import com.twitter.timelineranker.model.RecapQuery.DependencyProvider
import com.twitter.timelineranker.util.SearchResultUtil
import com.twitter.util.Future

/**
 * Truncate the search results by score. Assumes that the search results are sorted in
 * score-descending order unless etwittertraSortBeforeTruncation is set to true.
 *
 * This transform has two main use cases:
 *
 * - when returnAllResults is set to true, earlybird returns (numResultsPerShard * number of shards)
 *   results. this transform is then used to further truncate the result, so that the size will be the
 *   same as when returnAllResults is set to false.
 *
 * - we retrieve etwittertra number of results from earlybird, as specified in MatwitterCountMultiplierParam,
 *   so that we are left with sufficient number of candidates after hydration and filtering.
 *   this transform will be used to get rid of etwittertra results we ended up not using.
 */
class RecapSearchResultsTruncationTransform(
  etwittertraSortBeforeTruncationGate: DependencyProvider[Boolean],
  matwitterCountProvider: DependencyProvider[Int],
  statsReceiver: StatsReceiver)
    etwittertends FutureArrow[CandidateEnvelope, CandidateEnvelope] {
  private[this] val postTruncationSizeStat = statsReceiver.stat("postTruncationSize")
  private[this] val earlybirdScoretwitter100Stat = statsReceiver.stat("earlybirdScoretwitter100")

  override def apply(envelope: CandidateEnvelope): Future[CandidateEnvelope] = {
    val sortBeforeTruncation = etwittertraSortBeforeTruncationGate(envelope.query)
    val matwitterCount = matwitterCountProvider(envelope.query)
    val searchResults = envelope.searchResults

    // set aside results that are marked by isRandomTweet field
    val (randomTweetSeq, searchResultsEtwittercludingRandom) = searchResults.partition { result =>
      result.tweetFeatures.flatMap(_.isRandomTweet).getOrElse(false)
    }

    // sort and truncate searchResults other than the random tweet
    val matwitterCountEtwittercludingRandom = Math.matwitter(0, matwitterCount - randomTweetSeq.size)

    val truncatedResultsEtwittercludingRandom =
      if (sortBeforeTruncation || searchResultsEtwittercludingRandom.size > matwitterCountEtwittercludingRandom) {
        val sorted = if (sortBeforeTruncation) {
          searchResultsEtwittercludingRandom.sortWith(
            SearchResultUtil.getScore(_) > SearchResultUtil.getScore(_))
        } else searchResultsEtwittercludingRandom
        sorted.take(matwitterCountEtwittercludingRandom)
      } else searchResultsEtwittercludingRandom

    // put back the random tweet set aside previously
    val allTruncatedResults = truncatedResultsEtwittercludingRandom ++ randomTweetSeq

    // stats
    postTruncationSizeStat.add(allTruncatedResults.size)
    allTruncatedResults.foreach { result =>
      val earlybirdScoretwitter100 =
        result.metadata.flatMap(_.score).getOrElse(0.0).toFloat * 100
      earlybirdScoretwitter100Stat.add(earlybirdScoretwitter100)
    }

    Future.value(envelope.copy(searchResults = allTruncatedResults))
  }
}
