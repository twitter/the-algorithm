package com.tw ter.follow_recom ndat ons.common.base
 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorObserver
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Stopwatch
 mport java.ut l.concurrent.T  Un 
 mport scala.ut l.control.NonFatal

object StatsUt l {
  val LatencyNa  = "latency_ms"
  val RequestNa  = "requests"
  val SuccessNa  = "success"
  val Fa lureNa  = "fa lures"
  val ResultsNa  = "results"
  val ResultsStat = "results_stat"
  val EmptyResultsNa  = "empty"
  val NonEmptyResultsNa  = "non_empty"
  val Val dCount = "val d"
  val  nval dCount = " nval d"
  val  nval dHasReasons = "has_reasons"
  val Reasons = "reasons"
  val Qual yFactorStat = "qual y_factor_stat"
  val Qual yFactorCounts = "qual y_factor_counts"

  /**
   *  lper funct on for t m ng a st ch, return ng t  or g nal st ch.
   */
  def prof leSt ch[T](st ch: St ch[T], stat: StatsRece ver): St ch[T] = {

    St ch
      .t  (st ch)
      .map {
        case (response, st chRunDurat on) =>
          stat.counter(RequestNa ). ncr()
          stat.stat(LatencyNa ).add(st chRunDurat on. nM ll seconds)
          response
            .onSuccess { _ => stat.counter(SuccessNa ). ncr() }
            .onFa lure { e =>
              stat.counter(Fa lureNa ). ncr()
              stat.scope(Fa lureNa ).counter(getCleanClassNa (e)). ncr()
            }
      }
      .lo rFromTry
  }

  /**
   *  lper funct on for t m ng an arrow, return ng t  or g nal arrow.
   */
  def prof leArrow[T, U](arrow: Arrow[T, U], stat: StatsRece ver): Arrow[T, U] = {

    Arrow
      .t  (arrow)
      .map {
        case (response, st chRunDurat on) =>
          stat.counter(RequestNa ). ncr()
          stat.stat(LatencyNa ).add(st chRunDurat on. nM ll seconds)
          response
            .onSuccess { _ => stat.counter(SuccessNa ). ncr() }
            .onFa lure { e =>
              stat.counter(Fa lureNa ). ncr()
              stat.scope(Fa lureNa ).counter(getCleanClassNa (e)). ncr()
            }
      }
      .lo rFromTry
  }

  /**
   *  lper funct on to count and track t  d str but on of results
   */
  def prof leResults[T](results: T, stat: StatsRece ver, s ze: T =>  nt): T = {
    val numResults = s ze(results)
    stat.counter(ResultsNa ). ncr(numResults)
     f (numResults == 0) {
      stat.counter(EmptyResultsNa ). ncr()
      results
    } else {
      stat.stat(ResultsStat).add(numResults)
      stat.counter(NonEmptyResultsNa ). ncr()
      results
    }
  }

  /**
   *  lper funct on to count and track t  d str but on of a l st of results
   */
  def prof leSeqResults[T](results: Seq[T], stat: StatsRece ver): Seq[T] = {
    prof leResults[Seq[T]](results, stat, _.s ze)
  }

  /**
   *  lper funct on for t m ng a st ch and count t  number of results, return ng t  or g nal st ch.
   */
  def prof leSt chResults[T](st ch: St ch[T], stat: StatsRece ver, s ze: T =>  nt): St ch[T] = {
    prof leSt ch(st ch, stat).onSuccess { results => prof leResults(results, stat, s ze) }
  }

  /**
   *  lper funct on for t m ng an arrow and count t  number of results, return ng t  or g nal arrow.
   */
  def prof leArrowResults[T, U](
    arrow: Arrow[T, U],
    stat: StatsRece ver,
    s ze: U =>  nt
  ): Arrow[T, U] = {
    prof leArrow(arrow, stat).onSuccess { results => prof leResults(results, stat, s ze) }
  }

  /**
   *  lper funct on for t m ng a st ch and count a seq of results, return ng t  or g nal st ch.
   */
  def prof leSt chSeqResults[T](st ch: St ch[Seq[T]], stat: StatsRece ver): St ch[Seq[T]] = {
    prof leSt chResults[Seq[T]](st ch, stat, _.s ze)
  }

  /**
   *  lper funct on for t m ng a st ch and count opt onal results, return ng t  or g nal st ch.
   */
  def prof leSt chOpt onalResults[T](
    st ch: St ch[Opt on[T]],
    stat: StatsRece ver
  ): St ch[Opt on[T]] = {
    prof leSt chResults[Opt on[T]](st ch, stat, _.s ze)
  }

  /**
   *  lper funct on for t m ng a st ch and count a map of results, return ng t  or g nal st ch.
   */
  def prof leSt chMapResults[K, V](
    st ch: St ch[Map[K, V]],
    stat: StatsRece ver
  ): St ch[Map[K, V]] = {
    prof leSt chResults[Map[K, V]](st ch, stat, _.s ze)
  }

