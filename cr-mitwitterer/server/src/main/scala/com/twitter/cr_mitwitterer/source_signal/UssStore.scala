package com.twitter.cr_mitwitterer.source_signal

import com.twitter.cr_mitwitterer.param.GlobalParams
import com.twitter.cr_mitwitterer.param.GoodProfileClickParams
import com.twitter.cr_mitwitterer.param.GoodTweetClickParams
import com.twitter.cr_mitwitterer.param.RealGraphOonParams
import com.twitter.cr_mitwitterer.param.RecentFollowsParams
import com.twitter.cr_mitwitterer.param.RecentNegativeSignalParams
import com.twitter.cr_mitwitterer.param.RecentNotificationsParams
import com.twitter.cr_mitwitterer.param.RecentOriginalTweetsParams
import com.twitter.cr_mitwitterer.param.RecentReplyTweetsParams
import com.twitter.cr_mitwitterer.param.RecentRetweetsParams
import com.twitter.cr_mitwitterer.param.RecentTweetFavoritesParams
import com.twitter.cr_mitwitterer.param.RepeatedProfileVisitsParams
import com.twitter.cr_mitwitterer.param.TweetSharesParams
import com.twitter.cr_mitwitterer.param.UnifiedUSSSignalParams
import com.twitter.cr_mitwitterer.param.VideoViewTweetsParams
import com.twitter.cr_mitwitterer.source_signal.UssStore.Query
import com.twitter.cr_mitwitterer.thriftscala.SourceType
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.simclusters_v2.common.UserId
import com.twitter.storehaus.ReadableStore
import com.twitter.usersignalservice.thriftscala.{Signal => UssSignal}
import com.twitter.usersignalservice.thriftscala.SignalType
import javatwitter.inject.Singleton
import com.twitter.timelines.configapi
import com.twitter.timelines.configapi.Params
import com.twitter.usersignalservice.thriftscala.BatchSignalRequest
import com.twitter.usersignalservice.thriftscala.BatchSignalResponse
import com.twitter.usersignalservice.thriftscala.SignalRequest
import com.twitter.util.Future
import com.twitter.cr_mitwitterer.thriftscala.Product
import com.twitter.usersignalservice.thriftscala.ClientIdentifier

