package com.X.cr_mixer.module
package similarity_engine

import com.google.inject.Provides
import com.X.cr_mixer.config.TimeoutConfig
import com.X.simclusters_v2.common.TweetId
import com.X.cr_mixer.model.TweetWithScore
import com.X.cr_mixer.model.ModelConfig
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.similarity_engine.LookupSimilarityEngine
import com.X.cr_mixer.similarity_engine.TwhinCollabFilterSimilarityEngine.Query
import com.X.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.X.cr_mixer.similarity_engine.TwhinCollabFilterSimilarityEngine
import com.X.cr_mixer.thriftscala.SimilarityEngineType
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.storehaus.ReadableStore
import javax.inject.Named
import javax.inject.Singleton

/**
 * TwhinCandidatesLookupSimilarityEngineModule routes the request to the corresponding
 * twhin based candidate store which follow the same pattern as TwHIN Collaborative Filtering.
 */

object TwhinCollabFilterLookupSimilarityEngineModule extends XModule {
  @Provides
  @Singleton
  @Named(ModuleNames.TwhinCollabFilterSimilarityEngine)
  def providesTwhinCollabFilterLookupSimilarityEngineModule(
    @Named(ModuleNames.TwhinCollabFilterStratoStoreForFollow)
    twhinCollabFilterStratoStoreForFollow: ReadableStore[Long, Seq[TweetId]],
    @Named(ModuleNames.TwhinCollabFilterStratoStoreForEngagement)
    twhinCollabFilterStratoStoreForEngagement: ReadableStore[Long, Seq[TweetId]],
    @Named(ModuleNames.TwhinMultiClusterStratoStoreForFollow)
    twhinMultiClusterStratoStoreForFollow: ReadableStore[Long, Seq[TweetId]],
    @Named(ModuleNames.TwhinMultiClusterStratoStoreForEngagement)
    twhinMultiClusterStratoStoreForEngagement: ReadableStore[Long, Seq[TweetId]],
    timeoutConfig: TimeoutConfig,
    globalStats: StatsReceiver
  ): LookupSimilarityEngine[Query, TweetWithScore] = {
    val versionedStoreMap = Map(
      ModelConfig.TwhinCollabFilterForFollow -> TwhinCollabFilterSimilarityEngine(
        twhinCollabFilterStratoStoreForFollow,
        globalStats),
      ModelConfig.TwhinCollabFilterForEngagement -> TwhinCollabFilterSimilarityEngine(
        twhinCollabFilterStratoStoreForEngagement,
        globalStats),
      ModelConfig.TwhinMultiClusterForFollow -> TwhinCollabFilterSimilarityEngine(
        twhinMultiClusterStratoStoreForFollow,
        globalStats),
      ModelConfig.TwhinMultiClusterForEngagement -> TwhinCollabFilterSimilarityEngine(
        twhinMultiClusterStratoStoreForEngagement,
        globalStats),
    )

    new LookupSimilarityEngine[Query, TweetWithScore](
      versionedStoreMap = versionedStoreMap,
      identifier = SimilarityEngineType.TwhinCollabFilter,
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
