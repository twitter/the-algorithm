package com.twitter.follow_recommendations.common.candidate_sources.stp

import com.google.inject.Singleton
import com.twitter.hermit.model.Algorithm
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.generated.client.hub.PpmiDenseMatritwitterCandidatesClientColumn
import javatwitter.inject.Inject

/**
 * Main source for strong-tie-prediction candidates generated offline.
 */
@Singleton
class OfflineStpSourceWithDensePmiMatritwitter @Inject() (
  stpColumn: PpmiDenseMatritwitterCandidatesClientColumn)
    etwittertends OfflineStrongTiePredictionBaseSource(stpColumn.fetcher) {
  override val identifier: CandidateSourceIdentifier = OfflineStpSourceWithDensePmiMatritwitter.Identifier
}

object OfflineStpSourceWithDensePmiMatritwitter {
  val Identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier(Algorithm.StrongTiePredictionRec.toString)
}
