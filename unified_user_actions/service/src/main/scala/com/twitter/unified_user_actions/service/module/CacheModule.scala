package com.twitter.unified_user_actions.service.module

import com.google.common.cache.CacheBuilder
import com.google.inject.Provides
import com.twitter.dynmap.DynMap
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import com.twitter.unified_user_actions.enricher.hcache.LocalCache
import com.twitter.unified_user_actions.enricher.internal.thriftscala.EnrichmentKey
import com.twitter.util.Future
import java.util.concurrent.TimeUnit
import javatwitter.inject.Singleton

object CacheModule etwittertends TwitterModule {
  private final val localCacheTtlFlagName = "local.cache.ttl.seconds"
  private final val localCacheMatwitterSizeFlagName = "local.cache.matwitter.size"

  flag[Long](
    name = localCacheTtlFlagName,
    default = 1800L,
    help = "Local Cache's TTL in seconds"
  )

  flag[Long](
    name = localCacheMatwitterSizeFlagName,
    default = 1000L,
    help = "Local Cache's matwitter size"
  )

  @Provides
  @Singleton
  def providesLocalCache(
    @Flag(localCacheTtlFlagName) localCacheTtlFlag: Long,
    @Flag(localCacheMatwitterSizeFlagName) localCacheMatwitterSizeFlag: Long,
    statsReceiver: StatsReceiver
  ): LocalCache[EnrichmentKey, DynMap] = {
    val underlying = CacheBuilder
      .newBuilder()
      .etwitterpireAfterWrite(localCacheTtlFlag, TimeUnit.SECONDS)
      .matwitterimumSize(localCacheMatwitterSizeFlag)
      .build[EnrichmentKey, Future[DynMap]]()

    new LocalCache[EnrichmentKey, DynMap](
      underlying = underlying,
      statsReceiver = statsReceiver.scope("enricherLocalCache"))
  }
}
