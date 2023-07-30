package com.X.usersignalservice.base

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.Stats
import com.X.storehaus.ReadableStore
import com.X.twistly.common.UserId
import com.X.usersignalservice.base.BaseSignalFetcher.Timeout
import com.X.usersignalservice.thriftscala.Signal
import com.X.usersignalservice.thriftscala.SignalType
import com.X.util.Future
import com.X.util.Timer

case class AggregatedSignalController(
  signalsAggregationInfo: Seq[SignalAggregatedInfo],
  signalsWeightMapInfo: Map[SignalType, Double],
  stats: StatsReceiver,
  timer: Timer)
    extends ReadableStore[Query, Seq[Signal]] {

  val name: String = this.getClass.getCanonicalName
  val statsReceiver: StatsReceiver = stats.scope(name)

  override def get(query: Query): Future[Option[Seq[Signal]]] = {
    Stats
      .trackItems(statsReceiver) {
        val allSignalsFut =
          Future
            .collect(signalsAggregationInfo.map(_.getSignals(query.userId))).map(_.flatten.flatten)
        val aggregatedSignals =
          allSignalsFut.map { allSignals =>
            allSignals
              .groupBy(_.targetInternalId).collect {
                case (Some(internalId), signals) =>
                  val mostRecentEnagementTime = signals.map(_.timestamp).max
                  val totalWeight =
                    signals
                      .map(signal => signalsWeightMapInfo.getOrElse(signal.signalType, 0.0)).sum
                  (Signal(query.signalType, mostRecentEnagementTime, Some(internalId)), totalWeight)
              }.toSeq.sortBy { case (signal, weight) => (-weight, -signal.timestamp) }
              .map(_._1)
              .take(query.maxResults.getOrElse(Int.MaxValue))
          }
        aggregatedSignals.map(Some(_))
      }.raiseWithin(Timeout)(timer).handle {
        case e =>
          statsReceiver.counter(e.getClass.getCanonicalName).incr()
          Some(Seq.empty[Signal])
      }
  }
}

case class SignalAggregatedInfo(
  signalType: SignalType,
  signalFetcher: ReadableStore[Query, Seq[Signal]]) {
  def getSignals(userId: UserId): Future[Option[Seq[Signal]]] = {
    signalFetcher.get(Query(userId, signalType, None))
  }
}
