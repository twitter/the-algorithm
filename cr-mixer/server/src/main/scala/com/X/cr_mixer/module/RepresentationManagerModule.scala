package com.X.cr_mixer.module

import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.simclusters_v2.common.SimClustersEmbedding
import com.X.simclusters_v2.common.TweetId
import com.X.simclusters_v2.common.UserId
import com.X.storehaus.ReadableStore
import com.X.strato.client.{Client => StratoClient}
import com.X.representation_manager.thriftscala.SimClustersEmbeddingView
import com.X.simclusters_v2.thriftscala.EmbeddingType
import com.X.simclusters_v2.thriftscala.ModelVersion
import com.google.inject.Provides
import com.google.inject.Singleton
import javax.inject.Named
import com.X.cr_mixer.model.ModuleNames
import com.X.frigate.common.store.strato.StratoFetchableStore
import com.X.hermit.store.common.ObservedReadableStore
import com.X.simclusters_v2.thriftscala.{SimClustersEmbedding => ThriftSimClustersEmbedding}

object RepresentationManagerModule extends XModule {
  private val ColPathPrefix = "recommendations/representation_manager/"
  private val SimclustersTweetColPath = ColPathPrefix + "simClustersEmbedding.Tweet"
  private val SimclustersUserColPath = ColPathPrefix + "simClustersEmbedding.User"

  @Provides
  @Singleton
  @Named(ModuleNames.RmsTweetLogFavLongestL2EmbeddingStore)
  def providesRepresentationManagerTweetStore(
    statsReceiver: StatsReceiver,
    stratoClient: StratoClient,
  ): ReadableStore[TweetId, SimClustersEmbedding] = {
    ObservedReadableStore(
      StratoFetchableStore
        .withView[Long, SimClustersEmbeddingView, ThriftSimClustersEmbedding](
          stratoClient,
          SimclustersTweetColPath,
          SimClustersEmbeddingView(
            EmbeddingType.LogFavLongestL2EmbeddingTweet,
            ModelVersion.Model20m145k2020))
        .mapValues(SimClustersEmbedding(_)))(
      statsReceiver.scope("rms_tweet_log_fav_longest_l2_store"))
  }

  @Provides
  @Singleton
  @Named(ModuleNames.RmsUserFavBasedProducerEmbeddingStore)
  def providesRepresentationManagerUserFavBasedProducerEmbeddingStore(
    statsReceiver: StatsReceiver,
    stratoClient: StratoClient,
  ): ReadableStore[UserId, SimClustersEmbedding] = {
    ObservedReadableStore(
      StratoFetchableStore
        .withView[Long, SimClustersEmbeddingView, ThriftSimClustersEmbedding](
          stratoClient,
          SimclustersUserColPath,
          SimClustersEmbeddingView(
            EmbeddingType.FavBasedProducer,
            ModelVersion.Model20m145k2020
          )
        )
        .mapValues(SimClustersEmbedding(_)))(
      statsReceiver.scope("rms_user_fav_based_producer_store"))
  }

  @Provides
  @Singleton
  @Named(ModuleNames.RmsUserLogFavInterestedInEmbeddingStore)
  def providesRepresentationManagerUserLogFavConsumerEmbeddingStore(
    statsReceiver: StatsReceiver,
    stratoClient: StratoClient,
  ): ReadableStore[UserId, SimClustersEmbedding] = {
    ObservedReadableStore(
      StratoFetchableStore
        .withView[Long, SimClustersEmbeddingView, ThriftSimClustersEmbedding](
          stratoClient,
          SimclustersUserColPath,
          SimClustersEmbeddingView(
            EmbeddingType.LogFavBasedUserInterestedIn,
            ModelVersion.Model20m145k2020
          )
        )
        .mapValues(SimClustersEmbedding(_)))(
      statsReceiver.scope("rms_user_log_fav_interestedin_store"))
  }

  @Provides
  @Singleton
  @Named(ModuleNames.RmsUserFollowInterestedInEmbeddingStore)
  def providesRepresentationManagerUserFollowInterestedInEmbeddingStore(
    statsReceiver: StatsReceiver,
    stratoClient: StratoClient,
  ): ReadableStore[UserId, SimClustersEmbedding] = {
    ObservedReadableStore(
      StratoFetchableStore
        .withView[Long, SimClustersEmbeddingView, ThriftSimClustersEmbedding](
          stratoClient,
          SimclustersUserColPath,
          SimClustersEmbeddingView(
            EmbeddingType.FollowBasedUserInterestedIn,
            ModelVersion.Model20m145k2020
          )
        )
        .mapValues(SimClustersEmbedding(_)))(
      statsReceiver.scope("rms_user_follow_interestedin_store"))
  }
}
