package com.twitter.representationscorer.modules

import com.google.inject.Provides
import com.twitter.finagle.memcached.Client
import javax.inject.Singleton
import com.twitter.conversions.DurationOps._
import com.twitter.inject.TwitterModule
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.storehaus_internal.memcache.MemcacheStore
import com.twitter.storehaus_internal.util.ClientName
import com.twitter.storehaus_internal.util.ZkEndPoint

object CacheModule extends TwitterModule {

  private val cacheDest = flag[String]("cache_module.dest", "Path to memcache service")
  private val timeout = flag[Int]("memcache.timeout", "Memcache client timeout")
  private val retries = flag[Int]("memcache.retries", "Memcache timeout retries")

  @Singleton
  @Provides
  def providesCache(
    serviceIdentifier: ServiceIdentifier,
    stats: StatsReceiver
  ): Client =
    MemcacheStore.memcachedClient(
      name = ClientName("memcache_representation_manager"),
      dest = ZkEndPoint(cacheDest()),
      timeout = timeout().milliseconds,
      retries = retries(),
      statsReceiver = stats.scope("cache_client"),
      serviceIdentifier = serviceIdentifier
    )
}
