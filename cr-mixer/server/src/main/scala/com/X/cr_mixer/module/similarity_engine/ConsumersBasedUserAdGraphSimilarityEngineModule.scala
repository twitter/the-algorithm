package com.X.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.X.cr_mixer.config.TimeoutConfig
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.model.TweetWithScore
import com.X.cr_mixer.param.decider.CrMixerDecider
import com.X.cr_mixer.param.decider.DeciderConstants
import com.X.cr_mixer.similarity_engine.ConsumersBasedUserAdGraphSimilarityEngine
import com.X.cr_mixer.similarity_engine.SimilarityEngine.DeciderConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.X.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.X.cr_mixer.thriftscala.SimilarityEngineType
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.recos.user_ad_graph.thriftscala.ConsumersBasedRelatedAdRequest
import com.X.recos.user_ad_graph.thriftscala.RelatedAdResponse
import com.X.storehaus.ReadableStore
import javax.inject.Named
import javax.inject.Singleton

object ConsumersBasedUserAdGraphSimilarityEngineModule extends XModule {

  @Provides
  @Singleton
  @Named(ModuleNames.ConsumersBasedUserAdGraphSimilarityEngine)
  def providesConsumersBasedUserAdGraphSimilarityEngine(
    @Named(ModuleNames.ConsumerBasedUserAdGraphStore)
    consumersBasedUserAdGraphStore: ReadableStore[
      ConsumersBasedRelatedAdRequest,
      RelatedAdResponse
    ],
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver,
    decider: CrMixerDecider
  ): StandardSimilarityEngine[
    ConsumersBasedUserAdGraphSimilarityEngine.Query,
    TweetWithScore
  ] = {

    new StandardSimilarityEngine[
      ConsumersBasedUserAdGraphSimilarityEngine.Query,
      TweetWithScore
    ](
      implementingStore =
        ConsumersBasedUserAdGraphSimilarityEngine(consumersBasedUserAdGraphStore, statsReceiver),
      identifier = SimilarityEngineType.ConsumersBasedUserTweetGraph,
      globalStats = statsReceiver,
      engineConfig = SimilarityEngineConfig(
        timeout = timeoutConfig.similarityEngineTimeout,
        gatingConfig = GatingConfig(
          deciderConfig =
            Some(DeciderConfig(decider, DeciderConstants.enableUserTweetGraphTrafficDeciderKey)),
          enableFeatureSwitch = None
        )
      ),
      memCacheConfig = None
    )
  }
}
