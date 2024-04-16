package com.tw ter.product_m xer.core.serv ce.scor ng_p pel ne_executor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scor ngP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.Fa lOpenPol cy
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure. llegalStateFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.scor ng.Scor ngP pel ne
 mport com.tw ter.product_m xer.core.p pel ne.scor ng.Scor ngP pel neResult
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorObserver
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.scor ng_p pel ne_executor.Scor ngP pel neExecutor.Scor ngP pel neState
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.st ch.Arrow. so
 mport com.tw ter.ut l.logg ng.Logg ng

 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.collect on. mmutable.Queue

@S ngleton
class Scor ngP pel neExecutor @ nject() (overr de val statsRece ver: StatsRece ver)
    extends Executor
    w h Logg ng {
  def arrow[Query <: P pel neQuery, Cand date <: Un versalNoun[Any]](
    p pel nes: Seq[Scor ngP pel ne[Query, Cand date]],
    context: Executor.Context,
    defaultFa lOpenPol cy: Fa lOpenPol cy,
    fa lOpenPol c es: Map[Scor ngP pel ne dent f er, Fa lOpenPol cy],
    qual yFactorObserverByP pel ne: Map[Component dent f er, Qual yFactorObserver],
  ): Arrow[Scor ngP pel neExecutor. nputs[Query], Scor ngP pel neExecutorResult[Cand date]] = {
    val scor ngP pel neArrows = p pel nes.map { p pel ne =>
      val fa lOpenPol cy = fa lOpenPol c es.getOrElse(p pel ne. dent f er, defaultFa lOpenPol cy)
      val qual yFactorObserver = qual yFactorObserverByP pel ne.get(p pel ne. dent f er)

      get soArrowForScor ngP pel ne(
        p pel ne,
        context,
        fa lOpenPol cy,
        qual yFactorObserver
      )
    }
    val comb nedArrow =  soArrowsSequent ally(scor ngP pel neArrows)
    Arrow
      .map[Scor ngP pel neExecutor. nputs[Query], Scor ngP pel neState[Query, Cand date]] {
        case  nput =>
          Scor ngP pel neState(
             nput.query,
             nput. emCand datesW hDeta ls,
            Scor ngP pel neExecutorResult( nput. emCand datesW hDeta ls, Queue.empty))
      }.flatMapArrow(comb nedArrow).map { state =>
        state.executorResult.copy( nd v dualP pel neResults =
          // mater al ze t  Queue  nto a L st for faster future  erat ons
          state.executorResult. nd v dualP pel neResults.toL st)
      }
  }

  pr vate def get soArrowForScor ngP pel ne[
    Query <: P pel neQuery,
    Cand date <: Un versalNoun[Any]
  ](
    p pel ne: Scor ngP pel ne[Query, Cand date],
    context: Executor.Context,
    fa lOpenPol cy: Fa lOpenPol cy,
    qual yFactorObserver: Opt on[Qual yFactorObserver]
  ):  so[Scor ngP pel neState[Query, Cand date]] = {
    val p pel neArrow = Arrow
      .map[Scor ngP pel neState[Query, Cand date], Scor ngP pel ne. nputs[Query]] { state =>
        Scor ngP pel ne. nputs(state.query, state.allCand dates)
      }.flatMapArrow(p pel ne.arrow)

    val observedArrow = wrapP pel neW hExecutorBookkeep ng(
      context,
      p pel ne. dent f er,
      qual yFactorObserver,
      fa lOpenPol cy)(p pel neArrow)

    Arrow
      .z pW hArg(
        observedArrow
      ).map {
        case (
              scor ngP pel nesState: Scor ngP pel neState[Query, Cand date],
              scor ngP pel neResult: Scor ngP pel neResult[Cand date]) =>
          val updatedCand dates: Seq[ emCand dateW hDeta ls] =
            mkUpdatedCand dates(p pel ne. dent f er, scor ngP pel nesState, scor ngP pel neResult)
          Scor ngP pel neState(
            scor ngP pel nesState.query,
            updatedCand dates,
            scor ngP pel nesState.executorResult
              .copy(
                updatedCand dates,
                scor ngP pel nesState.executorResult. nd v dualP pel neResults :+ scor ngP pel neResult)
          )
      }
  }

  pr vate def mkUpdatedCand dates[Query <: P pel neQuery, Cand date <: Un versalNoun[Any]](
    scor ngP pel ne dent f er: Scor ngP pel ne dent f er,
    scor ngP pel nesState: Scor ngP pel neState[Query, Cand date],
    scor ngP pel neResult: Scor ngP pel neResult[Cand date]
  ): Seq[ emCand dateW hDeta ls] = {
     f (scor ngP pel neResult.fa lure. sEmpty) {

      /**
       *  's  mportant that   map back from wh ch actual  em cand date was scored by look ng
       * at t  selector results. T   s to defend aga nst t  sa  cand date be ng selected
       * from two d fferent cand date p pel nes.  f one  s selected and t  ot r  sn't,  
       * should only score t  selected one.  f both are selected and each  s scored d fferently
       *   should get t  r ght score for each.
       */
      val selected emCand dates: Seq[ emCand dateW hDeta ls] =
        scor ngP pel neResult.selectorResults
          .getOrElse(throw P pel neFa lure(
             llegalStateFa lure,
            s"M ss ng Selector Results  n Scor ng P pel ne $scor ngP pel ne dent f er")).selectedCand dates.collect {
            case  emCand dateW hDeta ls:  emCand dateW hDeta ls =>
               emCand dateW hDeta ls
          }
      val scoredFeatureMaps: Seq[FeatureMap] = scor ngP pel neResult.result
        .getOrElse(Seq.empty).map(_.features)

       f (scoredFeatureMaps. sEmpty) {
        //  's poss ble that all Scorers are [[Cond  onally]] off.  n t  case,   return empty
        // and don't val date t  l st s ze s nce t   s done  n t  hydrator/scorer executor.
        scor ngP pel nesState.allCand dates
      } else  f (selected emCand dates.length != scoredFeatureMaps.length) {
        // T  length of t   nputted cand dates should always match t  returned feature map, unless
        throw P pel neFa lure(
           llegalStateFa lure,
          s"M ss ng conf gured scorer result, length of scorer results does not match t  length of selected cand dates")
      } else {
        /* Z p t  selected  em cand date seq back to t  scored feature maps, t  works
         * because t  scored results w ll always have t  sa  number of ele nts returned
         * and   should match t  sa  order.   t n loop through all cand dates because t 
         * expectat on  s to always keep t  result s nce a subsequent scor ng p pel ne can score a
         * cand date that t  current one d d not.   only update t  feature map of t  cand date
         *   f   was selected and scored.
         */
        val selected emCand dateToScorerMap: Map[ emCand dateW hDeta ls, FeatureMap] =
          selected emCand dates.z p(scoredFeatureMaps).toMap
        scor ngP pel nesState.allCand dates.map {  emCand dateW hDeta ls =>
          selected emCand dateToScorerMap.get( emCand dateW hDeta ls) match {
            case So (scorerResult) =>
               emCand dateW hDeta ls.copy(features =
                 emCand dateW hDeta ls.features ++ scorerResult)
            case None =>  emCand dateW hDeta ls
          }
        }
      }
    } else {
      //  f t  underly ng scor ng p pel ne has fa led open, just keep t  ex st ng cand dates
      scor ngP pel nesState.allCand dates
    }
  }
}

object Scor ngP pel neExecutor {
  pr vate case class Scor ngP pel neState[Query <: P pel neQuery, Cand date <: Un versalNoun[Any]](
    query: Query,
    allCand dates: Seq[ emCand dateW hDeta ls],
    executorResult: Scor ngP pel neExecutorResult[Cand date])

  case class  nputs[Query <: P pel neQuery](
    query: Query,
     emCand datesW hDeta ls: Seq[ emCand dateW hDeta ls])
}
