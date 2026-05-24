package com.twitter.tweet_mixer.module

import com.twitter.inject.TwitterModule
import com.twitter.finagle.memcached.{Client => MemcachedClient}
import com.twitter.storehaus.ReadableStore
import com.twitter.tweet_mixer.model.ModuleNames
import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.twitter.conversions.DurationOps._
import com.twitter.frigate.common.store.strato.StratoFetchableStore
import com.twitter.hashing.KeyHasher
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.hermit.store.common.ObservedCachedReadableStore
import com.twitter.hermit.store.common.ObservedMemcachedReadableStore
import com.twitter.hermit.store.common.ObservedReadableStore
import com.twitter.relevance_platform.common.injection.LZ4Injection
import com.twitter.relevance_platform.common.injection.SeqObjectInjection
import com.twitter.strato.client.Client
import com.twitter.topic_recos.thriftscala.TopicTopTweets
import com.twitter.topic_recos.thriftscala.TopicTweet
import com.twitter.topic_recos.thriftscala.TopicTweetPartitionFlatKey

object SkitStratoTopicTweetsStoreModule extends TwitterModule {

  final val column = "recommendations/topic_recos/topicTopTweets"

  @Provides
  @Singleton
  @Named(ModuleNames.SkitStratoTopicTweetsStoreName)
  def providesSkitStratoTopicTweetsStore(
    memcachedClient: MemcachedClient,
    stratoClient: Client,
    statsReceiver: StatsReceiver
  ): ReadableStore[TopicTweetPartitionFlatKey, Seq[TopicTweet]] = {
    val skitStore = ObservedReadableStore(
      StratoFetchableStore
        .withUnitView[TopicTweetPartitionFlatKey, TopicTopTweets](stratoClient, column))(
      statsReceiver.scope(ModuleNames.SkitStratoTopicTweetsStoreName)).mapValues { topicTopTweets =>
      topicTopTweets.topTweets
    }

    val memCachedStore = ObservedMemcachedReadableStore
      .fromCacheClient(
        backingStore = skitStore,
        cacheClient = memcachedClient,
        ttl = 10.minutes
      )(
        valueInjection = LZ4Injection.compose(SeqObjectInjection[TopicTweet]()),
        statsReceiver = statsReceiver.scope("memcached_skit_store"),
        keyToString = { k => s"skit:${KeyHasher.FNV1A_64.hashKey(k.toString.getBytes)}" }
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
