package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.twitter.home_mixer.model.HomeFeatures.HasVideoFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetMediaCompletionRateFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetMediaIdsFeature
import com.twitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithModerateTimeout
import com.twitter.home_mixer.param.HomeMixerInjectionNames.MediaCompletionRateInMemCache
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
import com.twitter.strato.client.Client
import com.twitter.strato.generated.client.analytics.video.VideoCompletionRateOnApiMediaClientColumn
import com.twitter.strato.graphql.thriftscala.ApiMediaKey.TweetVideo
import com.twitter.strato.graphql.thriftscala.TweetVideoKey
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class MediaCompletionRateFeatureHydrator @Inject() (
  @Named(BatchedStratoClientWithModerateTimeout) stratoClient: Client,
  @Named(MediaCompletionRateInMemCache) mediaCompletionRateInMemCache: InProcessCache[Long, Double],
  statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("MediaCompletionRate")

  override val features: Set[Feature[_, _]] = Set(TweetMediaCompletionRateFeature)

  private val completionRateFetcher = stratoClient.fetcher[
    VideoCompletionRateOnApiMediaClientColumn.Key,
    VideoCompletionRateOnApiMediaClientColumn.View,
    VideoCompletionRateOnApiMediaClientColumn.Value
  ](VideoCompletionRateOnApiMediaClientColumn.Path)

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val keyFoundCounter = scopedStatsReceiver.counter("key/found")
  private val keyNotFoundCounter = scopedStatsReceiver.counter("key/notFound")
  private val cacheHitCounter = scopedStatsReceiver.counter("cache/hit")
  private val cacheMissCounter = scopedStatsReceiver.counter("cache/miss")
  private val fetchExceptionCounter =
    scopedStatsReceiver.counter("getFromCacheOrFetch/exception")

  private def getFromCacheOrFetch(mediaId: Long): Stitch[Option[Double]] = {
    mediaCompletionRateInMemCache
      .get(mediaId)
      .map { cachedValue =>
        cacheHitCounter.incr()
        Stitch.value(Some(cachedValue))
      }.getOrElse {
        cacheMissCounter.incr()
        val key = TweetVideo(TweetVideoKey(mediaId = mediaId, tweetId = 0L))
        completionRateFetcher
          .fetch(key)
          .flatMap { result =>
            val completionRate = result.v.map(_.organic)
            mediaCompletionRateInMemCache.set(mediaId, completionRate.getOrElse(0.0))
            Stitch.value(completionRate)
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
    val featureMaps = candidates.map { candidate =>
      val hasVideo = candidate.features.getOrElse(HasVideoFeature, false)
      val mediaIds = candidate.features.getOrElse(TweetMediaIdsFeature, Seq.empty[Long])
      val inNetwork = candidate.features.getOrElse(FromInNetworkSourceFeature, false)
      val completionRates = if (hasVideo && !inNetwork) {
        mediaIds.map { mediaId =>
          getFromCacheOrFetch(mediaId).map {
            case Some(completionRate) =>
              keyFoundCounter.incr()
              Some(completionRate)
            case _ =>
              keyNotFoundCounter.incr()
              None
          }
        }
      } else Seq.empty[Stitch[Option[Double]]]

      Stitch.collectToTry(completionRates).map { results =>
        val completionRates = results.map(_.toOption.flatten).flatten
        val maxCompletionRate = if (completionRates.nonEmpty) Some(completionRates.max) else None
        FeatureMap(TweetMediaCompletionRateFeature, maxCompletionRate)
      }
    }
    Stitch.collect(featureMaps)
  }
}
