package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.storage.client.manhattan.kv.Guarantee
import com.twitter.storehaus_internal.manhattan.ManhattanClusters
import com.twitter.timelines.clients.manhattan.store._
import com.twitter.timelines.impressionstore.impressionbloomfilter.ImpressionBloomFilter
import com.twitter.timelines.impressionstore.impressionbloomfilter.ImpressionBloomFilterManhattanKeyValueDescriptor
import javax.inject.Singleton

object ImpressionBloomFilterModule extends TwitterModule {

  private val ProdAppId = "impression_bloom_filter_store"
  private val ProdDataset = "impression_bloom_filter"
  private val StagingAppId = "impression_bloom_filter_store_staging"
  private val StagingDataset = "impression_bloom_filter_staging"
  private val ClientStatsScope = "tweetBloomFilterImpressionManhattanClient"
  private val DefaultTTL = 7.days

  @Provides
  @Singleton
  def providesImpressionBloomFilter(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): ImpressionBloomFilter = {
    val (appId, dataset) = serviceIdentifier.environment.toLowerCase match {
      case "prod" => (ProdAppId, ProdDataset)
      case _ => (StagingAppId, StagingDataset)
    }

    implicit val manhattanKeyValueDescriptor = ImpressionBloomFilterManhattanKeyValueDescriptor(
      dataset = dataset,
      ttl = DefaultTTL
    )

    val manhattanClient = ManhattanStoreClientBuilder.buildManhattanClient(
      serviceIdentifier = serviceIdentifier,
      cluster = ManhattanClusters.nash,
      appId = appId,
      defaultMaxTimeout = 100.milliseconds,
      maxRetryCount = 2,
      defaultGuarantee = Some(Guarantee.SoftDcReadMyWrites),
      isReadOnly = false,
      statsScope = ClientStatsScope,
      statsReceiver = statsReceiver
    )

    ImpressionBloomFilter(manhattanClient)
  }
}
