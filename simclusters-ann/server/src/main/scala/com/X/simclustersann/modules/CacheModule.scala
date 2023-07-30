package com.X.simclustersann.modules

import com.google.inject.Provides
import com.X.finagle.memcached.Client
import javax.inject.Singleton
import com.X.conversions.DurationOps._
import com.X.inject.XModule
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.inject.annotations.Flag
import com.X.simclustersann.common.FlagNames
import com.X.storehaus_internal.memcache.MemcacheStore
import com.X.storehaus_internal.util.ClientName
import com.X.storehaus_internal.util.ZkEndPoint

object CacheModule extends XModule {

  @Singleton
  @Provides
  def providesCache(
    @Flag(FlagNames.CacheDest) cacheDest: String,
    @Flag(FlagNames.CacheTimeout) cacheTimeout: Int,
    serviceIdentifier: ServiceIdentifier,
    stats: StatsReceiver
  ): Client =
    MemcacheStore.memcachedClient(
      name = ClientName("memcache_simclusters_ann"),
      dest = ZkEndPoint(cacheDest),
      timeout = cacheTimeout.milliseconds,
      retries = 0,
      statsReceiver = stats.scope("cache_client"),
      serviceIdentifier = serviceIdentifier
    )
}
