package com.twitter.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.twitter.cr_mixer.config.TimeoutConfig
import com.twitter.cr_mixer.model.ModuleNames
import com.twitter.cr_mixer.model.TweetWithScore
import com.twitter.cr_mixer.param.decider.CrMixerDecider
import com.twitter.cr_mixer.param.decider.DeciderConstants
import com.twitter.cr_mixer.similarity_engine.ConsumersBasedUserAdGraphSimilarityEngine
import com.twitter.cr_mixer.similarity_engine.SimilarityEngine.DeciderConfig
import com.twitter.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.twitter.cr_mixer.thriftscala.SimilarityEngineType
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.recos.user_ad_graph.thriftscala.ConsumersBasedRelatedAdRequest
import com.twitter.recos.user_ad_graph.thriftscala.RelatedAdResponse
import com.twitter.storehaus.ReadableStore
import javax.inject.Named
import javax.inject.Singleton

object ConsumersBasedUserAdGraphSimilarityEngineModule extends TwitterModule {

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
