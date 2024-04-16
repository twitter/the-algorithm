package com.tw ter.product_m xer.core.p pel ne.product

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Request
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.m xer.M xerP pel neBu lderFactory
 mport com.tw ter.product_m xer.core.p pel ne.recom ndat on.Recom ndat onP pel neBu lderFactory
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutor
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_execut on_logger.P pel neExecut onLogger
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_executor.P pel neExecutor
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_selector_executor.P pel neSelectorExecutor
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ProductP pel neBu lderFactory @ nject() (
  gateExecutor: GateExecutor,
  p pel neSelectorExecutor: P pel neSelectorExecutor,
  p pel neExecutor: P pel neExecutor,
  m xerP pel neBu lderFactory: M xerP pel neBu lderFactory,
  recom ndat onP pel neBu lderFactory: Recom ndat onP pel neBu lderFactory,
  statsRece ver: StatsRece ver,
  p pel neExecut onLogger: P pel neExecut onLogger) {
  def get[
    TRequest <: Request,
    Query <: P pel neQuery,
    Response
  ]: ProductP pel neBu lder[TRequest, Query, Response] = {
    new ProductP pel neBu lder[TRequest, Query, Response](
      gateExecutor,
      p pel neSelectorExecutor,
      p pel neExecutor,
      m xerP pel neBu lderFactory,
      recom ndat onP pel neBu lderFactory,
      statsRece ver,
      p pel neExecut onLogger
    )
  }
}
