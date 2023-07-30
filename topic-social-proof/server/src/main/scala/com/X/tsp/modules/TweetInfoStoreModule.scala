package com.X.tsp.modules

import com.google.inject.Module
import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.bijection.scrooge.BinaryScalaCodec
import com.X.conversions.DurationOps._
import com.X.finagle.memcached.{Client => MemClient}
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.store.health.TweetHealthModelStore
import com.X.frigate.common.store.health.TweetHealthModelStore.TweetHealthModelStoreConfig
import com.X.frigate.common.store.health.UserHealthModelStore
import com.X.frigate.common.store.interests.UserId
import com.X.frigate.thriftscala.TweetHealthScores
import com.X.frigate.thriftscala.UserAgathaScores
import com.X.hermit.store.common.DeciderableReadableStore
import com.X.hermit.store.common.ObservedCachedReadableStore
import com.X.hermit.store.common.ObservedMemcachedReadableStore
import com.X.inject.XModule
import com.X.simclusters_v2.common.TweetId
import com.X.stitch.tweetypie.TweetyPie
import com.X.storehaus.ReadableStore
import com.X.strato.client.{Client => StratoClient}
import com.X.tsp.common.DeciderKey
import com.X.tsp.common.TopicSocialProofDecider
import com.X.tsp.stores.TweetInfoStore
import com.X.tsp.stores.TweetyPieFieldsStore
import com.X.tweetypie.thriftscala.TweetService
import com.X.tsp.thriftscala.TspTweetInfo
import com.X.util.JavaTimer
import com.X.util.Timer

object TweetInfoStoreModule extends XModule {
  override def modules: Seq[Module] = Seq(UnifiedCacheClient)
  implicit val timer: Timer = new JavaTimer(true)

  @Provides
  @Singleton
  def providesTweetInfoStore(
    decider: TopicSocialProofDecider,
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver,
    stratoClient: StratoClient,
    tspUnifiedCacheClient: MemClient,
    tweetyPieService: TweetService.MethodPerEndpoint
  ): ReadableStore[TweetId, TspTweetInfo] = {
    val tweetHealthModelStore: ReadableStore[TweetId, TweetHealthScores] = {
      val underlyingStore = TweetHealthModelStore.buildReadableStore(
        stratoClient,
        Some(
          TweetHealthModelStoreConfig(
            enablePBlock = true,
            enableToxicity = true,
            enablePSpammy = true,
            enablePReported = true,
            enableSpammyTweetContent = true,
            enablePNegMultimodal = false))
      )(statsReceiver.scope("UnderlyingTweetHealthModelStore"))

      DeciderableReadableStore(
        ObservedMemcachedReadableStore.fromCacheClient(
          backingStore = underlyingStore,
          cacheClient = tspUnifiedCacheClient,
          ttl = 2.hours
        )(
          valueInjection = BinaryScalaCodec(TweetHealthScores),
          statsReceiver = statsReceiver.scope("TweetHealthModelStore"),
          keyToString = { k: TweetId => s"tHMS/$k" }
        ),
        decider.deciderGateBuilder.idGate(DeciderKey.enableHealthSignalsScoreDeciderKey),
        statsReceiver.scope("TweetHealthModelStore")
      )
    }

    val userHealthModelStore: ReadableStore[UserId, UserAgathaScores] = {
      val underlyingStore =
        UserHealthModelStore.buildReadableStore(stratoClient)(
          statsReceiver.scope("UnderlyingUserHealthModelStore"))

      DeciderableReadableStore(
        ObservedMemcachedReadableStore.fromCacheClient(
          backingStore = underlyingStore,
          cacheClient = tspUnifiedCacheClient,
          ttl = 18.hours
        )(
          valueInjection = BinaryScalaCodec(UserAgathaScores),
          statsReceiver = statsReceiver.scope("UserHealthModelStore"),
          keyToString = { k: UserId => s"uHMS/$k" }
        ),
        decider.deciderGateBuilder.idGate(DeciderKey.enableUserAgathaScoreDeciderKey),
        statsReceiver.scope("UserHealthModelStore")
      )
    }

    val tweetInfoStore: ReadableStore[TweetId, TspTweetInfo] = {
      val underlyingStore = TweetInfoStore(
        TweetyPieFieldsStore.getStoreFromTweetyPie(TweetyPie(tweetyPieService, statsReceiver)),
        tweetHealthModelStore: ReadableStore[TweetId, TweetHealthScores],
        userHealthModelStore: ReadableStore[UserId, UserAgathaScores],
        timer: Timer
      )(statsReceiver.scope("tweetInfoStore"))

      val memcachedStore = ObservedMemcachedReadableStore.fromCacheClient(
        backingStore = underlyingStore,
        cacheClient = tspUnifiedCacheClient,
        ttl = 15.minutes,
        // Hydrating tweetInfo is now a required step for all candidates,
        // hence we needed to tune these thresholds.
        asyncUpdate = serviceIdentifier.environment == "prod"
      )(
        valueInjection = BinaryScalaCodec(TspTweetInfo),
        statsReceiver = statsReceiver.scope("memCachedTweetInfoStore"),
        keyToString = { k: TweetId => s"tIS/$k" }
      )

      val inMemoryStore = ObservedCachedReadableStore.from(
        memcachedStore,
        ttl = 15.minutes,
        maxKeys = 8388607, // Check TweetInfo definition. size~92b. Around 736 MB
        windowSize = 10000L,
        cacheName = "tweet_info_cache",
        maxMultiGetSize = 20
      )(statsReceiver.scope("inMemoryCachedTweetInfoStore"))

      inMemoryStore
    }
    tweetInfoStore
  }
}
