package com.ExTwitter.home_mixer.module

import com.google.inject.Provides
import com.google.inject.name.Named
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.memcached.{Client => MemcachedClient}
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.hashing.KeyHasher
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.StaleTweetsCache
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.product_mixer.shared_library.memcached_client.MemcachedClientBuilder
import javax.inject.Singleton

object StaleTweetsCacheModule extends ExTwitterModule {

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
