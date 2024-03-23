package com.ExTwitter.cr_mixer.module

import com.google.inject.Module
import com.google.inject.Provides
import com.google.inject.Singleton
import com.ExTwitter.bijection.scrooge.BinaryScalaCodec
import com.ExTwitter.contentrecommender.thriftscala.TweetInfo
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.finagle.memcached.{Client => MemcachedClient}
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.frigate.common.store.health.TweetHealthModelStore
import com.ExTwitter.frigate.common.store.health.TweetHealthModelStore.TweetHealthModelStoreConfig
import com.ExTwitter.frigate.common.store.health.UserHealthModelStore
import com.ExTwitter.frigate.thriftscala.TweetHealthScores
import com.ExTwitter.frigate.thriftscala.UserAgathaScores
import com.ExTwitter.hermit.store.common.DeciderableReadableStore
import com.ExTwitter.hermit.store.common.ObservedCachedReadableStore
import com.ExTwitter.hermit.store.common.ObservedMemcachedReadableStore
import com.ExTwitter.hermit.store.common.ObservedReadableStore
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.simclusters_v2.common.TweetId
import com.ExTwitter.simclusters_v2.common.UserId
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.strato.client.{Client => StratoClient}
import com.ExTwitter.contentrecommender.store.TweetInfoStore
import com.ExTwitter.contentrecommender.store.TweetyPieFieldsStore
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.param.decider.CrMixerDecider
import com.ExTwitter.cr_mixer.param.decider.DeciderKey
import com.ExTwitter.frigate.data_pipeline.scalding.thriftscala.BlueVerifiedAnnotationsV2
import com.ExTwitter.recos.user_tweet_graph_plus.thriftscala.UserTweetGraphPlus
import com.ExTwitter.recos.user_tweet_graph_plus.thriftscala.TweetEngagementScores
import com.ExTwitter.relevance_platform.common.health_store.UserMediaRepresentationHealthStore
import com.ExTwitter.relevance_platform.common.health_store.MagicRecsRealTimeAggregatesStore
import com.ExTwitter.relevance_platform.thriftscala.MagicRecsRealTimeAggregatesScores
import com.ExTwitter.relevance_platform.thriftscala.UserMediaRepresentationScores
import com.ExTwitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.ExTwitter.tweetypie.thriftscala.TweetService
import com.ExTwitter.util.Future
import com.ExTwitter.util.JavaTimer
import com.ExTwitter.util.Timer

import javax.inject.Named

object TweetInfoStoreModule extends ExTwitterModule {
  implicit val timer: Timer = new JavaTimer(true)
  override def modules: Seq[Module] = Seq(UnifiedCacheClient)

