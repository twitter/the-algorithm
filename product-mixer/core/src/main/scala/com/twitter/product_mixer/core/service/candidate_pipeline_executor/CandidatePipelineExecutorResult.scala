package com.tw ter.product_m xer.core.serv ce.cand date_p pel ne_executor

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neResult

case class Cand dateP pel neExecutorResult(
  cand dateP pel neResults: Seq[Cand dateP pel neResult],
  queryFeatureMap: FeatureMap)
