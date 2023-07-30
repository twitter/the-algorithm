package com.X.representation_manager.modules

import com.google.inject.Provides
import com.X.escherbird.util.uttclient.CacheConfigV2
import com.X.escherbird.util.uttclient.CachedUttClientV2
import com.X.escherbird.util.uttclient.UttClientCacheConfigsV2
import com.X.escherbird.utt.strato.thriftscala.Environment
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.strato.client.{Client => StratoClient}
import javax.inject.Singleton

object UttClientModule extends XModule {

  @Singleton
  @Provides
  def providesUttClient(
    stratoClient: StratoClient,
    statsReceiver: StatsReceiver
  ): CachedUttClientV2 = {
    // Save 2 ^ 18 UTTs. Promising 100% cache rate
    val defaultCacheConfigV2: CacheConfigV2 = CacheConfigV2(262143)

    val uttClientCacheConfigsV2: UttClientCacheConfigsV2 = UttClientCacheConfigsV2(
      getTaxonomyConfig = defaultCacheConfigV2,
      getUttTaxonomyConfig = defaultCacheConfigV2,
      getLeafIds = defaultCacheConfigV2,
      getLeafUttEntities = defaultCacheConfigV2
    )

    // CachedUttClient to use StratoClient
    new CachedUttClientV2(
      stratoClient = stratoClient,
      env = Environment.Prod,
      cacheConfigs = uttClientCacheConfigsV2,
      statsReceiver = statsReceiver.scope("cached_utt_client")
    )
  }
}
