package com.X.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.X.cr_mixer.config.TimeoutConfig
import com.X.cr_mixer.model.ModelConfig
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.model.TripTweetWithScore
import com.X.cr_mixer.similarity_engine.ConsumerEmbeddingBasedTripSimilarityEngine
import com.X.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.X.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.X.cr_mixer.similarity_engine.TripEngineQuery
import com.X.cr_mixer.thriftscala.SimilarityEngineType
import com.X.finagle.stats.StatsReceiver
import com.X.hermit.store.common.ObservedReadableStore
import com.X.inject.XModule
import com.X.simclusters_v2.common.SimClustersEmbedding
import com.X.simclusters_v2.common.UserId
import com.X.storehaus.ReadableStore
import com.X.trends.trip_v1.trip_tweets.thriftscala.TripTweet
import com.X.trends.trip_v1.trip_tweets.thriftscala.TripDomain
import javax.inject.Named

object ConsumerEmbeddingBasedTripSimilarityEngineModule extends XModule {
  @Provides
  @Named(ModuleNames.ConsumerEmbeddingBasedTripSimilarityEngine)
  def providesConsumerEmbeddingBasedTripSimilarityEngineModule(
    @Named(ModuleNames.RmsUserLogFavInterestedInEmbeddingStore)
    userLogFavInterestedInEmbeddingStore: ReadableStore[UserId, SimClustersEmbedding],
    @Named(ModuleNames.RmsUserFollowInterestedInEmbeddingStore)
    userFollowInterestedInEmbeddingStore: ReadableStore[UserId, SimClustersEmbedding],
    @Named(ModuleNames.TripCandidateStore)
    tripCandidateStore: ReadableStore[TripDomain, Seq[TripTweet]],
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver,
  ): StandardSimilarityEngine[TripEngineQuery, TripTweetWithScore] = {
    val underlyingStore = ObservedReadableStore(
      ConsumerEmbeddingBasedTripSimilarityEngine(
        embeddingStoreLookUpMap = Map(
          ModelConfig.ConsumerLogFavBasedInterestedInEmbedding -> userLogFavInterestedInEmbeddingStore,
          ModelConfig.ConsumerFollowBasedInterestedInEmbedding -> userFollowInterestedInEmbeddingStore,
        ),
        tripCandidateSource = tripCandidateStore,
        statsReceiver
      ))(statsReceiver.scope("TripSimilarityEngine"))

    new StandardSimilarityEngine[TripEngineQuery, TripTweetWithScore](
      implementingStore = underlyingStore,
      identifier = SimilarityEngineType.ExploreTripOfflineSimClustersTweets,
      globalStats = statsReceiver,
      engineConfig = SimilarityEngineConfig(
        timeout = timeoutConfig.similarityEngineTimeout,
        gatingConfig = GatingConfig(
          deciderConfig = None,
          enableFeatureSwitch = None
        )
      )
    )
  }
}
