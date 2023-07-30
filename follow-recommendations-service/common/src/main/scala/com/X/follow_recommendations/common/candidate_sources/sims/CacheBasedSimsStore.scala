package com.X.follow_recommendations.common.candidate_sources.sims

import com.X.escherbird.util.stitchcache.StitchCache
import com.X.finagle.stats.StatsReceiver
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.follow_recommendations.common.models.HasSimilarToContext
import com.X.hermit.candidate.thriftscala.Candidates
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.stitch.Stitch
import com.X.strato.client.Fetcher
import com.X.timelines.configapi.HasParams
import com.X.util.Duration

import java.lang.{Long => JLong}

class CacheBasedSimsStore(
  id: CandidateSourceIdentifier,
  fetcher: Fetcher[Long, Unit, Candidates],
  maxCacheSize: Int,
  cacheTtl: Duration,
  statsReceiver: StatsReceiver)
    extends CandidateSource[HasParams with HasSimilarToContext, CandidateUser] {

  override val identifier: CandidateSourceIdentifier = id
  private def getUsersFromSimsSource(userId: JLong): Stitch[Option[Candidates]] = {
    fetcher
      .fetch(userId)
      .map(_.v)
  }

  private val simsCache = StitchCache[JLong, Option[Candidates]](
    maxCacheSize = maxCacheSize,
    ttl = cacheTtl,
    statsReceiver = statsReceiver,
    underlyingCall = getUsersFromSimsSource
  )

  override def apply(request: HasParams with HasSimilarToContext): Stitch[Seq[CandidateUser]] = {
    Stitch
      .traverse(request.similarToUserIds) { userId =>
        simsCache.readThrough(userId).map { candidatesOpt =>
          candidatesOpt
            .map { candidates =>
              StratoBasedSimsCandidateSource.map(userId, candidates)
            }.getOrElse(Nil)
        }
      }.map(_.flatten.distinct.map(_.withCandidateSource(identifier)))
  }
}
