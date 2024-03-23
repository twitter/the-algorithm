package com.ExTwitter.follow_recommendations.common.clients.cache

import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.Memcached
import com.ExTwitter.finagle.Memcached.Client
import com.ExTwitter.finagle.mtls.client.MtlsStackClient._
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.service.Retries
import com.ExTwitter.finagle.service.RetryPolicy
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import javax.inject.Singleton

object MemcacheModule extends ExTwitterModule {
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
