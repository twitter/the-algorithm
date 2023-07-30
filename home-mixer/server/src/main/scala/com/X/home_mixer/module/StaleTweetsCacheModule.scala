package com.X.home_mixer.module

import com.google.inject.Provides
import com.google.inject.name.Named
import com.X.conversions.DurationOps._
import com.X.finagle.memcached.{Client => MemcachedClient}
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.hashing.KeyHasher
import com.X.home_mixer.param.HomeMixerInjectionNames.StaleTweetsCache
import com.X.inject.XModule
import com.X.product_mixer.shared_library.memcached_client.MemcachedClientBuilder
import javax.inject.Singleton

object StaleTweetsCacheModule extends XModule {

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
