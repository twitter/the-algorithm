package com.X.timelineranker.uteg_liked_by_tweets

import com.X.search.earlybird.thriftscala.ThriftSearchResult
import com.X.servo.util.FutureArrow
import com.X.timelineranker.core.CandidateEnvelope
import com.X.timelines.model.UserId
import com.X.util.Future

object RemoveCandidatesAuthoredByWeightedFollowingsTransform
    extends FutureArrow[CandidateEnvelope, CandidateEnvelope] {
  override def apply(envelope: CandidateEnvelope): Future[CandidateEnvelope] = {
    val filteredSearchResults = envelope.query.utegLikedByTweetsOptions match {
      case Some(opts) =>
        envelope.searchResults.filterNot(isAuthorInWeightedFollowings(_, opts.weightedFollowings))
      case None => envelope.searchResults
    }
    Future.value(envelope.copy(searchResults = filteredSearchResults))
  }

  private def isAuthorInWeightedFollowings(
    searchResult: ThriftSearchResult,
    weightedFollowings: Map[UserId, Double]
  ): Boolean = {
    searchResult.metadata match {
      case Some(metadata) => weightedFollowings.contains(metadata.fromUserId)
      case None => false
    }
  }
}