  @Provides
  @Singleton
  def providesTweetInfoStore(
    statsReceiver: StatsReceiver,
    serviceIdentifier: ServiceIdentifier,
    stratoClient: StratoClient,
    @Named(ModuleNames.UnifiedCache) crMixerUnifiedCacheClient: MemcachedClient,
    manhattanKVClientMtlsParams: ManhattanKVClientMtlsParams,
    tweetyPieService: TweetService.MethodPerEndpoint,
    userTweetGraphPlusService: UserTweetGraphPlus.MethodPerEndpoint,
    @Named(ModuleNames.BlueVerifiedAnnotationStore) blueVerifiedAnnotationStore: ReadableStore[
      String,
      BlueVerifiedAnnotationsV2
    ],
    decider: CrMixerDecider
  ): ReadableStore[TweetId, TweetInfo] = {

    val tweetEngagementScoreStore: ReadableStore[TweetId, TweetEngagementScores] = {
      val underlyingStore =
        ObservedReadableStore(new ReadableStore[TweetId, TweetEngagementScores] {
          override def get(
            k: TweetId
          ): Future[Option[TweetEngagementScores]] = {
            userTweetGraphPlusService.tweetEngagementScore(k).map {
              Some(_)
            }
          }
        })(statsReceiver.scope("UserTweetGraphTweetEngagementScoreStore"))

      DeciderableReadableStore(
        underlyingStore,
        decider.deciderGateBuilder.idGate(
          DeciderKey.enableUtgRealTimeTweetEngagementScoreDeciderKey),
        statsReceiver.scope("UserTweetGraphTweetEngagementScoreStore")
      )

    }

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
            enablePNegMultimodal = true,
          ))
      )(statsReceiver.scope("UnderlyingTweetHealthModelStore"))

      DeciderableReadableStore(
        ObservedMemcachedReadableStore.fromCacheClient(
          backingStore = underlyingStore,
          cacheClient = crMixerUnifiedCacheClient,
          ttl = 2.hours
        )(
          valueInjection = BinaryScalaCodec(TweetHealthScores),
          statsReceiver = statsReceiver.scope("memCachedTweetHealthModelStore"),
          keyToString = { k: TweetId => s"tHMS/$k" }
        ),
        decider.deciderGateBuilder.idGate(DeciderKey.enableHealthSignalsScoreDeciderKey),
        statsReceiver.scope("TweetHealthModelStore")
      ) // use s"tHMS/$k" instead of s"tweetHealthModelStore/$k" to differentiate from CR cache
    }

    val userHealthModelStore: ReadableStore[UserId, UserAgathaScores] = {
      val underlyingStore = UserHealthModelStore.buildReadableStore(stratoClient)(
        statsReceiver.scope("UnderlyingUserHealthModelStore"))
      DeciderableReadableStore(
        ObservedMemcachedReadableStore.fromCacheClient(
          backingStore = underlyingStore,
          cacheClient = crMixerUnifiedCacheClient,
          ttl = 18.hours
        )(
          valueInjection = BinaryScalaCodec(UserAgathaScores),
          statsReceiver = statsReceiver.scope("memCachedUserHealthModelStore"),
          keyToString = { k: UserId => s"uHMS/$k" }
        ),
        decider.deciderGateBuilder.idGate(DeciderKey.enableUserAgathaScoreDeciderKey),
        statsReceiver.scope("UserHealthModelStore")
      )
    }

    val userMediaRepresentationHealthStore: ReadableStore[UserId, UserMediaRepresentationScores] = {
      val underlyingStore =
        UserMediaRepresentationHealthStore.buildReadableStore(
          manhattanKVClientMtlsParams,
          statsReceiver.scope("UnderlyingUserMediaRepresentationHealthStore")
        )
      DeciderableReadableStore(
        ObservedMemcachedReadableStore.fromCacheClient(
          backingStore = underlyingStore,
          cacheClient = crMixerUnifiedCacheClient,
          ttl = 12.hours
        )(
          valueInjection = BinaryScalaCodec(UserMediaRepresentationScores),
          statsReceiver = statsReceiver.scope("memCacheUserMediaRepresentationHealthStore"),
          keyToString = { k: UserId => s"uMRHS/$k" }
        ),
        decider.deciderGateBuilder.idGate(DeciderKey.enableUserMediaRepresentationStoreDeciderKey),
        statsReceiver.scope("UserMediaRepresentationHealthStore")
      )
    }

    val magicRecsRealTimeAggregatesStore: ReadableStore[
      TweetId,
      MagicRecsRealTimeAggregatesScores
    ] = {
      val underlyingStore =
        MagicRecsRealTimeAggregatesStore.buildReadableStore(
          serviceIdentifier,
          statsReceiver.scope("UnderlyingMagicRecsRealTimeAggregatesScores")
        )
      DeciderableReadableStore(
        underlyingStore,
        decider.deciderGateBuilder.idGate(DeciderKey.enableMagicRecsRealTimeAggregatesStore),
        statsReceiver.scope("MagicRecsRealTimeAggregatesStore")
      )
    }

    val tweetInfoStore: ReadableStore[TweetId, TweetInfo] = {
      val underlyingStore = TweetInfoStore(
        TweetyPieFieldsStore.getStoreFromTweetyPie(tweetyPieService),
        userMediaRepresentationHealthStore,
        magicRecsRealTimeAggregatesStore,
        tweetEngagementScoreStore,
        blueVerifiedAnnotationStore
      )(statsReceiver.scope("tweetInfoStore"))

      val memcachedStore = ObservedMemcachedReadableStore.fromCacheClient(
        backingStore = underlyingStore,
        cacheClient = crMixerUnifiedCacheClient,
        ttl = 15.minutes,
        // Hydrating tweetInfo is now a required step for all candidates,
        // hence we needed to tune these thresholds.
        asyncUpdate = serviceIdentifier.environment == "prod"
      )(
        valueInjection = BinaryScalaCodec(TweetInfo),
        statsReceiver = statsReceiver.scope("memCachedTweetInfoStore"),
        keyToString = { k: TweetId => s"tIS/$k" }
      )

      ObservedCachedReadableStore.from(
        memcachedStore,
        ttl = 15.minutes,
        maxKeys = 8388607, // Check TweetInfo definition. size~92b. Around 736 MB
        windowSize = 10000L,
        cacheName = "tweet_info_cache",
        maxMultiGetSize = 20
      )(statsReceiver.scope("inMemoryCachedTweetInfoStore"))
    }
    tweetInfoStore
  }
}
