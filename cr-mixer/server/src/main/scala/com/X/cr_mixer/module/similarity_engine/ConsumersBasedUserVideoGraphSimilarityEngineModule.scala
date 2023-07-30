package com.X.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.X.cr_mixer.config.TimeoutConfig
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.model.TweetWithScore
import com.X.cr_mixer.param.decider.CrMixerDecider
import com.X.cr_mixer.param.decider.DeciderConstants
import com.X.cr_mixer.similarity_engine.ConsumersBasedUserVideoGraphSimilarityEngine
import com.X.cr_mixer.similarity_engine.SimilarityEngine.DeciderConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.X.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.X.cr_mixer.thriftscala.SimilarityEngineType
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.recos.user_video_graph.thriftscala.ConsumersBasedRelatedTweetRequest
import com.X.recos.user_video_graph.thriftscala.RelatedTweetResponse
import com.X.storehaus.ReadableStore
import javax.inject.Named
import javax.inject.Singleton

object ConsumersBasedUserVideoGraphSimilarityEngineModule extends XModule {

  @Provides
  @Singleton
  @Named(ModuleNames.ConsumersBasedUserVideoGraphSimilarityEngine)
  def providesConsumersBasedUserVideoGraphSimilarityEngine(
    @Named(ModuleNames.ConsumerBasedUserVideoGraphStore)
    consumersBasedUserVideoGraphStore: ReadableStore[
      ConsumersBasedRelatedTweetRequest,
      RelatedTweetResponse
    ],
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver,
    decider: CrMixerDecider
  ): StandardSimilarityEngine[
    ConsumersBasedUserVideoGraphSimilarityEngine.Query,
    TweetWithScore
  ] = {

    new StandardSimilarityEngine[
      ConsumersBasedUserVideoGraphSimilarityEngine.Query,
      TweetWithScore
    ](
      implementingStore = ConsumersBasedUserVideoGraphSimilarityEngine(
        consumersBasedUserVideoGraphStore,
        statsReceiver),
      identifier = SimilarityEngineType.ConsumersBasedUserVideoGraph,
      globalStats = statsReceiver,
      engineConfig = SimilarityEngineConfig(
        timeout = timeoutConfig.similarityEngineTimeout,
        gatingConfig = GatingConfig(
          deciderConfig =
            Some(DeciderConfig(decider, DeciderConstants.enableUserVideoGraphTrafficDeciderKey)),
          enableFeatureSwitch = None
        )
      ),
      memCacheConfig = None
    )
  }
}
