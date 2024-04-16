package com.tw ter.product_m xer.shared_l brary.observer

 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.RollupStatsRece ver
 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l.CancelledExcept onExtractor
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Throwables
 mport com.tw ter.ut l.Try

/**
 *  lper funct ons to observe requests, success, fa lures, cancellat ons, except ons, and latency.
 * Supports nat ve funct ons and asynchronous operat ons.
 */
object Observer {
  val Requests = "requests"
  val Success = "success"
  val Fa lures = "fa lures"
  val Cancelled = "cancelled"
  val Latency = "latency_ms"

  /**
   *  lper funct on to observe a st ch
   *
   * @see [[St chObserver]]
   */
  def st ch[T](statsRece ver: StatsRece ver, scopes: Str ng*): St chObserver[T] =
    new St chObserver[T](statsRece ver, scopes)

  /**
   *  lper funct on to observe an arrow
   *
   * @see [[ArrowObserver]]
   */
  def arrow[ n, Out](statsRece ver: StatsRece ver, scopes: Str ng*): ArrowObserver[ n, Out] =
    new ArrowObserver[ n, Out](statsRece ver, scopes)

  /**
   *  lper funct on to observe a future
   *
   * @see [[FutureObserver]]
   */
  def future[T](statsRece ver: StatsRece ver, scopes: Str ng*): FutureObserver[T] =
    new FutureObserver[T](statsRece ver, scopes)

  /**
   *  lper funct on to observe a funct on
   *
   * @see [[Funct onObserver]]
   */
  def funct on[T](statsRece ver: StatsRece ver, scopes: Str ng*): Funct onObserver[T] =
    new Funct onObserver[T](statsRece ver, scopes)

  /**
   * [[St chObserver]] can record latency stats, success counters, and
   * deta led fa lure stats for t  results of a St ch computat on.
   */
  class St chObserver[T](
    overr de val statsRece ver: StatsRece ver,
    overr de val scopes: Seq[Str ng])
      extends Observer[T] {

    /**
     * Record stats for t  prov ded St ch.
     * T  result of t  computat on  s passed through.
     *
     * @note t  prov ded St ch must conta n t  parts that need to be t  d.
     *       Us ng t  on just t  result of t  computat on t  latency stat
     *       w ll be  ncorrect.
     */
    def apply(st ch: => St ch[T]): St ch[T] =
      St ch.t  (st ch).map(observe.tupled).lo rFromTry
  }

  /**
   * [[ArrowObserver]] can record t  latency stats, success counters, and
   * deta led fa lure stats for t  result of an Arrow computat on.
   * T  result of t  computat on  s passed through.
   */
  class ArrowObserver[ n, Out](
    overr de val statsRece ver: StatsRece ver,
    overr de val scopes: Seq[Str ng])
      extends Observer[Out] {

    /**
     * Returns a new Arrow that records stats w n  's run.
     * T  result of t  Arrow  s passed through.
     *
     * @note t  prov ded Arrow must conta n t  parts that need to be t  d.
     *       Us ng t  on just t  result of t  computat on t  latency stat
     *       w ll be  ncorrect.
     */
    def apply(arrow: Arrow[ n, Out]): Arrow[ n, Out] =
      Arrow.t  (arrow).map(observe.tupled).lo rFromTry
  }

  /**
   * [[FutureObserver]] can record latency stats, success counters, and
   * deta led fa lure stats for t  results of a Future computat on.
   */
  class FutureObserver[T](
    overr de val statsRece ver: StatsRece ver,
    overr de val scopes: Seq[Str ng])
      extends Observer[T] {

    /**
     * Record stats for t  prov ded Future.
     * T  result of t  computat on  s passed through.
     *
     * @note t  prov ded Future must conta n t  parts that need to be t  d.
     *       Us ng t  on just t  result of t  computat on t  latency stat
     *       w ll be  ncorrect.
     */
    def apply(future: => Future[T]): Future[T] =
      Stat
        .t  Future(latencyStat)(future)
        .onSuccess(observeSuccess)
        .onFa lure(observeFa lure)
  }

  /**
   * [[Funct onObserver]] can record latency stats, success counters, and
   * deta led fa lure stats for t  results of a computat on computat on.
   */
  class Funct onObserver[T](
    overr de val statsRece ver: StatsRece ver,
    overr de val scopes: Seq[Str ng])
      extends Observer[T] {

    /**
     * Record stats for t  prov ded computat on.
     * T  result of t  computat on  s passed through.
     *
     * @note t  prov ded computat on must conta n t  parts that need to be t  d.
     *       Us ng t  on just t  result of t  computat on t  latency stat
     *       w ll be  ncorrect.
     */
    def apply(f: => T): T = {
      Try(Stat.t  (latencyStat)(f))
        .onSuccess(observeSuccess)
        .onFa lure(observeFa lure)
        .apply()
    }
  }

  /** [[Observer]] prov des  thods for record ng latency, success, and fa lure stats */
  tra  Observer[T] {
    protected val statsRece ver: StatsRece ver

    /** Scopes that pref x all stats */
    protected val scopes: Seq[Str ng]

    pr vate val rollupStatsRece ver = new RollupStatsRece ver(statsRece ver.scope(scopes: _*))
    pr vate val requestsCounter: Counter = statsRece ver.counter(scopes :+ Requests: _*)
    pr vate val successCounter: Counter = statsRece ver.counter(scopes :+ Success: _*)

    // create t  stats so t  r  tr cs paths are always present but
    // defer to t  [[RollupStatsRece ver]] to  ncre nt t se stats
    rollupStatsRece ver.counter(Fa lures)
    rollupStatsRece ver.counter(Cancelled)

    /** Ser al ze a throwable and  's causes  nto a seq of Str ngs for scop ng  tr cs */
    protected def ser al zeThrowable(throwable: Throwable): Seq[Str ng] =
      Throwables.mkStr ng(throwable)

    /** Used to record latency  n m ll seconds */
    protected val latencyStat: Stat = statsRece ver.stat(scopes :+ Latency: _*)

    /** Records t  latency from a [[Durat on]] */
    protected val observeLatency: Durat on => Un  = { latency =>
      latencyStat.add(latency. nM ll seconds)
    }

    /** Records successes */
    protected val observeSuccess: T => Un  = { _ =>
      requestsCounter. ncr()
      successCounter. ncr()
    }

    /** Records fa lures and fa lure deta ls */
    protected val observeFa lure: Throwable => Un  = {
      case CancelledExcept onExtractor(throwable) =>
        requestsCounter. ncr()
        rollupStatsRece ver.counter(Cancelled +: ser al zeThrowable(throwable): _*). ncr()
      case throwable =>
        requestsCounter. ncr()
        rollupStatsRece ver.counter(Fa lures +: ser al zeThrowable(throwable): _*). ncr()
    }

    /** Records t  latency, successes, and fa lures */
    protected val observe: (Try[T], Durat on) => Try[T] =
      (response: Try[T], runDurat on: Durat on) => {
        observeLatency(runDurat on)
        response
          .onSuccess(observeSuccess)
          .onFa lure(observeFa lure)
      }
  }
}
