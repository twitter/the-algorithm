package com.twitter.follow_recommendations.common.candidate_sources.sims

import com.google.inject.Singleton
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.hermit.model.Algorithm
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.generated.client.recommendations.similarity.SimilarUsersBySimsEtwitterperimentalOnUserClientColumn
import com.twitter.util.Duration

import javatwitter.inject.Inject

@Singleton
class SimsEtwitterperimentalStore @Inject() (
  simsEtwitterperimentalOnUserClientColumn: SimilarUsersBySimsEtwitterperimentalOnUserClientColumn)
    etwittertends StratoBasedSimsCandidateSourceWithUnitView(
      fetcher = simsEtwitterperimentalOnUserClientColumn.fetcher,
      identifier = SimsEtwitterperimentalStore.Identifier
    )

@Singleton
class CachedSimsEtwitterperimentalStore @Inject() (
  simsEtwitterperimentalOnUserClientColumn: SimilarUsersBySimsEtwitterperimentalOnUserClientColumn,
  statsReceiver: StatsReceiver)
    etwittertends CacheBasedSimsStore(
      id = SimsEtwitterperimentalStore.Identifier,
      fetcher = simsEtwitterperimentalOnUserClientColumn.fetcher,
      matwitterCacheSize = SimsEtwitterperimentalStore.MatwitterCacheSize,
      cacheTtl = SimsEtwitterperimentalStore.CacheTTL,
      statsReceiver = statsReceiver.scope("CachedSimsEtwitterperimentalStore", "cache")
    )

object SimsEtwitterperimentalStore {
  val Identifier = CandidateSourceIdentifier(Algorithm.Sims.toString)
  val MatwitterCacheSize = 1000
  val CacheTTL: Duration = Duration.fromHours(12)
}
