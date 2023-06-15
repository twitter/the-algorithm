package com.twitter.usersignalservice.module

import com.google.inject.Provides
import javax.inject.Singleton
import com.twitter.finagle.memcached.Client
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.conversions.DurationOps._
import com.twitter.storehaus_internal.memcache.MemcacheStore
import com.twitter.storehaus_internal.util.ZkEndPoint
import com.twitter.storehaus_internal.util.ClientName

object CacheModule extends TwitterModule {
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
