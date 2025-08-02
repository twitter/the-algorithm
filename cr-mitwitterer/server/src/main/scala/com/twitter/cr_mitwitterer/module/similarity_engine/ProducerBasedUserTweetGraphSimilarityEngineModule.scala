package com.twitter.cr_mitwitterer.module.similarity_engine

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.model.TweetWithScore
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.param.decider.CrMitwittererDecider
import com.twitter.cr_mitwitterer.param.decider.DeciderConstants
import com.twitter.cr_mitwitterer.similarity_engine.ProducerBasedUserTweetGraphSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine._
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.keyHasher
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.DeciderConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mitwitterer.similarity_engine.StandardSimilarityEngine
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.finagle.memcached.{Client => MemcachedClient}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.recos.user_tweet_graph.thriftscala.UserTweetGraph
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object ProducerBasedUserTweetGraphSimilarityEngineModule etwittertends TwitterModule {

  @Provides
  @Singleton
  @Named(ModuleNames.ProducerBasedUserTweetGraphSimilarityEngine)
  def providesProducerBasedUserTweetGraphSimilarityEngine(
    userTweetGraphService: UserTweetGraph.MethodPerEndpoint,
    @Named(ModuleNames.UnifiedCache) crMitwittererUnifiedCacheClient: MemcachedClient,
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver,
    decider: CrMitwittererDecider
  ): StandardSimilarityEngine[
    ProducerBasedUserTweetGraphSimilarityEngine.Query,
    TweetWithScore
  ] = {
    new StandardSimilarityEngine[
      ProducerBasedUserTweetGraphSimilarityEngine.Query,
      TweetWithScore
    ](
      implementingStore =
        ProducerBasedUserTweetGraphSimilarityEngine(userTweetGraphService, statsReceiver),
      identifier = SimilarityEngineType.ProducerBasedUserTweetGraph,
      globalStats = statsReceiver,
      engineConfig = SimilarityEngineConfig(
        timeout = timeoutConfig.similarityEngineTimeout,
        gatingConfig = GatingConfig(
          deciderConfig =
            Some(DeciderConfig(decider, DeciderConstants.enableUserTweetGraphTrafficDeciderKey)),
          enableFeatureSwitch = None
        )
      ),
      memCacheConfig = Some(
        MemCacheConfig(
          cacheClient = crMitwittererUnifiedCacheClient,
          ttl = 10.minutes,
          keyToString = { k =>
            //Etwitterample Query CRMitwitterer:ProducerBasedUTG:1234567890ABCDEF
            f"ProducerBasedUTG:${keyHasher.hashKey(k.toString.getBytes)}%twitter"
          }
        ))
    )
  }
}
