package com.twitter.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.twitter.cr_mixer.config.TimeoutConfig
import com.twitter.cr_mixer.model.ModuleNames
import com.twitter.cr_mixer.model.TopicTweetWithScore
import com.twitter.cr_mixer.param.decider.CrMixerDecider
import com.twitter.cr_mixer.param.decider.DeciderConstants
import com.twitter.cr_mixer.similarity_engine.EngineQuery
import com.twitter.cr_mixer.similarity_engine.SimilarityEngine.DeciderConfig
import com.twitter.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mixer.similarity_engine.SkitHighPrecisionTopicTweetSimilarityEngine
import com.twitter.cr_mixer.similarity_engine.SkitTopicTweetSimilarityEngine
import com.twitter.cr_mixer.similarity_engine.SkitTopicTweetSimilarityEngine.Query
import com.twitter.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.twitter.cr_mixer.thriftscala.SimilarityEngineType
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.storehaus.ReadableStore
import com.twitter.topic_recos.thriftscala.TopicTweet
import com.twitter.topic_recos.thriftscala.TopicTweetPartitionFlatKey
import javax.inject.Named
import javax.inject.Singleton

object SkitTopicTweetSimilarityEngineModule extends TwitterModule {

  @Provides
  @Singleton
  @Named(ModuleNames.SkitHighPrecisionTopicTweetSimilarityEngine)
  def providesSkitHighPrecisionTopicTweetSimilarityEngine(
    @Named(ModuleNames.SkitStratoStoreName) skitStratoStore: ReadableStore[
      TopicTweetPartitionFlatKey,
      Seq[TopicTweet]
    ],
    timeoutConfig: TimeoutConfig,
    decider: CrMixerDecider,
    statsReceiver: StatsReceiver
  ): StandardSimilarityEngine[
    EngineQuery[Query],
    TopicTweetWithScore
  ] = {
    new StandardSimilarityEngine[EngineQuery[Query], TopicTweetWithScore](
      implementingStore =
        SkitHighPrecisionTopicTweetSimilarityEngine(skitStratoStore, statsReceiver),
      identifier = SimilarityEngineType.SkitHighPrecisionTopicTweet,
      globalStats = statsReceiver.scope(SimilarityEngineType.SkitHighPrecisionTopicTweet.name),
      engineConfig = SimilarityEngineConfig(
        timeout = timeoutConfig.topicTweetEndpointTimeout,
        gatingConfig = GatingConfig(
          deciderConfig =
            Some(DeciderConfig(decider, DeciderConstants.enableTopicTweetTrafficDeciderKey)),
          enableFeatureSwitch = None
        )
      )
    )
  }
  @Provides
  @Singleton
  @Named(ModuleNames.SkitTopicTweetSimilarityEngine)
  def providesSkitTfgTopicTweetSimilarityEngine(
    @Named(ModuleNames.SkitStratoStoreName) skitStratoStore: ReadableStore[
      TopicTweetPartitionFlatKey,
      Seq[TopicTweet]
    ],
    timeoutConfig: TimeoutConfig,
    decider: CrMixerDecider,
    statsReceiver: StatsReceiver
  ): StandardSimilarityEngine[
    EngineQuery[Query],
    TopicTweetWithScore
  ] = {
    new StandardSimilarityEngine[EngineQuery[Query], TopicTweetWithScore](
      implementingStore = SkitTopicTweetSimilarityEngine(skitStratoStore, statsReceiver),
      identifier = SimilarityEngineType.SkitTfgTopicTweet,
      globalStats = statsReceiver.scope(SimilarityEngineType.SkitTfgTopicTweet.name),
      engineConfig = SimilarityEngineConfig(
        timeout = timeoutConfig.topicTweetEndpointTimeout,
        gatingConfig = GatingConfig(
          deciderConfig =
            Some(DeciderConfig(decider, DeciderConstants.enableTopicTweetTrafficDeciderKey)),
          enableFeatureSwitch = None
        )
      )
    )
  }

}
