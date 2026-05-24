package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.google.inject.name.Named
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.memcached.{Client => MemcachedClient}
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.hashing.KeyHasher
import com.twitter.home_mixer.param.HomeMixerInjectionNames.StaleTweetsCache
import com.twitter.inject.TwitterModule
import com.twitter.product_mixer.shared_library.memcached_client.MemcachedClientBuilder
import javax.inject.Singleton

object StaleTweetsCacheModule extends TwitterModule {

  @Singleton
  @Provides
  @Named(StaleTweetsCache)
  def providesCache(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): MemcachedClient = {
    MemcachedClientBuilder.buildMemcachedClient(
      destName = "/srv#/prod/local/cache/staletweetscache:twemcaches",
      numTries = 3,
      numConnections = 1,
      requestTimeout = 200.milliseconds,
      globalTimeout = 500.milliseconds,
      connectTimeout = 200.milliseconds,
      acquisitionTimeout = 200.milliseconds,
      serviceIdentifier = serviceIdentifier,
      statsReceiver = statsReceiver,
      failureAccrualPolicy = None,
      keyHasher = Some(KeyHasher.FNV1_32)
    )
  }
}
