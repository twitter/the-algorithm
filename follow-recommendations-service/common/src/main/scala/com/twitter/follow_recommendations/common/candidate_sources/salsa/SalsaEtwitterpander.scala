package com.twitter.follow_recommendations.common.candidate_sources.salsa

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.strato.generated.client.onboarding.userrecs.SalsaFirstDegreeOnUserClientColumn
import com.twitter.strato.generated.client.onboarding.userrecs.SalsaSecondDegreeOnUserClientColumn
import com.twitter.follow_recommendations.common.models.AccountProof
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.FollowProof
import com.twitter.follow_recommendations.common.models.Reason
import com.twitter.stitch.Stitch
import com.twitter.wtf.candidate.thriftscala.Candidate
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

case class SalsaEtwitterpandedCandidate(
  candidateId: Long,
  numberOfConnections: Int,
  totalScore: Double,
  connectingUsers: Seq[Long]) {
  def toCandidateUser: CandidateUser =
    CandidateUser(
      id = candidateId,
      score = Some(totalScore),
      reason = Some(Reason(
        Some(AccountProof(followProof = Some(FollowProof(connectingUsers, connectingUsers.size))))))
    )
}

case class SimilarUserCandidate(candidateId: Long, score: Double, similarToCandidate: Long)

/**
 * Salsa etwitterpander uses pre-computed lists of candidates for each input user id and returns the highest scored candidates in the pre-computed lists as the etwitterpansion for the corresponding input id.
 */
@Singleton
class SalsaEtwitterpander @Inject() (
  statsReceiver: StatsReceiver,
  firstDegreeClient: SalsaFirstDegreeOnUserClientColumn,
  secondDegreeClient: SalsaSecondDegreeOnUserClientColumn,
) {

  val stats = statsReceiver.scope("salsa_etwitterpander")

  private def similarUsers(
    input: Seq[Long],
    neighbors: Seq[Option[Seq[Candidate]]]
  ): Seq[SalsaEtwitterpandedCandidate] = {
    input
      .zip(neighbors).flatMap {
        case (recId, Some(neighbors)) =>
          neighbors.map(neighbor => SimilarUserCandidate(neighbor.userId, neighbor.score, recId))
        case _ => Nil
      }.groupBy(_.candidateId).map {
        case (key, neighbors) =>
          val scores = neighbors.map(_.score)
          val connectingUsers = neighbors
            .sortBy(-_.score)
            .take(SalsaEtwitterpander.MatwitterConnectingUsersToOutputPerEtwitterpandedCandidate)
            .map(_.similarToCandidate)

          SalsaEtwitterpandedCandidate(key, scores.size, scores.sum, connectingUsers)
      }
      .filter(
        _.numberOfConnections >= math
          .min(SalsaEtwitterpander.MinConnectingUsersThreshold, input.size)
      )
      .toSeq
  }

  def apply(
    firstDegreeInput: Seq[Long],
    secondDegreeInput: Seq[Long],
    matwitterNumOfCandidatesToReturn: Int
  ): Stitch[Seq[CandidateUser]] = {

    val firstDegreeNeighborsStitch =
      Stitch
        .collect(firstDegreeInput.map(firstDegreeClient.fetcher
          .fetch(_).map(_.v.map(_.candidates.take(SalsaEtwitterpander.MatwitterDirectNeighbors))))).onSuccess {
          firstDegreeNeighbors =>
            stats.stat("first_degree_neighbors").add(firstDegreeNeighbors.flatten.size)
        }

    val secondDegreeNeighborsStitch =
      Stitch
        .collect(
          secondDegreeInput.map(
            secondDegreeClient.fetcher
              .fetch(_).map(
                _.v.map(_.candidates.take(SalsaEtwitterpander.MatwitterIndirectNeighbors))))).onSuccess {
          secondDegreeNeighbors =>
            stats.stat("second_degree_neighbors").add(secondDegreeNeighbors.flatten.size)
        }

    val neighborStitches =
      Stitch.join(firstDegreeNeighborsStitch, secondDegreeNeighborsStitch).map {
        case (first, second) => first ++ second
      }

    val similarUsersToInput = neighborStitches.map { neighbors =>
      similarUsers(firstDegreeInput ++ secondDegreeInput, neighbors)
    }

    similarUsersToInput.map {
      // Rank the candidate cot users by the combined weights from the connecting users. This is the default original implementation. It is unlikely to have weight ties and thus a second ranking function is not necessary.
      _.sortBy(-_.totalScore)
        .take(matwitterNumOfCandidatesToReturn)
        .map(_.toCandidateUser)
    }
  }
}

object SalsaEtwitterpander {
  val MatwitterDirectNeighbors = 2000
  val MatwitterIndirectNeighbors = 2000
  val MinConnectingUsersThreshold = 2
  val MatwitterConnectingUsersToOutputPerEtwitterpandedCandidate = 3
}
