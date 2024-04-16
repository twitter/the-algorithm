package com.tw ter.product_m xer.component_l brary.scorer.tensorbu lder

 mport com.tw ter.ml.ap .thr ftscala.FloatTensor
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.ModelFeatureNa 
 mport com.tw ter.product_m xer.core.feature.featuremap.featurestorev1.FeatureStoreV1FeatureMap._
 mport com.tw ter.product_m xer.core.feature.featurestorev1.FeatureStoreV1Cand dateFeature
 mport com.tw ter.product_m xer.core.feature.featurestorev1.FeatureStoreV1QueryFeature
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport  nference.GrpcServ ce.Model nferRequest. nfer nputTensor

class Cand date nfer nputTensorBu lder[-Cand date <: Un versalNoun[Any], +Value](
  bu lder:  nfer nputTensorBu lder[Value],
  features: Set[_ <: Feature[Cand date, _] w h ModelFeatureNa ]) {
  def apply(
    cand dates: Seq[Cand dateW hFeatures[Cand date]],
  ): Seq[ nfer nputTensor] = {
    features.flatMap { feature =>
      val featureValues: Seq[Value] = feature match {
        case feature: FeatureStoreV1Cand dateFeature[_, Cand date, _, Value] =>
          cand dates.map(_.features.getFeatureStoreV1Cand dateFeature(feature))
        case feature: FeatureStoreV1QueryFeature[_, _, _] =>
          throw new UnexpectedFeatureTypeExcept on(feature)
        case feature: FeatureW hDefaultOnFa lure[Cand date, Value] =>
          cand dates.map(_.features.getTry(feature).toOpt on.getOrElse(feature.defaultValue))
        case feature: Feature[Cand date, Value] =>
          cand dates.map(_.features.get(feature))
      }
      bu lder.apply(feature.featureNa , featureValues)
    }.toSeq
  }
}

case class Cand dateBoolean nfer nputTensorBu lder[-Cand date <: Un versalNoun[Any]](
  features: Set[_ <: Feature[Cand date, Boolean] w h ModelFeatureNa ])
    extends Cand date nfer nputTensorBu lder[Cand date, Boolean](
      Boolean nfer nputTensorBu lder,
      features)

case class Cand dateBytes nfer nputTensorBu lder[-Cand date <: Un versalNoun[Any]](
  features: Set[_ <: Feature[Cand date, Str ng] w h ModelFeatureNa ])
    extends Cand date nfer nputTensorBu lder[Cand date, Str ng](
      Bytes nfer nputTensorBu lder,
      features)

case class Cand dateFloat32 nfer nputTensorBu lder[-Cand date <: Un versalNoun[Any]](
  features: Set[_ <: Feature[Cand date, _ <: AnyVal] w h ModelFeatureNa ])
    extends Cand date nfer nputTensorBu lder[Cand date, AnyVal](
      Float32 nfer nputTensorBu lder,
      features)

case class Cand dateFloatTensor nfer nputTensorBu lder[-Cand date <: Un versalNoun[Any]](
  features: Set[_ <: Feature[Cand date, FloatTensor] w h ModelFeatureNa ])
    extends Cand date nfer nputTensorBu lder[Cand date, FloatTensor](
      FloatTensor nfer nputTensorBu lder,
      features)

case class Cand date nt64 nfer nputTensorBu lder[-Cand date <: Un versalNoun[Any]](
  features: Set[_ <: Feature[Cand date, _ <: AnyVal] w h ModelFeatureNa ])
    extends Cand date nfer nputTensorBu lder[Cand date, AnyVal](
       nt64 nfer nputTensorBu lder,
      features)

case class Cand dateSparseMap nfer nputTensorBu lder[-Cand date <: Un versalNoun[Any]](
  features: Set[_ <: Feature[Cand date, Opt on[Map[ nt, Double]]] w h ModelFeatureNa ])
    extends Cand date nfer nputTensorBu lder[Cand date, Opt on[Map[ nt, Double]]](
      SparseMap nfer nputTensorBu lder,
      features)
