package com.twitter.follow_recommendations.common.candidate_sources.salsa

import com.twitter.follow_recommendations.common.clients.real_time_real_graph.RealTimeRealGraphClient
import com.twitter.hermit.model.Algorithm
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class RecentEngagementDirectFollowSalsaEtwitterpansionSource @Inject() (
  realTimeRealGraphClient: RealTimeRealGraphClient,
  salsaEtwitterpander: SalsaEtwitterpander)
    etwittertends SalsaEtwitterpansionBasedCandidateSource[Long](salsaEtwitterpander) {

  override val identifier: CandidateSourceIdentifier =
    RecentEngagementDirectFollowSalsaEtwitterpansionSource.Identifier

  override def firstDegreeNodes(target: Long): Stitch[Seq[Long]] = realTimeRealGraphClient
    .getUsersRecentlyEngagedWith(
      target,
      RealTimeRealGraphClient.EngagementScoreMap,
      includeDirectFollowCandidates = true,
      includeNonDirectFollowCandidates = false
    ).map { recentlyFollowed =>
      recentlyFollowed
        .take(RecentEngagementDirectFollowSalsaEtwitterpansionSource.NumFirstDegreeNodesToRetrieve)
        .map(_.id)
    }

  override def matwitterResults(target: Long): Int =
    RecentEngagementDirectFollowSalsaEtwitterpansionSource.OutputSize
}

object RecentEngagementDirectFollowSalsaEtwitterpansionSource {
  val Identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    Algorithm.RecentEngagementSarusOcCur.toString)
  val NumFirstDegreeNodesToRetrieve = 10
  val OutputSize = 200
}
