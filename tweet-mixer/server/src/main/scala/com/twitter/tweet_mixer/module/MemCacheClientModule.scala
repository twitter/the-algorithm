package com.twitter.tweet_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.memcached.{Client => MemcachedClient}
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.hashing.KeyHasher
import com.twitter.inject.TwitterModule
import com.twitter.product_mixer.shared_library.memcached_client.MemcachedClientBuilder
import javax.inject.Singleton

object MemcacheClientModule extends TwitterModule {

  @Singleton
  @Provides
  def providesCache(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): MemcachedClient = {
    MemcachedClientBuilder.buildMemcachedClient(
      destName = "/srv#/prod/local/cache/content_recommender_unified_v2:twemcaches",
      numTries = 1,
      requestTimeout = 15.milliseconds,
      globalTimeout = 20.milliseconds,
      connectTimeout = 15.milliseconds,
      acquisitionTimeout = 15.milliseconds,
      serviceIdentifier = serviceIdentifier,
      statsReceiver = statsReceiver,
      failureAccrualPolicy = None,
      keyHasher = Some(KeyHasher.FNV1A_64)
    )
  }
}
