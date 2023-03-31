package com.twitter.home_mixer.module

import com.google.inject.Provides

import com.twitter.adserver.{thriftscala => ads}
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.storage.client.manhattan.kv.Guarantee
import com.twitter.storehaus.ReadableStore
import com.twitter.storehaus_internal.manhattan.ManhattanCluster
import com.twitter.storehaus_internal.manhattan.ManhattanClusters
import com.twitter.timelines.clients.ads.AdvertiserBrandSafetySettingsStore
import com.twitter.timelines.clients.manhattan.mhv3.ManhattanClientBuilder
import com.twitter.timelines.clients.manhattan.mhv3.ManhattanClientConfigWithDataset
import com.twitter.util.Duration

import javax.inject.Singleton

object AdvertiserBrandSafetySettingsStoreModule extends TwitterModule {

  @Provides
  @Singleton
  def providesAdvertiserBrandSafetySettingsStore(
    injectedServiceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): ReadableStore[Long, ads.AdvertiserBrandSafetySettings] = {
    val advertiserBrandSafetySettingsManhattanClientConfig = new ManhattanClientConfigWithDataset {
      override val cluster: ManhattanCluster = ManhattanClusters.apollo
      override val appId: String = "brand_safety_apollo"
      override val dataset = "advertiser_brand_safety_settings"
      override val statsScope: String = "AdvertiserBrandSafetySettingsManhattanClient"
      override val defaultGuarantee = Guarantee.Weak
      override val defaultMaxTimeout: Duration = 100.milliseconds
      override val maxRetryCount: Int = 1
      override val isReadOnly: Boolean = true
      override val serviceIdentifier: ServiceIdentifier = injectedServiceIdentifier
    }

    val advertiserBrandSafetySettingsManhattanEndpoint = ManhattanClientBuilder
      .buildManhattanEndpoint(advertiserBrandSafetySettingsManhattanClientConfig, statsReceiver)

    val advertiserBrandSafetySettingsStore: ReadableStore[Long, ads.AdvertiserBrandSafetySettings] =
      AdvertiserBrandSafetySettingsStore
        .cached(
          advertiserBrandSafetySettingsManhattanEndpoint,
          advertiserBrandSafetySettingsManhattanClientConfig.dataset,
          ttl = 60.minutes,
          maxKeys = 100000,
          windowSize = 10L
        )(statsReceiver)

    advertiserBrandSafetySettingsStore
  }
}
