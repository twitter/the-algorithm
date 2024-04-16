package com.tw ter.product_m xer.core.serv ce.qual y_factor_executor

 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er

case class Qual yFactorExecutorResult(
  p pel neQual yFactors: Map[Component dent f er, Double])

object Qual yFactorExecutorResult {
  val empty: Qual yFactorExecutorResult = Qual yFactorExecutorResult(Map.empty)
}
