package com.X.representationscorer.modules

import com.google.inject.Provides
import com.X.decider.Decider
import com.X.finagle.memcached.{Client => MemcachedClient}
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.finagle.thrift.ClientId
import com.X.hermit.store.common.ObservedReadableStore
import com.X.inject.XModule
import com.X.relevance_platform.common.readablestore.ReadableStoreWithTimeout
import com.X.representation_manager.migration.LegacyRMS
import com.X.representationscorer.DeciderConstants
import com.X.simclusters_v2.common.SimClustersEmbedding
import com.X.simclusters_v2.stores.SimClustersEmbeddingStore
import com.X.simclusters_v2.thriftscala.EmbeddingType
import com.X.simclusters_v2.thriftscala.EmbeddingType._
import com.X.simclusters_v2.thriftscala.ModelVersion
import com.X.simclusters_v2.thriftscala.ModelVersion._
import com.X.simclusters_v2.thriftscala.SimClustersEmbeddingId
import com.X.storehaus.ReadableStore
import com.X.util.Timer
import javax.inject.Singleton

object EmbeddingStoreModule extends XModule {
  @Singleton
  @Provides
  def providesEmbeddingStore(
    memCachedClient: MemcachedClient,
    serviceIdentifier: ServiceIdentifier,
    clientId: ClientId,
    timer: Timer,
    decider: Decider,
    stats: StatsReceiver
  ): ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding] = {
    val cacheHashKeyPrefix: String = "RMS"
    val embeddingStoreClient = new LegacyRMS(
      serviceIdentifier,
      memCachedClient,
      stats,
      decider,
      clientId,
      timer,
      cacheHashKeyPrefix
    )

    val underlyingStores: Map[
      (EmbeddingType, ModelVersion),
      ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding]
    ] = Map(
      // Tweet Embeddings
      (
        LogFavBasedTweet,
        Model20m145k2020) -> embeddingStoreClient.logFavBased20M145K2020TweetEmbeddingStore,
      (
        LogFavLongestL2EmbeddingTweet,
        Model20m145k2020) -> embeddingStoreClient.logFavBasedLongestL2Tweet20M145K2020EmbeddingStore,
      // InterestedIn Embeddings
      (
        LogFavBasedUserInterestedInFromAPE,
        Model20m145k2020) -> embeddingStoreClient.LogFavBasedInterestedInFromAPE20M145K2020Store,
      (
        FavBasedUserInterestedIn,
        Model20m145k2020) -> embeddingStoreClient.favBasedUserInterestedIn20M145K2020Store,
      // Author Embeddings
      (
        FavBasedProducer,
        Model20m145k2020) -> embeddingStoreClient.favBasedProducer20M145K2020EmbeddingStore,
      // Entity Embeddings
      (
        LogFavBasedKgoApeTopic,
        Model20m145k2020) -> embeddingStoreClient.logFavBasedApeEntity20M145K2020EmbeddingCachedStore,
      (FavTfgTopic, Model20m145k2020) -> embeddingStoreClient.favBasedTfgTopicEmbedding2020Store,
    )

    val simClustersEmbeddingStore: ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding] = {
      val underlying: ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding] =
        SimClustersEmbeddingStore.buildWithDecider(
          underlyingStores = underlyingStores,
          decider = decider,
          statsReceiver = stats.scope("simClusters_embeddings_store_deciderable")
        )

      val underlyingWithTimeout: ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding] =
        new ReadableStoreWithTimeout(
          rs = underlying,
          decider = decider,
          enableTimeoutDeciderKey = DeciderConstants.enableSimClustersEmbeddingStoreTimeouts,
          timeoutValueKey = DeciderConstants.simClustersEmbeddingStoreTimeoutValueMillis,
          timer = timer,
          statsReceiver = stats.scope("simClusters_embedding_store_timeouts")
        )

      ObservedReadableStore(
        store = underlyingWithTimeout
      )(stats.scope("simClusters_embeddings_store"))
    }
    simClustersEmbeddingStore
  }
}
