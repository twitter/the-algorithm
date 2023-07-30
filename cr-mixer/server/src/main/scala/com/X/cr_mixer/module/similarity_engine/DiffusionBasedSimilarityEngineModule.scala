package com.X.cr_mixer.module
package similarity_engine

import com.google.inject.Provides
import com.X.cr_mixer.config.TimeoutConfig
import com.X.cr_mixer.model.ModelConfig
import com.X.simclusters_v2.thriftscala.TweetsWithScore
import com.X.cr_mixer.model.TweetWithScore
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.similarity_engine.DiffusionBasedSimilarityEngine
import com.X.cr_mixer.similarity_engine.DiffusionBasedSimilarityEngine.Query
import com.X.cr_mixer.similarity_engine.LookupSimilarityEngine
import com.X.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.X.cr_mixer.thriftscala.SimilarityEngineType
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.storehaus.ReadableStore
import javax.inject.Named
import javax.inject.Singleton

object DiffusionBasedSimilarityEngineModule extends XModule {
  @Provides
  @Singleton
  @Named(ModuleNames.DiffusionBasedSimilarityEngine)
  def providesDiffusionBasedSimilarityEngineModule(
    @Named(ModuleNames.RetweetBasedDiffusionRecsMhStore)
    retweetBasedDiffusionRecsMhStore: ReadableStore[Long, TweetsWithScore],
    timeoutConfig: TimeoutConfig,
    globalStats: StatsReceiver
  ): LookupSimilarityEngine[Query, TweetWithScore] = {

    val versionedStoreMap = Map(
      ModelConfig.RetweetBasedDiffusion -> DiffusionBasedSimilarityEngine(
        retweetBasedDiffusionRecsMhStore,
        globalStats),
    )

    new LookupSimilarityEngine[Query, TweetWithScore](
      versionedStoreMap = versionedStoreMap,
      identifier = SimilarityEngineType.DiffusionBasedTweet,
      globalStats = globalStats,
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
