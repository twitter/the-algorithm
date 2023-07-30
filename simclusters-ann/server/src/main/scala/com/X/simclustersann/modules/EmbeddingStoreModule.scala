package com.X.simclustersann.modules

import com.google.inject.Provides
import com.X.decider.Decider
import com.X.finagle.memcached.{Client => MemcachedClient}
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.representation_manager.StoreBuilder
import com.X.representation_manager.config.{
  DefaultClientConfig => RepresentationManagerDefaultClientConfig
}
import com.X.representation_manager.thriftscala.SimClustersEmbeddingView
import com.X.simclusters_v2.common.SimClustersEmbedding
import com.X.simclusters_v2.stores.SimClustersEmbeddingStore
import com.X.simclusters_v2.thriftscala.EmbeddingType
import com.X.simclusters_v2.thriftscala.EmbeddingType._
import com.X.simclusters_v2.thriftscala.ModelVersion
import com.X.simclusters_v2.thriftscala.ModelVersion._
import com.X.simclusters_v2.thriftscala.SimClustersEmbeddingId
import com.X.storehaus.ReadableStore
import com.X.strato.client.{Client => StratoClient}
import javax.inject.Singleton

object EmbeddingStoreModule extends XModule {

  val TweetEmbeddings: Set[SimClustersEmbeddingView] = Set(
    SimClustersEmbeddingView(LogFavLongestL2EmbeddingTweet, Model20m145kUpdated),
    SimClustersEmbeddingView(LogFavLongestL2EmbeddingTweet, Model20m145k2020)
  )

  val UserEmbeddings: Set[SimClustersEmbeddingView] = Set(
    // KnownFor
    SimClustersEmbeddingView(FavBasedProducer, Model20m145kUpdated),
    SimClustersEmbeddingView(FavBasedProducer, Model20m145k2020),
    SimClustersEmbeddingView(FollowBasedProducer, Model20m145k2020),
    SimClustersEmbeddingView(AggregatableLogFavBasedProducer, Model20m145k2020),
    // InterestedIn
    SimClustersEmbeddingView(UnfilteredUserInterestedIn, Model20m145k2020),
    SimClustersEmbeddingView(
      LogFavBasedUserInterestedMaxpoolingAddressBookFromIIAPE,
      Model20m145k2020),
    SimClustersEmbeddingView(
      LogFavBasedUserInterestedAverageAddressBookFromIIAPE,
      Model20m145k2020),
    SimClustersEmbeddingView(
      LogFavBasedUserInterestedBooktypeMaxpoolingAddressBookFromIIAPE,
      Model20m145k2020),
    SimClustersEmbeddingView(
      LogFavBasedUserInterestedLargestDimMaxpoolingAddressBookFromIIAPE,
      Model20m145k2020),
    SimClustersEmbeddingView(
      LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE,
      Model20m145k2020),
    SimClustersEmbeddingView(
      LogFavBasedUserInterestedConnectedMaxpoolingAddressBookFromIIAPE,
      Model20m145k2020),
    SimClustersEmbeddingView(UserNextInterestedIn, Model20m145k2020),
    SimClustersEmbeddingView(LogFavBasedUserInterestedInFromAPE, Model20m145k2020)
  )

  @Singleton
  @Provides
  def providesEmbeddingStore(
    stratoClient: StratoClient,
    memCachedClient: MemcachedClient,
    decider: Decider,
    stats: StatsReceiver
  ): ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding] = {

    val rmsStoreBuilder = new StoreBuilder(
      clientConfig = RepresentationManagerDefaultClientConfig,
      stratoClient = stratoClient,
      memCachedClient = memCachedClient,
      globalStats = stats,
    )

    val underlyingStores: Map[
      (EmbeddingType, ModelVersion),
      ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding]
    ] = {
      val tweetEmbeddingStores: Map[
        (EmbeddingType, ModelVersion),
        ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding]
      ] = TweetEmbeddings
        .map(embeddingView =>
          (
            (embeddingView.embeddingType, embeddingView.modelVersion),
            rmsStoreBuilder
              .buildSimclustersTweetEmbeddingStoreWithEmbeddingIdAsKey(embeddingView))).toMap

      val userEmbeddingStores: Map[
        (EmbeddingType, ModelVersion),
        ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding]
      ] = UserEmbeddings
        .map(embeddingView =>
          (
            (embeddingView.embeddingType, embeddingView.modelVersion),
            rmsStoreBuilder
              .buildSimclustersUserEmbeddingStoreWithEmbeddingIdAsKey(embeddingView))).toMap

      tweetEmbeddingStores ++ userEmbeddingStores
    }

    SimClustersEmbeddingStore.buildWithDecider(
      underlyingStores = underlyingStores,
      decider = decider,
      statsReceiver = stats
    )
  }
}
