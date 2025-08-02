package com.twitter.follow_recommendations.common.candidate_sources.base

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.util.DefaultTimer
import com.twitter.follow_recommendations.common.candidate_sources.base.RealGraphEtwitterpansionRepository.DefaultScore
import com.twitter.follow_recommendations.common.candidate_sources.base.RealGraphEtwitterpansionRepository.MatwitterNumIntermediateNodesToKeep
import com.twitter.follow_recommendations.common.candidate_sources.base.RealGraphEtwitterpansionRepository.FirstDegreeCandidatesTimeout
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models._
import com.twitter.onboarding.relevance.features.ymbii.EtwitterpansionCandidateScores
import com.twitter.onboarding.relevance.features.ymbii.RawYMBIICandidateFeatures
import com.twitter.onboarding.relevance.store.thriftscala.CandidatesFollowedV1
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import com.twitter.util.Duration
import scala.collection.immutable
import scala.util.control.NonFatal

private final case class InterestEtwitterpansionCandidate(
  userID: Long,
  score: Double,
  features: RawYMBIICandidateFeatures)

abstract class RealGraphEtwitterpansionRepository[Request](
  realgraphEtwitterpansionStore: Fetcher[
    Long,
    Unit,
    CandidatesFollowedV1
  ],
  override val identifier: CandidateSourceIdentifier,
  statsReceiver: StatsReceiver = NullStatsReceiver,
  matwitterUnderlyingCandidatesToQuery: Int = 50,
  matwitterCandidatesToReturn: Int = 40,
  overrideUnderlyingTimeout: Option[Duration] = None,
  appendSocialProof: Boolean = false)
    etwittertends CandidateSource[
      Request,
      CandidateUser
    ] {

  val underlyingCandidateSource: Seq[
    CandidateSource[
      Request,
      CandidateUser
    ]
  ]

  private val stats = statsReceiver.scope(this.getClass.getSimpleName).scope(identifier.name)
  private val underlyingCandidateSourceFailureStats =
    stats.scope("underlying_candidate_source_failure")

  def apply(
    request: Request,
  ): Stitch[Seq[CandidateUser]] = {

    val candidatesFromUnderlyingSourcesStitch: Seq[Stitch[Seq[CandidateUser]]] =
      underlyingCandidateSource.map { candidateSource =>
        candidateSource
          .apply(request)
          .within(overrideUnderlyingTimeout.getOrElse(FirstDegreeCandidatesTimeout))(
            DefaultTimer
          )
          .handle {
            case NonFatal(e) =>
              underlyingCandidateSourceFailureStats
                .counter(candidateSource.identifier.name, e.getClass.getSimpleName).incr()
              Seq.empty
          }
      }

    for {
      underlyingCandidatesFromEachAlgo <- Stitch.collect(candidatesFromUnderlyingSourcesStitch)
      // The first algorithm in the list has the highest priority. Depending on if its not
      // populated, fall back to other algorithms. Once a particular algorithm is chosen, only
      // take the top few candidates from the underlying store for etwitterpansion.
      underlyingCandidatesTuple =
        underlyingCandidatesFromEachAlgo
          .zip(underlyingCandidateSource)
          .find(_._1.nonEmpty)

      underlyingAlgorithmUsed: Option[CandidateSourceIdentifier] = underlyingCandidatesTuple.map {
        case (_, candidateSource) => candidateSource.identifier
      }

      // Take matwitterUnderlyingCandidatesToQuery to query realgraphEtwitterpansionStore
      underlyingCandidates =
        underlyingCandidatesTuple
          .map {
            case (candidates, candidateSource) =>
              stats
                .scope("underlyingAlgorithmUsedScope").counter(
                  candidateSource.identifier.name).incr()
              candidates
          }
          .getOrElse(Seq.empty)
          .sortBy(_.score.getOrElse(DefaultScore))(Ordering.Double.reverse)
          .take(matwitterUnderlyingCandidatesToQuery)

      underlyingCandidateMap: Map[Long, Double] = underlyingCandidates.map { candidate =>
        (candidate.id, candidate.score.getOrElse(DefaultScore))
      }.toMap

      etwitterpansionCandidates <-
        Stitch
          .traverse(underlyingCandidateMap.keySet.toSeq) { candidateId =>
            Stitch.join(
              Stitch.value(candidateId),
              realgraphEtwitterpansionStore.fetch(candidateId).map(_.v))

          }.map(_.toMap)

      rerankedCandidates: Seq[InterestEtwitterpansionCandidate] =
        rerankCandidateEtwitterpansions(underlyingCandidateMap, etwitterpansionCandidates)

      rerankedCandidatesFiltered = rerankedCandidates.take(matwitterCandidatesToReturn)

    } yield {
      rerankedCandidatesFiltered.map { candidate =>
        val socialProofReason = if (appendSocialProof) {
          val socialProofIds = candidate.features.etwitterpansionCandidateScores
            .map(_.intermediateCandidateId)
          Some(
            Reason(Some(
              AccountProof(followProof = Some(FollowProof(socialProofIds, socialProofIds.size))))))
        } else {
          None
        }
        CandidateUser(
          id = candidate.userID,
          score = Some(candidate.score),
          reason = socialProofReason,
          userCandidateSourceDetails = Some(
            UserCandidateSourceDetails(
              primaryCandidateSource = Some(identifier),
              candidateSourceFeatures = Map(identifier -> Seq(candidate.features))
            ))
        ).addAddressBookMetadataIfAvailable(underlyingAlgorithmUsed.toSeq)
      }
    }
  }

  /**
   * Etwitterpands underlying candidates, returning them in sorted order.
   *
   * @param underlyingCandidatesMap A map from underlying candidate id to score
   * @param etwitterpansionCandidateMap A map from underlying candidate id to optional etwitterpansion candidates
   * @return A sorted sequence of etwitterpansion candidates and associated scores
   */
  private def rerankCandidateEtwitterpansions(
    underlyingCandidatesMap: Map[Long, Double],
    etwitterpansionCandidateMap: Map[Long, Option[CandidatesFollowedV1]]
  ): Seq[InterestEtwitterpansionCandidate] = {

    // etwittertract features
    val candidates: Seq[(Long, EtwitterpansionCandidateScores)] = for {
      (underlyingCandidateId, underlyingCandidateScore) <- underlyingCandidatesMap.toSeq
      etwitterpansionCandidates =
        etwitterpansionCandidateMap
          .get(underlyingCandidateId)
          .flatten
          .map(_.candidatesFollowed)
          .getOrElse(Seq.empty)
      etwitterpansionCandidate <- etwitterpansionCandidates
    } yield etwitterpansionCandidate.candidateID -> EtwitterpansionCandidateScores(
      underlyingCandidateId,
      Some(underlyingCandidateScore),
      Some(etwitterpansionCandidate.score)
    )

    // merge intermediate nodes for the same candidate
    val dedupedCandidates: Seq[(Long, Seq[EtwitterpansionCandidateScores])] =
      candidates.groupBy(_._1).mapValues(_.map(_._2).sortBy(_.intermediateCandidateId)).toSeq

    // score the candidate
    val candidatesWithTotalScore: Seq[((Long, Seq[EtwitterpansionCandidateScores]), Double)] =
      dedupedCandidates.map { candidate: (Long, Seq[EtwitterpansionCandidateScores]) =>
        (
          candidate,
          candidate._2.map { ieScore: EtwitterpansionCandidateScores =>
            ieScore.scoreFromUserToIntermediateCandidate.getOrElse(DefaultScore) *
              ieScore.scoreFromIntermediateToEtwitterpansionCandidate.getOrElse(DefaultScore)
          }.sum)
      }

    // sort candidate by score
    for {
      ((candidate, edges), score) <- candidatesWithTotalScore.sortBy(_._2)(Ordering[Double].reverse)
    } yield InterestEtwitterpansionCandidate(
      candidate,
      score,
      RawYMBIICandidateFeatures(
        edges.size,
        edges.take(MatwitterNumIntermediateNodesToKeep).to[immutable.Seq])
    )
  }

}

object RealGraphEtwitterpansionRepository {
  private val FirstDegreeCandidatesTimeout: Duration = 250.milliseconds
  private val MatwitterNumIntermediateNodesToKeep = 20
  private val DefaultScore = 0.0d

}
