package com.tw ter.product_m xer.core.p pel ne

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.state.HasExecutorResults
 mport com.tw ter.product_m xer.core.p pel ne.state.HasResult
 mport com.tw ter.product_m xer.core.p pel ne.step.Step
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorStatus
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.Executor.Context
 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.st ch.Arrow. so
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw

/**
 * P pel ne Arrow Bu lder used for construct ng a f nal arrow for a p pel ne after add ng necessary
 * steps.
 *
 * @param steps T  kept non-empty P pel ne Steps
 * @param addedSteps Steps that have been added, but not necessar ly kept.
 * @param statsRece ver Stats Rece ver for  tr c book keep ng
 * @tparam Result sT  expected f nal result type of t  p pel ne.
 * @tparam State T   nput state type, wh ch should  mple nt [[HasResult]].
 */
case class NewP pel neArrowBu lder[
  Result,
  State <: HasExecutorResults[State] w h HasResult[Result]
] pr vate (
  pr vate val steps: Seq[P pel neStep[State, _, _, _]],
  overr de val statsRece ver: StatsRece ver)
    extends Executor {

  def add[Conf g, Executor nput, ExResult <: ExecutorResult](
    p pel neStep dent f er: P pel neStep dent f er,
    step: Step[State, Conf g, Executor nput, ExResult],
    executorConf g: Conf g
  ): NewP pel neArrowBu lder[Result, State] = {
    requ re(
      !steps.conta ns(p pel neStep dent f er),
      s"Found dupl cate step $p pel neStep dent f er w n bu ld ng p pel ne arrow")

    //  f t  step has noth ng to execute, drop   for s mpl f cat on but st ll added   to t 
    // "addedSteps" f eld for bu ld t   val dat on
     f (step. sEmpty(executorConf g)) {
      t 
    } else {
      val newP pel neStep =
        P pel neStep(p pel neStep dent f er, executorConf g, step)
      val newSteps = steps :+ newP pel neStep
      t .copy(steps = newSteps)
    }
  }

  def bu ldArrow(
    context: Executor.Context
  ): Arrow[State, NewP pel neResult[Result]] = {
    val  n  alArrow = Arrow
      .map {  nput: State => NewStepData[State]( nput) }
    val allStepArrows = steps.map { step =>
       so.only f[NewStepData[State]] { stepData => !stepData.stopExecut ng } {
        wrapStepW hExecutorBookkeep ng(step, context)
      }
    }
    val comb nedArrow =  soArrowsSequent ally(allStepArrows)
    val resultArrow = Arrow.map { stepData: NewStepData[State] =>
      stepData.p pel neFa lure match {
        case So (fa lure) =>
          NewP pel neResult.Fa lure(fa lure, stepData.p pel neState.executorResultsByP pel neStep)
        case None =>
          NewP pel neResult.Success(
            stepData.p pel neState.bu ldResult,
            stepData.p pel neState.executorResultsByP pel neStep)
      }
    }
     n  alArrow.andT n(comb nedArrow).andT n(resultArrow)
  }

  pr vate[t ] def wrapStepW hExecutorBookkeep ng(
    step: P pel neStep[State, _, _, _],
    context: Context
  ): Arrow. so[NewStepData[State]] = {
    val wrapped = wrapStepW hExecutorBookkeep ng[NewStepData[State], NewStepData[State]](
      context = context,
       dent f er = step.step dent f er,
      arrow = step.arrow(context),
      // extract t  fa lure only  f  's present. Not sure  f t   s needed???
      transfor r = _.p pel neFa lure.map(Throw(_)).getOrElse(Return.Un )
    )

    Arrow
      .z pW hArg(wrapped.l ftToTry)
      .map {
        case (_: NewStepData[State], Return(result)) =>
          //  f Step was successful, return t  result
          result
        case (prev ous: NewStepData[State], Throw(p pel neFa lure: P pel neFa lure)) =>
          //  f t  Step fa led  n such a way that t  fa lure was NOT captured
          //  n t  result object, t n update t  State w h t  fa lure
          prev ous.w hFa lure(p pel neFa lure)
        case (_, Throw(ex)) =>
          // an except on was thrown wh ch was not handled by t  fa lure class f er
          // t  only happens w h cancellat on except ons wh ch are re-thrown
          throw ex
      }
  }

  /**
   * Sets up stats [[com.tw ter.f nagle.stats.Gauge]]s for any [[Qual yFactorStatus]]
   *
   * @note   use prov deGauge so t se gauges l ve forever even w hout a reference.
   */
  pr vate[p pel ne] def bu ldGaugesForQual yFactor(
    p pel ne dent f er: Component dent f er,
    qual yFactorStatus: Qual yFactorStatus,
    statsRece ver: StatsRece ver
  ): Un  = {
    qual yFactorStatus.qual yFactorByP pel ne.foreach {
      case ( dent f er, qual yFactor) =>
        // QF  s a relat ve stat (s nce t  parent p pel ne  s mon or ng a ch ld p pel ne)
        val scopes = p pel ne dent f er.toScopes ++  dent f er.toScopes :+ "Qual yFactor"
        statsRece ver.prov deGauge(scopes: _*) { qual yFactor.currentValue.toFloat }
    }
  }
}

object NewP pel neArrowBu lder {
  def apply[Result,  nputState <: HasExecutorResults[ nputState] w h HasResult[Result]](
    statsRece ver: StatsRece ver
  ): NewP pel neArrowBu lder[Result,  nputState] = {
    NewP pel neArrowBu lder(
      Seq.empty,
      statsRece ver
    )
  }
}

/**
 * T   s a p pel ne spec f c  nstance of a step,  .e, a gener c step w h t  step  dent f er
 * w h n t  p pel ne and  s executor conf gs.
 * @param step dent f er Step  dent f er of t  step w h n a p pel ne
 * @param executorConf g Conf g to execute t  step w h
 * @param step T  underly ng step to be used
 * @tparam  nputState T   nput state object
 * @tparam ExecutorConf g T  conf g expected for t  g ven step
 * @tparam Executor nput  nput for t  underly ng executor
 * @tparam ExecResult T  result type
 */
case class P pel neStep[
  State <: HasExecutorResults[State],
  P pel neStepConf g,
  Executor nput,
  ExecResult <: ExecutorResult
](
  step dent f er: P pel neStep dent f er,
  executorConf g: P pel neStepConf g,
  step: Step[State, P pel neStepConf g, Executor nput, ExecResult]) {

  def arrow(
    context: Executor.Context
  ): Arrow. so[NewStepData[State]] = {
    val  nputArrow = Arrow.map { stepData: NewStepData[State] =>
      step.adapt nput(stepData.p pel neState, executorConf g)
    }

    Arrow
      .z pW hArg( nputArrow.andT n(step.arrow(executorConf g, context))).map {
        case (stepData: NewStepData[State], executorResult: ExecResult @unc cked) =>
          val updatedResultsByP pel neStep =
            stepData.p pel neState.executorResultsByP pel neStep + (step dent f er -> executorResult)
          val updatedP pel neState = step
            .updateState(stepData.p pel neState, executorResult, executorConf g).setExecutorResults(
              updatedResultsByP pel neStep)

          NewStepData(updatedP pel neState)
      }
  }
}
