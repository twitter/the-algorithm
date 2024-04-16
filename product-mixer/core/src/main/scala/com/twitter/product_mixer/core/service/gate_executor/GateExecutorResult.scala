package com.tw ter.product_m xer.core.serv ce.gate_executor

 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult

case class GateExecutorResult(
   nd v dualGateResults: Seq[ExecutedGateResult])
    extends ExecutorResult
