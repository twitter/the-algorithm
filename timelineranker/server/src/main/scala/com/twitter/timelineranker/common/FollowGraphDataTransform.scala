package com.twitter.timelineranker.common

import com.twitter.servo.util.FutureArrow
import com.twitter.timelineranker.core.CandidateEnvelope
import com.twitter.timelineranker.model.RecapQuery.DependencyProvider
import com.twitter.timelineranker.visibility.FollowGraphDataProvider
import com.twitter.util.Future

class FollowGraphDataTransform(
  followGraphDataProvider: FollowGraphDataProvider,
  maxFollowedUsersProvider: DependencyProvider[Int])
    extends FutureArrow[CandidateEnvelope, CandidateEnvelope] {

  override def apply(envelope: CandidateEnvelope): Future[CandidateEnvelope] = {

    val followGraphData = followGraphDataProvider.getAsync(
      envelope.query.userId,
      maxFollowedUsersProvider(envelope.query)
    )

    Future.value(envelope.copy(followGraphData = followGraphData))
  }
}
