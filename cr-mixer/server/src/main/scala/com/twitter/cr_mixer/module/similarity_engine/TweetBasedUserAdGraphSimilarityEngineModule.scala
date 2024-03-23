package com.ExTwitter.cr_mixer.module.similarity_engine

import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.cr_mixer.config.TimeoutConfig
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.model.TweetWithScore
import com.ExTwitter.cr_mixer.param.decider.CrMixerDecider
import com.ExTwitter.cr_mixer.param.decider.DeciderConstants
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.DeciderConfig
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.GatingConfig
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.ExTwitter.cr_mixer.similarity_engine.StandardSimilarityEngine
import com.ExTwitter.cr_mixer.similarity_engine.TweetBasedUserAdGraphSimilarityEngine
import com.ExTwitter.cr_mixer.thriftscala.SimilarityEngineType
import com.ExTwitter.finagle.memcached.{Client => MemcachedClient}
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.hashing.KeyHasher
import com.ExTwitter.hermit.store.common.ObservedMemcachedReadableStore
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.recos.user_ad_graph.thriftscala.UserAdGraph
import com.ExTwitter.relevance_platform.common.injection.LZ4Injection
import com.ExTwitter.relevance_platform.common.injection.SeqObjectInjection
import com.ExTwitter.simclusters_v2.common.TweetId
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.twistly.thriftscala.TweetRecentEngagedUsers
import javax.inject.Named
import javax.inject.Singleton

object TweetBasedUserAdGraphSimilarityEngineModule extends ExTwitterModule {

  private val keyHasher: KeyHasher = KeyHasher.FNV1A_64

  @Provides
  @Singleton
  @Named(ModuleNames.TweetBasedUserAdGraphSimilarityEngine)
  def providesTweetBasedUserAdGraphSimilarityEngine(
    userAdGraphService: UserAdGraph.MethodPerEndpoint,
    tweetRecentEngagedUserStore: ReadableStore[TweetId, TweetRecentEngagedUsers],
    @Named(ModuleNames.UnifiedCache) crMixerUnifiedCacheClient: MemcachedClient,
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver,
    decider: CrMixerDecider
  ): StandardSimilarityEngine[
    TweetBasedUserAdGraphSimilarityEngine.Query,
    TweetWithScore
  ] = {

    val underlyingStore = TweetBasedUserAdGraphSimilarityEngine(
      userAdGraphService,
      tweetRecentEngagedUserStore,
      statsReceiver)

    val memCachedStore: ReadableStore[
      TweetBasedUserAdGraphSimilarityEngine.Query,
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
          statsReceiver = statsReceiver.scope("tweet_based_user_ad_graph_store_memcache"),
          keyToString = { k =>
            //Example Query CRMixer:TweetBasedUTG:1234567890ABCDEF
            f"CRMixer:TweetBasedUAG:${keyHasher.hashKey(k.toString.getBytes)}%X"
          }
        )

    new StandardSimilarityEngine[
      TweetBasedUserAdGraphSimilarityEngine.Query,
      TweetWithScore
    ](
      implementingStore = memCachedStore,
      identifier = SimilarityEngineType.TweetBasedUserAdGraph,
      globalStats = statsReceiver,
      engineConfig = SimilarityEngineConfig(
        timeout = timeoutConfig.similarityEngineTimeout,
        gatingConfig = GatingConfig(
          deciderConfig =
            Some(DeciderConfig(decider, DeciderConstants.enableUserAdGraphTrafficDeciderKey)),
          enableFeatureSwitch = None
        )
      )
    )
  }
}
