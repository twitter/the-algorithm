package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.ClipImageClusterIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetMediaIdsFeature
import com.twitter.home_mixer.param.HomeMixerInjectionNames.ImageClipClusterIdInMemCache
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.servo.cache.InProcessCache
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.videoRecommendations.twitterClip.TwitterClipImageClusterIdMh95ClientColumn
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ClipImageClusterIdFeatureHydrator @Inject() (
  twitterClipImageClusterIdMh95ClientColumn: TwitterClipImageClusterIdMh95ClientColumn,
  @Named(ImageClipClusterIdInMemCache) imageClipClusterIdInMemCache: InProcessCache[
    Long,
    Option[Option[Long]]
  ],
  statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("ClipImageClusterId")

  override val features: Set[Feature[_, _]] = Set(ClipImageClusterIdsFeature)

  private val clusterIdFetcher = twitterClipImageClusterIdMh95ClientColumn.fetcher

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val keyFoundCounter = scopedStatsReceiver.counter("key/found")
  private val keyNotFoundCounter = scopedStatsReceiver.counter("key/notFound")
  private val cacheHitCounter = scopedStatsReceiver.counter("cache/hit")
  private val cacheMissCounter = scopedStatsReceiver.counter("cache/miss")
  private val fetchExceptionCounter =
    scopedStatsReceiver.counter("getFromCacheOrFetch/exception")

  private def getFromCacheOrFetch(mediaId: Long): Stitch[Option[Option[Long]]] = {
    imageClipClusterIdInMemCache
      .get(mediaId)
      .map { cachedValue =>
        cacheHitCounter.incr()
        Stitch.value(cachedValue)
      }.getOrElse {
        cacheMissCounter.incr()
        clusterIdFetcher
          .fetch(mediaId)
          .map(_.v)
          .liftToOption()
          .flatMap { clusterIdOpt =>
            imageClipClusterIdInMemCache.set(mediaId, clusterIdOpt)
            Stitch.value(clusterIdOpt)
          }
      }.handle {
        case _: Exception =>
          fetchExceptionCounter.incr()
          None
      }
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadStitch {
    Stitch.collect(
      candidates.map { candidate =>
        val mediaIds = candidate.features.getOrElse(TweetMediaIdsFeature, Seq.empty[Long])
        val items: Seq[Stitch[Option[(Long, Long)]]] = mediaIds.map { mediaId =>
          getFromCacheOrFetch(mediaId).map {
            case Some(Some(mediaClusterId)) =>
              keyFoundCounter.incr()
              Some(mediaId -> mediaClusterId)
            case _ =>
              keyNotFoundCounter.incr()
              None
          }
        }

        Stitch.collect(items).map { results =>
          val mediaClusterMap = results.flatten.toMap
          FeatureMap(ClipImageClusterIdsFeature, mediaClusterMap)
        }
      }
    )
  }
}
