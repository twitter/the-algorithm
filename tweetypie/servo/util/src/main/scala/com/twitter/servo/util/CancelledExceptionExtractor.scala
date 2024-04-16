package com.tw ter.servo.ut l

 mport com.tw ter.f nagle.mux.stats.MuxCancelledCategor zer
 mport com.tw ter.f nagle.stats.CancelledCategor zer
 mport com.tw ter.ut l.FutureCancelledExcept on
 mport com.tw ter.ut l.Throwables.RootCause

/**
 *  lper that consol dates var ous ways (nested and top level) cancel except ons can be detected.
 */
object CancelledExcept onExtractor {
  def unapply(e: Throwable): Opt on[Throwable] = {
    e match {
      case _: FutureCancelledExcept on => So (e)
      case MuxCancelledCategor zer(cause) => So (cause)
      case CancelledCategor zer(cause) => So (cause)
      case RootCause(CancelledExcept onExtractor(cause)) => So (cause)
      case _ => None
    }
  }
}
