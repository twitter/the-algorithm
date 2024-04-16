package com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.featurestorev1

 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.ml.featurestore.l b.dynam c.BaseDynam cHydrat onConf g
 mport com.tw ter.product_m xer.core.feature.featurestorev1.BaseFeatureStoreV1Cand dateFeature
 mport com.tw ter.product_m xer.core.feature.featurestorev1.BaseFeatureStoreV1QueryFeature
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class FeatureStoreV1QueryFeatureHydrat onConf g[Query <: P pel neQuery](
  features: Set[BaseFeatureStoreV1QueryFeature[Query, _ <: Ent y d, _]])
    extends BaseDynam cHydrat onConf g[
      Query,
      BaseFeatureStoreV1QueryFeature[Query, _ <: Ent y d, _]
    ](features)

case class FeatureStoreV1Cand dateFeatureHydrat onConf g[
  Query <: P pel neQuery,
   nput <: Un versalNoun[Any]
](
  features: Set[BaseFeatureStoreV1Cand dateFeature[Query,  nput, _ <: Ent y d, _]])
    extends BaseDynam cHydrat onConf g[
      Query,
      BaseFeatureStoreV1Cand dateFeature[Query,  nput, _ <: Ent y d, _]
    ](features)
