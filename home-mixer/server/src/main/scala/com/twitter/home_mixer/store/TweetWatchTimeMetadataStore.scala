package com.twitter.home_mixer.store

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.hermit.store.common.ObservedMemcachedReadableStore
import com.twitter.storage.client.manhattan.bijections.Bijections
import com.twitter.storage.client.manhattan.kv.ManhattanKVClient
import com.twitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.twitter.storage.client.manhattan.kv.ManhattanKVEndpointBuilder
import com.twitter.storage.client.manhattan.kv.impl.Component
import com.twitter.storage.client.manhattan.kv.impl.KeyDescriptor
import com.twitter.storage.client.manhattan.kv.impl.ValueDescriptor
import com.twitter.storehaus.ReadableStore
import com.twitter.storehaus_internal.memcache.MemcacheStore
import com.twitter.storehaus_internal.util.ClientName
import com.twitter.storehaus_internal.util.ZkEndPoint
import com.twitter.twistly.thriftscala.VideoViewEngagementType
import com.twitter.twistly.thriftscala.WatchTimeMetadata
import com.twitter.bijection.Injection
import com.twitter.bijection.scrooge.BinaryScalaCodec
import com.twitter.io.Buf
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton
import scala.util.Try

@Singleton
class TweetWatchTimeMetadataStore @Inject() (
  statsReceiver: StatsReceiver,
  serviceIdentifier: ServiceIdentifier) {

  private val ManhattanDest = "/s/manhattan/nash.native-thrift"
  private val AppId = "uss_prod"
  private val Dataset = "video_watch_time_metadata"
  private val MemcacheDest = "/s/cache/tweet_aggregated_watch_time"
  private val KeyPrefix = "" // Empty key prefix as per config

  lazy val tweetWatchTimeMetadataStore: ReadableStore[
    (Long, VideoViewEngagementType),
    WatchTimeMetadata
  ] = {
    val manhattanStore = createManhattanStore()
    val cachedStore = createCachedStore(manhattanStore)
    cachedStore
  }

  private def createCachedStore(
    underlyingStore: ReadableStore[(Long, VideoViewEngagementType), WatchTimeMetadata]
  ): ReadableStore[(Long, VideoViewEngagementType), WatchTimeMetadata] = {
    val memcacheStats = statsReceiver.scope(s"memcache_${KeyPrefix}")
    val underlyingCacheClient = MemcacheStore.memcachedClient(
      name = ClientName(serviceIdentifier.name),
      dest = ZkEndPoint(MemcacheDest),
      statsReceiver = memcacheStats,
      serviceIdentifier = serviceIdentifier,
      timeout = 80.milliseconds
    )

    val memcacheStore = ObservedMemcachedReadableStore.fromCacheClient(
      backingStore = underlyingStore,
      cacheClient = underlyingCacheClient,
      ttl = 10.minutes,
    )(
      valueInjection = BinaryScalaCodec(WatchTimeMetadata),
      statsReceiver = memcacheStats,
      keyToString = { key: (Long, VideoViewEngagementType) =>
        val cacheKey =
          if (KeyPrefix.isEmpty) s"${key._1}_${key._2}" else s"${KeyPrefix}_${key._1}_${key._2}"
        cacheKey
      }
    )

    memcacheStore
  }

  private def createManhattanStore(
  ): ReadableStore[(Long, VideoViewEngagementType), WatchTimeMetadata] = {

    val pkeyInjection: Injection[Long, Buf] =
      Bijections.long2ByteArray.andThen(Bijections.BytesBijection)

    val lkeyInjection: Injection[VideoViewEngagementType, Buf] =
      Injection
        .build[VideoViewEngagementType, Int](_.value)(i => Try(VideoViewEngagementType(i)))
        .andThen(Bijections.int2ByteArray)
        .andThen(Bijections.BytesBijection)

    val keyDesc =
      KeyDescriptor(Component(pkeyInjection), Component(lkeyInjection)).withDataset(Dataset)

    val valueInjection: Injection[WatchTimeMetadata, Buf] =
      Bijections.BinaryScalaInjection(WatchTimeMetadata)

    val valueDesc = ValueDescriptor(valueInjection)

    val client = ManhattanKVClient(
      appId = AppId,
      dest = ManhattanDest,
      mtlsParams = ManhattanKVClientMtlsParams(serviceIdentifier),
      label = "TweetWatchTimeMetadata"
    )

    val endpoint = ManhattanKVEndpointBuilder(client)
      .maxRetryCount(3)
      .defaultMaxTimeout(120.milliseconds)
      .build()

    new ReadableStore[(Long, VideoViewEngagementType), WatchTimeMetadata] {
      override def get(key: (Long, VideoViewEngagementType)) = {
        val (tweetId, engType) = key
        val mhKey = keyDesc.withPkey(tweetId).withLkey(engType)
        Stitch.run(endpoint.get(mhKey, valueDesc)).map { opt =>
          opt.map { mv =>
            mv.contents
          }
        }
      }
    }
  }

}
