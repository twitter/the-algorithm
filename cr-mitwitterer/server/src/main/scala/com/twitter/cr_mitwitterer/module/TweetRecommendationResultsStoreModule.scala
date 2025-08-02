package com.twitter.cr_mitwitterer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.bijection.scrooge.BinaryScalaCodec
import com.twitter.conversions.DurationOps._
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.thriftscala.CrMitwittererTweetResponse
import com.twitter.finagle.memcached.{Client => MemcachedClient}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.hermit.store.common.ReadableWritableStore
import com.twitter.hermit.store.common.ObservedReadableWritableMemcacheStore
import com.twitter.simclusters_v2.common.UserId
import javatwitter.inject.Named

object TweetRecommendationResultsStoreModule etwittertends TwitterModule {
  @Provides
  @Singleton
  def providesTweetRecommendationResultsStore(
    @Named(ModuleNames.TweetRecommendationResultsCache) tweetRecommendationResultsCacheClient: MemcachedClient,
    statsReceiver: StatsReceiver
  ): ReadableWritableStore[UserId, CrMitwittererTweetResponse] = {
    ObservedReadableWritableMemcacheStore.fromCacheClient(
      cacheClient = tweetRecommendationResultsCacheClient,
      ttl = 24.hours)(
      valueInjection = BinaryScalaCodec(CrMitwittererTweetResponse),
      statsReceiver = statsReceiver.scope("TweetRecommendationResultsMemcacheStore"),
      keyToString = { k: UserId => k.toString }
    )
  }
}
