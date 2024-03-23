package com.ExTwitter.home_mixer.module

import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.inject.annotations.Flag
import com.ExTwitter.storage.client.manhattan.kv.Guarantee
import com.ExTwitter.storehaus_internal.manhattan.ManhattanClusters
import com.ExTwitter.timelines.clients.manhattan.mhv3.ManhattanClientBuilder
import com.ExTwitter.timelines.impressionstore.store.ManhattanTweetImpressionStoreClientConfig
import com.ExTwitter.timelines.impressionstore.store.ManhattanTweetImpressionStoreClient
import com.ExTwitter.util.Duration
import javax.inject.Singleton

object ManhattanTweetImpressionStoreModule extends ExTwitterModule {

  private val ProdAppId = "timelines_tweet_impression_store_v2"
  private val ProdDataset = "timelines_tweet_impressions_v2"
  private val StagingAppId = "timelines_tweet_impression_store_staging"
  private val StagingDataset = "timelines_tweet_impressions_staging"
  private val StatsScope = "manhattanTweetImpressionStoreClient"
  private val DefaultTTL = 2.days
  private final val Timeout = "mh_impression_store.timeout"

  flag[Duration](Timeout, 150.millis, "Timeout per request")

  @Provides
  @Singleton
  def providesManhattanTweetImpressionStoreClient(
    @Flag(Timeout) timeout: Duration,
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): ManhattanTweetImpressionStoreClient = {

    val (appId, dataset) = serviceIdentifier.environment.toLowerCase match {
      case "prod" => (ProdAppId, ProdDataset)
      case _ => (StagingAppId, StagingDataset)
    }

    val config = ManhattanTweetImpressionStoreClientConfig(
      cluster = ManhattanClusters.nash,
      appId = appId,
      dataset = dataset,
      statsScope = StatsScope,
      defaultGuarantee = Guarantee.SoftDcReadMyWrites,
      defaultMaxTimeout = timeout,
      maxRetryCount = 2,
      isReadOnly = false,
      serviceIdentifier = serviceIdentifier,
      ttl = DefaultTTL
    )

    val manhattanEndpoint = ManhattanClientBuilder.buildManhattanEndpoint(config, statsReceiver)
    ManhattanTweetImpressionStoreClient(config, manhattanEndpoint, statsReceiver)
  }
}
