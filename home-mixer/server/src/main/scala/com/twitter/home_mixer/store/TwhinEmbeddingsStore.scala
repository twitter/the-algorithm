package com.twitter.home_mixer.store

import com.twitter.bijection.scrooge.BinaryScalaCodec
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.hermit.store.common.ObservedMemcachedReadableStore
import com.twitter.hermit.store.offheap.Codecs
import com.twitter.hermit.store.offheap.OffheapCachedReadableStore
import com.twitter.scrooge.ThriftStruct
import com.twitter.simclusters_v2.summingbird.stores.ManhattanFromStratoStore
import com.twitter.simclusters_v2.thriftscala.PersistentTwhinTweetEmbedding
import com.twitter.simclusters_v2.thriftscala.PersistentTwhinUserEmbedding
import com.twitter.simclusters_v2.{thriftscala => sim}
import com.twitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.twitter.storehaus.ReadableStore
import com.twitter.storehaus_internal.memcache.MemcacheStore
import com.twitter.storehaus_internal.util._
import javax.inject.Inject
import javax.inject.Singleton
import com.twitter.offheap.KeyHashFunction
import com.twitter.offheap.MemoryWriter
import com.twitter.offheap.ObjectCodec
import com.twitter.offheap.OffheapReader
import com.twitter.simclusters_v2.thriftscala.TwhinTweetEmbedding

