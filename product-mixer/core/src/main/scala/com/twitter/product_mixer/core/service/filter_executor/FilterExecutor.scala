package com.tw ter.product_m xer.core.serv ce.f lter_executor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.f lter_executor.F lterExecutor.F lterState
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.st ch.Arrow. so
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.collect on. mmutable.Queue

/**
 * Appl es a `Seq[F lter]`  n sequent al order.
 * Returns t  results and a deta led Seq of each f lter's results (for debugg ng / co rence).
 *
 * Note that each success ve f lter  s only passed t  'kept' Seq from t  prev ous f lter, not t  full
 * set of cand dates.
 */
@S ngleton
class F lterExecutor @ nject() (overr de val statsRece ver: StatsRece ver) extends Executor {

  pr vate val Kept = "kept"
  pr vate val Removed = "removed"

  def arrow[Query <: P pel neQuery, Cand date <: Un versalNoun[Any]](
    f lters: Seq[F lter[Query, Cand date]],
    context: Executor.Context
  ): Arrow[(Query, Seq[Cand dateW hFeatures[Cand date]]), F lterExecutorResult[Cand date]] = {

    val f lterArrows = f lters.map(get soArrowForF lter(_, context))
    val comb nedArrow =  soArrowsSequent ally(f lterArrows)

    Arrow
      .map[(Query, Seq[Cand dateW hFeatures[Cand date]]), F lterState[Query, Cand date]] {
        case (query, f lterCand dates) =>
          // transform t   nput to t   n  al state of a `F lterExecutorResult`
          val  n  alF lterExecutorResult =
            F lterExecutorResult(f lterCand dates.map(_.cand date), Queue.empty)
          val allCand dates: Map[Cand date, Cand dateW hFeatures[Cand date]] =
            f lterCand dates.map { fc =>
              (fc.cand date, fc)
            }.toMap

          F lterState(query, allCand dates,  n  alF lterExecutorResult)
      }
      .flatMapArrow(comb nedArrow)
      .map {
        case F lterState(_, _, f lterExecutorResult) =>
          f lterExecutorResult.copy( nd v dualF lterResults =
            // mater al ze t  Queue  nto a L st for faster future  erat ons
            f lterExecutorResult. nd v dualF lterResults.toL st)
      }

  }

  /**
   * Adds f lter spec f c stats, gener c [[wrapComponentW hExecutorBookkeep ng]] stats, and wraps w h fa lure handl ng
   *
   *  f t  f lter  s a [[Cond  onally]] ensures that   dont record stats  f  s turned off
   *
   * @note For performance, t  [[F lterExecutorResult. nd v dualF lterResults]]  s bu ld backwards - t   ad be ng t  most recent result.
   * @param f lter t  f lter to make an [[Arrow]] out of
   * @param context t  [[Executor.Context]] for t  p pel ne t   s a part of
   */
  pr vate def get soArrowForF lter[Query <: P pel neQuery, Cand date <: Un versalNoun[Any]](
    f lter: F lter[Query, Cand date],
    context: Executor.Context
  ):  so[F lterState[Query, Cand date]] = {
    val broadcastStatsRece ver =
      Executor.broadcastStatsRece ver(context, f lter. dent f er, statsRece ver)

    val keptCounter = broadcastStatsRece ver.counter(Kept)
    val removedCounter = broadcastStatsRece ver.counter(Removed)

    val f lterArrow = Arrow.flatMap[
      (Query, Seq[Cand dateW hFeatures[Cand date]]),
      F lterExecutor nd v dualResult[Cand date]
    ] {
      case (query, cand dates) =>
        f lter.apply(query, cand dates).map { result =>
          F lterExecutor nd v dualResult(
             dent f er = f lter. dent f er,
            kept = result.kept,
            removed = result.removed)
        }
    }

    val observedArrow: Arrow[
      (Query, Seq[Cand dateW hFeatures[Cand date]]),
      F lterExecutor nd v dualResult[
        Cand date
      ]
    ] = wrapComponentW hExecutorBookkeep ng(
      context = context,
      currentComponent dent f er = f lter. dent f er,
      onSuccess = { result: F lterExecutor nd v dualResult[Cand date] =>
        keptCounter. ncr(result.kept.s ze)
        removedCounter. ncr(result.removed.s ze)
      }
    )(
      f lterArrow
    )

    val cond  onallyRunArrow: Arrow[
      (Query, Seq[Cand dateW hFeatures[Cand date]]),
       nd v dualF lterResults[
        Cand date
      ]
    ] =
      f lter match {
        case f lter: F lter[Query, Cand date] w h Cond  onally[
              F lter. nput[Query, Cand date] @unc cked
            ] =>
          Arrow. felse(
            {
              case (query, cand dates) =>
                f lter.only f(F lter. nput(query, cand dates))
            },
            observedArrow,
            Arrow.value(Cond  onalF lterD sabled(f lter. dent f er))
          )
        case _ => observedArrow
      }

    // return an ` so` arrow for eas er compos  on later
    Arrow
      .z pW hArg(
        Arrow
          .map[F lterState[Query, Cand date], (Query, Seq[Cand dateW hFeatures[Cand date]])] {
            case F lterState(query, cand dateToFeaturesMap, F lterExecutorResult(result, _)) =>
              (query, result.flatMap(cand dateToFeaturesMap.get))
          }.andT n(cond  onallyRunArrow))
      .map {
        case (
              F lterState(query, allCand dates, f lterExecutorResult),
              f lterResult: F lterExecutor nd v dualResult[Cand date]) =>
          val resultW hCurrentPrepended =
            f lterExecutorResult. nd v dualF lterResults :+ f lterResult
          val newF lterExecutorResult = F lterExecutorResult(
            result = f lterResult.kept,
             nd v dualF lterResults = resultW hCurrentPrepended)
          F lterState(query, allCand dates, newF lterExecutorResult)

        case (f lterState, f lterD sabledResult: Cond  onalF lterD sabled) =>
          f lterState.copy(
            executorResult = f lterState.executorResult.copy(
               nd v dualF lterResults =
                f lterState.executorResult. nd v dualF lterResults :+ f lterD sabledResult
            ))
      }
  }
}

object F lterExecutor {

  /**
   * F lterState  s an  nternal representat on of t  state that  s passed bet en each  nd v dual f lter arrow.
   *
   * @param query: T  query
   * @param cand dateToFeaturesMap: A lookup mapp ng from Cand date -> F lterCand date, to rebu ld t   nputs qu ckly for t  next f lter
   * @param executorResult: T   n-progress executor result
   */
  pr vate case class F lterState[Query <: P pel neQuery, Cand date <: Un versalNoun[Any]](
    query: Query,
    cand dateToFeaturesMap: Map[Cand date, Cand dateW hFeatures[Cand date]],
    executorResult: F lterExecutorResult[Cand date])
}
