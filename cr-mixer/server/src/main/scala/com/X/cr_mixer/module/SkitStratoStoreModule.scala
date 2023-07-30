package com.X.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.X.conversions.DurationOps._
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.similarity_engine.SimilarityEngine.keyHasher
import com.X.finagle.memcached.{Client => MemcachedClient}
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.store.strato.StratoFetchableStore
import com.X.hermit.store.common.ObservedCachedReadableStore
import com.X.hermit.store.common.ObservedMemcachedReadableStore
import com.X.hermit.store.common.ObservedReadableStore
import com.X.inject.XModule
import com.X.relevance_platform.common.injection.LZ4Injection
import com.X.relevance_platform.common.injection.SeqObjectInjection
import com.X.storehaus.ReadableStore
import com.X.strato.client.Client
import com.X.topic_recos.thriftscala.TopicTopTweets
import com.X.topic_recos.thriftscala.TopicTweet
import com.X.topic_recos.thriftscala.TopicTweetPartitionFlatKey

/**
 * Strato store that wraps the topic top tweets pipeline indexed from a Summingbird job
 */
object SkitStratoStoreModule extends XModule {

  val column = "recommendations/topic_recos/topicTopTweets"

  @Provides
  @Singleton
  @Named(ModuleNames.SkitStratoStoreName)
  def providesSkitStratoStore(
    @Named(ModuleNames.UnifiedCache) crMixerUnifiedCacheClient: MemcachedClient,
    stratoClient: Client,
    statsReceiver: StatsReceiver
  ): ReadableStore[TopicTweetPartitionFlatKey, Seq[TopicTweet]] = {
    val skitStore = ObservedReadableStore(
      StratoFetchableStore
        .withUnitView[TopicTweetPartitionFlatKey, TopicTopTweets](stratoClient, column))(
      statsReceiver.scope(ModuleNames.SkitStratoStoreName)).mapValues { topicTopTweets =>
      topicTopTweets.topTweets
    }

    val memCachedStore = ObservedMemcachedReadableStore
      .fromCacheClient(
        backingStore = skitStore,
        cacheClient = crMixerUnifiedCacheClient,
        ttl = 10.minutes
      )(
        valueInjection = LZ4Injection.compose(SeqObjectInjection[TopicTweet]()),
        statsReceiver = statsReceiver.scope("memcached_skit_store"),
        keyToString = { k => s"skit:${keyHasher.hashKey(k.toString.getBytes)}" }
      )

    ObservedCachedReadableStore.from[TopicTweetPartitionFlatKey, Seq[TopicTweet]](
      memCachedStore,
      ttl = 5.minutes,
      maxKeys = 100000, // ~150MB max
      cacheName = "skit_in_memory_cache",
      windowSize = 10000L
    )(statsReceiver.scope("skit_in_memory_cache"))
  }
}
