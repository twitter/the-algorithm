package com.tw ter.product_m xer.core.serv ce.cand date_s ce_executor

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult

case class Cand dateS ceExecutorResult[Cand date <: Un versalNoun[Any]](
  cand dates: Seq[Fetc dCand dateW hFeatures[Cand date]],
  cand dateS ceFeatureMap: FeatureMap)
    extends ExecutorResult
