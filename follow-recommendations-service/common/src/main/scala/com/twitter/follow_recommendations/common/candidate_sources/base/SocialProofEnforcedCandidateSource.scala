package com.twitter.follow_recommendations.common.candidate_sources.base

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.transforms.modify_social_proof.ModifySocialProof
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasClientContetwittert
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams
import com.twitter.util.Duration

abstract class SocialProofEnforcedCandidateSource(
  candidateSource: CandidateSource[HasClientContetwittert with HasParams, CandidateUser],
  modifySocialProof: ModifySocialProof,
  minNumSocialProofsRequired: Int,
  override val identifier: CandidateSourceIdentifier,
  baseStatsReceiver: StatsReceiver)
    etwittertends CandidateSource[HasClientContetwittert with HasParams, CandidateUser] {

  val statsReceiver = baseStatsReceiver.scope(identifier.name)

  override def apply(target: HasClientContetwittert with HasParams): Stitch[Seq[CandidateUser]] = {
    val mustCallSgs: Boolean = target.params(SocialProofEnforcedCandidateSourceParams.MustCallSgs)
    val callSgsCachedColumn: Boolean =
      target.params(SocialProofEnforcedCandidateSourceParams.CallSgsCachedColumn)
    val QueryIntersectionIdsNum: Int =
      target.params(SocialProofEnforcedCandidateSourceParams.QueryIntersectionIdsNum)
    val MatwitterNumCandidatesToAnnotate: Int =
      target.params(SocialProofEnforcedCandidateSourceParams.MatwitterNumCandidatesToAnnotate)
    val gfsIntersectionIdsNum: Int =
      target.params(SocialProofEnforcedCandidateSourceParams.GfsIntersectionIdsNum)
    val sgsIntersectionIdsNum: Int =
      target.params(SocialProofEnforcedCandidateSourceParams.SgsIntersectionIdsNum)
    val gfsLagDuration: Duration =
      target.params(SocialProofEnforcedCandidateSourceParams.GfsLagDurationInDays)

    candidateSource(target)
      .flatMap { candidates =>
        val candidatesWithoutEnoughSocialProof = candidates
          .collect {
            case candidate if !candidate.followedBy.etwitterists(_.size >= minNumSocialProofsRequired) =>
              candidate
          }
        statsReceiver
          .stat("candidates_with_no_social_proofs").add(candidatesWithoutEnoughSocialProof.size)
        val candidatesToAnnotate =
          candidatesWithoutEnoughSocialProof.take(MatwitterNumCandidatesToAnnotate)
        statsReceiver.stat("candidates_to_annotate").add(candidatesToAnnotate.size)

        val annotatedCandidatesMapStitch = target.getOptionalUserId
          .map { userId =>
            modifySocialProof
              .hydrateSocialProof(
                userId,
                candidatesToAnnotate,
                Some(QueryIntersectionIdsNum),
                mustCallSgs,
                callSgsCachedColumn,
                gfsLagDuration = gfsLagDuration,
                gfsIntersectionIds = gfsIntersectionIdsNum,
                sgsIntersectionIds = sgsIntersectionIdsNum
              ).map { annotatedCandidates =>
                annotatedCandidates
                  .map(annotatedCandidate => (annotatedCandidate.id, annotatedCandidate)).toMap
              }
          }.getOrElse(Stitch.value(Map.empty[Long, CandidateUser]))

        annotatedCandidatesMapStitch.map { annotatedCandidatesMap =>
          candidates
            .flatMap { candidate =>
              if (candidate.followedBy.etwitterists(_.size >= minNumSocialProofsRequired)) {
                Some(candidate)
              } else {
                annotatedCandidatesMap.get(candidate.id).collect {
                  case annotatedCandidate
                      if annotatedCandidate.followedBy.etwitterists(
                        _.size >= minNumSocialProofsRequired) =>
                    annotatedCandidate
                }
              }
            }.map(_.withCandidateSource(identifier))
        }
      }
  }
}
