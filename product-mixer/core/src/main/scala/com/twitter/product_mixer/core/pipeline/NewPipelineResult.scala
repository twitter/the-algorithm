package com.tw ter.product_m xer.core.p pel ne

 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult
 mport scala.collect on. mmutable.L stMap

sealed tra  NewP pel neResult[-Result] {
  def executorResultsByP pel neStep: L stMap[P pel neStep dent f er, ExecutorResult]
}

object NewP pel neResult {
  case class Fa lure(
    fa lure: P pel neFa lure,
    overr de val executorResultsByP pel neStep: L stMap[P pel neStep dent f er, ExecutorResult])
      extends NewP pel neResult[Any]

  case class Success[Result](
    result: Result,
    overr de val executorResultsByP pel neStep: L stMap[P pel neStep dent f er, ExecutorResult])
      extends NewP pel neResult[Result]
}
