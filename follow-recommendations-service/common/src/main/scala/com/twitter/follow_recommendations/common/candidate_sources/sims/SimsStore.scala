package com.ExTwitter.follow_recommendations.common.candidate_sources.sims

import com.google.inject.Singleton
import com.google.inject.name.Named
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.follow_recommendations.common.constants.GuiceNamedConstants
import com.ExTwitter.hermit.candidate.thriftscala.Candidates
import com.ExTwitter.hermit.model.Algorithm
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.strato.client.Fetcher
import com.ExTwitter.util.Duration

import javax.inject.Inject

@Singleton
class SimsStore @Inject() (
  @Named(GuiceNamedConstants.SIMS_FETCHER) fetcher: Fetcher[Long, Unit, Candidates])
    extends StratoBasedSimsCandidateSourceWithUnitView(fetcher, identifier = SimsStore.Identifier)

@Singleton
class CachedSimsStore @Inject() (
  @Named(GuiceNamedConstants.SIMS_FETCHER) fetcher: Fetcher[Long, Unit, Candidates],
  statsReceiver: StatsReceiver)
    extends CacheBasedSimsStore(
      id = SimsStore.Identifier,
      fetcher = fetcher,
      maxCacheSize = SimsStore.MaxCacheSize,
      cacheTtl = SimsStore.CacheTTL,
      statsReceiver = statsReceiver.scope("CachedSimsStore", "cache")
    )

object SimsStore {
  val Identifier = CandidateSourceIdentifier(Algorithm.Sims.toString)
  val MaxCacheSize = 50000
  val CacheTTL: Duration = Duration.fromHours(24)
}
