package com.X.home_mixer.module

import com.google.inject.Provides
import com.X.conversions.DurationOps._
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.inject.annotations.Flag
import com.X.storage.client.manhattan.kv.Guarantee
import com.X.storehaus_internal.manhattan.ManhattanClusters
import com.X.timelines.clients.manhattan.mhv3.ManhattanClientBuilder
import com.X.timelines.impressionstore.store.ManhattanTweetImpressionStoreClientConfig
import com.X.timelines.impressionstore.store.ManhattanTweetImpressionStoreClient
import com.X.util.Duration
import javax.inject.Singleton

object ManhattanTweetImpressionStoreModule extends XModule {

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
