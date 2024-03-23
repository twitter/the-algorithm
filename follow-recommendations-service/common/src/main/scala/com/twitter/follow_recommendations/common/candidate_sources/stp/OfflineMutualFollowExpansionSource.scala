package com.ExTwitter.follow_recommendations.common.candidate_sources.stp

import com.google.inject.Singleton
import com.ExTwitter.hermit.model.Algorithm
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.strato.generated.client.onboarding.userrecs.MutualFollowExpansionClientColumn
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
