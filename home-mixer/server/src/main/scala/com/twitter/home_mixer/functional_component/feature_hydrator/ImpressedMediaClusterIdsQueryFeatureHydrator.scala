package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.param.HomeMixerInjectionNames.MediaClusterId95Store
import com.twitter.home_mixer.param.HomeMixerInjectionNames.MediaClipClusterIdInMemCache
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.servo.cache.InProcessCache
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import com.twitter.timelines.impressionstore.thriftscala.ImpressionList
import com.twitter.util.Future
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

case object ImpressedMediaClusterIds extends FeatureWithDefaultOnFailure[PipelineQuery, Seq[Long]] {
  override val defaultValue: Seq[Long] = Seq.empty
}

/**
 * Get the list of media cluster ids that the user has already seen.
 */
@Singleton
case class ImpressedMediaClusterIdsQueryFeatureHydrator @Inject() (
  @Named(MediaClusterId95Store) clusterIdStore: ReadableStore[Long, Long],
  tweetImpressionStore: ReadableStore[Long, ImpressionList],
  @Named(MediaClipClusterIdInMemCache) mediaClipClusterIdInMemCache: InProcessCache[
    Long,
    Option[Option[Long]]
  ],
  statsReceiver: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery] {
  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "ImpressedMediaClusterIdsQuery")

  override val features: Set[Feature[_, _]] = Set(ImpressedMediaClusterIds)

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val keyFoundCounter = scopedStatsReceiver.counter("impressionKey/found")
  private val keyNotFoundCounter = scopedStatsReceiver.counter("impressionKey/notFound")
  private val cacheHitCounter = scopedStatsReceiver.counter("cache/hit")
  private val cacheMissCounter = scopedStatsReceiver.counter("cache/miss")
  private val storeHitCounter = scopedStatsReceiver.counter("store/hit")
  private val storeMissCounter = scopedStatsReceiver.counter("store/miss")
  private val fetchExceptionCounter = scopedStatsReceiver.counter("fetch/exception")

  private def getmediaClipClusterId(tweetId: Long): Stitch[Option[Option[Long]]] = {
    mediaClipClusterIdInMemCache
      .get(tweetId)
      .map { cachedValue =>
        cacheHitCounter.incr()
        Stitch.value(cachedValue)
      }.getOrElse {
        cacheMissCounter.incr()
        Stitch
          .callFuture(clusterIdStore.get(tweetId))
          .flatMap { clusterIdOpt =>
            if (clusterIdOpt.isDefined) {
              storeHitCounter.incr()
            } else {
              storeMissCounter.incr()
            }
            val wrappedClusterIdOpt = Some(clusterIdOpt)
            mediaClipClusterIdInMemCache.set(tweetId, wrappedClusterIdOpt)
            Stitch.value(wrappedClusterIdOpt)
          }
      }.handle {
        case _: Exception =>
          fetchExceptionCounter.incr()
          None
      }
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    query.getOptionalUserId match {
      case Some(userId) =>
        val featureMapResult: Future[Seq[Long]] = tweetImpressionStore
          .get(userId).map { impressionListOpt =>
            if (impressionListOpt.isEmpty) {
              keyNotFoundCounter.incr()
            }
            keyFoundCounter.incr()

            val tweetIdsOpt = for {
              impressionList <- impressionListOpt
              impressions <- impressionList.impressions
            } yield {
              impressions.take(250).map(_.tweetId)
            }
            tweetIdsOpt.getOrElse(Seq.empty)
          }

        Stitch.callFuture(featureMapResult).flatMap { tweetIds =>
          Stitch
            .traverse(tweetIds) { tweetId =>
              getmediaClipClusterId(tweetId)
            }.map { results: Seq[Option[Option[Long]]] =>
              FeatureMapBuilder().add(ImpressedMediaClusterIds, results.flatten.flatten).build()
            }
        }

      case None =>
        val featureMapResult = FeatureMapBuilder().add(ImpressedMediaClusterIds, Seq.empty).build()
        Stitch.value(featureMapResult)
    }
  }
}
