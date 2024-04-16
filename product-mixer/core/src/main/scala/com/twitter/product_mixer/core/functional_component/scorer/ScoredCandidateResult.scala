package com.tw ter.product_m xer.core.funct onal_component.scorer

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun

/** A [[Cand date]] and  's [[FeatureMap]] after be ng processed by a [[Scorer]] */
case class ScoredCand dateResult[Cand date <: Un versalNoun[Any]](
  cand date: Cand date,
  scorerResult: FeatureMap)
    extends Cand dateW hFeatures[Cand date] {
  overr de val features: FeatureMap = scorerResult
}
