package com.X.usersignalservice.signals

import com.X.finagle.stats.StatsReceiver
import com.X.simclusters_v2.common.UserId
import com.X.simclusters_v2.thriftscala.InternalId
import com.X.strato.client.Client
import com.X.strato.data.Conv
import com.X.strato.thrift.ScroogeConv
import com.X.twistly.common.TwistlyProfile
import com.X.twistly.thriftscala.EngagementMetadata.RetweetMetadata
import com.X.twistly.thriftscala.RecentEngagedTweet
import com.X.twistly.thriftscala.UserRecentEngagedTweets
import com.X.usersignalservice.base.Query
import com.X.usersignalservice.base.StratoSignalFetcher
import com.X.usersignalservice.thriftscala.Signal
import com.X.util.Future
import com.X.util.Timer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class RetweetsFetcher @Inject() (
  stratoClient: Client,
  timer: Timer,
  stats: StatsReceiver)
    extends StratoSignalFetcher[(UserId, Long), Unit, UserRecentEngagedTweets] {
  import RetweetsFetcher._
  override type RawSignalType = RecentEngagedTweet
  override val name: String = this.getClass.getCanonicalName
  override val statsReceiver: StatsReceiver = stats.scope(name)

  override val stratoColumnPath: String =
    TwistlyProfile.TwistlyProdProfile.userRecentEngagedStorePath
  override val stratoView: Unit = None

  override protected val keyConv: Conv[(UserId, Long)] = Conv.ofType
  override protected val viewConv: Conv[Unit] = Conv.ofType
  override protected val valueConv: Conv[UserRecentEngagedTweets] =
    ScroogeConv.fromStruct[UserRecentEngagedTweets]

  override protected def toStratoKey(userId: UserId): (UserId, Long) = (userId, DefaultVersion)

  override protected def toRawSignals(
    userRecentEngagedTweets: UserRecentEngagedTweets
  ): Seq[RawSignalType] =
    userRecentEngagedTweets.recentEngagedTweets

  override def process(
    query: Query,
    rawSignals: Future[Option[Seq[RawSignalType]]]
  ): Future[Option[Seq[Signal]]] = {
    rawSignals.map {
      _.map { signals =>
        val lookBackWindowFilteredSignals =
          SignalFilter.lookBackWindow90DayFilter(signals, query.signalType)
        lookBackWindowFilteredSignals
          .filter { recentEngagedTweet =>
            recentEngagedTweet.features.statusCounts
              .flatMap(_.favoriteCount).exists(_ >= MinFavCount)
          }.collect {
            case RecentEngagedTweet(tweetId, engagedAt, _: RetweetMetadata, _) =>
              Signal(query.signalType, engagedAt, Some(InternalId.TweetId(tweetId)))
          }.take(query.maxResults.getOrElse(Int.MaxValue))
      }
    }
  }

}

object RetweetsFetcher {
  private val MinFavCount = 10
  // see com.X.twistly.store.UserRecentEngagedTweetsStore
  private val DefaultVersion = 0
}
