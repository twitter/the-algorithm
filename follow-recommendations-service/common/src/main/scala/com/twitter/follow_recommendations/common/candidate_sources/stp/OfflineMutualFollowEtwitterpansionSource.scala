package com.twitter.follow_recommendations.common.candidate_sources.stp

import com.google.inject.Singleton
import com.twitter.hermit.model.Algorithm
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.generated.client.onboarding.userrecs.MutualFollowEtwitterpansionClientColumn
import javatwitter.inject.Inject

/**
 * A source that finds the mutual follows of one's mutual follows that one isn't following already.
 */
@Singleton
class OfflineMutualFollowEtwitterpansionSource @Inject() (
  column: MutualFollowEtwitterpansionClientColumn)
    etwittertends OfflineStrongTiePredictionBaseSource(column.fetcher) {
  override val identifier: CandidateSourceIdentifier =
    OfflineMutualFollowEtwitterpansionSource.Identifier
}

object OfflineMutualFollowEtwitterpansionSource {
  val Identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier(Algorithm.MutualFollowEtwitterpansion.toString)
}
