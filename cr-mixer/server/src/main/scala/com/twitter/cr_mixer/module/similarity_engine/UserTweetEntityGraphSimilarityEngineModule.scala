package com.ExTwitter.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.model.TweetWithScoreAndSocialProof
import com.ExTwitter.cr_mixer.config.TimeoutConfig
import com.ExTwitter.cr_mixer.param.decider.CrMixerDecider
import com.ExTwitter.cr_mixer.param.decider.DeciderConstants
import com.ExTwitter.cr_mixer.similarity_engine.UserTweetEntityGraphSimilarityEngine
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.DeciderConfig
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.ExTwitter.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.ExTwitter.cr_mixer.thriftscala.SimilarityEngineType
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.recos.user_tweet_entity_graph.thriftscala.UserTweetEntityGraph
import javax.inject.Named
import javax.inject.Singleton

object UserTweetEntityGraphSimilarityEngineModule extends ExTwitterModule {

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
