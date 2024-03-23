package com.ExTwitter.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.cr_mixer.config.TimeoutConfig
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.model.TweetWithScore
import com.ExTwitter.cr_mixer.param.decider.CrMixerDecider
import com.ExTwitter.cr_mixer.param.decider.DeciderConstants
import com.ExTwitter.cr_mixer.similarity_engine.ProducerBasedUserAdGraphSimilarityEngine
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.DeciderConfig
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine._
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.keyHasher
import com.ExTwitter.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.ExTwitter.cr_mixer.thriftscala.SimilarityEngineType
import com.ExTwitter.finagle.memcached.{Client => MemcachedClient}
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.recos.user_ad_graph.thriftscala.UserAdGraph
import javax.inject.Named
import javax.inject.Singleton

object ProducerBasedUserAdGraphSimilarityEngineModule extends ExTwitterModule {

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
