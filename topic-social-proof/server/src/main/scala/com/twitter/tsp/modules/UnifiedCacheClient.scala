package com.twitter.tsp.modules

import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.app.Flag
import com.twitter.finagle.memcached.Client
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.storehaus_internal.memcache.MemcacheStore
import com.twitter.storehaus_internal.util.ClientName
import com.twitter.storehaus_internal.util.ZkEndPoint

object UnifiedCacheClient extends TwitterModule {
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
