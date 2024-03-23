package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.ExTwitter.bijection.scrooge.BinaryScalaCodec
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.thriftscala.CrMixerTweetResponse
import com.ExTwitter.finagle.memcached.{Client => MemcachedClient}
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.hermit.store.common.ReadableWritableStore
import com.ExTwitter.hermit.store.common.ObservedReadableWritableMemcacheStore
import com.ExTwitter.simclusters_v2.common.UserId
import javax.inject.Named

object TweetRecommendationResultsStoreModule extends ExTwitterModule {
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
