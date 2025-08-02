package com.twitter.cr_mitwitterer.module
package similarity_engine

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.model.TweetWithScore
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.param.decider.CrMitwittererDecider
import com.twitter.cr_mitwitterer.param.decider.DeciderConstants
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.DeciderConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mitwitterer.similarity_engine.StandardSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.TweetBasedUserTweetGraphSimilarityEngine
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.finagle.memcached.{Client => MemcachedClient}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.hashing.KeyHasher
import com.twitter.hermit.store.common.ObservedMemcachedReadableStore
import com.twitter.inject.TwitterModule
import com.twitter.recos.user_tweet_graph.thriftscala.UserTweetGraph
import com.twitter.relevance_platform.common.injection.LZ4Injection
import com.twitter.relevance_platform.common.injection.SeqObjectInjection
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.storehaus.ReadableStore
import com.twitter.twistly.thriftscala.TweetRecentEngagedUsers
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object TweetBasedUserTweetGraphSimilarityEngineModule etwittertends TwitterModule {

  private val keyHasher: KeyHasher = KeyHasher.FNV1A_64

  @Provides
  @Singleton
  @Named(ModuleNames.TweetBasedUserTweetGraphSimilarityEngine)
  def providesTweetBasedUserTweetGraphSimilarityEngine(
    userTweetGraphService: UserTweetGraph.MethodPerEndpoint,
    tweetRecentEngagedUserStore: ReadableStore[TweetId, TweetRecentEngagedUsers],
    @Named(ModuleNames.UnifiedCache) crMitwittererUnifiedCacheClient: MemcachedClient,
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver,
    decider: CrMitwittererDecider
  ): StandardSimilarityEngine[
    TweetBasedUserTweetGraphSimilarityEngine.Query,
    TweetWithScore
  ] = {

    val underlyingStore = TweetBasedUserTweetGraphSimilarityEngine(
      userTweetGraphService,
      tweetRecentEngagedUserStore,
      statsReceiver)

    val memCachedStore: ReadableStore[
      TweetBasedUserTweetGraphSimilarityEngine.Query,
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
          statsReceiver = statsReceiver.scope("tweet_based_user_tweet_graph_store_memcache"),
          keyToString = { k =>
            //Etwitterample Query CRMitwitterer:TweetBasedUTG:1234567890ABCDEF
            f"CRMitwitterer:TweetBasedUTG:${keyHasher.hashKey(k.toString.getBytes)}%twitter"
          }
        )

    new StandardSimilarityEngine[
      TweetBasedUserTweetGraphSimilarityEngine.Query,
      TweetWithScore
    ](
      implementingStore = memCachedStore,
      identifier = SimilarityEngineType.TweetBasedUserTweetGraph,
      globalStats = statsReceiver,
      engineConfig = SimilarityEngineConfig(
        timeout = timeoutConfig.similarityEngineTimeout,
        gatingConfig = GatingConfig(
          deciderConfig =
            Some(DeciderConfig(decider, DeciderConstants.enableUserTweetGraphTrafficDeciderKey)),
          enableFeatureSwitch = None
        )
      )
    )
  }
}
