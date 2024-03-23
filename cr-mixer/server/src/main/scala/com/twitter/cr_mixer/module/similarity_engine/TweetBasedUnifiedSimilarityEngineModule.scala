package com.ExTwitter.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.model.TweetWithCandidateGenerationInfo
import com.ExTwitter.cr_mixer.model.TweetWithScore
import com.ExTwitter.cr_mixer.config.TimeoutConfig
import com.ExTwitter.cr_mixer.similarity_engine.HnswANNSimilarityEngine
import com.ExTwitter.cr_mixer.similarity_engine.SimClustersANNSimilarityEngine
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.ExTwitter.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.ExTwitter.cr_mixer.similarity_engine.TweetBasedQigSimilarityEngine
import com.ExTwitter.cr_mixer.similarity_engine.TweetBasedUnifiedSimilarityEngine
import com.ExTwitter.cr_mixer.similarity_engine.TweetBasedUserTweetGraphSimilarityEngine
import com.ExTwitter.cr_mixer.similarity_engine.TweetBasedUserVideoGraphSimilarityEngine
import com.ExTwitter.cr_mixer.thriftscala.SimilarityEngineType
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.storehaus.ReadableStore
import javax.inject.Named
import javax.inject.Singleton

object TweetBasedUnifiedSimilarityEngineModule extends ExTwitterModule {

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
