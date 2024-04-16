package com.tw ter.product_m xer.core.serv ce.cand date_feature_transfor r_executor

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er

case class Cand dateFeatureTransfor rExecutorResult(
  featureMaps: Seq[FeatureMap],
   nd v dualFeatureMaps: Seq[Map[Transfor r dent f er, FeatureMap]])
