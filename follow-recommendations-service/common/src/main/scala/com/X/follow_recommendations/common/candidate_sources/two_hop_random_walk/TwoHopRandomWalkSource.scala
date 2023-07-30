package com.X.follow_recommendations.common.candidate_sources.two_hop_random_walk

import com.X.follow_recommendations.common.candidate_sources.base.StratoFetcherWithUnitViewSource
import com.X.follow_recommendations.common.constants.GuiceNamedConstants
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.hermit.model.Algorithm
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.strato.client.Fetcher
import com.X.wtf.candidate.thriftscala.{CandidateSeq => TCandidateSeq}
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class TwoHopRandomWalkSource @Inject() (
  @Named(GuiceNamedConstants.TWO_HOP_RANDOM_WALK_FETCHER) fetcher: Fetcher[
    Long,
    Unit,
    TCandidateSeq
  ]) extends StratoFetcherWithUnitViewSource[Long, TCandidateSeq](
      fetcher,
      TwoHopRandomWalkSource.Identifier) {

  override def map(targetUserId: Long, tCandidateSeq: TCandidateSeq): Seq[CandidateUser] =
    TwoHopRandomWalkSource.map(targetUserId, tCandidateSeq)

}

object TwoHopRandomWalkSource {
  def map(targetUserId: Long, tCandidateSeq: TCandidateSeq): Seq[CandidateUser] = {
    tCandidateSeq.candidates
      .sortBy(-_.score)
      .map { tCandidate =>
        CandidateUser(id = tCandidate.userId, score = Some(tCandidate.score))
      }
  }

  val Identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier(Algorithm.TwoHopRandomWalk.toString)
}
