package com.twitter.servo.util

import com.twitter.finagle.mutwitter.stats.MutwitterCancelledCategorizer
import com.twitter.finagle.stats.CancelledCategorizer
import com.twitter.util.FutureCancelledEtwitterception
import com.twitter.util.Throwables.RootCause

/**
 * Helper that consolidates various ways (nested and top level) cancel etwitterceptions can be detected.
 */
object CancelledEtwitterceptionEtwittertractor {
  def unapply(e: Throwable): Option[Throwable] = {
    e match {
      case _: FutureCancelledEtwitterception => Some(e)
      case MutwitterCancelledCategorizer(cause) => Some(cause)
      case CancelledCategorizer(cause) => Some(cause)
      case RootCause(CancelledEtwitterceptionEtwittertractor(cause)) => Some(cause)
      case _ => None
    }
  }
}
