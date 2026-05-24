package com.twitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.twitter.conversions.DurationOps._
import com.twitter.cr_mixer.model.ModuleNames
import com.twitter.cr_mixer.similarity_engine.SimilarityEngine.keyHasher
import com.twitter.finagle.memcached.{Client => MemcachedClient}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.hermit.store.common.ObservedCachedReadableStore
import com.twitter.hermit.store.common.ObservedMemcachedReadableStore
import com.twitter.hermit.store.common.ObservedReadableStore
import com.twitter.inject.TwitterModule
import com.twitter.relevance_platform.common.injection.LZ4Injection
import com.twitter.relevance_platform.common.injection.SeqObjectInjection
import com.twitter.simclusters_v2.thriftscala.TopicId
import com.twitter.storehaus.ReadableStore
import com.twitter.strato.client.Client
import com.twitter.topic_recos.stores.CertoTopicTopKTweetsStore
import com.twitter.topic_recos.thriftscala.TweetWithScores

object CertoStratoStoreModule extends TwitterModule {

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
