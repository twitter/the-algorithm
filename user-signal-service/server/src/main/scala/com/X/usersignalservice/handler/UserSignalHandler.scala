package com.X.usersignalservice.handler

import com.X.storehaus.ReadableStore
import com.X.usersignalservice.thriftscala.BatchSignalRequest
import com.X.usersignalservice.thriftscala.BatchSignalResponse
import com.X.util.Future
import com.X.conversions.DurationOps._
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.util.StatsUtil
import com.X.usersignalservice.config.SignalFetcherConfig
import com.X.usersignalservice.base.Query
import com.X.usersignalservice.thriftscala.ClientIdentifier
import com.X.usersignalservice.thriftscala.SignalType
import com.X.util.Duration
import com.X.util.Timer
import com.X.util.TimeoutException

class UserSignalHandler(
  signalFetcherConfig: SignalFetcherConfig,
  timer: Timer,
  stats: StatsReceiver) {
  import UserSignalHandler._

  val statsReceiver: StatsReceiver = stats.scope("user-signal-service/service")

  def getBatchSignalsResponse(request: BatchSignalRequest): Future[Option[BatchSignalResponse]] = {
    StatsUtil.trackOptionStats(statsReceiver) {
      val allSignals = request.signalRequest.map { signalRequest =>
        signalFetcherConfig
          .SignalFetcherMapper(signalRequest.signalType)
          .get(Query(
            userId = request.userId,
            signalType = signalRequest.signalType,
            maxResults = signalRequest.maxResults.map(_.toInt),
            clientId = request.clientId.getOrElse(ClientIdentifier.Unknown)
          ))
          .map(signalOpt => (signalRequest.signalType, signalOpt))
      }

      Future.collect(allSignals).map { signalsSeq =>
        val signalsMap = signalsSeq.map {
          case (signalType: SignalType, signalsOpt) =>
            (signalType, signalsOpt.getOrElse(EmptySeq))
        }.toMap
        Some(BatchSignalResponse(signalsMap))
      }
    }
  }

  def toReadableStore: ReadableStore[BatchSignalRequest, BatchSignalResponse] = {
    new ReadableStore[BatchSignalRequest, BatchSignalResponse] {
      override def get(request: BatchSignalRequest): Future[Option[BatchSignalResponse]] = {
        getBatchSignalsResponse(request).raiseWithin(UserSignalServiceTimeout)(timer).rescue {
          case _: TimeoutException =>
            statsReceiver.counter("endpointGetBatchSignals/failure/timeout").incr()
            EmptyResponse
          case e =>
            statsReceiver.counter("endpointGetBatchSignals/failure/" + e.getClass.getName).incr()
            EmptyResponse
        }
      }
    }
  }
}

object UserSignalHandler {
  val UserSignalServiceTimeout: Duration = 25.milliseconds

  val EmptySeq: Seq[Nothing] = Seq.empty
  val EmptyResponse: Future[Option[BatchSignalResponse]] = Future.value(Some(BatchSignalResponse()))
}
