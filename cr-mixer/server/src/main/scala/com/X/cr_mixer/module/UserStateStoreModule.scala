package com.X.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.bijection.Bufferable
import com.X.bijection.Injection
import com.X.bijection.scrooge.BinaryScalaCodec
import com.X.cr_mixer.model.ModuleNames
import com.X.conversions.DurationOps._
import com.X.finagle.memcached.{Client => MemcachedClient}
import com.X.finagle.stats.StatsReceiver
import com.X.hermit.store.common.ObservedMemcachedReadableStore
import com.X.inject.XModule
import com.X.simclusters_v2.common.UserId
import com.X.snowflake.id.SnowflakeId
import com.X.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.X.storehaus.ReadableStore
import com.X.storehaus_internal.manhattan.ManhattanRO
import com.X.storehaus_internal.manhattan.ManhattanROConfig
import com.X.storehaus_internal.util.HDFSPath
import com.X.core_workflows.user_model.thriftscala.UserState
import com.X.core_workflows.user_model.thriftscala.CondensedUserState
import com.X.cr_mixer.config.TimeoutConfig
import com.X.cr_mixer.param.decider.CrMixerDecider
import com.X.cr_mixer.param.decider.DeciderKey
import com.X.hermit.store.common.DeciderableReadableStore
import com.X.storehaus_internal.manhattan.Apollo
import com.X.storehaus_internal.util.ApplicationID
import com.X.storehaus_internal.util.DatasetName
import com.X.util.Duration
import com.X.util.Future
import com.X.util.JavaTimer
import com.X.util.Time
import com.X.util.TimeoutException
import com.X.util.Timer
import javax.inject.Named

object UserStateStoreModule extends XModule {
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
