package com.ExTwitter.follow_recommendations.common.candidate_sources.stp

import com.google.inject.Singleton
import com.ExTwitter.hermit.model.Algorithm
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.strato.generated.client.hub.PpmiDenseMatrixCandidatesClientColumn
import javax.inject.Inject

/**
 * Main source for strong-tie-prediction candidates generated offline.
 */
@Singleton
class OfflineStpSourceWithDensePmiMatrix @Inject() (
  stpColumn: PpmiDenseMatrixCandidatesClientColumn)
    extends OfflineStrongTiePredictionBaseSource(stpColumn.fetcher) {
  override val identifier: CandidateSourceIdentifier = OfflineStpSourceWithDensePmiMatrix.Identifier
}

object OfflineStpSourceWithDensePmiMatrix {
  val Identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier(Algorithm.StrongTiePredictionRec.toString)
}
