package com.ExTwitter.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.ExTwitter.cr_mixer.config.TimeoutConfig
import com.ExTwitter.cr_mixer.model.ModelConfig
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.model.TripTweetWithScore
import com.ExTwitter.cr_mixer.similarity_engine.ConsumerEmbeddingBasedTripSimilarityEngine
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.ExTwitter.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.ExTwitter.cr_mixer.similarity_engine.TripEngineQuery
import com.ExTwitter.cr_mixer.thriftscala.SimilarityEngineType
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.hermit.store.common.ObservedReadableStore
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.simclusters_v2.common.SimClustersEmbedding
import com.ExTwitter.simclusters_v2.common.UserId
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.trends.trip_v1.trip_tweets.thriftscala.TripTweet
import com.ExTwitter.trends.trip_v1.trip_tweets.thriftscala.TripDomain
import javax.inject.Named

object ConsumerEmbeddingBasedTripSimilarityEngineModule extends ExTwitterModule {
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
