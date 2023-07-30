package com.X.tweetypie.federated
package columns

import com.X.finagle.stats.NullStatsReceiver
import com.X.tweetypie.StatsReceiver
import com.X.util.logging.Logger

object TrackingId {
  private[this] val log = Logger(getClass)

  def parse(s: String, statsReceiver: StatsReceiver = NullStatsReceiver): Option[Long] = {
    val trackingStats = statsReceiver.scope("tracking_id_parser")

    val parsedCountCounter = trackingStats.scope("parsed").counter("count")
    val parseFailedCounter = trackingStats.scope("parse_failed").counter("count")
    Option(s).map(_.trim).filter(_.nonEmpty).flatMap { idStr =>
      try {
        val id = java.lang.Long.parseLong(idStr, 16)
        parsedCountCounter.incr()
        Some(id)
      } catch {
        case _: NumberFormatException =>
          parseFailedCounter.incr()
          log.warn(s"invalid tracking ID: '$s'")
          None
      }
    }
  }
}
