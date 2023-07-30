package com.X.follow_recommendations.common.candidate_sources.recent_engagement

import com.X.follow_recommendations.common.clients.real_time_real_graph.RealTimeRealGraphClient
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.hermit.model.Algorithm
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecentEngagementNonDirectFollowSource @Inject() (
  realTimeRealGraphClient: RealTimeRealGraphClient)
    extends CandidateSource[Long, CandidateUser] {

  val identifier: CandidateSourceIdentifier =
    RecentEngagementNonDirectFollowSource.Identifier

  /**
   * Generate a list of candidates for the target using RealtimeGraphClient
   * and RecentEngagementStore.
   */
  override def apply(targetUserId: Long): Stitch[Seq[CandidateUser]] = {
    realTimeRealGraphClient
      .getUsersRecentlyEngagedWith(
        userId = targetUserId,
        engagementScoreMap = RealTimeRealGraphClient.EngagementScoreMap,
        includeDirectFollowCandidates = false,
        includeNonDirectFollowCandidates = true
      )
      .map(_.map(_.withCandidateSource(identifier)).sortBy(-_.score.getOrElse(0.0)))
  }
}

object RecentEngagementNonDirectFollowSource {
  val Identifier = CandidateSourceIdentifier(Algorithm.RecentEngagementNonDirectFollow.toString)
}
