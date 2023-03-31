package com.twitter.follow_recommendations.common.candidate_sources.sims_expansion

import com.google.inject.Singleton
import com.twitter.follow_recommendations.common.candidate_sources.sims.SwitchingSimsSource
import com.twitter.follow_recommendations.common.clients.real_time_real_graph.RealTimeRealGraphClient
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.hermit.model.Algorithm
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams

import javax.inject.Inject

@Singleton
class RecentStrongEngagementDirectFollowSimilarUsersSource @Inject() (
  realTimeRealGraphClient: RealTimeRealGraphClient,
  switchingSimsSource: SwitchingSimsSource)
    extends SimsExpansionBasedCandidateSource[HasClientContext with HasParams](
      switchingSimsSource) {

  val identifier = RecentStrongEngagementDirectFollowSimilarUsersSource.Identifier

  override def firstDegreeNodes(
    request: HasClientContext with HasParams
  ): Stitch[Seq[CandidateUser]] = request.getOptionalUserId
    .map { userId =>
      realTimeRealGraphClient
        .getUsersRecentlyEngagedWith(
          userId,
          RealTimeRealGraphClient.StrongEngagementScoreMap,
          includeDirectFollowCandidates = true,
          includeNonDirectFollowCandidates = false
        ).map(_.take(RecentStrongEngagementDirectFollowSimilarUsersSource.MaxFirstDegreeNodes))
    }.getOrElse(Stitch.Nil)

  override def maxSecondaryDegreeNodes(request: HasClientContext with HasParams): Int = Int.MaxValue

  override def maxResults(request: HasClientContext with HasParams): Int =
    RecentStrongEngagementDirectFollowSimilarUsersSource.MaxResults

  override def scoreCandidate(sourceScore: Double, similarToScore: Double): Double = {
    sourceScore * similarToScore
  }

  override def calibrateDivisor(req: HasClientContext with HasParams): Double = 1.0d
}

object RecentStrongEngagementDirectFollowSimilarUsersSource {
  val Identifier = CandidateSourceIdentifier(Algorithm.RecentStrongEngagementSimilarUser.toString)
  val MaxFirstDegreeNodes = 10
  val MaxResults = 200
}
