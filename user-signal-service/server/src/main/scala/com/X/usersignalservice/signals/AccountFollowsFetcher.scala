package com.X.usersignalservice.signals

import com.X.finagle.stats.StatsReceiver
import com.X.socialgraph.thriftscala.RelationshipType
import com.X.socialgraph.thriftscala.SocialGraphService
import com.X.twistly.common.UserId
import com.X.usersignalservice.base.BaseSignalFetcher
import com.X.usersignalservice.base.Query
import com.X.usersignalservice.signals.common.SGSUtils
import com.X.usersignalservice.thriftscala.Signal
import com.X.usersignalservice.thriftscala.SignalType
import com.X.util.Future
import com.X.util.Timer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class AccountFollowsFetcher @Inject() (
  sgsClient: SocialGraphService.MethodPerEndpoint,
  timer: Timer,
  stats: StatsReceiver)
    extends BaseSignalFetcher {

  override type RawSignalType = Signal
  override val name: String = this.getClass.getCanonicalName
  override val statsReceiver: StatsReceiver = stats.scope(this.name)

  override def getRawSignals(
    userId: UserId
  ): Future[Option[Seq[RawSignalType]]] = {
    SGSUtils.getSGSRawSignals(
      userId,
      sgsClient,
      RelationshipType.Following,
      SignalType.AccountFollow)
  }

  override def process(
    query: Query,
    rawSignals: Future[Option[Seq[RawSignalType]]]
  ): Future[Option[Seq[Signal]]] = {
    rawSignals.map(_.map(_.take(query.maxResults.getOrElse(Int.MaxValue))))
  }
}
