package com.twitter.timelineranker.common

import com.twitter.servo.util.FutureArrow
import com.twitter.timelineranker.core.CandidateEnvelope
import com.twitter.timelines.model.TweetId
import com.twitter.util.Future
import scala.collection.mutable

/**
 * Remove duplicate search results and order them reverse-chron.
 */
object SearchResultDedupAndSortingTransform
    extends FutureArrow[CandidateEnvelope, CandidateEnvelope] {
  def apply(envelope: CandidateEnvelope): Future[CandidateEnvelope] = {
    val seenTweetIds = mutable.Set.empty[TweetId]
    val dedupedResults = envelope.searchResults
      .filter(result => seenTweetIds.add(result.id))
      .sortBy(_.id)(Ordering[TweetId].reverse)

    val transformedEnvelope = envelope.copy(searchResults = dedupedResults)
    Future.value(transformedEnvelope)
  }
}
