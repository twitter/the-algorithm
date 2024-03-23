package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.keyHasher
import com.ExTwitter.finagle.memcached.{Client => MemcachedClient}
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.hermit.store.common.ObservedCachedReadableStore
import com.ExTwitter.hermit.store.common.ObservedMemcachedReadableStore
import com.ExTwitter.hermit.store.common.ObservedReadableStore
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.relevance_platform.common.injection.LZ4Injection
import com.ExTwitter.relevance_platform.common.injection.SeqObjectInjection
import com.ExTwitter.simclusters_v2.thriftscala.TopicId
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.strato.client.Client
import com.ExTwitter.topic_recos.stores.CertoTopicTopKTweetsStore
import com.ExTwitter.topic_recos.thriftscala.TweetWithScores

object CertoStratoStoreModule extends ExTwitterModule {

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
