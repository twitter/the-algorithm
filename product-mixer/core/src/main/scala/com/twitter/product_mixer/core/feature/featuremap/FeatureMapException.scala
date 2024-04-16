package com.tw ter.product_m xer.core.feature.featuremap

 mport com.tw ter.product_m xer.core.feature.Feature

case class M ss ngFeatureExcept on(feature: Feature[_, _])
    extends Except on("M ss ng value for " + feature) {
  overr de def toStr ng: Str ng = get ssage
}

class  nval dPred ct onRecord rgeExcept on
    extends Except on(
      "Use FeatureMap.plusPlusOpt m zed  nstead of FeatureMap.++ w n t  FeatureMaps on both s des of t   rge conta n Pred ct onRecords")
