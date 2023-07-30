package com.X.servo.util

import com.X.finagle.mux.stats.MuxCancelledCategorizer
import com.X.finagle.stats.CancelledCategorizer
import com.X.util.FutureCancelledException
import com.X.util.Throwables.RootCause

/**
 * Helper that consolidates various ways (nested and top level) cancel exceptions can be detected.
 */
object CancelledExceptionExtractor {
  def unapply(e: Throwable): Option[Throwable] = {
    e match {
      case _: FutureCancelledException => Some(e)
      case MuxCancelledCategorizer(cause) => Some(cause)
      case CancelledCategorizer(cause) => Some(cause)
      case RootCause(CancelledExceptionExtractor(cause)) => Some(cause)
      case _ => None
    }
  }
}
