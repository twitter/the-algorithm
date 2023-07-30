package com.X.follow_recommendations.common.clients.cache

import com.google.inject.Provides
import com.X.conversions.DurationOps._
import com.X.finagle.Memcached
import com.X.finagle.Memcached.Client
import com.X.finagle.mtls.client.MtlsStackClient._
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.service.Retries
import com.X.finagle.service.RetryPolicy
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import javax.inject.Singleton

object MemcacheModule extends XModule {
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
