package com.tw ter.servo.repos ory

 mport com.tw ter.f nagle.mux.Cl entD scardedRequestExcept on
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.{CancelledConnect onExcept on, CancelledRequestExcept on}
 mport com.tw ter.servo.ut l.{Gate, SuccessRateTracker}
 mport com.tw ter.ut l.Throwables.RootCause
 mport java.ut l.concurrent.Cancellat onExcept on

object SuccessRateTrack ngRepos ory {

  /**
   * (successes, fa lures)
   */
  type SuccessRateObserver = ( nt,  nt) => Un 

  /**
   *  dent f es [[Throwable]]s that should not be counted as fa lures.
   *
   * T   s a total funct on  nstead of a part al funct on so   can rel ably recurse on  self
   * to f nd a root cause.
   */
  def  sCancellat on(t: Throwable): Boolean =
    t match {
      //   don't cons der CancelledRequestExcept ons or CancelledConnect onExcept ons to be
      // fa lures  n order not to tarn sh   success rate on upstream request cancellat ons.
      case _: CancelledRequestExcept on => true
      case _: CancelledConnect onExcept on => true
      // non-f nagle backends can throw Cancellat onExcept ons w n t  r futures are cancelled.
      case _: Cancellat onExcept on => true
      // Mux servers can return Cl entD scardedRequestExcept on.
      case _: Cl entD scardedRequestExcept on => true
      // Most of t se except ons can be wrapped  n com.tw ter.f nagle.Fa lure
      case RootCause(t) =>  sCancellat on(t)
      case _ => false
    }

  /**
   * Return a Success Rate (SR) track ng repos ory along w h t  gate controll ng  .
   *
   * @param stats Prov des ava lab l y gauge
   * @param ava lab l yFromSuccessRate funct on to calculate ava lab l y g ven SR
   * @param tracker strategy for track ng (usually recent) SR
   * @param should gnore don't count certa n except ons as fa lures, e.g. cancellat ons
   * @return tuple of (SR track ng repo, gate clos ng  f SR drops too far)
   */
  def w hGate[Q <: Seq[K], K, V](
    stats: StatsRece ver,
    ava lab l yFromSuccessRate: Double => Double,
    tracker: SuccessRateTracker,
    should gnore: Throwable => Boolean =  sCancellat on
  ): (KeyValueRepos ory[Q, K, V] => KeyValueRepos ory[Q, K, V], Gate[Un ]) = {
    val successRateGate = tracker.observedAva lab l yGate(ava lab l yFromSuccessRate, stats)

    (new SuccessRateTrack ngRepos ory[Q, K, V](_, tracker.record, should gnore), successRateGate)
  }
}

/**
 * A KeyValueRepos ory that prov des feedback on query success rate to
 * a SuccessRateObserver.  Both found and not found are cons dered successful
 * responses, wh le fa lures are not. Cancellat ons are  gnored by default.
 */
class SuccessRateTrack ngRepos ory[Q <: Seq[K], K, V](
  underly ng: KeyValueRepos ory[Q, K, V],
  observer: SuccessRateTrack ngRepos ory.SuccessRateObserver,
  should gnore: Throwable => Boolean = SuccessRateTrack ngRepos ory. sCancellat on)
    extends KeyValueRepos ory[Q, K, V] {
  def apply(query: Q) =
    underly ng(query) onSuccess { kvr =>
      val non gnoredFa lures = kvr.fa led.values.foldLeft(0) {
        case (count, t)  f should gnore(t) => count
        case (count, _) => count + 1
      }
      observer(kvr.found.s ze + kvr.notFound.s ze, non gnoredFa lures)
    } onFa lure { t =>
       f (!should gnore(t)) {
        observer(0, query.s ze)
      }
    }
}
