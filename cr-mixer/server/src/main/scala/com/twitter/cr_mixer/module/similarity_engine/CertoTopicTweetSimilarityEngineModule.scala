package com.ExTwitter.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.ExTwitter.cr_mixer.config.TimeoutConfig
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.model.TopicTweetWithScore
import com.ExTwitter.cr_mixer.param.decider.CrMixerDecider
import com.ExTwitter.cr_mixer.param.decider.DeciderConstants
import com.ExTwitter.cr_mixer.similarity_engine.CertoTopicTweetSimilarityEngine
import com.ExTwitter.cr_mixer.similarity_engine.CertoTopicTweetSimilarityEngine.Query
import com.ExTwitter.cr_mixer.similarity_engine.EngineQuery
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.DeciderConfig
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.ExTwitter.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.ExTwitter.cr_mixer.thriftscala.SimilarityEngineType
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.simclusters_v2.thriftscala.TopicId
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.topic_recos.thriftscala.TweetWithScores
import javax.inject.Named
import javax.inject.Singleton

object CertoTopicTweetSimilarityEngineModule extends ExTwitterModule {

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
