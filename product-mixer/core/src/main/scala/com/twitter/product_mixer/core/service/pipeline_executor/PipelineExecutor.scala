package com.tw ter.product_m xer.core.serv ce.p pel ne_executor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel ne
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure. nval dP pel neSelected
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorObserver
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.ut l.logg ng.Logg ng

 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * P pel neExecutor executes a s ngle p pel ne (of any type)
 *   does not currently support fa l open/closed pol c es l ke Cand dateP pel neExecutor does
 *  n t  future, maybe t y can be  rged.
 */

case class P pel neExecutorRequest[Query](query: Query, p pel ne dent f er: Component dent f er)

@S ngleton
class P pel neExecutor @ nject() (overr de val statsRece ver: StatsRece ver)
    extends Executor
    w h Logg ng {

  def arrow[Query, ResultType](
    p pel neBy dent f er: Map[Component dent f er, P pel ne[Query, ResultType]],
    qual yFactorObserverByP pel ne: Map[Component dent f er, Qual yFactorObserver],
    context: Executor.Context
  ): Arrow[P pel neExecutorRequest[Query], P pel neExecutorResult[ResultType]] = {

    val wrappedP pel neArrowsBy dent f er = p pel neBy dent f er.mapValues { p pel ne =>
      wrapP pel neW hExecutorBookkeep ng(
        context,
        p pel ne. dent f er,
        qual yFactorObserverByP pel ne.get(p pel ne. dent f er))(p pel ne.arrow)
    }

    val appl edP pel neArrow = Arrow
      . dent y[P pel neExecutorRequest[Query]]
      .map {
        case P pel neExecutorRequest(query, p pel ne dent f er) =>
          val p pel ne = wrappedP pel neArrowsBy dent f er.getOrElse(
            p pel ne dent f er,
            // throw ng  nstead of return ng a `Throw(_)` and t n `.lo rFromTry` because t   s an except onal case and   want to emphas ze that by expl c ly throw ng
            // t  case should never happen s nce t   s c cked  n t  `P pel neSelectorExecutor` but   c ck   anyway
            throw P pel neFa lure(
               nval dP pel neSelected,
              s"${context.componentStack.peek} attempted to execute $p pel ne dent f er",
              // t  `componentStack`  ncludes t  m ss ng p pel ne so   can show up  n  tr cs eas er
              componentStack = So (context.componentStack.push(p pel ne dent f er))
            )
          )
          (p pel ne, query)
      }
      // less eff c ent than an `andT n` but s nce   d spatch t  dynam cally   need to use e  r `applyArrow` or `flatMap` and t   s t  better of those opt ons
      .applyArrow
      .map(P pel neExecutorResult(_))

    // no add  onal error handl ng needed s nce   populate t  component stack above already
    appl edP pel neArrow
  }
}
