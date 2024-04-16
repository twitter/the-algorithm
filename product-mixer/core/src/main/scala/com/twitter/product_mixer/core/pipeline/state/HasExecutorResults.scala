package com.tw ter.product_m xer.core.p pel ne.state

 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult
 mport scala.collect on. mmutable.L stMap

tra  HasExecutorResults[State] {
  //   use a l st map to ma nta n t   nsert on order
  val executorResultsByP pel neStep: L stMap[P pel neStep dent f er, ExecutorResult]
  pr vate[p pel ne] def setExecutorResults(
    newMap: L stMap[P pel neStep dent f er, ExecutorResult]
  ): State
}
