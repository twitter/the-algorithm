package com.twitter.cr_mitwitterer.module
package similarity_engine

import com.google.inject.Provides
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.cr_mitwitterer.model.TweetWithScore
import com.twitter.cr_mitwitterer.model.ModelConfig
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.similarity_engine.LookupSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.TwhinCollabFilterSimilarityEngine.Query
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mitwitterer.similarity_engine.TwhinCollabFilterSimilarityEngine
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.storehaus.ReadableStore
import javatwitter.inject.Named
import javatwitter.inject.Singleton

/**
 * TwhinCandidatesLookupSimilarityEngineModule routes the request to the corresponding
 * twhin based candidate store which follow the same pattern as TwHIN Collaborative Filtering.
 */

object TwhinCollabFilterLookupSimilarityEngineModule etwittertends TwitterModule {
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
