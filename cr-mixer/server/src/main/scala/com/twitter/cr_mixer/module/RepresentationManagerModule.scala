package com.ExTwitter.cr_mixer.module

import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.simclusters_v2.common.SimClustersEmbedding
import com.ExTwitter.simclusters_v2.common.TweetId
import com.ExTwitter.simclusters_v2.common.UserId
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.strato.client.{Client => StratoClient}
import com.ExTwitter.representation_manager.thriftscala.SimClustersEmbeddingView
import com.ExTwitter.simclusters_v2.thriftscala.EmbeddingType
import com.ExTwitter.simclusters_v2.thriftscala.ModelVersion
import com.google.inject.Provides
import com.google.inject.Singleton
import javax.inject.Named
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.frigate.common.store.strato.StratoFetchableStore
import com.ExTwitter.hermit.store.common.ObservedReadableStore
import com.ExTwitter.simclusters_v2.thriftscala.{SimClustersEmbedding => ThriftSimClustersEmbedding}

object RepresentationManagerModule extends ExTwitterModule {
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
