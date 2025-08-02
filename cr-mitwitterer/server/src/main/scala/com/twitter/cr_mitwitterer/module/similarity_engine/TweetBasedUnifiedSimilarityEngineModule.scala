package com.twitter.cr_mitwitterer.module.similarity_engine

import com.google.inject.Provides
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.model.TweetWithCandidateGenerationInfo
import com.twitter.cr_mitwitterer.model.TweetWithScore
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.similarity_engine.HnswANNSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.SimClustersANNSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mitwitterer.similarity_engine.StandardSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.TweetBasedQigSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.TweetBasedUnifiedSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.TweetBasedUserTweetGraphSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.TweetBasedUserVideoGraphSimilarityEngine
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.storehaus.ReadableStore
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object TweetBasedUnifiedSimilarityEngineModule etwittertends TwitterModule {

  @Provides
  @Singleton
  @Named(ModuleNames.TweetBasedUnifiedSimilarityEngine)
  def providesTweetBasedUnifiedSimilarityEngine(
    @Named(ModuleNames.TweetBasedUserTweetGraphSimilarityEngine) tweetBasedUserTweetGraphSimilarityEngine: StandardSimilarityEngine[
      TweetBasedUserTweetGraphSimilarityEngine.Query,
      TweetWithScore
    ],
    @Named(ModuleNames.TweetBasedUserVideoGraphSimilarityEngine) tweetBasedUserVideoGraphSimilarityEngine: StandardSimilarityEngine[
      TweetBasedUserVideoGraphSimilarityEngine.Query,
      TweetWithScore
    ],
    @Named(ModuleNames.TweetBasedTwHINANNSimilarityEngine)
    tweetBasedTwHINANNSimilarityEngine: HnswANNSimilarityEngine,
    @Named(ModuleNames.TweetBasedQigSimilarityEngine) tweetBasedQigSimilarityEngine: StandardSimilarityEngine[
      TweetBasedQigSimilarityEngine.Query,
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
    TweetBasedUnifiedSimilarityEngine.Query,
    TweetWithCandidateGenerationInfo
  ] = {

    val underlyingStore: ReadableStore[TweetBasedUnifiedSimilarityEngine.Query, Seq[
      TweetWithCandidateGenerationInfo
    ]] = TweetBasedUnifiedSimilarityEngine(
      tweetBasedUserTweetGraphSimilarityEngine,
      tweetBasedUserVideoGraphSimilarityEngine,
      simClustersANNSimilarityEngine,
      tweetBasedQigSimilarityEngine,
      tweetBasedTwHINANNSimilarityEngine,
      statsReceiver
    )

    new StandardSimilarityEngine[
      TweetBasedUnifiedSimilarityEngine.Query,
      TweetWithCandidateGenerationInfo
    ](
      implementingStore = underlyingStore,
      identifier = SimilarityEngineType.TweetBasedUnifiedSimilarityEngine,
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
