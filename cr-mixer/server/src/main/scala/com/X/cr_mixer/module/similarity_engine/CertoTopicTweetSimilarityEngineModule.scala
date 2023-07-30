package com.X.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.X.cr_mixer.config.TimeoutConfig
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.model.TopicTweetWithScore
import com.X.cr_mixer.param.decider.CrMixerDecider
import com.X.cr_mixer.param.decider.DeciderConstants
import com.X.cr_mixer.similarity_engine.CertoTopicTweetSimilarityEngine
import com.X.cr_mixer.similarity_engine.CertoTopicTweetSimilarityEngine.Query
import com.X.cr_mixer.similarity_engine.EngineQuery
import com.X.cr_mixer.similarity_engine.SimilarityEngine.DeciderConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.X.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.X.cr_mixer.thriftscala.SimilarityEngineType
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.simclusters_v2.thriftscala.TopicId
import com.X.storehaus.ReadableStore
import com.X.topic_recos.thriftscala.TweetWithScores
import javax.inject.Named
import javax.inject.Singleton

object CertoTopicTweetSimilarityEngineModule extends XModule {

  @Provides
  @Singleton
  @Named(ModuleNames.CertoTopicTweetSimilarityEngine)
  def providesCertoTopicTweetSimilarityEngine(
    @Named(ModuleNames.CertoStratoStoreName) certoStratoStore: ReadableStore[
      TopicId,
      Seq[TweetWithScores]
    ],
    timeoutConfig: TimeoutConfig,
    decider: CrMixerDecider,
    statsReceiver: StatsReceiver
  ): StandardSimilarityEngine[
    EngineQuery[Query],
    TopicTweetWithScore
  ] = {
    new StandardSimilarityEngine[EngineQuery[Query], TopicTweetWithScore](
      implementingStore = CertoTopicTweetSimilarityEngine(certoStratoStore, statsReceiver),
      identifier = SimilarityEngineType.CertoTopicTweet,
      globalStats = statsReceiver,
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
