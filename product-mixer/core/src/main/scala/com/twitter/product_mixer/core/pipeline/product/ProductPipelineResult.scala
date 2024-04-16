package com.tw ter.product_m xer.core.p pel ne.product

 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.P pel neResult
 mport com.tw ter.product_m xer.core.p pel ne.m xer.M xerP pel neResult
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.recom ndat on.Recom ndat onP pel neResult
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_selector_executor.P pel neSelectorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.qual y_factor_executor.Qual yFactorExecutorResult

case class ProductP pel neResult[Result](
  transfor dQuery: Opt on[P pel neQuery],
  qual yFactorResult: Opt on[Qual yFactorExecutorResult],
  gateResult: Opt on[GateExecutorResult],
  p pel neSelectorResult: Opt on[P pel neSelectorExecutorResult],
  m xerP pel neResult: Opt on[M xerP pel neResult[Result]],
  recom ndat onP pel neResult: Opt on[Recom ndat onP pel neResult[_, Result]],
  trace d: Opt on[Str ng],
  fa lure: Opt on[P pel neFa lure],
  result: Opt on[Result])
    extends P pel neResult[Result] {

  overr de val resultS ze:  nt = {
     f (m xerP pel neResult. sDef ned) {
      m xerP pel neResult.map(_.resultS ze).getOrElse(0)
    } else {
      recom ndat onP pel neResult.map(_.resultS ze).getOrElse(0)
    }
  }

  overr de def w hFa lure(fa lure: P pel neFa lure): P pel neResult[Result] =
    copy(fa lure = So (fa lure))

  overr de def w hResult(result: Result): P pel neResult[Result] = copy(result = So (result))
}

object ProductP pel neResult {
  def empty[A]: ProductP pel neResult[A] = ProductP pel neResult(
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None
  )

  def fromResult[A](result: A): ProductP pel neResult[A] = ProductP pel neResult(
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    So (result)
  )
}
