package com.X.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.bijection.scrooge.BinaryScalaCodec
import com.X.conversions.DurationOps._
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.thriftscala.CrMixerTweetResponse
import com.X.finagle.memcached.{Client => MemcachedClient}
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.hermit.store.common.ReadableWritableStore
import com.X.hermit.store.common.ObservedReadableWritableMemcacheStore
import com.X.simclusters_v2.common.UserId
import javax.inject.Named

object TweetRecommendationResultsStoreModule extends XModule {
  @Provides
  @Singleton
  def providesTweetRecommendationResultsStore(
    @Named(ModuleNames.TweetRecommendationResultsCache) tweetRecommendationResultsCacheClient: MemcachedClient,
    statsReceiver: StatsReceiver
  ): ReadableWritableStore[UserId, CrMixerTweetResponse] = {
    ObservedReadableWritableMemcacheStore.fromCacheClient(
      cacheClient = tweetRecommendationResultsCacheClient,
      ttl = 24.hours)(
      valueInjection = BinaryScalaCodec(CrMixerTweetResponse),
      statsReceiver = statsReceiver.scope("TweetRecommendationResultsMemcacheStore"),
      keyToString = { k: UserId => k.toString }
    )
  }
}
