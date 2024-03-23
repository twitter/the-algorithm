package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.ExTwitter.bijection.Bufferable
import com.ExTwitter.bijection.Injection
import com.ExTwitter.bijection.scrooge.BinaryScalaCodec
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.memcached.{Client => MemcachedClient}
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.hermit.store.common.ObservedMemcachedReadableStore
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.simclusters_v2.common.UserId
import com.ExTwitter.snowflake.id.SnowflakeId
import com.ExTwitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.storehaus_internal.manhattan.ManhattanRO
import com.ExTwitter.storehaus_internal.manhattan.ManhattanROConfig
import com.ExTwitter.storehaus_internal.util.HDFSPath
import com.ExTwitter.core_workflows.user_model.thriftscala.UserState
import com.ExTwitter.core_workflows.user_model.thriftscala.CondensedUserState
import com.ExTwitter.cr_mixer.config.TimeoutConfig
import com.ExTwitter.cr_mixer.param.decider.CrMixerDecider
import com.ExTwitter.cr_mixer.param.decider.DeciderKey
import com.ExTwitter.hermit.store.common.DeciderableReadableStore
import com.ExTwitter.storehaus_internal.manhattan.Apollo
import com.ExTwitter.storehaus_internal.util.ApplicationID
import com.ExTwitter.storehaus_internal.util.DatasetName
import com.ExTwitter.util.Duration
import com.ExTwitter.util.Future
import com.ExTwitter.util.JavaTimer
import com.ExTwitter.util.Time
import com.ExTwitter.util.TimeoutException
import com.ExTwitter.util.Timer
import javax.inject.Named

object UserStateStoreModule extends ExTwitterModule {
  implicit val timer: Timer = new JavaTimer(true)
  final val NewUserCreateDaysThreshold = 7
  final val DefaultUnknownUserStateValue = 100

  // Convert CondensedUserState to UserState Enum
  // If CondensedUserState is None, back fill by checking whether the user is new user
  class UserStateStore(
    userStateStore: ReadableStore[UserId, CondensedUserState],
    timeout: Duration,
    statsReceiver: StatsReceiver)
      extends ReadableStore[UserId, UserState] {
    override def get(userId: UserId): Future[Option[UserState]] = {
      userStateStore
        .get(userId).map(_.flatMap(_.userState)).map {
          case Some(userState) => Some(userState)
          case None =>
            val isNewUser = SnowflakeId.timeFromIdOpt(userId).exists { userCreateTime =>
              Time.now - userCreateTime < Duration.fromDays(NewUserCreateDaysThreshold)
            }
            if (isNewUser) Some(UserState.New)
            else Some(UserState.EnumUnknownUserState(DefaultUnknownUserStateValue))

        }.raiseWithin(timeout)(timer).rescue {
          case _: TimeoutException =>
            statsReceiver.counter("TimeoutException").incr()
            Future.None
        }
    }
  }

  @Provides
  @Singleton
  def providesUserStateStore(
    crMixerDecider: CrMixerDecider,
    statsReceiver: StatsReceiver,
    manhattanKVClientMtlsParams: ManhattanKVClientMtlsParams,
    @Named(ModuleNames.UnifiedCache) crMixerUnifiedCacheClient: MemcachedClient,
    timeoutConfig: TimeoutConfig
  ): ReadableStore[UserId, UserState] = {

    val underlyingStore = new UserStateStore(
      ManhattanRO
        .getReadableStoreWithMtls[UserId, CondensedUserState](
          ManhattanROConfig(
            HDFSPath(""),
            ApplicationID("cr_mixer_apollo"),
            DatasetName("condensed_user_state"),
            Apollo),
          manhattanKVClientMtlsParams
        )(
          implicitly[Injection[Long, Array[Byte]]],
          BinaryScalaCodec(CondensedUserState)
        ),
      timeoutConfig.userStateStoreTimeout,
      statsReceiver.scope("UserStateStore")
    ).mapValues(_.value) // Read the value of Enum so that we only caches the Int

    val memCachedStore = ObservedMemcachedReadableStore
      .fromCacheClient(
        backingStore = underlyingStore,
        cacheClient = crMixerUnifiedCacheClient,
        ttl = 24.hours,
      )(
        valueInjection = Bufferable.injectionOf[Int], // Cache Value is Enum Value for UserState
        statsReceiver = statsReceiver.scope("memCachedUserStateStore"),
        keyToString = { k: UserId => s"uState/$k" }
      ).mapValues(value => UserState.getOrUnknown(value))

    DeciderableReadableStore(
      memCachedStore,
      crMixerDecider.deciderGateBuilder.idGate(DeciderKey.enableUserStateStoreDeciderKey),
      statsReceiver.scope("UserStateStore")
    )
  }
}
