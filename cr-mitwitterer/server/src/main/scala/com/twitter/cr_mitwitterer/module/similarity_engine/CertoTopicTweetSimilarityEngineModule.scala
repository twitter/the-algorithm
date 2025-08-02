package com.twitter.cr_mitwitterer.module.similarity_engine

import com.google.inject.Provides
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.model.TopicTweetWithScore
import com.twitter.cr_mitwitterer.param.decider.CrMitwittererDecider
import com.twitter.cr_mitwitterer.param.decider.DeciderConstants
import com.twitter.cr_mitwitterer.similarity_engine.CertoTopicTweetSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.CertoTopicTweetSimilarityEngine.Query
import com.twitter.cr_mitwitterer.similarity_engine.EngineQuery
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.DeciderConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mitwitterer.similarity_engine.StandardSimilarityEngine
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.simclusters_v2.thriftscala.TopicId
import com.twitter.storehaus.ReadableStore
import com.twitter.topic_recos.thriftscala.TweetWithScores
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object CertoTopicTweetSimilarityEngineModule etwittertends TwitterModule {

  @Provides
  @Singleton
  @Named(ModuleNames.CertoTopicTweetSimilarityEngine)
  def providesCertoTopicTweetSimilarityEngine(
    @Named(ModuleNames.CertoStratoStoreName) certoStratoStore: ReadableStore[
      TopicId,
      Seq[TweetWithScores]
    ],
    timeoutConfig: TimeoutConfig,
    decider: CrMitwittererDecider,
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
