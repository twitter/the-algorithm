package com.tw ter.ho _m xer.ut l

 mport com.tw ter.ho _m xer.model.Ho Features.T et mpress onsFeature
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query. mpressed_t ets. mpressedT ets
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap

object T et mpress ons lper {
  def t et mpress ons(features: FeatureMap): Set[Long] = {
    val manhattan mpress ons =
      features.getOrElse(T et mpress onsFeature, Seq.empty).flatMap(_.t et ds)
    val  mcac  mpress ons = features.getOrElse( mpressedT ets, Seq.empty)

    (manhattan mpress ons ++  mcac  mpress ons).toSet
  }
}
