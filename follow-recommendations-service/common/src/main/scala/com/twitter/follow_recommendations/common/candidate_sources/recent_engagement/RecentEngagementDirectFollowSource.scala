package com.twitter.follow_recommendations.common.candidate_sources.recent_engagement

import com.twitter.follow_recommendations.common.clients.real_time_real_graph.RealTimeRealGraphClient
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.hermit.model.Algorithm
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class RecentEngagementDirectFollowSource @Inject() (
  realTimeRealGraphClient: RealTimeRealGraphClient)
    etwittertends CandidateSource[Long, CandidateUser] {

  val identifier: CandidateSourceIdentifier =
    RecentEngagementDirectFollowSource.Identifier

  /**
   * Generate a list of candidates for the target using RealtimeGraphClient
   * and RecentEngagementStore.
   */
  override def apply(targetUserId: Long): Stitch[Seq[CandidateUser]] = {
    realTimeRealGraphClient
      .getUsersRecentlyEngagedWith(
        userId = targetUserId,
        engagementScoreMap = RealTimeRealGraphClient.EngagementScoreMap,
        includeDirectFollowCandidates = true,
        includeNonDirectFollowCandidates = false
      )
      .map(_.map(_.withCandidateSource(identifier)).sortBy(-_.score.getOrElse(0.0)))
  }
}

object RecentEngagementDirectFollowSource {
  val Identifier = CandidateSourceIdentifier(Algorithm.RecentEngagementDirectFollow.toString)
}
