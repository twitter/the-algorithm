package com.tw ter.product_m xer.shared_l brary.observer

 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.shared_l brary.observer.Observer.ArrowObserver
 mport com.tw ter.product_m xer.shared_l brary.observer.Observer.Funct onObserver
 mport com.tw ter.product_m xer.shared_l brary.observer.Observer.FutureObserver
 mport com.tw ter.product_m xer.shared_l brary.observer.Observer.Observer
 mport com.tw ter.product_m xer.shared_l brary.observer.Observer.St chObserver
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Try

/**
 *  lper funct ons to observe requests, successes, fa lures, cancellat ons, except ons, latency,
 * and result counts. Supports nat ve funct ons and asynchronous operat ons.
 */
object ResultsObserver {
  val Total = "total"
  val Found = "found"
  val NotFound = "not_found"

  /**
   *  lper funct on to observe a st ch and result counts
   *
   * @see [[St chResultsObserver]]
   */
  def st chResults[T](
    s ze: T =>  nt,
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): St chResultsObserver[T] = {
    new St chResultsObserver[T](s ze, statsRece ver, scopes)
  }

  /**
   *  lper funct on to observe a st ch and traversable (e.g. Seq, Set) result counts
   *
   * @see [[St chResultsObserver]]
   */
  def st chResults[T <: TraversableOnce[_]](
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): St chResultsObserver[T] = {
    new St chResultsObserver[T](_.s ze, statsRece ver, scopes)
  }

  /**
   *  lper funct on to observe an arrow and result counts
   *
   * @see [[ArrowResultsObserver]]
   */
  def arrowResults[ n, Out](
    s ze: Out =>  nt,
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): ArrowResultsObserver[ n, Out] = {
    new ArrowResultsObserver[ n, Out](s ze, statsRece ver, scopes)
  }

  /**
   *  lper funct on to observe an arrow and traversable (e.g. Seq, Set) result counts
   *
   * @see [[ArrowResultsObserver]]
   */
  def arrowResults[ n, Out <: TraversableOnce[_]](
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): ArrowResultsObserver[ n, Out] = {
    new ArrowResultsObserver[ n, Out](_.s ze, statsRece ver, scopes)
  }

  /**
   *  lper funct on to observe an arrow and result counts
   *
   * @see [[Transform ngArrowResultsObserver]]
   */
  def transform ngArrowResults[ n, Out, Transfor d](
    transfor r: Out => Try[Transfor d],
    s ze: Transfor d =>  nt,
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): Transform ngArrowResultsObserver[ n, Out, Transfor d] = {
    new Transform ngArrowResultsObserver[ n, Out, Transfor d](
      transfor r,
      s ze,
      statsRece ver,
      scopes)
  }

  /**
   *  lper funct on to observe an arrow and traversable (e.g. Seq, Set) result counts
   *
   * @see [[Transform ngArrowResultsObserver]]
   */
  def transform ngArrowResults[ n, Out, Transfor d <: TraversableOnce[_]](
    transfor r: Out => Try[Transfor d],
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): Transform ngArrowResultsObserver[ n, Out, Transfor d] = {
    new Transform ngArrowResultsObserver[ n, Out, Transfor d](
      transfor r,
      _.s ze,
      statsRece ver,
      scopes)
  }

  /**
   *  lper funct on to observe a future and result counts
   *
   * @see [[FutureResultsObserver]]
   */
  def futureResults[T](
    s ze: T =>  nt,
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): FutureResultsObserver[T] = {
    new FutureResultsObserver[T](s ze, statsRece ver, scopes)
  }

  /**
   *  lper funct on to observe a future and traversable (e.g. Seq, Set) result counts
   *
   * @see [[FutureResultsObserver]]
   */
  def futureResults[T <: TraversableOnce[_]](
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): FutureResultsObserver[T] = {
    new FutureResultsObserver[T](_.s ze, statsRece ver, scopes)
  }

  /**
   *  lper funct on to observe a funct on and result counts
   *
   * @see [[Funct onResultsObserver]]
   */
  def funct onResults[T](
    s ze: T =>  nt,
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): Funct onResultsObserver[T] = {
    new Funct onResultsObserver[T](s ze, statsRece ver, scopes)
  }

  /**
   *  lper funct on to observe a funct on and traversable (e.g. Seq, Set) result counts
   *
   * @see [[Funct onResultsObserver]]
   */
  def funct onResults[T <: TraversableOnce[_]](
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): Funct onResultsObserver[T] = {
    new Funct onResultsObserver[T](_.s ze, statsRece ver, scopes)
  }

