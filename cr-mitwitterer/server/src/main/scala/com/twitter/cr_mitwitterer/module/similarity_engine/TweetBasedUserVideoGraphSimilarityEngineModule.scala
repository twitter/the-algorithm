package com.twitter.cr_mitwitterer.module.similarity_engine

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.model.TweetWithScore
import com.twitter.cr_mitwitterer.param.decider.CrMitwittererDecider
import com.twitter.cr_mitwitterer.param.decider.DeciderConstants
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.DeciderConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mitwitterer.similarity_engine.StandardSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.TweetBasedUserVideoGraphSimilarityEngine
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.finagle.memcached.{Client => MemcachedClient}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.hashing.KeyHasher
import com.twitter.hermit.store.common.ObservedMemcachedReadableStore
import com.twitter.inject.TwitterModule
import com.twitter.recos.user_video_graph.thriftscala.UserVideoGraph
import com.twitter.relevance_platform.common.injection.LZ4Injection
import com.twitter.relevance_platform.common.injection.SeqObjectInjection
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.storehaus.ReadableStore
import com.twitter.twistly.thriftscala.TweetRecentEngagedUsers
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object TweetBasedUserVideoGraphSimilarityEngineModule etwittertends TwitterModule {

  private val keyHasher: KeyHasher = KeyHasher.FNV1A_64

  @Provides
  @Singleton
  @Named(ModuleNames.TweetBasedUserVideoGraphSimilarityEngine)
  def providesTweetBasedUserVideoGraphSimilarityEngine(
    userVideoGraphService: UserVideoGraph.MethodPerEndpoint,
    tweetRecentEngagedUserStore: ReadableStore[TweetId, TweetRecentEngagedUsers],
    @Named(ModuleNames.UnifiedCache) crMitwittererUnifiedCacheClient: MemcachedClient,
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver,
    decider: CrMitwittererDecider
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
          cacheClient = crMitwittererUnifiedCacheClient,
          ttl = 10.minutes
        )(
          valueInjection = LZ4Injection.compose(SeqObjectInjection[TweetWithScore]()),
          statsReceiver = statsReceiver.scope("tweet_based_user_video_graph_store_memcache"),
          keyToString = { k =>
            //Etwitterample Query CRMitwitterer:TweetBasedUVG:1234567890ABCDEF
            f"CRMitwitterer:TweetBasedUVG:${keyHasher.hashKey(k.toString.getBytes)}%twitter"
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
