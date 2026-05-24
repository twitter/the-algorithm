package com.twitter.representation_manager.modules

import com.google.inject.Provides
import com.twitter.escherbird.util.uttclient.CacheConfigV2
import com.twitter.escherbird.util.uttclient.CachedUttClientV2
import com.twitter.escherbird.util.uttclient.UttClientCacheConfigsV2
import com.twitter.escherbird.utt.strato.thriftscala.Environment
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.strato.client.{Client => StratoClient}
import javax.inject.Singleton

object UttClientModule extends TwitterModule {

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
