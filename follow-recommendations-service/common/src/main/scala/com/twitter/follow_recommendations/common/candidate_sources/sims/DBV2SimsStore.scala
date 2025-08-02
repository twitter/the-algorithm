package com.twitter.follow_recommendations.common.candidate_sources.sims

import com.google.inject.Singleton
import com.google.inject.name.Named
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.constants.GuiceNamedConstants
import com.twitter.hermit.candidate.thriftscala.Candidates
import com.twitter.hermit.model.Algorithm
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.client.Fetcher
import com.twitter.util.Duration

import javatwitter.inject.Inject

@Singleton
class DBV2SimsStore @Inject() (
  @Named(GuiceNamedConstants.DBV2_SIMS_FETCHER) fetcher: Fetcher[Long, Unit, Candidates])
    etwittertends StratoBasedSimsCandidateSourceWithUnitView(
      fetcher,
      identifier = DBV2SimsStore.Identifier)

@Singleton
class CachedDBV2SimsStore @Inject() (
  @Named(GuiceNamedConstants.DBV2_SIMS_FETCHER) fetcher: Fetcher[Long, Unit, Candidates],
  statsReceiver: StatsReceiver)
    etwittertends CacheBasedSimsStore(
      id = DBV2SimsStore.Identifier,
      fetcher = fetcher,
      matwitterCacheSize = DBV2SimsStore.MatwitterCacheSize,
      cacheTtl = DBV2SimsStore.CacheTTL,
      statsReceiver = statsReceiver.scope("CachedDBV2SimsStore", "cache")
    )

object DBV2SimsStore {
  val Identifier = CandidateSourceIdentifier(Algorithm.Sims.toString)
  val MatwitterCacheSize = 1000
  val CacheTTL: Duration = Duration.fromHours(24)
}
