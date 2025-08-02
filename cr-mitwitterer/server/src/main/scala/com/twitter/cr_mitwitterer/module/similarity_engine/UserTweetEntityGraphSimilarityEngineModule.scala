package com.twitter.cr_mitwitterer.module.similarity_engine

import com.google.inject.Provides
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.model.TweetWithScoreAndSocialProof
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.param.decider.CrMitwittererDecider
import com.twitter.cr_mitwitterer.param.decider.DeciderConstants
import com.twitter.cr_mitwitterer.similarity_engine.UserTweetEntityGraphSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.DeciderConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mitwitterer.similarity_engine.StandardSimilarityEngine
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.recos.user_tweet_entity_graph.thriftscala.UserTweetEntityGraph
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object UserTweetEntityGraphSimilarityEngineModule etwittertends TwitterModule {

  @Provides
  @Singleton
  @Named(ModuleNames.UserTweetEntityGraphSimilarityEngine)
  def providesUserTweetEntityGraphSimilarityEngine(
    userTweetEntityGraphService: UserTweetEntityGraph.MethodPerEndpoint,
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver,
    decider: CrMitwittererDecider
  ): StandardSimilarityEngine[
    UserTweetEntityGraphSimilarityEngine.Query,
    TweetWithScoreAndSocialProof
  ] = {
    new StandardSimilarityEngine[
      UserTweetEntityGraphSimilarityEngine.Query,
      TweetWithScoreAndSocialProof
    ](
      implementingStore =
        UserTweetEntityGraphSimilarityEngine(userTweetEntityGraphService, statsReceiver),
      identifier = SimilarityEngineType.Uteg,
      globalStats = statsReceiver,
      engineConfig = SimilarityEngineConfig(
        timeout = timeoutConfig.utegSimilarityEngineTimeout,
        gatingConfig = GatingConfig(
          deciderConfig = Some(
            DeciderConfig(decider, DeciderConstants.enableUserTweetEntityGraphTrafficDeciderKey)),
          enableFeatureSwitch = None
        )
      ),
      // We cannot use the key to cache anything in UTEG because the key contains a long list of userIds
      memCacheConfig = None
    )
  }
}
