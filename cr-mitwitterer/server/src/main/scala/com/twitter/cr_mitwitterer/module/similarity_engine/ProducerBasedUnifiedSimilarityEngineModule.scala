package com.twitter.cr_mitwitterer.module.similarity_engine

import com.google.inject.Provides
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.model.TweetWithCandidateGenerationInfo
import com.twitter.cr_mitwitterer.model.TweetWithScore
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.similarity_engine.ProducerBasedUserTweetGraphSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.ProducerBasedUnifiedSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mitwitterer.similarity_engine.StandardSimilarityEngine
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.cr_mitwitterer.similarity_engine.SimClustersANNSimilarityEngine
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.storehaus.ReadableStore
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object ProducerBasedUnifiedSimilarityEngineModule etwittertends TwitterModule {

  @Provides
  @Singleton
  @Named(ModuleNames.ProducerBasedUnifiedSimilarityEngine)
  def providesProducerBasedUnifiedSimilarityEngine(
    @Named(ModuleNames.ProducerBasedUserTweetGraphSimilarityEngine)
    producerBasedUserTweetGraphSimilarityEngine: StandardSimilarityEngine[
      ProducerBasedUserTweetGraphSimilarityEngine.Query,
      TweetWithScore
    ],
    @Named(ModuleNames.SimClustersANNSimilarityEngine)
    simClustersANNSimilarityEngine: StandardSimilarityEngine[
      SimClustersANNSimilarityEngine.Query,
      TweetWithScore
    ],
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver,
  ): StandardSimilarityEngine[
    ProducerBasedUnifiedSimilarityEngine.Query,
    TweetWithCandidateGenerationInfo
  ] = {

    val underlyingStore: ReadableStore[ProducerBasedUnifiedSimilarityEngine.Query, Seq[
      TweetWithCandidateGenerationInfo
    ]] = ProducerBasedUnifiedSimilarityEngine(
      producerBasedUserTweetGraphSimilarityEngine,
      simClustersANNSimilarityEngine,
      statsReceiver
    )

    new StandardSimilarityEngine[
      ProducerBasedUnifiedSimilarityEngine.Query,
      TweetWithCandidateGenerationInfo
    ](
      implementingStore = underlyingStore,
      identifier = SimilarityEngineType.ProducerBasedUnifiedSimilarityEngine,
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