  def getCleanClassNa (obj: Object): Str ng =
    obj.getClass.getS mpleNa .str pSuff x("$")

  /**
   *  lper funct on for t m ng a st ch and count a l st of Pred cateResult
   */
  def prof lePred cateResults(
    pred cateResult: St ch[Seq[Pred cateResult]],
    statsRece ver: StatsRece ver
  ): St ch[Seq[Pred cateResult]] = {
    prof leSt ch[Seq[Pred cateResult]](
      pred cateResult,
      statsRece ver
    ).onSuccess {
      _.map {
        case Pred cateResult.Val d =>
          statsRece ver.counter(Val dCount). ncr()
        case Pred cateResult. nval d(reasons) =>
          statsRece ver.counter( nval dCount). ncr()
          reasons.map { f lterReason =>
            statsRece ver.counter( nval dHasReasons). ncr()
            statsRece ver.scope(Reasons).counter(f lterReason.reason). ncr()
          }
      }
    }
  }

  /**
   *  lper funct on for t m ng a st ch and count  nd v dual Pred cateResult
   */
  def prof lePred cateResult(
    pred cateResult: St ch[Pred cateResult],
    statsRece ver: StatsRece ver
  ): St ch[Pred cateResult] = {
    prof lePred cateResults(
      pred cateResult.map(Seq(_)),
      statsRece ver
    ).map(_. ad)
  }

  /**
   *  lper funct on for t m ng an arrow and count a l st of Pred cateResult
   */
  def prof lePred cateResults[Q](
    pred cateResult: Arrow[Q, Seq[Pred cateResult]],
    statsRece ver: StatsRece ver
  ): Arrow[Q, Seq[Pred cateResult]] = {
    prof leArrow[Q, Seq[Pred cateResult]](
      pred cateResult,
      statsRece ver
    ).onSuccess {
      _.map {
        case Pred cateResult.Val d =>
          statsRece ver.counter(Val dCount). ncr()
        case Pred cateResult. nval d(reasons) =>
          statsRece ver.counter( nval dCount). ncr()
          reasons.map { f lterReason =>
            statsRece ver.counter( nval dHasReasons). ncr()
            statsRece ver.scope(Reasons).counter(f lterReason.reason). ncr()
          }
      }
    }
  }

  /**
   *  lper funct on for t m ng an arrow and count  nd v dual Pred cateResult
   */
  def prof lePred cateResult[Q](
    pred cateResult: Arrow[Q, Pred cateResult],
    statsRece ver: StatsRece ver
  ): Arrow[Q, Pred cateResult] = {
    prof lePred cateResults(
      pred cateResult.map(Seq(_)),
      statsRece ver
    ).map(_. ad)
  }

  /**
   *  lper funct on for t m ng a st ch code block
   */
  def prof leSt chSeqResults[T](
    stats: StatsRece ver
  )(
    block: => St ch[Seq[T]]
  ): St ch[Seq[T]] = {
    stats.counter(RequestNa ). ncr()
    prof leSt ch(stats.stat(LatencyNa ), T  Un .M LL SECONDS) {
      block onSuccess { r =>
         f (r. sEmpty) stats.counter(EmptyResultsNa ). ncr()
        stats.stat(ResultsStat).add(r.s ze)
      } onFa lure { e =>
        {
          stats.counter(Fa lureNa ). ncr()
          stats.scope(Fa lureNa ).counter(e.getClass.getNa ). ncr()
        }
      }
    }
  }

  /**
   * T   a g ven asynchronous `f` us ng t  g ven `un `.
   */
  def prof leSt ch[A](stat: Stat, un : T  Un )(f: => St ch[A]): St ch[A] = {
    val start = Stopwatch.t  Nanos()
    try {
      f.respond { _ => stat.add(un .convert(Stopwatch.t  Nanos() - start, T  Un .NANOSECONDS)) }
    } catch {
      case NonFatal(e) =>
        stat.add(un .convert(Stopwatch.t  Nanos() - start, T  Un .NANOSECONDS))
        St ch.except on(e)
    }
  }

  def observeSt chQual yFactor[T](
    st ch: St ch[T],
    qual yFactorObserverOpt on: Opt on[Qual yFactorObserver],
    statsRece ver: StatsRece ver
  ): St ch[T] = {
    qual yFactorObserverOpt on
      .map { observer =>
        St ch
          .t  (st ch)
          .map {
            case (response, st chRunDurat on) =>
              observer(response, st chRunDurat on)
              val qfVal = observer.qual yFactor.currentValue.floatValue() * 10000
              statsRece ver.counter(Qual yFactorCounts). ncr()
              statsRece ver
                .stat(Qual yFactorStat)
                .add(qfVal)
              response
          }
          .lo rFromTry
      }.getOrElse(st ch)
  }
}
