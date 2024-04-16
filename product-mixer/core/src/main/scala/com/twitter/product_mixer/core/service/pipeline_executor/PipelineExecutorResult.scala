package com.tw ter.product_m xer.core.serv ce.p pel ne_executor

 mport com.tw ter.product_m xer.core.p pel ne.P pel neResult
 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult

case class P pel neExecutorResult[ResultType](
  p pel neResult: P pel neResult[ResultType])
    extends ExecutorResult
