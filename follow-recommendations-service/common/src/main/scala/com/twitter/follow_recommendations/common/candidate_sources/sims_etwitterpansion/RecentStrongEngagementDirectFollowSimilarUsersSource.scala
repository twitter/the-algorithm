package com.twitter.follow_recommendations.common.candidate_sources.sims_etwitterpansion

import com.google.inject.Singleton
import com.twitter.follow_recommendations.common.candidate_sources.sims.SwitchingSimsSource
import com.twitter.follow_recommendations.common.clients.real_time_real_graph.RealTimeRealGraphClient
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.hermit.model.Algorithm
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasClientContetwittert
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams

import javatwitter.inject.Inject

@Singleton
class RecentStrongEngagementDirectFollowSimilarUsersSource @Inject() (
  realTimeRealGraphClient: RealTimeRealGraphClient,
  switchingSimsSource: SwitchingSimsSource)
    etwittertends SimsEtwitterpansionBasedCandidateSource[HasClientContetwittert with HasParams](
      switchingSimsSource) {

  val identifier = RecentStrongEngagementDirectFollowSimilarUsersSource.Identifier

  override def firstDegreeNodes(
    request: HasClientContetwittert with HasParams
  ): Stitch[Seq[CandidateUser]] = request.getOptionalUserId
    .map { userId =>
      realTimeRealGraphClient
        .getUsersRecentlyEngagedWith(
          userId,
          RealTimeRealGraphClient.StrongEngagementScoreMap,
          includeDirectFollowCandidates = true,
          includeNonDirectFollowCandidates = false
        ).map(_.take(RecentStrongEngagementDirectFollowSimilarUsersSource.MatwitterFirstDegreeNodes))
    }.getOrElse(Stitch.Nil)

  override def matwitterSecondaryDegreeNodes(request: HasClientContetwittert with HasParams): Int = Int.MatwitterValue

  override def matwitterResults(request: HasClientContetwittert with HasParams): Int =
    RecentStrongEngagementDirectFollowSimilarUsersSource.MatwitterResults

  override def scoreCandidate(sourceScore: Double, similarToScore: Double): Double = {
    sourceScore * similarToScore
  }

  override def calibrateDivisor(req: HasClientContetwittert with HasParams): Double = 1.0d
}

object RecentStrongEngagementDirectFollowSimilarUsersSource {
  val Identifier = CandidateSourceIdentifier(Algorithm.RecentStrongEngagementSimilarUser.toString)
  val MatwitterFirstDegreeNodes = 10
  val MatwitterResults = 200
}