@Singleton()
class TwhinEmbeddingsStore @Inject() (
  statsReceiver: StatsReceiver,
  serviceIdentifier: ServiceIdentifier) {

  val ManhattanNashDest = "/s/manhattan/nash.native-thrift"
  val TwhinEmbeddingsProdAppId = "twhin_embeddings_prod"
  val UserPositiveDataset = "twhin_user_positive_embeddings"
  val RebuildUserPositiveDataset = "twhin_rebuild_user_rt_pos_emb"
  val UserNegativeDataset = "twhin_user_negative_embeddings"
  val TweetDataset = "twhin_tweet_embeddings"
  val TweetRebuildDataset = "twhin_rebuild_tweet_rt_emb"
  val VideoDataset = "twhin_video_embeddings"

  val MemcacheTweetDest = "/s/cache/twhin_embeddings"
  val MemcacheVideoDest = "/s/cache/twhin_video_embeddings"
  val KeyPrefixTweet = "twhin_tweets"
  val KeyPrefixTweetRebuild = "twhin_tweets_rebuild"
  val KeyPrefixVideo = "twhin_videos"
  val InMemoryCachePrefix = "in_memory_cache"

  val MinEngagementCount = 16
  val IsProdEnv = serviceIdentifier.environment == "prod"

  /**
   * We do not generate the tweet or video embedding if the number of recent engagements
   * is < `MinEngagementCount`. This is based on prior Simcluster embedding aggregation
   * experience and in order to be consistent with the Strato column
   * strato/config/columns/recommendations/twhin/CachedTwhinTweetEmbeddings.Tweet.strato
   */
  private def normalizeByCount(
    persistentEmbedding: sim.PersistentTwhinTweetEmbedding
  ): sim.TwhinTweetEmbedding = {
    val embedding = persistentEmbedding.embedding.embedding
    val updatedEmbedding =
      if (persistentEmbedding.updatedCount < MinEngagementCount) embedding.map(_ => 0.0)
      else embedding.map(_ / persistentEmbedding.updatedCount)
    sim.TwhinTweetEmbedding(updatedEmbedding)
  }

  private def createManhattanStore[T <: ThriftStruct: Manifest](
    dataset: String
  ): ReadableStore[Long, T] = {
    ManhattanFromStratoStore
      .createPersistentTwhinStore[T](
        dataset = dataset,
        mhMtlsParams = ManhattanKVClientMtlsParams(serviceIdentifier),
        statsReceiver = statsReceiver,
        appId = TwhinEmbeddingsProdAppId,
        dest = ManhattanNashDest
      ).composeKeyMapping((_, 0L))
  }

  private def createManhattanVersionedStore[T <: ThriftStruct: Manifest](
    dataset: String
  ): ReadableStore[(Long, Long), T] = {
    ManhattanFromStratoStore
      .createPersistentTwhinStore[T](
        dataset = dataset,
        mhMtlsParams = ManhattanKVClientMtlsParams(serviceIdentifier),
        statsReceiver = statsReceiver,
        appId = TwhinEmbeddingsProdAppId,
        dest = ManhattanNashDest
      ).composeKeyMapping[(Long, Long)](key => key)
  }

  private def createCachedStore[K](
    underlyingStore: ReadableStore[K, sim.TwhinTweetEmbedding],
    cacheDest: String,
    keyPrefix: String,
    keyCodec: ObjectCodec[K],
    keyHashFunction: KeyHashFunction[K]
  ): ReadableStore[K, sim.TwhinTweetEmbedding] = {
    val scopedStatsReceiver = statsReceiver.scope(keyPrefix)
    val underlyingCacheClient = MemcacheStore.memcachedClient(
      name = ClientName(keyPrefix),
      dest = ZkEndPoint(cacheDest),
      statsReceiver = scopedStatsReceiver,
      serviceIdentifier = serviceIdentifier,
      timeout = 80.milliseconds
    )

    val memcacheStore = ObservedMemcachedReadableStore.fromCacheClient(
      backingStore = underlyingStore,
      cacheClient = underlyingCacheClient,
      ttl = 15.minutes,
      asyncUpdate = IsProdEnv
    )(
      valueInjection = BinaryScalaCodec(sim.TwhinTweetEmbedding),
      statsReceiver = scopedStatsReceiver,
      keyToString = { key: K => s"${keyPrefix}_${key}" }
    )

    OffheapCachedReadableStore.fromCache(
      memcacheStore,
      tableSize = 256 * 1024,
      capacity = 256 * 1024 * 2048,
      keyHashFunction = keyHashFunction,
      keyCodec = keyCodec,
      valueCodec = TwhinEmbeddingsStore.TwhinTweetEmbeddingCodec,
      ttl = 1.minutes,
      statsReceiver = scopedStatsReceiver
    )
  }

  val mhUserPositiveStore: ReadableStore[Long, sim.TwhinTweetEmbedding] =
    createManhattanStore[PersistentTwhinUserEmbedding](UserPositiveDataset).mapValues(_.embedding)

  val mhRebuildUserPositiveStore: ReadableStore[(Long, Long), sim.TwhinTweetEmbedding] =
    createManhattanVersionedStore[PersistentTwhinUserEmbedding](RebuildUserPositiveDataset)
      .mapValues(_.embedding)

  val mhUserNegativeStore: ReadableStore[Long, sim.TwhinTweetEmbedding] =
    createManhattanStore[PersistentTwhinUserEmbedding](UserNegativeDataset).mapValues(_.embedding)

  val mhTweetStore: ReadableStore[Long, sim.TwhinTweetEmbedding] =
    createManhattanStore[PersistentTwhinTweetEmbedding](TweetDataset).mapValues(normalizeByCount)

  val mhVideoStore: ReadableStore[Long, sim.TwhinTweetEmbedding] =
    createManhattanStore[PersistentTwhinTweetEmbedding](VideoDataset).mapValues(normalizeByCount)

  val cachedTweetStore: ReadableStore[Long, TwhinTweetEmbedding] = createCachedStore(
    mhTweetStore,
    MemcacheTweetDest,
    KeyPrefixTweet,
    Codecs.LongKeyCodec,
    key => java.lang.Long.hashCode(key)
  )

  val mhTweetRebuildStore: ReadableStore[(Long, Long), sim.TwhinTweetEmbedding] =
    createManhattanVersionedStore[PersistentTwhinTweetEmbedding](TweetRebuildDataset)
      .mapValues(normalizeByCount)

  val cachedTweetRebuildStore: ReadableStore[(Long, Long), TwhinTweetEmbedding] =
    createCachedStore[(Long, Long)](
      mhTweetRebuildStore,
      MemcacheTweetDest,
      KeyPrefixTweetRebuild,
      Codecs.LongTupleKeyCodec,
      key => key.hashCode()
    )

  val cachedVideoStore: ReadableStore[Long, TwhinTweetEmbedding] = createCachedStore(
    mhVideoStore,
    MemcacheVideoDest,
    KeyPrefixVideo,
    Codecs.LongKeyCodec,
    key => java.lang.Long.hashCode(key)
  )
}

object TwhinEmbeddingsStore {
  object TwhinTweetEmbeddingCodec extends ObjectCodec[sim.TwhinTweetEmbedding] {
    override def size(decoded: sim.TwhinTweetEmbedding): Int = {
      Codecs.SeqDoubleCodec.size(decoded.embedding)
    }

    override def encode(decoded: sim.TwhinTweetEmbedding, writer: MemoryWriter): Unit = {
      Codecs.SeqDoubleCodec.encode(decoded.embedding, writer)
    }

    override def decode(reader: OffheapReader): sim.TwhinTweetEmbedding = {

      sim.TwhinTweetEmbedding(
        embedding = Codecs.SeqDoubleCodec.decode(reader)
      )
    }
  }
}
