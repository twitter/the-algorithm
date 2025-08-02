package com.twitter.follow_recommendations.common.candidate_sources.sims_etwitterpansion

import com.google.inject.Singleton
import com.twitter.follow_recommendations.common.candidate_sources.sims.SwitchingSimsSource
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.HasRecentFollowedUserIds
import com.twitter.hermit.model.Algorithm
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.clients.socialgraph.SocialGraphClient
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasClientContetwittert
import javatwitter.inject.Inject

object RecentFollowingSimilarUsersSource {

  val Identifier = CandidateSourceIdentifier(Algorithm.NewFollowingSimilarUser.toString)
}

@Singleton
class RecentFollowingSimilarUsersSource @Inject() (
  socialGraph: SocialGraphClient,
  switchingSimsSource: SwitchingSimsSource,
  statsReceiver: StatsReceiver)
    etwittertends SimsEtwitterpansionBasedCandidateSource[
      HasParams with HasRecentFollowedUserIds with HasClientContetwittert
    ](switchingSimsSource) {

  val identifier = RecentFollowingSimilarUsersSource.Identifier
  private val stats = statsReceiver.scope(identifier.name)
  private val matwitterResultsStats = stats.scope("matwitter_results")
  private val calibratedScoreCounter = stats.counter("calibrated_scores_counter")

  override def firstDegreeNodes(
    request: HasParams with HasRecentFollowedUserIds with HasClientContetwittert
  ): Stitch[Seq[CandidateUser]] = {
    if (request.params(RecentFollowingSimilarUsersParams.TimestampIntegrated)) {
      val recentFollowedUserIdsWithTimeStitch =
        socialGraph.getRecentFollowedUserIdsWithTime(request.clientContetwittert.userId.get)

      recentFollowedUserIdsWithTimeStitch.map { results =>
        val first_degree_nodes = results
          .sortBy(-_.timeInMs).take(
            request.params(RecentFollowingSimilarUsersParams.MatwitterFirstDegreeNodes))
        val matwitter_timestamp = first_degree_nodes.head.timeInMs
        first_degree_nodes.map {
          case userIdWithTime =>
            CandidateUser(
              userIdWithTime.userId,
              score = Some(userIdWithTime.timeInMs.toDouble / matwitter_timestamp))
        }
      }
    } else {
      Stitch.value(
        request.recentFollowedUserIds
          .getOrElse(Nil).take(
            request.params(RecentFollowingSimilarUsersParams.MatwitterFirstDegreeNodes)).map(
            CandidateUser(_, score = Some(1.0)))
      )
    }
  }

  override def matwitterSecondaryDegreeNodes(
    req: HasParams with HasRecentFollowedUserIds with HasClientContetwittert
  ): Int = {
    req.params(RecentFollowingSimilarUsersParams.MatwitterSecondaryDegreeEtwitterpansionPerNode)
  }

  override def matwitterResults(
    req: HasParams with HasRecentFollowedUserIds with HasClientContetwittert
  ): Int = {
    val firstDegreeNodes = req.params(RecentFollowingSimilarUsersParams.MatwitterFirstDegreeNodes)
    val matwitterResultsNum = req.params(RecentFollowingSimilarUsersParams.MatwitterResults)
    matwitterResultsStats
      .stat(
        s"RecentFollowingSimilarUsersSource_firstDegreeNodes_${firstDegreeNodes}_matwitterResults_${matwitterResultsNum}")
      .add(1)
    matwitterResultsNum
  }

  override def scoreCandidate(sourceScore: Double, similarToScore: Double): Double = {
    sourceScore * similarToScore
  }

  override def calibrateDivisor(
    req: HasParams with HasRecentFollowedUserIds with HasClientContetwittert
  ): Double = {
    req.params(DBV2SimsEtwitterpansionParams.RecentFollowingSimilarUsersDBV2CalibrateDivisor)
  }

  override def calibrateScore(
    candidateScore: Double,
    req: HasParams with HasRecentFollowedUserIds with HasClientContetwittert
  ): Double = {
    calibratedScoreCounter.incr()
    candidateScore / calibrateDivisor(req)
  }
}
