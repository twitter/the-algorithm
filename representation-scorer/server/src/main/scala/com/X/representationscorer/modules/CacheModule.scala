package com.X.representationscorer.modules

import com.google.inject.Provides
import com.X.finagle.memcached.Client
import javax.inject.Singleton
import com.X.conversions.DurationOps._
import com.X.inject.XModule
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.storehaus_internal.memcache.MemcacheStore
import com.X.storehaus_internal.util.ClientName
import com.X.storehaus_internal.util.ZkEndPoint

object CacheModule extends XModule {

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
