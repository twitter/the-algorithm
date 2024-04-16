package com.tw ter.product_m xer.core.p pel ne

 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.state.HasExecutorResults

case class NewStepData[State <: HasExecutorResults[State]](
  p pel neState: State,
  p pel neFa lure: Opt on[P pel neFa lure] = None) {

  val stopExecut ng = p pel neFa lure. sDef ned
  def w hFa lure(fa lure: P pel neFa lure): NewStepData[State] =
    t .copy(p pel neFa lure = So (fa lure))
}
