package com.ExTwitter.home_mixer.module

import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.inject.annotations.Flag
import com.ExTwitter.storage.client.manhattan.kv.Guarantee
import com.ExTwitter.storehaus_internal.manhattan.ManhattanClusters
import com.ExTwitter.timelines.clients.manhattan.store._
import com.ExTwitter.timelines.impressionbloomfilter.{thriftscala => blm}
import com.ExTwitter.timelines.impressionstore.impressionbloomfilter.ImpressionBloomFilterManhattanKeyValueDescriptor
import com.ExTwitter.util.Duration
import javax.inject.Singleton

object ImpressionBloomFilterModule extends ExTwitterModule {

  private val ProdAppId = "impression_bloom_filter_store"
  private val ProdDataset = "impression_bloom_filter"
  private val StagingAppId = "impression_bloom_filter_store_staging"
  private val StagingDataset = "impression_bloom_filter_staging"
  private val ClientStatsScope = "tweetBloomFilterImpressionManhattanClient"
  private val DefaultTTL = 7.days
  private final val Timeout = "mh_impression_store_bloom_filter.timeout"

  flag[Duration](Timeout, 150.millis, "Timeout per request")

  @Provides
  @Singleton
  def providesImpressionBloomFilter(
    @Flag(Timeout) timeout: Duration,
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): ManhattanStoreClient[blm.ImpressionBloomFilterKey, blm.ImpressionBloomFilterSeq] = {
    val (appId, dataset) = serviceIdentifier.environment.toLowerCase match {
      case "prod" => (ProdAppId, ProdDataset)
      case _ => (StagingAppId, StagingDataset)
    }

    implicit val manhattanKeyValueDescriptor: ImpressionBloomFilterManhattanKeyValueDescriptor =
      ImpressionBloomFilterManhattanKeyValueDescriptor(
        dataset = dataset,
        ttl = DefaultTTL
      )

    ManhattanStoreClientBuilder.buildManhattanClient(
      serviceIdentifier = serviceIdentifier,
      cluster = ManhattanClusters.nash,
      appId = appId,
      defaultMaxTimeout = timeout,
      maxRetryCount = 2,
      defaultGuarantee = Some(Guarantee.SoftDcReadMyWrites),
      isReadOnly = false,
      statsScope = ClientStatsScope,
      statsReceiver = statsReceiver
    )
  }
}
