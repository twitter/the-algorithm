package com.X.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.X.conversions.DurationOps._
import com.X.cr_mixer.config.TimeoutConfig
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.model.TweetWithScore
import com.X.cr_mixer.param.decider.CrMixerDecider
import com.X.cr_mixer.param.decider.DeciderConstants
import com.X.cr_mixer.similarity_engine.SimilarityEngine.DeciderConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.X.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.X.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.X.cr_mixer.similarity_engine.TweetBasedUserVideoGraphSimilarityEngine
import com.X.cr_mixer.thriftscala.SimilarityEngineType
import com.X.finagle.memcached.{Client => MemcachedClient}
import com.X.finagle.stats.StatsReceiver
import com.X.hashing.KeyHasher
import com.X.hermit.store.common.ObservedMemcachedReadableStore
import com.X.inject.XModule
import com.X.recos.user_video_graph.thriftscala.UserVideoGraph
import com.X.relevance_platform.common.injection.LZ4Injection
import com.X.relevance_platform.common.injection.SeqObjectInjection
import com.X.simclusters_v2.common.TweetId
import com.X.storehaus.ReadableStore
import com.X.twistly.thriftscala.TweetRecentEngagedUsers
import javax.inject.Named
import javax.inject.Singleton

object TweetBasedUserVideoGraphSimilarityEngineModule extends XModule {

  private val keyHasher: KeyHasher = KeyHasher.FNV1A_64

  @Provides
  @Singleton
  @Named(ModuleNames.TweetBasedUserVideoGraphSimilarityEngine)
  def providesTweetBasedUserVideoGraphSimilarityEngine(
    userVideoGraphService: UserVideoGraph.MethodPerEndpoint,
    tweetRecentEngagedUserStore: ReadableStore[TweetId, TweetRecentEngagedUsers],
    @Named(ModuleNames.UnifiedCache) crMixerUnifiedCacheClient: MemcachedClient,
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver,
    decider: CrMixerDecider
  ): StandardSimilarityEngine[
    TweetBasedUserVideoGraphSimilarityEngine.Query,
    TweetWithScore
  ] = {

    val underlyingStore =
      TweetBasedUserVideoGraphSimilarityEngine(
        userVideoGraphService,
        tweetRecentEngagedUserStore,
        statsReceiver)

    val memCachedStore: ReadableStore[
      TweetBasedUserVideoGraphSimilarityEngine.Query,
      Seq[
        TweetWithScore
      ]
    ] =
      ObservedMemcachedReadableStore
        .fromCacheClient(
          backingStore = underlyingStore,
          cacheClient = crMixerUnifiedCacheClient,
          ttl = 10.minutes
        )(
          valueInjection = LZ4Injection.compose(SeqObjectInjection[TweetWithScore]()),
          statsReceiver = statsReceiver.scope("tweet_based_user_video_graph_store_memcache"),
          keyToString = { k =>
            //Example Query CRMixer:TweetBasedUVG:1234567890ABCDEF
            f"CRMixer:TweetBasedUVG:${keyHasher.hashKey(k.toString.getBytes)}%X"
          }
        )

    new StandardSimilarityEngine[
      TweetBasedUserVideoGraphSimilarityEngine.Query,
      TweetWithScore
    ](
      implementingStore = memCachedStore,
      identifier = SimilarityEngineType.TweetBasedUserVideoGraph,
      globalStats = statsReceiver,
      engineConfig = SimilarityEngineConfig(
        timeout = timeoutConfig.similarityEngineTimeout,
        gatingConfig = GatingConfig(
          deciderConfig =
            Some(DeciderConfig(decider, DeciderConstants.enableUserVideoGraphTrafficDeciderKey)),
          enableFeatureSwitch = None
        )
      )
    )
  }
}
