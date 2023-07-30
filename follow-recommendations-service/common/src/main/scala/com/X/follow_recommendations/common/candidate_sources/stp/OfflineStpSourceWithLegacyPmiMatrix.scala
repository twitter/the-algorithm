package com.X.follow_recommendations.common.candidate_sources.stp

import com.google.inject.Singleton
import com.X.hermit.model.Algorithm
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.strato.generated.client.onboarding.userrecs.StrongTiePredictionClientColumn
import javax.inject.Inject

/**
 * Main source for strong-tie-prediction candidates generated offline.
 */
@Singleton
class OfflineStpSourceWithLegacyPmiMatrix @Inject() (
  stpColumn: StrongTiePredictionClientColumn)
    extends OfflineStrongTiePredictionBaseSource(stpColumn.fetcher) {
  override val identifier: CandidateSourceIdentifier =
    OfflineStpSourceWithLegacyPmiMatrix.Identifier
}

object OfflineStpSourceWithLegacyPmiMatrix {
  val Identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier(Algorithm.StrongTiePredictionRec.toString)
}
