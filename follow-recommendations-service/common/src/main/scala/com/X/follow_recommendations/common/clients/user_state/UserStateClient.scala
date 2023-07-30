package com.X.follow_recommendations.common.clients.user_state

import com.google.inject.name.Named
import com.X.conversions.DurationOps._
import com.X.core_workflows.user_model.thriftscala.CondensedUserState
import com.X.core_workflows.user_model.thriftscala.UserState
import com.X.decider.Decider
import com.X.decider.RandomRecipient
import com.X.finagle.Memcached.Client
import com.X.finagle.stats.StatsReceiver
import com.X.finagle.util.DefaultTimer
import com.X.follow_recommendations.common.base.StatsUtil
import com.X.follow_recommendations.common.clients.cache.MemcacheClient
import com.X.follow_recommendations.common.clients.cache.ThriftEnumOptionBijection
import com.X.follow_recommendations.common.constants.GuiceNamedConstants
import com.X.follow_recommendations.configapi.deciders.DeciderKey
import com.X.stitch.Stitch
import com.X.strato.client.Fetcher
import com.X.util.Duration
import javax.inject.Inject
import javax.inject.Singleton
import java.lang.{Long => JLong}

@Singleton
class UserStateClient @Inject() (
  @Named(GuiceNamedConstants.USER_STATE_FETCHER) userStateFetcher: Fetcher[
    Long,
    Unit,
    CondensedUserState
  ],
  client: Client,
  statsReceiver: StatsReceiver,
  decider: Decider = Decider.False) {

  private val stats: StatsReceiver = statsReceiver.scope("user_state_client")

  // client to memcache cluster
  val bijection = new ThriftEnumOptionBijection[UserState](UserState.apply)
  val memcacheClient = MemcacheClient[Option[UserState]](
    client = client,
    dest = "/s/cache/follow_recos_service:twemcaches",
    valueBijection = bijection,
    ttl = UserStateClient.CacheTTL,
    statsReceiver = stats.scope("twemcache")
  )

  def getUserState(userId: Long): Stitch[Option[UserState]] = {
    val deciderKey: String = DeciderKey.EnableDistributedCaching.toString
    val enableDistributedCaching: Boolean = decider.isAvailable(deciderKey, Some(RandomRecipient))
    val userStateStitch: Stitch[Option[UserState]] = 
      enableDistributedCaching match {
        // read from memcache
        case true => memcacheClient.readThrough(
          // add a key prefix to address cache key collisions
          key = "UserStateClient" + userId.toString,
          underlyingCall = () => fetchUserState(userId)
        )
        case false => fetchUserState(userId)
      }
    val userStateStitchWithTimeout: Stitch[Option[UserState]] = 
      userStateStitch
        // set a 150ms timeout limit for user state fetches
        .within(150.milliseconds)(DefaultTimer)
        .rescue {
          case e: Exception =>
            stats.scope("rescued").counter(e.getClass.getSimpleName).incr()
            Stitch(None)
        }
    // profile the latency of stitch call and return the result
    StatsUtil.profileStitch(
      userStateStitchWithTimeout,
      stats.scope("getUserState")
    )
  }

  def fetchUserState(userId: JLong): Stitch[Option[UserState]] = {
    userStateFetcher.fetch(userId).map(_.v.flatMap(_.userState))
  }
}

object UserStateClient {
  val CacheTTL: Duration = Duration.fromHours(6)
}
