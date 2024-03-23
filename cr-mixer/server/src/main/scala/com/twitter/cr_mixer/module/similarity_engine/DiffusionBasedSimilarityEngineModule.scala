package com.ExTwitter.cr_mixer.module
package similarity_engine

import com.google.inject.Provides
import com.ExTwitter.cr_mixer.config.TimeoutConfig
import com.ExTwitter.cr_mixer.model.ModelConfig
import com.ExTwitter.simclusters_v2.thriftscala.TweetsWithScore
import com.ExTwitter.cr_mixer.model.TweetWithScore
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.similarity_engine.DiffusionBasedSimilarityEngine
import com.ExTwitter.cr_mixer.similarity_engine.DiffusionBasedSimilarityEngine.Query
import com.ExTwitter.cr_mixer.similarity_engine.LookupSimilarityEngine
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.ExTwitter.cr_mixer.thriftscala.SimilarityEngineType
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.storehaus.ReadableStore
import javax.inject.Named
import javax.inject.Singleton

object DiffusionBasedSimilarityEngineModule extends ExTwitterModule {
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
