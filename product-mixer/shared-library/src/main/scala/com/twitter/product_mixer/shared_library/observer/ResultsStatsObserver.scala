package com.tw ter.product_m xer.shared_l brary.observer

 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.shared_l brary.observer.Observer.ArrowObserver
 mport com.tw ter.product_m xer.shared_l brary.observer.Observer.Funct onObserver
 mport com.tw ter.product_m xer.shared_l brary.observer.Observer.FutureObserver
 mport com.tw ter.product_m xer.shared_l brary.observer.Observer.Observer
 mport com.tw ter.product_m xer.shared_l brary.observer.Observer.St chObserver
 mport com.tw ter.product_m xer.shared_l brary.observer.ResultsObserver.ResultsObserver
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Try

/**
 *  lper funct ons to observe requests, successes, fa lures, cancellat ons, except ons, latency,
 * and result counts and t  -ser es stats. Supports nat ve funct ons and asynchronous operat ons.
 *
 * Note that s nce t  -ser es stats are expens ve to compute (relat ve to counters), prefer
 * [[ResultsObserver]] unless a t  -ser es stat  s needed.
 */
object ResultsStatsObserver {
  val S ze = "s ze"

  /**
   *  lper funct on to observe a st ch and result counts and t  -ser es stats
   */
  def st chResultsStats[T](
    s ze: T =>  nt,
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): St chResultsStatsObserver[T] = {
    new St chResultsStatsObserver[T](s ze, statsRece ver, scopes)
  }

  /**
   *  lper funct on to observe a st ch and traversable (e.g. Seq, Set) result counts and
   * t  -ser es stats
   */
  def st chResultsStats[T <: TraversableOnce[_]](
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): St chResultsStatsObserver[T] = {
    new St chResultsStatsObserver[T](_.s ze, statsRece ver, scopes)
  }

  /**
   *  lper funct on to observe an arrow and result counts and t  -ser es stats
   */
  def arrowResultsStats[T, U](
    s ze: U =>  nt,
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): ArrowResultsStatsObserver[T, U] = {
    new ArrowResultsStatsObserver[T, U](s ze, statsRece ver, scopes)
  }

  /**
   *  lper funct on to observe an arrow and traversable (e.g. Seq, Set) result counts and
   * * t  -ser es stats
   */
  def arrowResultsStats[T, U <: TraversableOnce[_]](
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): ArrowResultsStatsObserver[T, U] = {
    new ArrowResultsStatsObserver[T, U](_.s ze, statsRece ver, scopes)
  }

  /**
   *  lper funct on to observe an arrow and result counts
   *
   * @see [[Transform ngArrowResultsStatsObserver]]
   */
  def transform ngArrowResultsStats[ n, Out, Transfor d](
    transfor r: Out => Try[Transfor d],
    s ze: Transfor d =>  nt,
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): Transform ngArrowResultsStatsObserver[ n, Out, Transfor d] = {
    new Transform ngArrowResultsStatsObserver[ n, Out, Transfor d](
      transfor r,
      s ze,
      statsRece ver,
      scopes)
  }

  /**
   *  lper funct on to observe an arrow and traversable (e.g. Seq, Set) result counts
   *
   * @see [[Transform ngArrowResultsStatsObserver]]
   */
  def transform ngArrowResultsStats[ n, Out, Transfor d <: TraversableOnce[_]](
    transfor r: Out => Try[Transfor d],
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): Transform ngArrowResultsStatsObserver[ n, Out, Transfor d] = {
    new Transform ngArrowResultsStatsObserver[ n, Out, Transfor d](
      transfor r,
      _.s ze,
      statsRece ver,
      scopes)
  }

  /**
   *  lper funct on to observe a future and result counts and t  -ser es stats
   */
  def futureResultsStats[T](
    s ze: T =>  nt,
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): FutureResultsStatsObserver[T] = {
    new FutureResultsStatsObserver[T](s ze, statsRece ver, scopes)
  }

