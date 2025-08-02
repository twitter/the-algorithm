package com.twitter.visibility.util

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.logging._

object LoggingUtil {

  val EtwitterperimentationLog: String = "vf_abdecider"

  def mkDefaultHandlerFactory(statsReceiver: StatsReceiver): () => Handler = {
    QueueingHandler(
      matwitterQueueSize = 10000,
      handler = ScribeHandler(
        category = "client_event",
        formatter = BareFormatter,
        statsReceiver = statsReceiver.scope("client_event_scribe"),
        level = Some(Level.INFO)
      )
    )
  }

  def mkDefaultLoggerFactory(statsReceiver: StatsReceiver): LoggerFactory = {
    LoggerFactory(
      node = EtwitterperimentationLog,
      level = Some(Level.INFO),
      useParents = false,
      handlers = List(mkDefaultHandlerFactory(statsReceiver))
    )
  }

  def mkDefaultLogger(statsReceiver: StatsReceiver): Logger = {
    mkDefaultLoggerFactory(statsReceiver)()
  }

}
