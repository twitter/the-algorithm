package com.ExTwitter.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.ExTwitter.cr_mixer.config.TimeoutConfig
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.model.TweetWithScore
import com.ExTwitter.cr_mixer.param.decider.CrMixerDecider
import com.ExTwitter.cr_mixer.param.decider.DeciderConstants
import com.ExTwitter.cr_mixer.similarity_engine.ConsumersBasedUserVideoGraphSimilarityEngine
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.DeciderConfig
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.ExTwitter.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.ExTwitter.cr_mixer.thriftscala.SimilarityEngineType
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.recos.user_video_graph.thriftscala.ConsumersBasedRelatedTweetRequest
import com.ExTwitter.recos.user_video_graph.thriftscala.RelatedTweetResponse
import com.ExTwitter.storehaus.ReadableStore
import javax.inject.Named
import javax.inject.Singleton

object ConsumersBasedUserVideoGraphSimilarityEngineModule extends ExTwitterModule {

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
