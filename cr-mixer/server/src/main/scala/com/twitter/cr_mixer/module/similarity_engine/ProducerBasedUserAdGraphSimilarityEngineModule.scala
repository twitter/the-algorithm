package com.twitter.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.cr_mixer.config.TimeoutConfig
import com.twitter.cr_mixer.model.ModuleNames
import com.twitter.cr_mixer.model.TweetWithScore
import com.twitter.cr_mixer.param.decider.CrMixerDecider
import com.twitter.cr_mixer.param.decider.DeciderConstants
import com.twitter.cr_mixer.similarity_engine.ProducerBasedUserAdGraphSimilarityEngine
import com.twitter.cr_mixer.similarity_engine.SimilarityEngine.DeciderConfig
import com.twitter.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mixer.similarity_engine.SimilarityEngine._
import com.twitter.cr_mixer.similarity_engine.SimilarityEngine.keyHasher
import com.twitter.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.twitter.cr_mixer.thriftscala.SimilarityEngineType
import com.twitter.finagle.memcached.{Client => MemcachedClient}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.recos.user_ad_graph.thriftscala.UserAdGraph
import javax.inject.Named
import javax.inject.Singleton

object ProducerBasedUserAdGraphSimilarityEngineModule extends TwitterModule {

  @Provides
  @Singleton
  @Named(ModuleNames.ProducerBasedUserAdGraphSimilarityEngine)
  def providesProducerBasedUserAdGraphSimilarityEngine(
    userAdGraphService: UserAdGraph.MethodPerEndpoint,
    @Named(ModuleNames.UnifiedCache) crMixerUnifiedCacheClient: MemcachedClient,
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver,
    decider: CrMixerDecider
  ): StandardSimilarityEngine[
    ProducerBasedUserAdGraphSimilarityEngine.Query,
    TweetWithScore
  ] = {
    new StandardSimilarityEngine[
      ProducerBasedUserAdGraphSimilarityEngine.Query,
      TweetWithScore
    ](
      implementingStore =
        ProducerBasedUserAdGraphSimilarityEngine(userAdGraphService, statsReceiver),
      identifier = SimilarityEngineType.ProducerBasedUserAdGraph,
      globalStats = statsReceiver,
      engineConfig = SimilarityEngineConfig(
        timeout = timeoutConfig.similarityEngineTimeout,
        gatingConfig = GatingConfig(
          deciderConfig =
            Some(DeciderConfig(decider, DeciderConstants.enableUserAdGraphTrafficDeciderKey)),
          enableFeatureSwitch = None
        )
      ),
      memCacheConfig = Some(
        MemCacheConfig(
          cacheClient = crMixerUnifiedCacheClient,
          ttl = 10.minutes,
          keyToString = { k =>
            //Example Query CRMixer:ProducerBasedUTG:1234567890ABCDEF
            f"ProducerBasedUTG:${keyHasher.hashKey(k.toString.getBytes)}%X"
          }
        ))
    )
  }
}
