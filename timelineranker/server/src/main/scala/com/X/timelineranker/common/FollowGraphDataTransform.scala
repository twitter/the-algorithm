package com.X.timelineranker.common

import com.X.servo.util.FutureArrow
import com.X.timelineranker.core.CandidateEnvelope
import com.X.timelineranker.model.RecapQuery.DependencyProvider
import com.X.timelineranker.visibility.FollowGraphDataProvider
import com.X.util.Future

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
