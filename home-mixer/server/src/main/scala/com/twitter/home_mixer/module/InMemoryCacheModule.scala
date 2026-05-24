package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.home_mixer.param.HomeMixerInjectionNames.ImageClipClusterIdInMemCache
import com.twitter.home_mixer.param.HomeMixerInjectionNames.IsColdStartPostInMemCache
import com.twitter.home_mixer.param.HomeMixerInjectionNames.MediaClipClusterIdInMemCache
import com.twitter.home_mixer.param.HomeMixerInjectionNames.MediaCompletionRateInMemCache
import com.twitter.inject.TwitterModule
import com.twitter.servo.cache.ExpiringLruInProcessCache
import com.twitter.servo.cache.InProcessCache
import javax.inject.Named
import javax.inject.Singleton
import scala.util.Random

object InMemoryCacheModule extends TwitterModule {
  @Singleton
  @Provides
  @Named(MediaClipClusterIdInMemCache)
  def providesMediaClipClusterIdInMemCache(
  ): InProcessCache[Long, Option[Option[Long]]] = {
    val BaseTTL = 4
    val TTL = (BaseTTL + Random.nextInt(3)).minutes
    val cache: InProcessCache[Long, Option[Option[Long]]] =
      new ExpiringLruInProcessCache(ttl = TTL, maximumSize = 500000)
    cache
  }

  @Singleton
  @Provides
  @Named(ImageClipClusterIdInMemCache)
  def providesImageClipClusterIdInMemCache(
  ): InProcessCache[Long, Option[Option[Long]]] = {
    val BaseTTL = 4
    val TTL = (BaseTTL + Random.nextInt(3)).minutes
    val cache: InProcessCache[Long, Option[Option[Long]]] =
      new ExpiringLruInProcessCache(ttl = TTL, maximumSize = 500000)
    cache
  }

  @Singleton
  @Provides
  @Named(MediaCompletionRateInMemCache)
  def providesMediaCompletionRateInMemCache(
  ): InProcessCache[Long, Double] = {
    val BaseTTL = 20
    val TTL = (BaseTTL + Random.nextInt(15)).minutes
    val cache: InProcessCache[Long, Double] =
      new ExpiringLruInProcessCache(ttl = TTL, maximumSize = 10000000)
    cache
  }

  @Singleton
  @Provides
  @Named(IsColdStartPostInMemCache)
  def providesIsColdStartPostInMemCache(
  ): InProcessCache[Long, Boolean] = {
    val BaseTTL = 4
    val TTL = (BaseTTL + Random.nextInt(3)).minutes
    val cache: InProcessCache[Long, Boolean] =
      new ExpiringLruInProcessCache(ttl = TTL, maximumSize = 500000)
    cache
  }
}
