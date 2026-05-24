package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.param.HomeMixerInjectionNames.ImageClipClusterIdInMemCache
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
import com.twitter.strato.client.Client
import com.twitter.strato.client.Fetcher
import com.twitter.util.Future
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
case object ImpressedImageClusterIds extends FeatureWithDefaultOnFailure[PipelineQuery, Seq[Long]] {
  override val defaultValue: Seq[Long] = Seq.empty
}

/**
 * Get the list of image cluster ids that the user has already seen.
 */
@Singleton
case class ImpressedImageClusterIdsQueryFeatureHydrator @Inject() (
  stratoClient: Client,
  tweetImpressionStore: ReadableStore[Long, ImpressionList],
  @Named(ImageClipClusterIdInMemCache) imageClipClusterIdInMemCache: InProcessCache[
    Long,
    Option[Option[Long]]
  ],
  statsReceiver: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery] {
  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "ImpressedImageClusterIdsQuery")

  override val features: Set[Feature[_, _]] = Set(ImpressedImageClusterIds)

  val clusterIdFetcher: Fetcher[Long, Unit, Long] =
    stratoClient.fetcher[Long, Unit, Long](
      "videoRecommendations/twitterClip/twitterClipImageClusterIdMh95")

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val keyFoundCounter = scopedStatsReceiver.counter("impressionKey/found")
  private val keyNotFoundCounter = scopedStatsReceiver.counter("impressionKey/notFound")
  private val cacheHitCounter = scopedStatsReceiver.counter("cache/hit")
  private val cacheMissCounter = scopedStatsReceiver.counter("cache/miss")
  private val fetchExceptionCounter =
    scopedStatsReceiver.counter("getFromCacheOrFetch/exception")

  private def getImageClipClusterId(tweetId: Long): Stitch[Option[Option[Long]]] = {
    imageClipClusterIdInMemCache
      .get(tweetId)
      .map { cachedValue =>
        cacheHitCounter.incr()
        Stitch.value(cachedValue)
      }.getOrElse {
        cacheMissCounter.incr()
        clusterIdFetcher
          .fetch(tweetId)
          .map(_.v)
          .liftToOption()
          .flatMap { clusterIdOpt =>
            imageClipClusterIdInMemCache.set(tweetId, clusterIdOpt)
            Stitch.value(clusterIdOpt)
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
              getImageClipClusterId(tweetId)
            }.map { results: Seq[Option[Option[Long]]] =>
              FeatureMapBuilder().add(ImpressedImageClusterIds, results.flatten.flatten).build()
            }
        }

      case None =>
        val featureMapResult = FeatureMapBuilder().add(ImpressedImageClusterIds, Seq.empty).build()
        Stitch.value(featureMapResult)
    }
  }
}