  /** [[St chObserver]] that also records result s ze */
  class St chResultsObserver[T](
    overr de val s ze: T =>  nt,
    overr de val statsRece ver: StatsRece ver,
    overr de val scopes: Seq[Str ng])
      extends St chObserver[T](statsRece ver, scopes)
      w h ResultsObserver[T] {

    overr de def apply(st ch: => St ch[T]): St ch[T] =
      super
        .apply(st ch)
        .onSuccess(observeResults)
  }

  /** [[ArrowObserver]] that also records result s ze */
  class ArrowResultsObserver[ n, Out](
    overr de val s ze: Out =>  nt,
    overr de val statsRece ver: StatsRece ver,
    overr de val scopes: Seq[Str ng])
      extends ArrowObserver[ n, Out](statsRece ver, scopes)
      w h ResultsObserver[Out] {

    overr de def apply(arrow: Arrow[ n, Out]): Arrow[ n, Out] =
      super
        .apply(arrow)
        .onSuccess(observeResults)
  }

  /**
   * [[Transform ngArrowResultsObserver]] funct ons l ke an [[ArrowObserver]] except
   * that   transforms t  result us ng [[transfor r]] before record ng stats.
   *
   * T  or g nal non-transfor d result  s t n returned.
   */
  class Transform ngArrowResultsObserver[ n, Out, Transfor d](
    val transfor r: Out => Try[Transfor d],
    overr de val s ze: Transfor d =>  nt,
    overr de val statsRece ver: StatsRece ver,
    overr de val scopes: Seq[Str ng])
      extends Observer[Transfor d]
      w h ResultsObserver[Transfor d] {

    /**
     * Returns a new Arrow that records stats on t  result after apply ng [[transfor r]] w n  's run.
     * T  or g nal, non-transfor d, result of t  Arrow  s passed through.
     *
     * @note t  prov ded Arrow must conta n t  parts that need to be t  d.
     *       Us ng t  on just t  result of t  computat on t  latency stat
     *       w ll be  ncorrect.
     */
    def apply(arrow: Arrow[ n, Out]): Arrow[ n, Out] = {
      Arrow
        .t  (arrow)
        .map {
          case (response, st chRunDurat on) =>
            observe(response.flatMap(transfor r), st chRunDurat on)
              .onSuccess(observeResults)
            response
        }.lo rFromTry
    }
  }

  /** [[FutureObserver]] that also records result s ze */
  class FutureResultsObserver[T](
    overr de val s ze: T =>  nt,
    overr de val statsRece ver: StatsRece ver,
    overr de val scopes: Seq[Str ng])
      extends FutureObserver[T](statsRece ver, scopes)
      w h ResultsObserver[T] {

    overr de def apply(future: => Future[T]): Future[T] =
      super
        .apply(future)
        .onSuccess(observeResults)
  }

  /** [[Funct onObserver]] that also records result s ze */
  class Funct onResultsObserver[T](
    overr de val s ze: T =>  nt,
    overr de val statsRece ver: StatsRece ver,
    overr de val scopes: Seq[Str ng])
      extends Funct onObserver[T](statsRece ver, scopes)
      w h ResultsObserver[T] {

    overr de def apply(f: => T): T = observeResults(super.apply(f))
  }

  /** [[ResultsObserver]] prov des  thods for record ng stats for t  result s ze */
  tra  ResultsObserver[T] {
    protected val statsRece ver: StatsRece ver

    /** Scopes that pref x all stats */
    protected val scopes: Seq[Str ng]

    protected val totalCounter: Counter = statsRece ver.counter(scopes :+ Total: _*)
    protected val foundCounter: Counter = statsRece ver.counter(scopes :+ Found: _*)
    protected val notFoundCounter: Counter = statsRece ver.counter(scopes :+ NotFound: _*)

    /** g ven a [[T]] returns  's s ze. */
    protected val s ze: T =>  nt

    /** Records t  s ze of t  `results` us ng [[s ze]] and return t  or g nal value. */
    protected def observeResults(results: T): T = {
      val resultsS ze = s ze(results)
      observeResultsW hS ze(results, resultsS ze)
    }

    /**
     * Records t  `resultsS ze` and returns t  `results`
     *
     * T   s useful  f t  s ze  s already ava lable and  s expens ve to calculate.
     */
    protected def observeResultsW hS ze(results: T, resultsS ze:  nt): T = {
       f (resultsS ze > 0) {
        totalCounter. ncr(resultsS ze)
        foundCounter. ncr()
      } else {
        notFoundCounter. ncr()
      }
      results
    }
  }
}