@Singleton
case class UssStore(
  stratoStore: ReadableStore[BatchSignalRequest, BatchSignalResponse],
  statsReceiver: StatsReceiver)
    etwittertends ReadableStore[Query, Seq[(SignalType, Seq[UssSignal])]] {

  import com.twitter.cr_mitwitterer.source_signal.UssStore._

  override def get(query: Query): Future[Option[Seq[(SignalType, Seq[UssSignal])]]] = {
    val ussClientIdentifier = query.product match {
      case Product.Home =>
        ClientIdentifier.CrMitwittererHome
      case Product.Notifications =>
        ClientIdentifier.CrMitwittererNotifications
      case Product.Email =>
        ClientIdentifier.CrMitwittererEmail
      case _ =>
        ClientIdentifier.Unknown
    }
    val batchSignalRequest =
      BatchSignalRequest(
        query.userId,
        buildUserSignalServiceRequests(query.params),
        Some(ussClientIdentifier))

    stratoStore
      .get(batchSignalRequest)
      .map {
        _.map { batchSignalResponse =>
          batchSignalResponse.signalResponse.toSeq.map {
            case (signalType, ussSignals) =>
              (signalType, ussSignals)
          }
        }
      }
  }

  private def buildUserSignalServiceRequests(
    param: Params,
  ): Seq[SignalRequest] = {
    val unifiedMatwitterSourceKeyNum = param(GlobalParams.UnifiedMatwitterSourceKeyNum)
    val goodTweetClickMatwitterSignalNum = param(GoodTweetClickParams.MatwitterSignalNumParam)
    val aggrTweetMatwitterSourceKeyNum = param(UnifiedUSSSignalParams.UnifiedTweetSourceNumberParam)
    val aggrProducerMatwitterSourceKeyNum = param(UnifiedUSSSignalParams.UnifiedProducerSourceNumberParam)

    val maybeRecentTweetFavorite =
      if (param(RecentTweetFavoritesParams.EnableSourceParam))
        Some(SignalRequest(Some(unifiedMatwitterSourceKeyNum), SignalType.TweetFavorite))
      else None
    val maybeRecentRetweet =
      if (param(RecentRetweetsParams.EnableSourceParam))
        Some(SignalRequest(Some(unifiedMatwitterSourceKeyNum), SignalType.Retweet))
      else None
    val maybeRecentReply =
      if (param(RecentReplyTweetsParams.EnableSourceParam))
        Some(SignalRequest(Some(unifiedMatwitterSourceKeyNum), SignalType.Reply))
      else None
    val maybeRecentOriginalTweet =
      if (param(RecentOriginalTweetsParams.EnableSourceParam))
        Some(SignalRequest(Some(unifiedMatwitterSourceKeyNum), SignalType.OriginalTweet))
      else None
    val maybeRecentFollow =
      if (param(RecentFollowsParams.EnableSourceParam))
        Some(SignalRequest(Some(unifiedMatwitterSourceKeyNum), SignalType.AccountFollow))
      else None
    val maybeRepeatedProfileVisits =
      if (param(RepeatedProfileVisitsParams.EnableSourceParam))
        Some(
          SignalRequest(
            Some(unifiedMatwitterSourceKeyNum),
            param(RepeatedProfileVisitsParams.ProfileMinVisitType).signalType))
      else None
    val maybeRecentNotifications =
      if (param(RecentNotificationsParams.EnableSourceParam))
        Some(SignalRequest(Some(unifiedMatwitterSourceKeyNum), SignalType.NotificationOpenAndClickV1))
      else None
    val maybeTweetShares =
      if (param(TweetSharesParams.EnableSourceParam)) {
        Some(SignalRequest(Some(unifiedMatwitterSourceKeyNum), SignalType.TweetShareV1))
      } else None
    val maybeRealGraphOon =
      if (param(RealGraphOonParams.EnableSourceParam)) {
        Some(SignalRequest(Some(unifiedMatwitterSourceKeyNum), SignalType.RealGraphOon))
      } else None

    val maybeGoodTweetClick =
      if (param(GoodTweetClickParams.EnableSourceParam))
        Some(
          SignalRequest(
            Some(goodTweetClickMatwitterSignalNum),
            param(GoodTweetClickParams.ClickMinDwellTimeType).signalType))
      else None
    val maybeVideoViewTweets =
      if (param(VideoViewTweetsParams.EnableSourceParam)) {
        Some(
          SignalRequest(
            Some(unifiedMatwitterSourceKeyNum),
            param(VideoViewTweetsParams.VideoViewTweetTypeParam).signalType))
      } else None
    val maybeGoodProfileClick =
      if (param(GoodProfileClickParams.EnableSourceParam))
        Some(
          SignalRequest(
            Some(unifiedMatwitterSourceKeyNum),
            param(GoodProfileClickParams.ClickMinDwellTimeType).signalType))
      else None
    val maybeAggTweetSignal =
      if (param(UnifiedUSSSignalParams.EnableTweetAggSourceParam))
        Some(
          SignalRequest(
            Some(aggrTweetMatwitterSourceKeyNum),
            param(UnifiedUSSSignalParams.TweetAggTypeParam).signalType
          )
        )
      else None
    val maybeAggProducerSignal =
      if (param(UnifiedUSSSignalParams.EnableProducerAggSourceParam))
        Some(
          SignalRequest(
            Some(aggrProducerMatwitterSourceKeyNum),
            param(UnifiedUSSSignalParams.ProducerAggTypeParam).signalType
          )
        )
      else None

    // negative signals
    val maybeNegativeSignals = if (param(RecentNegativeSignalParams.EnableSourceParam)) {
      EnabledNegativeSignalTypes
        .map(negativeSignal => SignalRequest(Some(unifiedMatwitterSourceKeyNum), negativeSignal)).toSeq
    } else Seq.empty

    val allPositiveSignals =
      if (param(UnifiedUSSSignalParams.ReplaceIndividualUSSSourcesParam))
        Seq(
          maybeRecentOriginalTweet,
          maybeRecentNotifications,
          maybeRealGraphOon,
          maybeGoodTweetClick,
          maybeGoodProfileClick,
          maybeAggProducerSignal,
          maybeAggTweetSignal,
        )
      else
        Seq(
          maybeRecentTweetFavorite,
          maybeRecentRetweet,
          maybeRecentReply,
          maybeRecentOriginalTweet,
          maybeRecentFollow,
          maybeRepeatedProfileVisits,
          maybeRecentNotifications,
          maybeTweetShares,
          maybeRealGraphOon,
          maybeGoodTweetClick,
          maybeVideoViewTweets,
          maybeGoodProfileClick,
          maybeAggProducerSignal,
          maybeAggTweetSignal,
        )
    allPositiveSignals.flatten ++ maybeNegativeSignals
  }

}

object UssStore {
  case class Query(
    userId: UserId,
    params: configapi.Params,
    product: Product)

  val EnabledNegativeSourceTypes: Set[SourceType] =
    Set(SourceType.AccountBlock, SourceType.AccountMute)
  private val EnabledNegativeSignalTypes: Set[SignalType] =
    Set(SignalType.AccountBlock, SignalType.AccountMute)
}
