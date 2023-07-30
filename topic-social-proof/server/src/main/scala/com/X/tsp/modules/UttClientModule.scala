package com.X.tsp.modules

import com.google.inject.Provides
import com.X.escherbird.util.uttclient.CacheConfigV2
import com.X.escherbird.util.uttclient.CachedUttClientV2
import com.X.escherbird.util.uttclient.UttClientCacheConfigsV2
import com.X.escherbird.utt.strato.thriftscala.Environment
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.strato.client.Client
import com.X.topiclisting.clients.utt.UttClient
import javax.inject.Singleton

object UttClientModule extends XModule {

  @Provides
  @Singleton
  def providesUttClient(
    stratoClient: Client,
    statsReceiver: StatsReceiver
  ): UttClient = {

    // Save 2 ^ 18 UTTs. Promising 100% cache rate
    lazy val defaultCacheConfigV2: CacheConfigV2 = CacheConfigV2(262143)
    lazy val uttClientCacheConfigsV2: UttClientCacheConfigsV2 = UttClientCacheConfigsV2(
      getTaxonomyConfig = defaultCacheConfigV2,
      getUttTaxonomyConfig = defaultCacheConfigV2,
      getLeafIds = defaultCacheConfigV2,
      getLeafUttEntities = defaultCacheConfigV2
    )

    // CachedUttClient to use StratoClient
    lazy val cachedUttClientV2: CachedUttClientV2 = new CachedUttClientV2(
      stratoClient = stratoClient,
      env = Environment.Prod,
      cacheConfigs = uttClientCacheConfigsV2,
      statsReceiver = statsReceiver.scope("CachedUttClient")
    )
    new UttClient(cachedUttClientV2, statsReceiver)
  }
}
