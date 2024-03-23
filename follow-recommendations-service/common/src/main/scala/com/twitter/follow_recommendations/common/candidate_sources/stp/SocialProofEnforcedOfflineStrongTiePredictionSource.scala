package com.ExTwitter.follow_recommendations.common.candidate_sources.stp

import com.google.inject.Singleton
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.follow_recommendations.common.candidate_sources.base.SocialProofEnforcedCandidateSource
import com.ExTwitter.follow_recommendations.common.transforms.modify_social_proof.ModifySocialProof
import com.ExTwitter.hermit.model.Algorithm
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import javax.inject.Inject

@Singleton
class SocialProofEnforcedOfflineStrongTiePredictionSource @Inject() (
  offlineStrongTiePredictionSource: OfflineStrongTiePredictionSource,
  modifySocialProof: ModifySocialProof,
  statsReceiver: StatsReceiver)
    extends SocialProofEnforcedCandidateSource(
      offlineStrongTiePredictionSource,
      modifySocialProof,
      SocialProofEnforcedOfflineStrongTiePredictionSource.MinNumSocialProofsRequired,
      SocialProofEnforcedOfflineStrongTiePredictionSource.Identifier,
      statsReceiver)

object SocialProofEnforcedOfflineStrongTiePredictionSource {
  val Identifier = CandidateSourceIdentifier(
    Algorithm.StrongTiePredictionRecWithSocialProof.toString)

  val MinNumSocialProofsRequired = 1
}
