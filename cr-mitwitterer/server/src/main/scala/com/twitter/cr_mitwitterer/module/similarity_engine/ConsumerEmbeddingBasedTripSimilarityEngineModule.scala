package com.twitter.cr_mitwitterer.module.similarity_engine

import com.google.inject.Provides
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.model.ModelConfig
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.model.TripTweetWithScore
import com.twitter.cr_mitwitterer.similarity_engine.ConsumerEmbeddingBasedTripSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mitwitterer.similarity_engine.StandardSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.TripEngineQuery
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.hermit.store.common.ObservedReadableStore
import com.twitter.inject.TwitterModule
import com.twitter.simclusters_v2.common.SimClustersEmbedding
import com.twitter.simclusters_v2.common.UserId
import com.twitter.storehaus.ReadableStore
import com.twitter.trends.trip_v1.trip_tweets.thriftscala.TripTweet
import com.twitter.trends.trip_v1.trip_tweets.thriftscala.TripDomain
import javatwitter.inject.Named

object ConsumerEmbeddingBasedTripSimilarityEngineModule etwittertends TwitterModule {
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
      identifier = SimilarityEngineType.EtwitterploreTripOfflineSimClustersTweets,
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
