package com.X.usersignalservice.module

import com.google.inject.Provides
import javax.inject.Singleton
import com.X.finagle.memcached.Client
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.conversions.DurationOps._
import com.X.storehaus_internal.memcache.MemcacheStore
import com.X.storehaus_internal.util.ZkEndPoint
import com.X.storehaus_internal.util.ClientName

object CacheModule extends XModule {
  private val cacheDest =
    flag[String](name = "cache_module.dest", help = "Path to memcache service")
  private val timeout =
    flag[Int](name = "memcache.timeout", help = "Memcache client timeout")

  @Singleton
  @Provides
  def providesCache(
    serviceIdentifier: ServiceIdentifier,
    stats: StatsReceiver
  ): Client =
    MemcacheStore.memcachedClient(
      name = ClientName("memcache_user_signal_service"),
      dest = ZkEndPoint(cacheDest()),
      timeout = timeout().milliseconds,
      retries = 0,
      statsReceiver = stats.scope("memcache"),
      serviceIdentifier = serviceIdentifier
    )
}
