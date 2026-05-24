package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.MediaCategoryFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetMediaClusterIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetMediaIdsFeature
import com.twitter.home_mixer.param.HomeMixerInjectionNames.MediaClipClusterIdInMemCache
import com.twitter.home_mixer.param.HomeMixerInjectionNames.MediaClusterId95Store
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.EnableMediaClusterFeatureHydrationParam
import com.twitter.mediaservices.commons.thriftscala.MediaCategory
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.servo.cache.InProcessCache
import com.twitter.stitch.Stitch

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import com.twitter.storehaus.ReadableStore

@Singleton
class MediaClusterIdFeatureHydrator @Inject() (
  @Named(MediaClusterId95Store) clusterIdStore: ReadableStore[Long, Long],
  @Named(MediaClipClusterIdInMemCache) mediaClipClusterIdInMemCache: InProcessCache[
    Long,
    Option[Option[Long]]
  ],
  statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with Conditionally[PipelineQuery]
    with WithDefaultFeatureMap {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("MediaClusterId")

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableMediaClusterFeatureHydrationParam)

  override val features: Set[Feature[_, _]] = Set(TweetMediaClusterIdsFeature)

  override val defaultFeatureMap: FeatureMap =
    FeatureMap(TweetMediaClusterIdsFeature, Map.empty[Long, Long])

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val keyFoundCounter = scopedStatsReceiver.counter("key/found")
  private val keyNotFoundCounter = scopedStatsReceiver.counter("key/notFound")
  private val cacheHitCounter = scopedStatsReceiver.counter("cache/hit")
  private val cacheMissCounter = scopedStatsReceiver.counter("cache/miss")
  private val fetchExceptionCounter =
    scopedStatsReceiver.counter("getFromCacheOrFetch/exception")

  private def getFromCacheOrFetch(tweetId: Long): Stitch[Option[Option[Long]]] = {
    mediaClipClusterIdInMemCache
      .get(tweetId)
      .map { cachedValue =>
        cacheHitCounter.incr()
        Stitch.value(cachedValue)
      }.getOrElse {
        cacheMissCounter.incr()
        // Use the store which handles memcache + Manhattan fallback
        Stitch
          .callFuture(clusterIdStore.get(tweetId)).map { result =>
            result match {
              case Some(clusterId) =>
                keyFoundCounter.incr()
                val finalResult = Some(Some(clusterId))
                mediaClipClusterIdInMemCache.set(tweetId, finalResult)
                finalResult
              case None =>
                keyNotFoundCounter.incr()
                mediaClipClusterIdInMemCache.set(tweetId, Some(None))
                Some(None)
            }
          }.handle {
            case ex: Exception =>
              fetchExceptionCounter.incr()
              ex.printStackTrace()
              None
          }
      }
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadStitch {
    Stitch.collect(
      candidates.map { candidate =>
        val mediaIds = candidate.features.getOrElse(TweetMediaIdsFeature, Seq.empty[Long])
        val mediaCategory = candidate.features.getOrElse(MediaCategoryFeature, None)
        if (mediaCategory.contains(MediaCategory.TweetVideo) || mediaCategory.contains(
            MediaCategory.AmplifyVideo)) {
          val items: Seq[Stitch[Option[(Long, Long)]]] = mediaIds.map { mediaId =>
            getFromCacheOrFetch(candidate.candidate.id).map {
              case Some(Some(mediaClusterId)) =>
                keyFoundCounter.incr()
                Some(mediaId -> mediaClusterId)
              case _ =>
                keyNotFoundCounter.incr()
                None
            }
          }

          Stitch.collect(items).map { results =>
            val mediaClusterMap =
              results.flatten.toMap // Flatten to remove None and create Map[Long, Long]
            FeatureMapBuilder().add(TweetMediaClusterIdsFeature, mediaClusterMap).build()
          }
        } else {
          Stitch.value(
            FeatureMapBuilder().add(TweetMediaClusterIdsFeature, Map.empty[Long, Long]).build())
        }
      }
    )
  }
}
