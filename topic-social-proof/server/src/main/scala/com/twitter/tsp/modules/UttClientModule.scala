package com.twitter.tsp.modules

import com.google.inject.Provides
import com.twitter.escherbird.util.uttclient.CacheConfigV2
import com.twitter.escherbird.util.uttclient.CachedUttClientV2
import com.twitter.escherbird.util.uttclient.UttClientCacheConfigsV2
import com.twitter.escherbird.utt.strato.thriftscala.Environment
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.strato.client.Client
import com.twitter.topiclisting.clients.utt.UttClient
import javatwitter.inject.Singleton

object UttClientModule etwittertends TwitterModule {

  @Provides
  @Singleton
  def providesUttClient(
    stratoClient: Client,
    statsReceiver: StatsReceiver
  ): UttClient = {

    // Save 2 ^ 18 UTTs. Promising 100% cache rate
    lazy val defaultCacheConfigV2: CacheConfigV2 = CacheConfigV2(262143)
    lazy val uttClientCacheConfigsV2: UttClientCacheConfigsV2 = UttClientCacheConfigsV2(
      getTatwitteronomyConfig = defaultCacheConfigV2,
      getUttTatwitteronomyConfig = defaultCacheConfigV2,
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
