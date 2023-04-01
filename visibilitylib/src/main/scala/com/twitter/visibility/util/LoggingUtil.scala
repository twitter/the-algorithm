package com.twitter.visibility.util

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.logging._

object LoggingUtil {

  val ExperimentationLog: String = "vf_abdecider"

  def mkDefaultHandlerFactory(statsReceiver: StatsReceiver): () => Handler = {
    QueueingHandler(
      maxQueueSize = 10000,
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
      node = ExperimentationLog,
      level = Some(Level.INFO),
      useParents = false,
      handlers = List(mkDefaultHandlerFactory(statsReceiver))
    )
  }

  def mkDefaultLogger(statsReceiver: StatsReceiver): Logger = {
    mkDefaultLoggerFactory(statsReceiver)()
  }

}
