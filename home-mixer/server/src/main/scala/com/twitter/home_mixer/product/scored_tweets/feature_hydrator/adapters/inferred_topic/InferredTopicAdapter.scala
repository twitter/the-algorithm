package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters. nferred_top c

 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.t  l nes.pred ct on.common.adapters.T  l nesMutat ngAdapterBase
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures
 mport scala.collect on.JavaConverters._

object  nferredTop cAdapter extends T  l nesMutat ngAdapterBase[Map[Long, Double]] {

  overr de val getFeatureContext: FeatureContext = new FeatureContext(
    T  l nesSharedFeatures. NFERRED_TOP C_ DS)

  overr de val commonFeatures: Set[Feature[_]] = Set.empty

  overr de def setFeatures(
     nferredTop cFeatures: Map[Long, Double],
    r chDataRecord: R chDataRecord
  ): Un  = {
    r chDataRecord.setFeatureValue(
      T  l nesSharedFeatures. NFERRED_TOP C_ DS,
       nferredTop cFeatures.keys.map(_.toStr ng).toSet.asJava)
  }
}
