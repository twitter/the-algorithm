package com.ExTwitter.home_mixer.module

import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.Memcached
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.HomeAuthorFeaturesCacheClient
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.RealTimeInteractionGraphUserVertexClient
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.TimelinesRealTimeAggregateClient
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.TwhinAuthorFollowFeatureCacheClient
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.product_mixer.shared_library.memcached_client.MemcachedClientBuilder
import com.ExTwitter.servo.cache.FinagleMemcacheFactory
import com.ExTwitter.servo.cache.Memcache
import javax.inject.Named
import javax.inject.Singleton

object MemcachedFeatureRepositoryModule extends ExTwitterModule {

  // This must match the respective parameter on the write path. Note that servo sets a different
  // hasher by default. See [[com.ExTwitter.hashing.KeyHasher]] for the list of other available
  // hashers.
  private val memcacheKeyHasher = "ketama"

  @Provides
  @Singleton
  @Named(TimelinesRealTimeAggregateClient)
  def providesTimelinesRealTimeAggregateClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Memcache = {
    val rawClient = MemcachedClientBuilder.buildRawMemcachedClient(
      numTries = 3,
      numConnections = 1,
      requestTimeout = 100.milliseconds,
      globalTimeout = 300.milliseconds,
      connectTimeout = 200.milliseconds,
      acquisitionTimeout = 200.milliseconds,
      serviceIdentifier = serviceIdentifier,
      statsReceiver = statsReceiver
    )

    buildMemcacheClient(rawClient, "/s/cache/timelines_real_time_aggregates:twemcaches")
  }

  @Provides
  @Singleton
  @Named(HomeAuthorFeaturesCacheClient)
  def providesHomeAuthorFeaturesCacheClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Memcache = {
    val cacheClient = MemcachedClientBuilder.buildRawMemcachedClient(
      numTries = 2,
      numConnections = 1,
      requestTimeout = 150.milliseconds,
      globalTimeout = 300.milliseconds,
      connectTimeout = 200.milliseconds,
      acquisitionTimeout = 200.milliseconds,
      serviceIdentifier = serviceIdentifier,
      statsReceiver = statsReceiver
    )

    buildMemcacheClient(cacheClient, "/s/cache/timelines_author_features:twemcaches")
  }

  @Provides
  @Singleton
  @Named(TwhinAuthorFollowFeatureCacheClient)
  def providesTwhinAuthorFollowFeatureCacheClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Memcache = {
    val cacheClient = MemcachedClientBuilder.buildRawMemcachedClient(
      numTries = 2,
      numConnections = 1,
      requestTimeout = 150.milliseconds,
      globalTimeout = 300.milliseconds,
      connectTimeout = 200.milliseconds,
      acquisitionTimeout = 200.milliseconds,
      serviceIdentifier = serviceIdentifier,
      statsReceiver = statsReceiver
    )

    buildMemcacheClient(cacheClient, "/s/cache/home_twhin_author_features:twemcaches")
  }

  @Provides
  @Singleton
  @Named(RealTimeInteractionGraphUserVertexClient)
  def providesRealTimeInteractionGraphUserVertexClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Memcache = {
    val cacheClient = MemcachedClientBuilder.buildRawMemcachedClient(
      numTries = 2,
      numConnections = 1,
      requestTimeout = 150.milliseconds,
      globalTimeout = 300.milliseconds,
      connectTimeout = 200.milliseconds,
      acquisitionTimeout = 200.milliseconds,
      serviceIdentifier = serviceIdentifier,
      statsReceiver = statsReceiver
    )

    buildMemcacheClient(cacheClient, "/s/cache/realtime_interactive_graph_prod_v2:twemcaches")
  }

  private def buildMemcacheClient(cacheClient: Memcached.Client, dest: String): Memcache =
    FinagleMemcacheFactory(
      client = cacheClient,
      dest = dest,
      hashName = memcacheKeyHasher
    )()

}
