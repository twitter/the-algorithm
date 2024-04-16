package com.tw ter.product_m xer.core.model.common

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap

/** [[Cand date]] and  's FeatureMap */
tra  Cand dateW hFeatures[+Cand date <: Un versalNoun[Any]] {
  val cand date: Cand date
  val features: FeatureMap
}

object Cand dateW hFeatures {
  def unapply[Cand date <: Un versalNoun[Any]](
    cand dateW hFeatures: Cand dateW hFeatures[Cand date]
  ): Opt on[(Cand date, FeatureMap)] =
    So (
      (cand dateW hFeatures.cand date, cand dateW hFeatures.features)
    )
}
