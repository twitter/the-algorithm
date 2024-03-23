package com.ExTwitter.follow_recommendations.common.candidate_sources.sims

import com.google.inject.Singleton
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.hermit.model.Algorithm
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.strato.generated.client.recommendations.similarity.SimilarUsersBySimsExperimentalOnUserClientColumn
import com.ExTwitter.util.Duration

import javax.inject.Inject

@Singleton
class SimsExperimentalStore @Inject() (
  simsExperimentalOnUserClientColumn: SimilarUsersBySimsExperimentalOnUserClientColumn)
    extends StratoBasedSimsCandidateSourceWithUnitView(
      fetcher = simsExperimentalOnUserClientColumn.fetcher,
      identifier = SimsExperimentalStore.Identifier
    )

@Singleton
class CachedSimsExperimentalStore @Inject() (
  simsExperimentalOnUserClientColumn: SimilarUsersBySimsExperimentalOnUserClientColumn,
  statsReceiver: StatsReceiver)
    extends CacheBasedSimsStore(
      id = SimsExperimentalStore.Identifier,
      fetcher = simsExperimentalOnUserClientColumn.fetcher,
      maxCacheSize = SimsExperimentalStore.MaxCacheSize,
      cacheTtl = SimsExperimentalStore.CacheTTL,
      statsReceiver = statsReceiver.scope("CachedSimsExperimentalStore", "cache")
    )

object SimsExperimentalStore {
  val Identifier = CandidateSourceIdentifier(Algorithm.Sims.toString)
  val MaxCacheSize = 1000
  val CacheTTL: Duration = Duration.fromHours(12)
}
