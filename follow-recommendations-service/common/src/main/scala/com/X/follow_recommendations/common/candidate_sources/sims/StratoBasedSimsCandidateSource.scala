package com.X.follow_recommendations.common.candidate_sources.sims

import com.X.follow_recommendations.common.candidate_sources.base.StratoFetcherSource
import com.X.follow_recommendations.common.models.AccountProof
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.follow_recommendations.common.models.Reason
import com.X.follow_recommendations.common.models.SimilarToProof
import com.X.hermit.candidate.thriftscala.Candidates
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.strato.client.Fetcher

abstract class StratoBasedSimsCandidateSource[U](
  fetcher: Fetcher[Long, U, Candidates],
  view: U,
  override val identifier: CandidateSourceIdentifier)
    extends StratoFetcherSource[Long, U, Candidates](fetcher, view, identifier) {

  override def map(target: Long, candidates: Candidates): Seq[CandidateUser] =
    StratoBasedSimsCandidateSource.map(target, candidates)
}

object StratoBasedSimsCandidateSource {
  def map(target: Long, candidates: Candidates): Seq[CandidateUser] = {
    for {
      candidate <- candidates.candidates
    } yield CandidateUser(
      id = candidate.userId,
      score = Some(candidate.score),
      reason = Some(
        Reason(
          Some(
            AccountProof(
              similarToProof = Some(SimilarToProof(Seq(target)))
            )
          )
        )
      )
    )
  }
}
