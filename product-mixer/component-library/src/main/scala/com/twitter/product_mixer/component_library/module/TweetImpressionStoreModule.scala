package com.ExTwitter.product_mixer.component_library.module

import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.Memcached
import com.ExTwitter.finagle.Resolver
import com.ExTwitter.finagle.memcached.protocol.Command
import com.ExTwitter.finagle.memcached.protocol.Response
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.mtls.client.MtlsStackClient._
import com.ExTwitter.finagle.param.HighResTimer
import com.ExTwitter.finagle.service.RetryExceptionsFilter
import com.ExTwitter.finagle.service.RetryPolicy
import com.ExTwitter.finagle.service.StatsFilter
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.timelines.impressionstore.store.TweetImpressionsStore
import com.ExTwitter.timelines.impressionstore.thriftscala.ImpressionList
import javax.inject.Singleton

object TweetImpressionStoreModule extends ExTwitterModule {
  private val TweetImpressionMemcacheWilyPath = "/s/cache/timelines_impressionstore:twemcaches"
  private val tweetImpressionLabel = "timelinesTweetImpressions"

  @Provides
  @Singleton
  def provideTimelineTweetImpressionStore(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): ReadableStore[Long, ImpressionList] = {
    val scopedStatsReceiver = statsReceiver.scope("timelinesTweetImpressions")

    // the below values for configuring the Memcached client
    // are set to be the same as Home timeline's read path to the impression store.
    val acquisitionTimeoutMillis = 200.milliseconds
    val requestTimeoutMillis = 300.milliseconds
    val numTries = 2

    val statsFilter = new StatsFilter[Command, Response](scopedStatsReceiver)
    val retryFilter = new RetryExceptionsFilter[Command, Response](
      retryPolicy = RetryPolicy.tries(
        numTries,
        RetryPolicy.TimeoutAndWriteExceptionsOnly
          .orElse(RetryPolicy.ChannelClosedExceptionsOnly)
      ),
      timer = HighResTimer.Default,
      statsReceiver = scopedStatsReceiver
    )

    val client = Memcached.client
      .withMutualTls(serviceIdentifier)
      .withSession
      .acquisitionTimeout(acquisitionTimeoutMillis)
      .withRequestTimeout(requestTimeoutMillis)
      .withStatsReceiver(scopedStatsReceiver)
      .filtered(statsFilter.andThen(retryFilter))
      .newRichClient(
        dest = Resolver.eval(TweetImpressionMemcacheWilyPath),
        label = tweetImpressionLabel
      )

    TweetImpressionsStore.tweetImpressionsStore(client)
  }

}
