package com.twitter.home_mixer.store

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.hermit.store.common.ObservedMemcachedReadableStore
import com.twitter.storage.client.manhattan.bijections.Bijections
import com.twitter.storage.client.manhattan.kv.ManhattanKVClient
import com.twitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.twitter.storage.client.manhattan.kv.ManhattanKVEndpointBuilder
import com.twitter.storage.client.manhattan.kv.impl.ReadOnlyKeyDescriptor
import com.twitter.storage.client.manhattan.kv.impl.ValueDescriptor
import com.twitter.storehaus.ReadableStore
import com.twitter.storehaus_internal.memcache.MemcacheStore
import com.twitter.storehaus_internal.util.ClientName
import com.twitter.storehaus_internal.util.ZkEndPoint
import com.twitter.media_understanding.video_summary.thriftscala.VideoEmbedding
import com.twitter.bijection.Injection
import com.twitter.bijection.scrooge.BinaryScalaCodec
import com.twitter.io.Buf
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoEmbeddingMHStore @Inject() (
  statsReceiver: StatsReceiver,
  serviceIdentifier: ServiceIdentifier) {

  private val ManhattanDest = "/s/manhattan/nash.native-thrift"
  private val AppId = "media_understanding_video_summary"
  private val Dataset = "video_summary_embedding"
  private val MemcacheDest = "/s/cache/video_summary_embedding"
  private val KeyPrefix = "summary_embedding"

  lazy val videoEmbeddingMHStore: ReadableStore[Long, VideoEmbedding] = {
    val manhattanStore = createManhattanStore()
    val cachedStore = createCachedStore(manhattanStore)
    cachedStore
  }

  private def createCachedStore(
    underlyingStore: ReadableStore[Long, VideoEmbedding]
  ): ReadableStore[Long, VideoEmbedding] = {
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
      ttl = 7.days, // foundTtl from config
    )(
      valueInjection = BinaryScalaCodec(VideoEmbedding),
      statsReceiver = memcacheStats,
      keyToString = { key: Long =>
        s"${KeyPrefix}_${key}"
      }
    )

    memcacheStore
  }

  private def createManhattanStore(): ReadableStore[Long, VideoEmbedding] = {

    val keyInjection: Injection[Long, Buf] =
      Bijections.long2ByteArray.andThen(Bijections.BytesBijection)

    val keyDesc = ReadOnlyKeyDescriptor(keyInjection)
    val datasetKey = keyDesc.withDataset(Dataset)

    val valueInjection: Injection[VideoEmbedding, Buf] =
      Bijections.BinaryScalaInjection(VideoEmbedding)

    val valueDesc = ValueDescriptor(valueInjection)

    val client = ManhattanKVClient(
      appId = AppId,
      dest = ManhattanDest,
      mtlsParams = ManhattanKVClientMtlsParams(serviceIdentifier),
      label = "VideoEmbeddingMH"
    )

    val endpoint = ManhattanKVEndpointBuilder(client)
      .maxRetryCount(3)
      .defaultMaxTimeout(120.milliseconds)
      .build()

    new ReadableStore[Long, VideoEmbedding] {
      override def get(key: Long) = {
        val mhKey = datasetKey.withPkey(key)
        Stitch.run(endpoint.get(mhKey, valueDesc)).map { opt =>
          opt.map { mv =>
            mv.contents
          }
        }
      }
    }
  }

}
