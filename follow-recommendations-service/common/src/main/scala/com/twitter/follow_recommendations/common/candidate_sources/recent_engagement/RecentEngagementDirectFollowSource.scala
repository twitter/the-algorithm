package com.ExTwitter.follow_recommendations.common.candidate_sources.recent_engagement

import com.ExTwitter.follow_recommendations.common.clients.real_time_real_graph.RealTimeRealGraphClient
import com.ExTwitter.follow_recommendations.common.models.CandidateUser
import com.ExTwitter.hermit.model.Algorithm
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecentEngagementDirectFollowSource @Inject() (
  realTimeRealGraphClient: RealTimeRealGraphClient)
    extends CandidateSource[Long, CandidateUser] {

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
