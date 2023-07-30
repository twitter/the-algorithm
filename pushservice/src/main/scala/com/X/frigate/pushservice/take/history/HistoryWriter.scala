package com.X.frigate.pushservice.take.history

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.history.HistoryStoreKeyContext
import com.X.frigate.common.history.PushServiceHistoryStore
import com.X.frigate.common.rec_types.RecTypes
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.frigate.thriftscala.FrigateNotification
import com.X.util.Duration
import com.X.util.Future
import com.X.conversions.DurationOps._

class HistoryWriter(historyStore: PushServiceHistoryStore, stats: StatsReceiver) {
  private lazy val historyWriterStats = stats.scope(this.getClass.getSimpleName)
  private lazy val historyWriteCounter = historyWriterStats.counter("history_write_num")
  private lazy val loggedOutHistoryWriteCounter =
    historyWriterStats.counter("logged_out_history_write_num")

  private def writeTtlForHistory(candidate: PushCandidate): Duration = {
    if (candidate.target.isLoggedOutUser) {
      60.days
    } else if (RecTypes.isTweetType(candidate.commonRecType)) {
      candidate.target.params(PushFeatureSwitchParams.FrigateHistoryTweetNotificationWriteTtl)
    } else candidate.target.params(PushFeatureSwitchParams.FrigateHistoryOtherNotificationWriteTtl)
  }

  def writeSendToHistory(
    candidate: PushCandidate,
    frigateNotificationForPersistence: FrigateNotification
  ): Future[Unit] = {
    val historyStoreKeyContext = HistoryStoreKeyContext(
      candidate.target.targetId,
      candidate.target.pushContext.flatMap(_.useMemcacheForHistory).getOrElse(false)
    )
    if (candidate.target.isLoggedOutUser) {
      loggedOutHistoryWriteCounter.incr()
    } else {
      historyWriteCounter.incr()
    }
    historyStore
      .put(
        historyStoreKeyContext,
        candidate.createdAt,
        frigateNotificationForPersistence,
        Some(writeTtlForHistory(candidate))
      )
  }
}
