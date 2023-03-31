package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.storage.client.manhattan.kv.Guarantee
import com.twitter.storehaus_internal.manhattan.ManhattanClusters
import com.twitter.timelines.clients.manhattan.mhv3.ManhattanClientBuilder
import com.twitter.timelines.impressionstore.store.ManhattanTweetImpressionStoreClientConfig
import com.twitter.timelines.impressionstore.store.ManhattanTweetImpressionStoreClient
import javax.inject.Singleton

object ManhattanTweetImpressionStoreModule extends TwitterModule {

  private val ProdAppId = "timelines_tweet_impression_store_v2"
  private val ProdDataset = "timelines_tweet_impressions_v2"
  private val StagingAppId = "timelines_tweet_impression_store_staging"
  private val StagingDataset = "timelines_tweet_impressions_staging"
  private val StatsScope = "manhattanTweetImpressionStoreClient"
  private val DefaultTTL = 2.days

  @Provides
  @Singleton
  def providesManhattanTweetImpressionStoreClient(
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
      defaultMaxTimeout = 100.milliseconds,
      maxRetryCount = 2,
      isReadOnly = false,
      serviceIdentifier = serviceIdentifier,
      ttl = DefaultTTL
    )

    val manhattanEndpoint = ManhattanClientBuilder.buildManhattanEndpoint(config, statsReceiver)
    ManhattanTweetImpressionStoreClient(config, manhattanEndpoint, statsReceiver)
  }
}
