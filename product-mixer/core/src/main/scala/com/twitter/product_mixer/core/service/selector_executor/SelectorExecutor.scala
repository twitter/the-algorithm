package com.tw ter.product_m xer.core.serv ce.selector_executor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common. dent f er.Selector dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure. llegalStateFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.st ch.Arrow

 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Appl es a `Seq[Selector]`  n sequent al order.
 * Returns t  results, and also a deta led l st each selector's results (for debugg ng / understandab l y).
 */
@S ngleton
class SelectorExecutor @ nject() (overr de val statsRece ver: StatsRece ver) extends Executor {
  def arrow[Query <: P pel neQuery](
    selectors: Seq[Selector[Query]],
    context: Executor.Context
  ): Arrow[SelectorExecutor. nputs[Query], SelectorExecutorResult] = {

     f (selectors. sEmpty) {
      throw P pel neFa lure(
         llegalStateFa lure,
        "Must prov de a non-empty Seq of Selectors. C ck t  conf g  nd cated by t  componentStack and ensure that a non-empty Selector Seq  s prov ded.",
        componentStack = So (context.componentStack)
      )
    }

    val selectorArrows =
      selectors.z pW h ndex.foldLeft(Arrow. dent y[(Query,  ndexedSeq[SelectorResult])]) {
        case (prev ousSelectorArrows, (selector,  ndex)) =>
          val selectorResult = get nd v dualSelector soArrow(selector,  ndex, context)
          prev ousSelectorArrows.andT n(selectorResult)
      }

    Arrow
      .z pW hArg(
        Arrow
          .map[SelectorExecutor. nputs[Query], (Query,  ndexedSeq[SelectorResult])] {
            case SelectorExecutor. nputs(query, cand dates) =>
              (query,  ndexedSeq(SelectorResult(cand dates, Seq.empty)))
          }.andT n(selectorArrows)).map {
        case ( nputs, (_, selectorResults)) =>
          // t  last results, safe because  's always non-empty s nce   starts w h 1 ele nt  n  
          val SelectorResult(rema n ngCand dates, result) = selectorResults.last

          val resultsAndRema n ngCand dates =
            (result. erator ++ rema n ngCand dates. erator).toSet

          // t  droppedCand dates are all t  cand dates wh ch are  n ne  r t  result or rema n ngCand dates
          val droppedCand dates =  nputs.cand datesW hDeta ls. erator
            .f lterNot(resultsAndRema n ngCand dates.conta ns)
            .to ndexedSeq

          SelectorExecutorResult(
            selectedCand dates = result,
            rema n ngCand dates = rema n ngCand dates,
            droppedCand dates = droppedCand dates,
             nd v dualSelectorResults =
              selectorResults.ta l // `.ta l` to remove t   n  al state   had
          )
      }
  }

  pr vate def get nd v dualSelector soArrow[Query <: P pel neQuery](
    selector: Selector[Query],
     ndex:  nt,
    context: Executor.Context
  ): Arrow. so[(Query,  ndexedSeq[SelectorResult])] = {
    val  dent f er = Selector dent f er(selector.getClass.getS mpleNa ,  ndex)

    val arrow = Arrow
      . dent y[(Query,  ndexedSeq[SelectorResult])]
      .map {
        case (query, prev ousResults) =>
          // last  s safe  re because   pass  n a non-empty  ndexedSeq
          val prev ousResult = prev ousResults.last
          val currentResult = selector.apply(
            query,
            prev ousResult.rema n ngCand dates,
            prev ousResult.result
          )
          (query, prev ousResults :+ currentResult)
      }

    wrapComponentsW hTrac ngOnly(context,  dent f er)(
      wrapW hErrorHandl ng(context,  dent f er)(
        arrow
      )
    )
  }
}

object SelectorExecutor {
  case class  nputs[Query <: P pel neQuery](
    query: Query,
    cand datesW hDeta ls: Seq[Cand dateW hDeta ls])
}
