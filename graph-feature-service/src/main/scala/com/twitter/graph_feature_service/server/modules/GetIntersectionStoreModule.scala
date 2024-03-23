package com.ExTwitter.graph_feature_service.server.modules

import com.google.inject.Provides
import com.ExTwitter.bijection.scrooge.CompactScalaCodec
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.graph_feature_service.common.Configs._
import com.ExTwitter.graph_feature_service.server.stores.GetIntersectionStore
import com.ExTwitter.graph_feature_service.server.stores.GetIntersectionStore.GetIntersectionQuery
import com.ExTwitter.graph_feature_service.thriftscala.CachedIntersectionResult
import com.ExTwitter.hermit.store.common.ObservedMemcachedReadableStore
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.inject.annotations.Flag
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.storehaus_internal.memcache.MemcacheStore
import com.ExTwitter.storehaus_internal.util.{ClientName, ZkEndPoint}
import com.ExTwitter.util.Duration
import javax.inject.{Named, Singleton}

/**
 * Initialize the MemCache based GetIntersectionStore.
 * The Key of MemCache is UserId~CandidateId~FeatureTypes~IntersectionIdLimit.
 */
object GetIntersectionStoreModule extends ExTwitterModule {

  private[this] val requestTimeout: Duration = 25.millis
  private[this] val retries: Int = 0

  @Provides
  @Named("ReadThroughGetIntersectionStore")
  @Singleton
  def provideReadThroughGetIntersectionStore(
    graphFeatureServiceWorkerClients: GraphFeatureServiceWorkerClients,
    serviceIdentifier: ServiceIdentifier,
    @Flag(ServerFlagNames.MemCacheClientName) memCacheName: String,
    @Flag(ServerFlagNames.MemCachePath) memCachePath: String
  )(
    implicit statsReceiver: StatsReceiver
  ): ReadableStore[GetIntersectionQuery, CachedIntersectionResult] = {
    buildMemcacheStore(
      graphFeatureServiceWorkerClients,
      memCacheName,
      memCachePath,
      serviceIdentifier)
  }

  @Provides
  @Named("BypassCacheGetIntersectionStore")
  @Singleton
  def provideReadOnlyGetIntersectionStore(
    graphFeatureServiceWorkerClients: GraphFeatureServiceWorkerClients,
  )(
    implicit statsReceiver: StatsReceiver
  ): ReadableStore[GetIntersectionQuery, CachedIntersectionResult] = {
    // Bypass the Memcache.
    GetIntersectionStore(graphFeatureServiceWorkerClients, statsReceiver)
  }

  private[this] def buildMemcacheStore(
    graphFeatureServiceWorkerClients: GraphFeatureServiceWorkerClients,
    memCacheName: String,
    memCachePath: String,
    serviceIdentifier: ServiceIdentifier,
  )(
    implicit statsReceiver: StatsReceiver
  ): ReadableStore[GetIntersectionQuery, CachedIntersectionResult] = {
    val backingStore = GetIntersectionStore(graphFeatureServiceWorkerClients, statsReceiver)

    val cacheClient = MemcacheStore.memcachedClient(
      name = ClientName(memCacheName),
      dest = ZkEndPoint(memCachePath),
      timeout = requestTimeout,
      retries = retries,
      serviceIdentifier = serviceIdentifier,
      statsReceiver = statsReceiver
    )

    ObservedMemcachedReadableStore.fromCacheClient[GetIntersectionQuery, CachedIntersectionResult](
      backingStore = backingStore,
      cacheClient = cacheClient,
      ttl = MemCacheTTL
    )(
      valueInjection = LZ4Injection.compose(CompactScalaCodec(CachedIntersectionResult)),
      statsReceiver = statsReceiver.scope("mem_cache"),
      keyToString = { key =>
        s"L~${key.userId}~${key.candidateId}~${key.featureTypesString}~${key.intersectionIdLimit}"
      }
    )
  }
}