  /**
   *  lper funct on to observe a future and traversable (e.g. Seq, Set) result counts and
   * t  -ser es stats
   */
  def futureResultsStats[T <: TraversableOnce[_]](
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): FutureResultsStatsObserver[T] = {
    new FutureResultsStatsObserver[T](_.s ze, statsRece ver, scopes)
  }

  /**
   *  lper funct on observe a funct on and result counts and t  -ser es stats
   */
  def funct onResultsStats[T](
    s ze: T =>  nt,
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): Funct onResultsStatsObserver[T] = {
    new Funct onResultsStatsObserver[T](s ze, statsRece ver, scopes)
  }

  /**
   *  lper funct on observe a funct on and traversable (e.g. Seq, Set) result counts and
   * t  -ser es stats
   */
  def funct onResultsStats[T <: TraversableOnce[_]](
    statsRece ver: StatsRece ver,
    scopes: Str ng*
  ): Funct onResultsStatsObserver[T] = {
    new Funct onResultsStatsObserver[T](_.s ze, statsRece ver, scopes)
  }

  class St chResultsStatsObserver[T](
    overr de val s ze: T =>  nt,
    overr de val statsRece ver: StatsRece ver,
    overr de val scopes: Seq[Str ng])
      extends St chObserver[T](statsRece ver, scopes)
      w h ResultsStatsObserver[T] {

    overr de def apply(st ch: => St ch[T]): St ch[T] =
      super
        .apply(st ch)
        .onSuccess(observeResults)
  }

  class ArrowResultsStatsObserver[T, U](
    overr de val s ze: U =>  nt,
    overr de val statsRece ver: StatsRece ver,
    overr de val scopes: Seq[Str ng])
      extends ArrowObserver[T, U](statsRece ver, scopes)
      w h ResultsStatsObserver[U] {

    overr de def apply(arrow: Arrow[T, U]): Arrow[T, U] =
      super
        .apply(arrow)
        .onSuccess(observeResults)
  }

  /**
   * [[Transform ngArrowResultsStatsObserver]] funct ons l ke an [[ArrowObserver]] except
   * that   transforms t  result us ng [[transfor r]] before record ng stats.
   *
   * T  or g nal non-transfor d result  s t n returned.
   */
  class Transform ngArrowResultsStatsObserver[ n, Out, Transfor d](
    val transfor r: Out => Try[Transfor d],
    overr de val s ze: Transfor d =>  nt,
    overr de val statsRece ver: StatsRece ver,
    overr de val scopes: Seq[Str ng])
      extends Observer[Transfor d]
      w h ResultsStatsObserver[Transfor d] {

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

  class FutureResultsStatsObserver[T](
    overr de val s ze: T =>  nt,
    overr de val statsRece ver: StatsRece ver,
    overr de val scopes: Seq[Str ng])
      extends FutureObserver[T](statsRece ver, scopes)
      w h ResultsStatsObserver[T] {

    overr de def apply(future: => Future[T]): Future[T] =
      super
        .apply(future)
        .onSuccess(observeResults)
  }

  class Funct onResultsStatsObserver[T](
    overr de val s ze: T =>  nt,
    overr de val statsRece ver: StatsRece ver,
    overr de val scopes: Seq[Str ng])
      extends Funct onObserver[T](statsRece ver, scopes)
      w h ResultsStatsObserver[T] {

    overr de def apply(f: => T): T = {
      observeResults(super.apply(f))
    }
  }

  tra  ResultsStatsObserver[T] extends ResultsObserver[T] {
    pr vate val s zeStat: Stat = statsRece ver.stat(scopes :+ S ze: _*)

    protected overr de def observeResults(results: T): T = {
      val resultsS ze = s ze(results)
      s zeStat.add(resultsS ze)
      observeResultsW hS ze(results, resultsS ze)
    }
  }
}
