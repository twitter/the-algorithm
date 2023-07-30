package com.X.follow_recommendations.common.candidate_sources.sims

import com.google.inject.Singleton
import com.google.inject.name.Named
import com.X.finagle.stats.StatsReceiver
import com.X.follow_recommendations.common.constants.GuiceNamedConstants
import com.X.hermit.candidate.thriftscala.Candidates
import com.X.hermit.model.Algorithm
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.strato.client.Fetcher
import com.X.util.Duration

import javax.inject.Inject

@Singleton
class DBV2SimsStore @Inject() (
  @Named(GuiceNamedConstants.DBV2_SIMS_FETCHER) fetcher: Fetcher[Long, Unit, Candidates])
    extends StratoBasedSimsCandidateSourceWithUnitView(
      fetcher,
      identifier = DBV2SimsStore.Identifier)

@Singleton
class CachedDBV2SimsStore @Inject() (
  @Named(GuiceNamedConstants.DBV2_SIMS_FETCHER) fetcher: Fetcher[Long, Unit, Candidates],
  statsReceiver: StatsReceiver)
    extends CacheBasedSimsStore(
      id = DBV2SimsStore.Identifier,
      fetcher = fetcher,
      maxCacheSize = DBV2SimsStore.MaxCacheSize,
      cacheTtl = DBV2SimsStore.CacheTTL,
      statsReceiver = statsReceiver.scope("CachedDBV2SimsStore", "cache")
    )

object DBV2SimsStore {
  val Identifier = CandidateSourceIdentifier(Algorithm.Sims.toString)
  val MaxCacheSize = 1000
  val CacheTTL: Duration = Duration.fromHours(24)
}
