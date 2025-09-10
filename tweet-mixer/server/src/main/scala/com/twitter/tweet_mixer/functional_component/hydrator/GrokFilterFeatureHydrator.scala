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
import com.twitter.tweet_mixer.utils.MemcacheStitchClient
import com.twitter.tweet_mixer.utils.Transformers
import com.twitter.tweet_mixer.utils.Utils
import com.twitter.strato.generated.client.content_understanding.ColdStartPostsMetadataMhClientColumn
import com.twitter.strato.columns.content_understanding.content_exploration.thriftscala.ColdStartPostStatus
import javax.inject.Inject
import javax.inject.Singleton

object GrokFilterFeature extends Feature[TweetCandidate, Boolean]

@Singleton
class GrokFilterFeatureHydrator @Inject() (
  coldStartPostsMetadataMhClientColumn: ColdStartPostsMetadataMhClientColumn,
  statsReceiver: StatsReceiver,
  memcache: MemcacheStitchClient,
  inMemoryCache: AsyncValueCache[java.lang.Long, Boolean])
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("GrokFilter")

  override val features: Set[Feature[_, _]] = Set(GrokFilterFeature)

  private val grokFilterTrueStat = statsReceiver.counter("GrokFilterTrueCount")
  private val grokFilterFalseStat = statsReceiver.counter("GrokFilterFalseCount")

  private val inMemoryCacheRequestCounter = statsReceiver.counter("InMemoryCacheRequests")
  private val memcacheRequestsCounter = statsReceiver.counter("MemcacheMisses")
  private val manhattanRequestCounter = statsReceiver.counter("ManhattanRequests")

  private val TTLSeconds = Utils.randomizedTTL(10 * 60)
  private val DefaultFeatureMap = FeatureMap(GrokFilterFeature, false)

  private def getCacheKey(tweetId: Long): String = "GrokFilter:" + tweetId.toString

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadStitch {
    val featureMaps = candidates.map { candidate =>
      val postId = candidate.candidate.id
      inMemoryCacheRequestCounter.incr()
      inMemoryCache
        .get(postId).getOrElse(fetchFromMemcache(postId)).map { filter =>
          if (filter) grokFilterTrueStat.incr() else grokFilterFalseStat.incr()
          FeatureMap(GrokFilterFeature, filter)
        }
        .handle { case _ => DefaultFeatureMap }
    }

    Stitch.collect(featureMaps)
  }

  private def fetchFromMemcache(tweetId: Long): Stitch[Boolean] = {
    memcacheRequestsCounter.incr()

    memcache
      .get(getCacheKey(tweetId))
      .flatMap {
        case Some(value) =>
          val filter = Transformers.deserializeFilterBoolean(value)
          Stitch.value(filter).applyEffect { result =>
            Stitch.async { inMemoryCache.set(tweetId, Stitch.value(result)) }
          }
        case None =>
          fetchFromManhattan(tweetId)
            .map { result =>
              result match {
                case Some(status) if status != ColdStartPostStatus.Tier1Ineligible => false
                case _ => true
              }
            }.applyEffect { filter =>
              Stitch.async {
                val buf = Transformers.serializeFilterBoolean(filter)
                val memCacheSet = memcache.set(getCacheKey(tweetId), buf, TTLSeconds)
                val inMemoryCacheSet = inMemoryCache.set(tweetId, Stitch.value(filter))
                Stitch.join(inMemoryCacheSet, memCacheSet).map { _ => () }
              }
            }
      }
  }

  private def fetchFromManhattan(postId: Long): Stitch[Option[ColdStartPostStatus]] = {
    manhattanRequestCounter.incr()
    coldStartPostsMetadataMhClientColumn.fetcher
      .fetch(postId)
      .map { response => response.v.flatMap(_.status) }
  }
}
