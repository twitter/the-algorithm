package com.X.follow_recommendations.common.candidate_sources.stp

import com.google.inject.Singleton
import com.X.hermit.model.Algorithm
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.strato.generated.client.onboarding.userrecs.MutualFollowExpansionClientColumn
import javax.inject.Inject

/**
 * A source that finds the mutual follows of one's mutual follows that one isn't following already.
 */
@Singleton
class OfflineMutualFollowExpansionSource @Inject() (
  column: MutualFollowExpansionClientColumn)
    extends OfflineStrongTiePredictionBaseSource(column.fetcher) {
  override val identifier: CandidateSourceIdentifier =
    OfflineMutualFollowExpansionSource.Identifier
}

object OfflineMutualFollowExpansionSource {
  val Identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier(Algorithm.MutualFollowExpansion.toString)
}
