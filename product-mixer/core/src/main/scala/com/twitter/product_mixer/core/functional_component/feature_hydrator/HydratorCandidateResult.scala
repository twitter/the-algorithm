package com.tw ter.product_m xer.core.funct onal_component.feature_hydrator

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun

case class HydratorCand dateResult[+Cand date <: Un versalNoun[Any]](
  overr de val cand date: Cand date,
  overr de val features: FeatureMap)
    extends Cand dateW hFeatures[Cand date]
