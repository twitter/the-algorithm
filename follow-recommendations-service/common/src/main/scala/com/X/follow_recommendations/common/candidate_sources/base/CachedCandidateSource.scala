package com.X.follow_recommendations.common.candidate_sources.base

import com.X.escherbird.util.stitchcache.StitchCache
import com.X.finagle.stats.StatsReceiver
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.stitch.Stitch
import com.X.util.Duration

class CachedCandidateSource[K <: Object, V <: Object](
  candidateSource: CandidateSource[K, V],
  maxCacheSize: Int,
  cacheTTL: Duration,
  statsReceiver: StatsReceiver,
  override val identifier: CandidateSourceIdentifier)
    extends CandidateSource[K, V] {

  private val cache = StitchCache[K, Seq[V]](
    maxCacheSize = maxCacheSize,
    ttl = cacheTTL,
    statsReceiver = statsReceiver.scope(identifier.name, "cache"),
    underlyingCall = (k: K) => candidateSource(k)
  )

  override def apply(target: K): Stitch[Seq[V]] = cache.readThrough(target)
}
