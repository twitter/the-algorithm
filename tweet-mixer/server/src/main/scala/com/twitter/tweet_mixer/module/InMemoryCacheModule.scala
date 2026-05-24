package com.twitter.tweet_mixer.module

import com.github.benmanes.caffeine.cache.Caffeine
import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import com.twitter.stitch.Stitch
import com.twitter.stitch.cache.AsyncValueCache
import com.twitter.stitch.cache.ConcurrentMapCache
import com.twitter.tweet_mixer.model.ModuleNames
import com.twitter.tweet_mixer.utils.Utils

import java.util.concurrent.ConcurrentMap
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

object InMemoryCacheModule extends TwitterModule {
  @Singleton
  @Provides
  @Named(ModuleNames.TweeypieInMemCache)
  def providesTweetypieInMemCache(
  ): AsyncValueCache[java.lang.Long, Option[(Int, Long, Long)]] = {
    val TTLSeconds = Utils.randomizedTTL(15 * 60)
    val mapCache: ConcurrentMap[java.lang.Long, Stitch[Option[(Int, Long, Long)]]] =
      Caffeine
        .newBuilder()
        .recordStats()
        .expireAfterWrite(TTLSeconds, TimeUnit.SECONDS)
        .maximumSize(8388607)
        .build[java.lang.Long, Stitch[Option[(Int, Long, Long)]]]
        .asMap
    AsyncValueCache.apply(new ConcurrentMapCache(mapCache))
  }

  @Singleton
  @Provides
  @Named(ModuleNames.MediaMetadataInMemCache)
  def providesMediaMetadataInMemCache(
  ): AsyncValueCache[java.lang.Long, Option[Long]] = {
    val TTLSeconds = Utils.randomizedTTL(15 * 60)
    val mapCache: ConcurrentMap[java.lang.Long, Stitch[Option[Long]]] =
      Caffeine
        .newBuilder()
        .recordStats()
        .expireAfterWrite(TTLSeconds, TimeUnit.SECONDS)
        .maximumSize(8388607)
        .build[java.lang.Long, Stitch[Option[Long]]]
        .asMap
    AsyncValueCache.apply(new ConcurrentMapCache(mapCache))
  }

  @Singleton
  @Provides
  @Named(ModuleNames.GrokFilterInMemCache)
  def providesGrokFilterInMemCache(
  ): AsyncValueCache[java.lang.Long, Boolean] = {
    val TTLSeconds = Utils.randomizedTTL(15 * 60)
    val mapCache: ConcurrentMap[java.lang.Long, Stitch[Boolean]] =
      Caffeine
        .newBuilder()
        .recordStats()
        .expireAfterWrite(TTLSeconds, TimeUnit.SECONDS)
        .maximumSize(8388607)
        .build[java.lang.Long, Stitch[Boolean]]
        .asMap
    AsyncValueCache.apply(new ConcurrentMapCache(mapCache))
  }
}
