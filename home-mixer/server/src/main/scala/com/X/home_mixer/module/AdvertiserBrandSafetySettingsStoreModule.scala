package com.X.home_mixer.module

import com.google.inject.Provides

import com.X.adserver.{thriftscala => ads}
import com.X.conversions.DurationOps._
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.storage.client.manhattan.kv.Guarantee
import com.X.storehaus.ReadableStore
import com.X.storehaus_internal.manhattan.ManhattanCluster
import com.X.storehaus_internal.manhattan.ManhattanClusters
import com.X.timelines.clients.ads.AdvertiserBrandSafetySettingsStore
import com.X.timelines.clients.manhattan.mhv3.ManhattanClientBuilder
import com.X.timelines.clients.manhattan.mhv3.ManhattanClientConfigWithDataset
import com.X.util.Duration

import javax.inject.Singleton

object AdvertiserBrandSafetySettingsStoreModule extends XModule {

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
