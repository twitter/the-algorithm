package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TwhinRebuildTweetEmbeddingsStore
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TwhinRebuildUserPositiveEmbeddingsStore
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TwhinTweetEmbeddingsStore
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TwhinUserNegativeEmbeddingsStore
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TwhinUserPositiveEmbeddingsStore
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TwhinVideoEmbeddingsStore
import com.twitter.home_mixer.store.TwhinEmbeddingsStore
import com.twitter.inject.TwitterModule
import com.twitter.simclusters_v2.{thriftscala => t}
import com.twitter.storehaus.ReadableStore
import javax.inject.Named
import javax.inject.Singleton

object TwhinEmbeddingsModule extends TwitterModule {

  @Provides
  @Singleton
  @Named(TwhinTweetEmbeddingsStore)
  def providesTwhinTweetEmbeddingFeaturesStore(
    twhinEmbeddingsStore: TwhinEmbeddingsStore
  ): ReadableStore[Long, t.TwhinTweetEmbedding] = twhinEmbeddingsStore.cachedTweetStore

  @Provides
  @Singleton
  @Named(TwhinVideoEmbeddingsStore)
  def providesTwhinVideoEmbeddingFeaturesStore(
    twhinEmbeddingsStore: TwhinEmbeddingsStore
  ): ReadableStore[Long, t.TwhinTweetEmbedding] = twhinEmbeddingsStore.cachedVideoStore

  @Provides
  @Singleton
  @Named(TwhinUserPositiveEmbeddingsStore)
  def providesTwhinUserPositiveEmbeddingFeaturesStore(
    twhinEmbeddingsStore: TwhinEmbeddingsStore
  ): ReadableStore[Long, t.TwhinTweetEmbedding] = twhinEmbeddingsStore.mhUserPositiveStore

  @Provides
  @Singleton
  @Named(TwhinRebuildUserPositiveEmbeddingsStore)
  def providesTwhinRebuildUserPositiveEmbeddingFeatureStore(
    twhinEmbeddingStore: TwhinEmbeddingsStore
  ): ReadableStore[(Long, Long), t.TwhinTweetEmbedding] =
    twhinEmbeddingStore.mhRebuildUserPositiveStore

  @Provides
  @Singleton
  @Named(TwhinUserNegativeEmbeddingsStore)
  def providesTwhinUserNegativeEmbeddingFeaturesStore(
    twhinEmbeddingsStore: TwhinEmbeddingsStore
  ): ReadableStore[Long, t.TwhinTweetEmbedding] = twhinEmbeddingsStore.mhUserNegativeStore

  @Provides
  @Singleton
  @Named(TwhinRebuildTweetEmbeddingsStore)
  def providesTwhinRebuildTweetEmbeddingFeaturesStore(
    twhinEmbeddingsStore: TwhinEmbeddingsStore
  ): ReadableStore[(Long, Long), t.TwhinTweetEmbedding] =
    twhinEmbeddingsStore.cachedTweetRebuildStore
}
