package com.ExTwitter.home_mixer.module

import com.google.inject.Provides

import com.ExTwitter.adserver.{thriftscala => ads}
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.storage.client.manhattan.kv.Guarantee
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.storehaus_internal.manhattan.ManhattanCluster
import com.ExTwitter.storehaus_internal.manhattan.ManhattanClusters
import com.ExTwitter.timelines.clients.ads.AdvertiserBrandSafetySettingsStore
import com.ExTwitter.timelines.clients.manhattan.mhv3.ManhattanClientBuilder
import com.ExTwitter.timelines.clients.manhattan.mhv3.ManhattanClientConfigWithDataset
import com.ExTwitter.util.Duration

import javax.inject.Singleton

object AdvertiserBrandSafetySettingsStoreModule extends ExTwitterModule {

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
