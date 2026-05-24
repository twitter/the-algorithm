package com.twitter.follow_recommendations.common.clients.cache

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.Memcached
import com.twitter.finagle.Memcached.Client
import com.twitter.finagle.mtls.client.MtlsStackClient._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.service.Retries
import com.twitter.finagle.service.RetryPolicy
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import javax.inject.Singleton

object MemcacheModule extends TwitterModule {
  @Provides
  @Singleton
  def provideMemcacheClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver,
  ): Client = {
    Memcached.client
      .withMutualTls(serviceIdentifier)
      .withStatsReceiver(statsReceiver.scope("twemcache"))
      .withTransport.connectTimeout(1.seconds)
      .withRequestTimeout(1.seconds)
      .withSession.acquisitionTimeout(10.seconds)
      .configured(Retries.Policy(RetryPolicy.tries(1)))
  }
}
