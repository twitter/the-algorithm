package com.twitter.representationscorer.modules

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.inject.TwitterModule
import com.twitter.representation_manager.config.ClientConfig
import com.twitter.representation_manager.config.EnabledInMemoryCacheParams
import com.twitter.representation_manager.config.InMemoryCacheParams
import com.twitter.simclusters_v2.thriftscala.EmbeddingType
import com.twitter.simclusters_v2.thriftscala.EmbeddingType._
import com.twitter.simclusters_v2.thriftscala.ModelVersion
import com.twitter.simclusters_v2.thriftscala.ModelVersion._
import javax.inject.Singleton

object RMSConfigModule extends TwitterModule {
  def getCacheName(embedingType: EmbeddingType, modelVersion: ModelVersion): String =
    s"${embedingType.name}_${modelVersion.name}_in_mem_cache"

  @Singleton
  @Provides
  def providesRMSClientConfig: ClientConfig = {
    val cacheParamsMap: Map[
      (EmbeddingType, ModelVersion),
      InMemoryCacheParams
    ] = Map(
      // Tweet Embeddings
      (LogFavBasedTweet, Model20m145k2020) -> EnabledInMemoryCacheParams(
        ttl = 10.minutes,
        maxKeys = 1048575, // 800MB
        cacheName = getCacheName(LogFavBasedTweet, Model20m145k2020)),
      (LogFavLongestL2EmbeddingTweet, Model20m145k2020) -> EnabledInMemoryCacheParams(
        ttl = 5.minute,
        maxKeys = 1048575, // 800MB
        cacheName = getCacheName(LogFavLongestL2EmbeddingTweet, Model20m145k2020)),
      // User - KnownFor Embeddings
      (FavBasedProducer, Model20m145k2020) -> EnabledInMemoryCacheParams(
        ttl = 1.day,
        maxKeys = 500000, // 400MB
        cacheName = getCacheName(FavBasedProducer, Model20m145k2020)),
      // User - InterestedIn Embeddings
      (LogFavBasedUserInterestedInFromAPE, Model20m145k2020) -> EnabledInMemoryCacheParams(
        ttl = 6.hours,
        maxKeys = 262143,
        cacheName = getCacheName(LogFavBasedUserInterestedInFromAPE, Model20m145k2020)),
      (FavBasedUserInterestedIn, Model20m145k2020) -> EnabledInMemoryCacheParams(
        ttl = 6.hours,
        maxKeys = 262143,
        cacheName = getCacheName(FavBasedUserInterestedIn, Model20m145k2020)),
      // Topic Embeddings
      (FavTfgTopic, Model20m145k2020) -> EnabledInMemoryCacheParams(
        ttl = 12.hours,
        maxKeys = 262143, // 200MB
        cacheName = getCacheName(FavTfgTopic, Model20m145k2020)),
      (LogFavBasedKgoApeTopic, Model20m145k2020) -> EnabledInMemoryCacheParams(
        ttl = 6.hours,
        maxKeys = 262143,
        cacheName = getCacheName(LogFavBasedKgoApeTopic, Model20m145k2020)),
    )

    new ClientConfig(inMemCacheParamsOverrides = cacheParamsMap)
  }

}
