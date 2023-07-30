package com.X.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.model.TweetWithScoreAndSocialProof
import com.X.cr_mixer.config.TimeoutConfig
import com.X.cr_mixer.param.decider.CrMixerDecider
import com.X.cr_mixer.param.decider.DeciderConstants
import com.X.cr_mixer.similarity_engine.UserTweetEntityGraphSimilarityEngine
import com.X.cr_mixer.similarity_engine.SimilarityEngine.DeciderConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.X.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.X.cr_mixer.thriftscala.SimilarityEngineType
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.recos.user_tweet_entity_graph.thriftscala.UserTweetEntityGraph
import javax.inject.Named
import javax.inject.Singleton

object UserTweetEntityGraphSimilarityEngineModule extends XModule {

  @Provides
  @Singleton
  @Named(ModuleNames.UserTweetEntityGraphSimilarityEngine)
  def providesUserTweetEntityGraphSimilarityEngine(
    userTweetEntityGraphService: UserTweetEntityGraph.MethodPerEndpoint,
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver,
    decider: CrMixerDecider
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
