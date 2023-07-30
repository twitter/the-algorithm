package com.X.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.X.conversions.DurationOps._
import com.X.cr_mixer.config.TimeoutConfig
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.model.TweetWithScore
import com.X.cr_mixer.param.decider.CrMixerDecider
import com.X.cr_mixer.param.decider.DeciderConstants
import com.X.cr_mixer.similarity_engine.ProducerBasedUserAdGraphSimilarityEngine
import com.X.cr_mixer.similarity_engine.SimilarityEngine.DeciderConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine._
import com.X.cr_mixer.similarity_engine.SimilarityEngine.keyHasher
import com.X.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.X.cr_mixer.thriftscala.SimilarityEngineType
import com.X.finagle.memcached.{Client => MemcachedClient}
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.recos.user_ad_graph.thriftscala.UserAdGraph
import javax.inject.Named
import javax.inject.Singleton

object ProducerBasedUserAdGraphSimilarityEngineModule extends XModule {

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
