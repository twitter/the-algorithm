package com.X.timelineranker.common

import com.X.servo.util.FutureArrow
import com.X.timelineranker.core.CandidateEnvelope
import com.X.timelines.model.TweetId
import com.X.util.Future
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
