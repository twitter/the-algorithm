package com.X.tsp.modules

import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.app.Flag
import com.X.finagle.memcached.Client
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.storehaus_internal.memcache.MemcacheStore
import com.X.storehaus_internal.util.ClientName
import com.X.storehaus_internal.util.ZkEndPoint

object UnifiedCacheClient extends XModule {
  val tspUnifiedCacheDest: Flag[String] = flag[String](
    name = "tsp.unifiedCacheDest",
    default = "/srv#/prod/local/cache/topic_social_proof_unified",
    help = "Wily path to topic social proof unified cache"
  )

  @Provides
  @Singleton
  def provideUnifiedCacheClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver,
  ): Client =
    MemcacheStore.memcachedClient(
      name = ClientName("topic-social-proof-unified-memcache"),
      dest = ZkEndPoint(tspUnifiedCacheDest()),
      statsReceiver = statsReceiver.scope("cache_client"),
      serviceIdentifier = serviceIdentifier
    )
}
