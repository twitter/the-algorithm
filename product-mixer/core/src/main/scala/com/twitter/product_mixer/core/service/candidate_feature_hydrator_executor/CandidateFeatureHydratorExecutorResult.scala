package com.tw ter.product_m xer.core.serv ce.cand date_feature_hydrator_executor

 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult

case class Cand dateFeatureHydratorExecutorResult[+Result <: Un versalNoun[Any]](
  results: Seq[Cand dateW hFeatures[Result]],
   nd v dualFeatureHydratorResults: Map[
    _ <: Component dent f er,
    Base nd v dualFeatureHydratorResult[Result]
  ]) extends ExecutorResult

sealed tra  Base nd v dualFeatureHydratorResult[+Result <: Un versalNoun[Any]]
case class FeatureHydratorD sabled[+Result <: Un versalNoun[Any]]()
    extends Base nd v dualFeatureHydratorResult[Result]
case class  nd v dualFeatureHydratorResult[+Result <: Un versalNoun[Any]](
  result: Seq[Cand dateW hFeatures[Result]])
    extends Base nd v dualFeatureHydratorResult[Result]
