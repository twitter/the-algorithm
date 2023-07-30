package com.X.home_mixer.module

import com.google.inject.Provides
import com.X.conversions.DurationOps._
import com.X.finagle.Memcached
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.home_mixer.param.HomeMixerInjectionNames.HomeAuthorFeaturesCacheClient
import com.X.home_mixer.param.HomeMixerInjectionNames.RealTimeInteractionGraphUserVertexClient
import com.X.home_mixer.param.HomeMixerInjectionNames.TimelinesRealTimeAggregateClient
import com.X.home_mixer.param.HomeMixerInjectionNames.TwhinAuthorFollowFeatureCacheClient
import com.X.inject.XModule
import com.X.product_mixer.shared_library.memcached_client.MemcachedClientBuilder
import com.X.servo.cache.FinagleMemcacheFactory
import com.X.servo.cache.Memcache
import javax.inject.Named
import javax.inject.Singleton

object MemcachedFeatureRepositoryModule extends XModule {

  // This must match the respective parameter on the write path. Note that servo sets a different
  // hasher by default. See [[com.X.hashing.KeyHasher]] for the list of other available
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
