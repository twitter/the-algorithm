package com.X.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.X.conversions.DurationOps._
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.similarity_engine.SimilarityEngine.keyHasher
import com.X.finagle.memcached.{Client => MemcachedClient}
import com.X.finagle.stats.StatsReceiver
import com.X.hermit.store.common.ObservedCachedReadableStore
import com.X.hermit.store.common.ObservedMemcachedReadableStore
import com.X.hermit.store.common.ObservedReadableStore
import com.X.inject.XModule
import com.X.relevance_platform.common.injection.LZ4Injection
import com.X.relevance_platform.common.injection.SeqObjectInjection
import com.X.simclusters_v2.thriftscala.TopicId
import com.X.storehaus.ReadableStore
import com.X.strato.client.Client
import com.X.topic_recos.stores.CertoTopicTopKTweetsStore
import com.X.topic_recos.thriftscala.TweetWithScores

object CertoStratoStoreModule extends XModule {

  @Provides
  @Singleton
  @Named(ModuleNames.CertoStratoStoreName)
  def providesCertoStratoStore(
    @Named(ModuleNames.UnifiedCache) crMixerUnifiedCacheClient: MemcachedClient,
    stratoClient: Client,
    statsReceiver: StatsReceiver
  ): ReadableStore[TopicId, Seq[TweetWithScores]] = {
    val certoStore = ObservedReadableStore(CertoTopicTopKTweetsStore.prodStore(stratoClient))(
      statsReceiver.scope(ModuleNames.CertoStratoStoreName)).mapValues { topKTweetsWithScores =>
      topKTweetsWithScores.topTweetsByFollowerL2NormalizedCosineSimilarityScore
    }

    val memCachedStore = ObservedMemcachedReadableStore
      .fromCacheClient(
        backingStore = certoStore,
        cacheClient = crMixerUnifiedCacheClient,
        ttl = 10.minutes
      )(
        valueInjection = LZ4Injection.compose(SeqObjectInjection[TweetWithScores]()),
        statsReceiver = statsReceiver.scope("memcached_certo_store"),
        keyToString = { k => s"certo:${keyHasher.hashKey(k.toString.getBytes)}" }
      )

    ObservedCachedReadableStore.from[TopicId, Seq[TweetWithScores]](
      memCachedStore,
      ttl = 5.minutes,
      maxKeys = 100000, // ~150MB max
      cacheName = "certo_in_memory_cache",
      windowSize = 10000L
    )(statsReceiver.scope("certo_in_memory_cache"))
  }
}
