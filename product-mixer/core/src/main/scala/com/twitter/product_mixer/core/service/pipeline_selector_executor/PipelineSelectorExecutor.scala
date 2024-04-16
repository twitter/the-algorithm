package com.tw ter.product_m xer.core.serv ce.p pel ne_selector_executor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.logg ng.Logg ng
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Platform dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel ne
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure. nval dP pel neSelected
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.st ch.Arrow

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class P pel neSelectorExecutor @ nject() (overr de val statsRece ver: StatsRece ver)
    extends Executor
    w h Logg ng {

  val  dent f er: Component dent f er = Platform dent f er("P pel neSelector")

  def arrow[Query <: P pel neQuery, Response](
    p pel neBy dent f er: Map[Component dent f er, P pel ne[Query, Response]],
    p pel neSelector: Query => Component dent f er,
    context: Executor.Context
  ): Arrow[Query, P pel neSelectorExecutorResult] = {

    val val dateSelectedP pel neEx sts = Arrow
      .map(p pel neSelector)
      .map { chosen dent f er =>
         f (p pel neBy dent f er.conta ns(chosen dent f er)) {
          P pel neSelectorExecutorResult(chosen dent f er)
        } else {
          // throw ng  nstead of return ng a `Throw(_)` and t n `.lo rFromTry` because t   s an except onal case and   want to emphas ze that by expl c ly throw ng
          throw P pel neFa lure(
             nval dP pel neSelected,
            s"${context.componentStack.peek} attempted to select $chosen dent f er",
            // t  `componentStack`  ncludes t  m ss ng p pel ne so   can show up  n  tr cs eas er
            componentStack = So (context.componentStack.push(chosen dent f er))
          )
        }
      }

    wrapW hErrorHandl ng(context,  dent f er)(val dateSelectedP pel neEx sts)
  }
}
