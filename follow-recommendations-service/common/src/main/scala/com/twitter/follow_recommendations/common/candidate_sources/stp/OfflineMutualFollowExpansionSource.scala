package com.twitter.follow_recommendations.common.candidate_sources.stp

import com.google.inject.Singleton
import com.twitter.hermit.model.Algorithm
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.generated.client.onboarding.userrecs.MutualFollowExpansionClientColumn
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
