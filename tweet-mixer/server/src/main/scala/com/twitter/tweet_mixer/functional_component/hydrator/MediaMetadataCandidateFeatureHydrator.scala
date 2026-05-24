package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.stitch.cache.AsyncValueCache
import com.twitter.strato.generated.client.videoRecommendations.twitterClip.TwitterClipClusterIdMhClientColumn
import com.twitter.tweet_mixer.feature.MediaClusterIdFeature
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableDebugMode
import com.twitter.tweet_mixer.utils.MemcacheStitchClient
import com.twitter.tweet_mixer.utils.Transformers
import com.twitter.tweet_mixer.utils.Utils

object MediaMetadataCandidateFeatureHydrator {
  val memcachePrefix = "MediaMetadata:"
  val DefaultFeatureMap =
    FeatureMap(MediaClusterIdFeature, None)
  private val MemcacheTTLSeconds = Utils.randomizedTTL(10 * 60)
}

class MediaMetadataCandidateFeatureHydrator(
  twitterClipClusterIdMhClientColumn: TwitterClipClusterIdMhClientColumn,
  memcache: MemcacheStitchClient,
  inMemoryCache: AsyncValueCache[java.lang.Long, Option[Long]],
  statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  import MediaMetadataCandidateFeatureHydrator._

  override val features: Set[Feature[_, _]] =
    Set(MediaClusterIdFeature)

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("MediaMetadata")

  private val mediaMetadataInMemCacheRequestCounter =
    statsReceiver.counter("MediaMetadataInMemCacheRequests")
  private val mediaMetadataInMemCacheMissCounter =
    statsReceiver.counter("MediaMetadataInMemCacheMisses")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val candidatesBatched = candidates.grouped(1000)

    val featureMapsBatched = candidatesBatched.map { candidatesBatch =>
      OffloadFuturePools.offloadStitch {
        Stitch.traverse(candidatesBatch) { candidate =>
          mediaMetadataInMemCacheRequestCounter.incr()
          val tweetId = candidate.candidate.id
          inMemoryCache
            .get(tweetId)
            .getOrElse(getMediaMetadata(query, tweetId))
            .map(FeatureMap(MediaClusterIdFeature, _))
            .handle { case _ => DefaultFeatureMap }
        }
      }
    }
    Stitch.collect(featureMapsBatched.toSeq).map(_.flatten)
  }

  private def getMediaMetadata(
    query: PipelineQuery,
    tweetId: Long
  ): Stitch[Option[Long]] = {
    mediaMetadataInMemCacheMissCounter.incr()
    memcache
      .get(memcachePrefix + tweetId.toString)
      .flatMap {
        case Some(value) =>
          Stitch
            .value(Transformers.deserializeMediaMetadataCacheOption(value))
            .applyEffect { result =>
              Stitch.async {
                inMemoryCache.set(tweetId, Stitch.value(result))
              }
            }
        case None =>
          // If debug mode on, wait for strato, otherwise just do it asynchronously.
          if (query.params(EnableDebugMode)) readFromStrato(query, tweetId)
          else
            Stitch.None.applyEffect { _ =>
              Stitch.async(readFromStrato(query, tweetId))
            }
      }
  }

  private def readFromStrato(
    query: PipelineQuery,
    tweetId: Long
  ): Stitch[Option[Long]] = {
    twitterClipClusterIdMhClientColumn.fetcher
      .fetch(
        tweetId
      ).map(response => response.v).flatMap {
        case Some(mediaClusterId) =>
          val response = Some(mediaClusterId)
          val inMemoryCacheSet = inMemoryCache.set(tweetId, Stitch.value(response))
          val memCacheSet = memcache.set(
            memcachePrefix + tweetId.toString,
            Transformers.serializeMediaMetadataCacheOption(response),
            MemcacheTTLSeconds)
          Stitch.join(inMemoryCacheSet, memCacheSet)
        case _ =>
          val inMemoryCacheSet = inMemoryCache.set(tweetId, Stitch.value(None))
          val memCacheSet = memcache.set(
            memcachePrefix + tweetId.toString,
            Transformers.serializeMediaMetadataCacheOption(None),
            MemcacheTTLSeconds)
          Stitch.join(inMemoryCacheSet, memCacheSet)
      }.map { case (response, _) => response }
  }
}
