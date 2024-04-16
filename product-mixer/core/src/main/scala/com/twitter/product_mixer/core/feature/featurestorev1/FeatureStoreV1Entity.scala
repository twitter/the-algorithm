package com.tw ter.product_m xer.core.feature.featurestorev1

 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.ml.featurestore.l b.ent y.Ent y
 mport com.tw ter.ml.featurestore.l b.ent y.Ent yW h d
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

sealed tra  FeatureStoreV1Ent y[
  -Query <: P pel neQuery,
  - nput,
  FeatureStoreEnt y d <: Ent y d] {

  val ent y: Ent y[FeatureStoreEnt y d]
}

tra  FeatureStoreV1QueryEnt y[-Query <: P pel neQuery, FeatureStoreEnt y d <: Ent y d]
    extends FeatureStoreV1Ent y[Query, Query, FeatureStoreEnt y d] {

  def ent yW h d(query: Query): Ent yW h d[FeatureStoreEnt y d]
}

tra  FeatureStoreV1Cand dateEnt y[
  -Query <: P pel neQuery,
  - nput <: Un versalNoun[Any],
  FeatureStoreEnt y d <: Ent y d]
    extends FeatureStoreV1Ent y[Query,  nput, FeatureStoreEnt y d] {

  def ent yW h d(
    query: Query,
     nput:  nput,
    ex st ngFeatures: FeatureMap
  ): Ent yW h d[FeatureStoreEnt y d]
}
