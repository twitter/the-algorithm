package com.twitter.simclustersann.modules

import com.google.inject.Provides
import com.twitter.finagle.memcached.Client
import javax.inject.Singleton
import com.twitter.conversions.DurationOps._
import com.twitter.inject.TwitterModule
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.annotations.Flag
import com.twitter.simclustersann.common.FlagNames
import com.twitter.storehaus_internal.memcache.MemcacheStore
import com.twitter.storehaus_internal.util.ClientName
import com.twitter.storehaus_internal.util.ZkEndPoint

object CacheModule extends TwitterModule {

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
