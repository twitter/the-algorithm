package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.Memcached
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.param.HomeMixerInjectionNames.EntityRealGraphClientStore
import com.twitter.home_mixer.param.HomeMixerInjectionNames.HomeAuthorFeaturesCacheClient
import com.twitter.home_mixer.param.HomeMixerInjectionNames.RealTimeInteractionGraphUserVertexClient
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TimelinesRealTimeAggregateClient
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TwhinAuthorFollowFeatureCacheClient
import com.twitter.inject.TwitterModule
import com.twitter.product_mixer.shared_library.memcached_client.MemcachedClientBuilder
import com.twitter.servo.cache.ExpiringLruInProcessCache
import com.twitter.servo.cache.FinagleMemcache
import com.twitter.servo.cache.FinagleMemcacheFactory
import com.twitter.servo.cache.HotKeyMemcacheClient
import com.twitter.servo.cache.Memcache
import com.twitter.storehaus.ReadableStore
import com.twitter.wtf.entity_real_graph.summingbird.client.EntityRealGraphClient
import com.twitter.wtf.entity_real_graph.summingbird.common.config.Configs.Environment
import com.twitter.wtf.entity_real_graph.{thriftscala => erg}
import com.twitter.finagle.memcached.compressing.scheme.Lz4
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TvRealTimeAggregateClient
import javax.inject.Named
import javax.inject.Singleton

object MemcachedFeatureRepositoryModule extends TwitterModule {

  // This must match the respective parameter on the write path. Note that servo sets a different
  // hasher by default. See [[com.twitter.hashing.KeyHasher]] for the list of other available
  // hashers.
  private val memcacheKeyHasher = "ketama"

  @Provides
  @Singleton
  @Named(TimelinesRealTimeAggregateClient)
  def providesTimelinesRealTimeAggregateClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Memcache = {
    val cacheClient = MemcachedClientBuilder.buildMemcachedClient(
      destName = "/s/cache/timelines_real_time_aggregates:twemcaches",
      numTries = 3,
      numConnections = 1,
      requestTimeout = 100.milliseconds,
      globalTimeout = 300.milliseconds,
      connectTimeout = 200.milliseconds,
      acquisitionTimeout = 200.milliseconds,
      serviceIdentifier = serviceIdentifier,
      statsReceiver = statsReceiver
    )

    val hotkeyCacheClient = new HotKeyMemcacheClient(
      proxyClient = cacheClient,
      inProcessCache = new ExpiringLruInProcessCache(ttl = 15.minute, maximumSize = 75000),
      statsReceiver = statsReceiver.scope(TimelinesRealTimeAggregateClient).scope("inProcess")
    )

    new FinagleMemcache(hotkeyCacheClient, memcacheKeyHasher)
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
      statsReceiver = statsReceiver,
      compressionScheme = Lz4
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

  @Provides
  @Singleton
  @Named(TvRealTimeAggregateClient)
  def providesTvRealTimeAggregateClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Memcache = {
    val cacheClient = MemcachedClientBuilder.buildRawMemcachedClient(
      numTries = 1,
      numConnections = 1,
      requestTimeout = 200.milliseconds,
      globalTimeout = 200.milliseconds,
      connectTimeout = 200.milliseconds,
      acquisitionTimeout = 200.milliseconds,
      serviceIdentifier = serviceIdentifier,
      statsReceiver = statsReceiver
    )
    buildMemcacheClient(cacheClient, "/srv#/prod/local/cache/tv_real_time_aggregates:twemcaches")
  }

  @Provides
  @Singleton
  @Named(EntityRealGraphClientStore)
  def providesEntityRealGraphClient(
    serviceIdentifier: ServiceIdentifier
  ): ReadableStore[erg.EntityRealGraphRequest, erg.EntityRealGraphResponse] = {
    EntityRealGraphClient(Environment.withName("prod"), serviceIdentifier, Some(250.millis))
  }

  private def buildMemcacheClient(cacheClient: Memcached.Client, dest: String): Memcache =
    FinagleMemcacheFactory(
      client = cacheClient,
      dest = dest,
      hashName = memcacheKeyHasher
    )()

}
