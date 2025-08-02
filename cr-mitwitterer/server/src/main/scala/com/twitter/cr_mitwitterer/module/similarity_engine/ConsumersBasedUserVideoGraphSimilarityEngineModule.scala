package com.twitter.cr_mitwitterer.module.similarity_engine

import com.google.inject.Provides
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.model.TweetWithScore
import com.twitter.cr_mitwitterer.param.decider.CrMitwittererDecider
import com.twitter.cr_mitwitterer.param.decider.DeciderConstants
import com.twitter.cr_mitwitterer.similarity_engine.ConsumersBasedUserVideoGraphSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.DeciderConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mitwitterer.similarity_engine.StandardSimilarityEngine
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.recos.user_video_graph.thriftscala.ConsumersBasedRelatedTweetRequest
import com.twitter.recos.user_video_graph.thriftscala.RelatedTweetResponse
import com.twitter.storehaus.ReadableStore
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object ConsumersBasedUserVideoGraphSimilarityEngineModule etwittertends TwitterModule {

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
    decider: CrMitwittererDecider
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
