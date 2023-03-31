package com.twitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.app.Flag
import com.twitter.conversions.DurationOps._
import com.twitter.cr_mixer.model.ModuleNames
import com.twitter.finagle.memcached.Client
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.storehaus_internal.memcache.MemcacheStore
import com.twitter.storehaus_internal.util.ClientName
import com.twitter.storehaus_internal.util.ZkEndPoint
import javax.inject.Named

object UnifiedCacheClient extends TwitterModule {

  private val TIME_OUT = 20.milliseconds

  val crMixerUnifiedCacheDest: Flag[String] = flag[String](
    name = "crMixer.unifiedCacheDest",
    default = "/s/cache/content_recommender_unified_v2",
    help = "Wily path to Content Recommender unified cache"
  )

  val tweetRecommendationResultsCacheDest: Flag[String] = flag[String](
    name = "tweetRecommendationResults.CacheDest",
    default = "/s/cache/tweet_recommendation_results",
    help = "Wily path to CrMixer getTweetRecommendations() results cache"
  )

  val earlybirdTweetsCacheDest: Flag[String] = flag[String](
    name = "earlybirdTweets.CacheDest",
    default = "/s/cache/crmixer_earlybird_tweets",
    help = "Wily path to CrMixer Earlybird Recency Based Similarity Engine result cache"
  )

  @Provides
  @Singleton
  @Named(ModuleNames.UnifiedCache)
  def provideUnifiedCacheClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver,
  ): Client =
    MemcacheStore.memcachedClient(
      name = ClientName("memcache-content-recommender-unified"),
      dest = ZkEndPoint(crMixerUnifiedCacheDest()),
      statsReceiver = statsReceiver.scope("cache_client"),
      serviceIdentifier = serviceIdentifier,
      timeout = TIME_OUT
    )

  @Provides
  @Singleton
  @Named(ModuleNames.TweetRecommendationResultsCache)
  def providesTweetRecommendationResultsCache(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver,
  ): Client =
    MemcacheStore.memcachedClient(
      name = ClientName("memcache-tweet-recommendation-results"),
      dest = ZkEndPoint(tweetRecommendationResultsCacheDest()),
      statsReceiver = statsReceiver.scope("cache_client"),
      serviceIdentifier = serviceIdentifier,
      timeout = TIME_OUT
    )

  @Provides
  @Singleton
  @Named(ModuleNames.EarlybirdTweetsCache)
  def providesEarlybirdTweetsCache(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver,
  ): Client =
    MemcacheStore.memcachedClient(
      name = ClientName("memcache-crmixer-earlybird-tweets"),
      dest = ZkEndPoint(earlybirdTweetsCacheDest()),
      statsReceiver = statsReceiver.scope("cache_client"),
      serviceIdentifier = serviceIdentifier,
      timeout = TIME_OUT
    )
}
