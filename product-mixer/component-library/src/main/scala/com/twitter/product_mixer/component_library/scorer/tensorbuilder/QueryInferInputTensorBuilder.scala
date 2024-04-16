package com.tw ter.product_m xer.component_l brary.scorer.tensorbu lder

 mport com.tw ter.ml.ap .thr ftscala.FloatTensor
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.ModelFeatureNa 
 mport com.tw ter.product_m xer.core.feature.featuremap.featurestorev1.FeatureStoreV1FeatureMap._
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featurestorev1.FeatureStoreV1Cand dateFeature
 mport com.tw ter.product_m xer.core.feature.featurestorev1.FeatureStoreV1QueryFeature
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport  nference.GrpcServ ce.Model nferRequest. nfer nputTensor

class Query nfer nputTensorBu lder[-Query <: P pel neQuery, +Value](
  bu lder:  nfer nputTensorBu lder[Value],
  features: Set[_ <: Feature[Query, _] w h ModelFeatureNa ]) {
  def apply(query: Query): Seq[ nfer nputTensor] = {
    val featureMap = query.features.getOrElse(FeatureMap.empty)
    features.flatMap { feature =>
      val queryFeatureValue: Value = feature match {
        case feature: FeatureStoreV1QueryFeature[Query, _, Value] =>
          featureMap.getFeatureStoreV1QueryFeature(feature)
        case feature: FeatureStoreV1Cand dateFeature[Query, _, _, Value] =>
          throw new UnexpectedFeatureTypeExcept on(feature)
        case feature: FeatureW hDefaultOnFa lure[Query, Value] =>
          featureMap.getTry(feature).toOpt on.getOrElse(feature.defaultValue)
        case feature: Feature[Query, Value] =>
          featureMap.get(feature)
      }
      bu lder.apply(feature.featureNa , Seq(queryFeatureValue))
    }.toSeq
  }
}

case class QueryBoolean nfer nputTensorBu lder[-Query <: P pel neQuery](
  features: Set[_ <: Feature[Query, Boolean] w h ModelFeatureNa ])
    extends Query nfer nputTensorBu lder[Query, Boolean](Boolean nfer nputTensorBu lder, features)

case class QueryBytes nfer nputTensorBu lder[-Query <: P pel neQuery](
  features: Set[_ <: Feature[Query, Str ng] w h ModelFeatureNa ])
    extends Query nfer nputTensorBu lder[Query, Str ng](Bytes nfer nputTensorBu lder, features)

case class QueryFloat32 nfer nputTensorBu lder[-Query <: P pel neQuery](
  features: Set[_ <: Feature[Query, _ <: AnyVal] w h ModelFeatureNa ])
    extends Query nfer nputTensorBu lder[Query, AnyVal](Float32 nfer nputTensorBu lder, features)

case class QueryFloatTensor nfer nputTensorBu lder[-Query <: P pel neQuery](
  features: Set[_ <: Feature[Query, FloatTensor] w h ModelFeatureNa ])
    extends Query nfer nputTensorBu lder[Query, FloatTensor](
      FloatTensor nfer nputTensorBu lder,
      features)

case class Query nt64 nfer nputTensorBu lder[-Query <: P pel neQuery](
  features: Set[_ <: Feature[Query, _ <: AnyVal] w h ModelFeatureNa ])
    extends Query nfer nputTensorBu lder[Query, AnyVal]( nt64 nfer nputTensorBu lder, features)

case class QuerySparseMap nfer nputTensorBu lder[-Query <: P pel neQuery](
  features: Set[_ <: Feature[Query, Opt on[Map[ nt, Double]]] w h ModelFeatureNa ])
    extends Query nfer nputTensorBu lder[Query, Opt on[Map[ nt, Double]]](
      SparseMap nfer nputTensorBu lder,
      features)
