package com.tw ter.product_m xer.core.p pel ne

 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f erStack
 mport com.tw ter.product_m xer.core.p pel ne.state.HasExecutorResults
 mport com.tw ter.product_m xer.core.p pel ne.state.HasResult

/**
 * A p pel ne bu lder that  s respons ble for tak ng a P pel neConf g and creat ng a f nal p pel ne
 * from  .   prov des an [[NewP pel neArrowBu lder]] for compos ng t  p pel ne's underly ng arrow
 * from [[Step]]s.
 *
 * @tparam Conf g T  P pel ne Conf g
 * @tparam P pel neArrowResult T  expected f nal result
 * @tparam P pel neArrowState State object for ma nta n ng state across t  p pel ne.
 * @tparam OutputP pel ne T  f nal p pel ne
 */
tra  NewP pel neBu lder[
  Conf g <: P pel neConf g,
  P pel neArrowResult,
  P pel neArrowState <: HasExecutorResults[P pel neArrowState] w h HasResult[P pel neArrowResult],
  OutputP pel ne <: P pel ne[_, _]] {

  type ArrowResult = P pel neArrowResult
  type ArrowState = P pel neArrowState

  def bu ld(
    parentComponent dent f erStack: Component dent f erStack,
    arrowBu lder: NewP pel neArrowBu lder[ArrowResult, ArrowState],
    conf g: Conf g
  ): OutputP pel ne
}
