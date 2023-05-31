package com.twitter.frigate.pushservice.take

import com.twitter.finagle.stats.BroadcastStatsReceiver
import com.twitter.finagle.stats.Counter
import com.twitter.finagle.stats.Stat
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.base.CandidateResult
import com.twitter.frigate.common.base.Invalid
import com.twitter.frigate.common.base.OK
import com.twitter.frigate.common.base.Response
import com.twitter.frigate.common.base.Result
import com.twitter.frigate.common.base.Stats.track
import com.twitter.frigate.common.config.CommonConstants
import com.twitter.frigate.common.logger.MRLogger
import com.twitter.frigate.common.util.PushServiceUtil.FilteredLoggedOutResponseFut
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.refresh_handler.RFPHStatsRecorder
import com.twitter.frigate.pushservice.thriftscala.LoggedOutResponse
import com.twitter.frigate.pushservice.thriftscala.PushStatus
import com.twitter.util.Future
import com.twitter.util.JavaTimer
import com.twitter.util.Timer

class LoggedOutRefreshForPushNotifier(
  rfphStatsRecorder: RFPHStatsRecorder,
  loCandidateNotifier: CandidateNotifier
)(
  globalStats: StatsReceiver) {
  private implicit val statsReceiver: StatsReceiver =
    globalStats.scope("LoggedOutRefreshForPushHandler")
  private val loPushStats: StatsReceiver = statsReceiver.scope("logged_out_push")
  private val loSendLatency: StatsReceiver = statsReceiver.scope("logged_out_send")
  private val processedCandidatesCounter: Counter =
    statsReceiver.counter("processed_candidates_count")
  private val validCandidatesCounter: Counter = statsReceiver.counter("valid_candidates_count")
  private val okayCandidateCounter: Counter = statsReceiver.counter("ok_candidate_count")
  private val nonOkayCandidateCounter: Counter = statsReceiver.counter("non_ok_candidate_count")
  private val successNotifyCounter: Counter = statsReceiver.counter("success_notify_count")
  private val notifyCandidate: Counter = statsReceiver.counter("notify_candidate")
  private val noneCandidateResultCounter: Counter = statsReceiver.counter("none_candidate_count")
  private val nonOkayPredsResult: Counter = statsReceiver.counter("non_okay_preds_result")
  private val invalidResultCounter: Counter = statsReceiver.counter("invalid_result_count")
  private val filteredLoggedOutResponse: Counter = statsReceiver.counter("filtered_response_count")

  implicit private val timer: Timer = new JavaTimer(true)
  val log = MRLogger("LoggedOutRefreshForNotifier")

  private def notify(
    candidatesResult: CandidateResult[PushCandidate, Result]
  ): Future[LoggedOutResponse] = {
    val candidate = candidatesResult.candidate
    if (candidate != null)
      notifyCandidate.incr()
    val predsResult = candidatesResult.result
    if (predsResult != OK) {
      nonOkayPredsResult.incr()
      val invalidResult = predsResult
      invalidResult match {
        case Invalid(Some(reason)) =>
          invalidResultCounter.incr()
          Future.value(LoggedOutResponse(PushStatus.Filtered, Some(reason)))
        case _ =>
          filteredLoggedOutResponse.incr()
          Future.value(LoggedOutResponse(PushStatus.Filtered, None))
      }
    } else {
      track(loSendLatency)(loCandidateNotifier.loggedOutNotify(candidate).map { res =>
        LoggedOutResponse(res.status)
      })
    }
  }

  def checkResponseAndNotify(
    response: Response[PushCandidate, Result]
  ): Future[LoggedOutResponse] = {
    val receivers = Seq(statsReceiver)
    val loggedOutResponse = response match {
      case Response(OK, processedCandidates) =>
        processedCandidatesCounter.incr(processedCandidates.size)
        val validCandidates = processedCandidates.filter(_.result == OK)
        validCandidatesCounter.incr(validCandidates.size)

        validCandidates.headOption match {
          case Some(candidatesResult) =>
            candidatesResult.result match {
              case OK =>
                okayCandidateCounter.incr()
                notify(candidatesResult)
                  .onSuccess { nr =>
                    successNotifyCounter.incr()
                    loPushStats.scope("lo_result").counter(nr.status.name).incr()
                  }
              case _ =>
                nonOkayCandidateCounter.incr()
                FilteredLoggedOutResponseFut
            }
          case _ =>
            noneCandidateResultCounter.incr()
            FilteredLoggedOutResponseFut
        }

      case Response(Invalid(reason), _) =>
        FilteredLoggedOutResponseFut.map(_.copy(filteredBy = reason))

      case _ =>
        FilteredLoggedOutResponseFut
    }
    val bstats = BroadcastStatsReceiver(receivers)
    Stat
      .timeFuture(bstats.stat("logged_out_latency"))(
        loggedOutResponse.raiseWithin(CommonConstants.maxPushRequestDuration)
      )
      .onFailure { exception =>
        rfphStatsRecorder.loggedOutRequestExceptionStats(exception, bstats)
      }
    loggedOutResponse
  }
}
