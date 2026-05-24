package com.twitter.tweet_mixer.module

import com.google.inject.Provides
import com.twitter.frigate.common.store.strato.StratoFetchableStore
import com.twitter.tweet_mixer.store.TwhinEmbeddingsStore
import com.twitter.inject.TwitterModule
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.simclusters_v2.common.VersionId
import com.twitter.simclusters_v2.thriftscala.TwhinTweetEmbedding
import com.twitter.storehaus.ReadableStore
import com.twitter.strato.client.{Client => StratoClient}
import com.twitter.tweet_mixer.model.ModuleNames

import javax.inject.Named
import javax.inject.Singleton

object TwHINEmbeddingStoreModule extends TwitterModule {

  val prodCachedTweetStratoColumn = "recommendations/twhin/CachedTwhinTweetEmbeddings.Tweet"
  val prodCachedRebuildTweetStratoColumn =
    "recommendations/twhin/CachedTwhinRebuildVersionedTweetEmbeddings"

  @Provides
  @Singleton
  @Named(ModuleNames.TwhinRebuildUserPositiveEmbeddingsStore)
  def providesTwhinRebuildUserPositiveEmbeddingFeatureStore(
    twhinEmbeddingStore: TwhinEmbeddingsStore
  ): ReadableStore[(Long, Long), TwhinTweetEmbedding] =
    twhinEmbeddingStore.mhRebuildUserPositiveStore

  @Provides
  @Singleton
  @Named(ModuleNames.TwHINTweetEmbeddingStratoStore)
  def providesTwhinTweetEmbeddingStore(
    stratoClient: StratoClient
  ): ReadableStore[TweetId, TwhinTweetEmbedding] = {
    StratoFetchableStore.withUnitView[TweetId, TwhinTweetEmbedding](
      stratoClient,
      column = prodCachedTweetStratoColumn
    )
  }

  @Provides
  @Singleton
  @Named(ModuleNames.TwHINRebuildTweetEmbeddingStratoStore)
  def providesTwHINRebuildTweetEmbeddingStore(
    stratoClient: StratoClient
  ): ReadableStore[(TweetId, VersionId), TwhinTweetEmbedding] = {
    StratoFetchableStore.withUnitView[(TweetId, VersionId), TwhinTweetEmbedding](
      stratoClient,
      column = prodCachedRebuildTweetStratoColumn
    )
  }
}
