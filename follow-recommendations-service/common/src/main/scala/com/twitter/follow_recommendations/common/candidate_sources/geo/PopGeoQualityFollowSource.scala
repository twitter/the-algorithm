package com.ExTwitter.follow_recommendations.common.candidate_sources.geo
import com.google.inject.Singleton
import com.ExTwitter.escherbird.util.stitchcache.StitchCache
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.follow_recommendations.common.models.AccountProof
import com.ExTwitter.follow_recommendations.common.models.CandidateUser
import com.ExTwitter.follow_recommendations.common.models.PopularInGeoProof
import com.ExTwitter.follow_recommendations.common.models.Reason
import com.ExTwitter.hermit.model.Algorithm
import com.ExTwitter.hermit.pop_geo.thriftscala.PopUsersInPlace
import com.ExTwitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.strato.generated.client.onboarding.userrecs.UniquePopQualityFollowUsersInPlaceClientColumn
import com.ExTwitter.util.Duration
import javax.inject.Inject

@Singleton
class PopGeohashQualityFollowSource @Inject() (
  popGeoSource: PopGeoQualityFollowSource,
  statsReceiver: StatsReceiver)
    extends BasePopGeohashSource(
      popGeoSource = popGeoSource,
      statsReceiver = statsReceiver.scope("PopGeohashQualityFollowSource"),
    ) {
  override val identifier: CandidateSourceIdentifier = PopGeohashQualityFollowSource.Identifier
  override def maxResults(target: Target): Int = {
    target.params(PopGeoQualityFollowSourceParams.PopGeoSourceMaxResultsPerPrecision)
  }
  override def minGeohashLength(target: Target): Int = {
    target.params(PopGeoQualityFollowSourceParams.PopGeoSourceGeoHashMinPrecision)
  }
  override def maxGeohashLength(target: Target): Int = {
    target.params(PopGeoQualityFollowSourceParams.PopGeoSourceGeoHashMaxPrecision)
  }
  override def returnResultFromAllPrecision(target: Target): Boolean = {
    target.params(PopGeoQualityFollowSourceParams.PopGeoSourceReturnFromAllPrecisions)
  }
  override def candidateSourceEnabled(target: Target): Boolean = {
    target.params(PopGeoQualityFollowSourceParams.CandidateSourceEnabled)
  }
}

object PopGeohashQualityFollowSource {
  val Identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    Algorithm.PopGeohashQualityFollow.toString)
}

object PopGeoQualityFollowSource {
  val MaxCacheSize = 20000
  val CacheTTL: Duration = Duration.fromHours(24)
  val MaxResults = 200
}

@Singleton
class PopGeoQualityFollowSource @Inject() (
  popGeoQualityFollowClientColumn: UniquePopQualityFollowUsersInPlaceClientColumn,
  statsReceiver: StatsReceiver,
) extends CandidateSource[String, CandidateUser] {

  /** @see [[CandidateSourceIdentifier]] */
  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    "PopGeoQualityFollowSource")

  private val cache = StitchCache[String, Option[PopUsersInPlace]](
    maxCacheSize = PopGeoQualityFollowSource.MaxCacheSize,
    ttl = PopGeoQualityFollowSource.CacheTTL,
    statsReceiver = statsReceiver.scope(identifier.name, "cache"),
    underlyingCall = (k: String) => {
      popGeoQualityFollowClientColumn.fetcher
        .fetch(k)
        .map { result => result.v }
    }
  )

  override def apply(target: String): Stitch[Seq[CandidateUser]] = {
    val result: Stitch[Option[PopUsersInPlace]] = cache.readThrough(target)
    result.map { pu =>
      pu.map { candidates =>
          candidates.popUsers.sortBy(-_.score).take(PopGeoQualityFollowSource.MaxResults).map {
            candidate =>
              CandidateUser(
                id = candidate.userId,
                score = Some(candidate.score),
                reason = Some(
                  Reason(
                    Some(
                      AccountProof(
                        popularInGeoProof = Some(PopularInGeoProof(location = candidates.place))
                      )
                    )
                  )
                )
              )
          }
        }.getOrElse(Nil)
    }
  }
}
