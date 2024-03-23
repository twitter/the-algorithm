package com.ExTwitter.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.model.TweetWithCandidateGenerationInfo
import com.ExTwitter.cr_mixer.model.TweetWithScore
import com.ExTwitter.cr_mixer.config.TimeoutConfig
import com.ExTwitter.cr_mixer.similarity_engine.ProducerBasedUserTweetGraphSimilarityEngine
import com.ExTwitter.cr_mixer.similarity_engine.ProducerBasedUnifiedSimilarityEngine
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.ExTwitter.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.ExTwitter.cr_mixer.thriftscala.SimilarityEngineType
import com.ExTwitter.cr_mixer.similarity_engine.SimClustersANNSimilarityEngine
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.storehaus.ReadableStore
import javax.inject.Named
import javax.inject.Singleton

object ProducerBasedUnifiedSimilarityEngineModule extends ExTwitterModule {

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
