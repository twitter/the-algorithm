package com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.featurestorev1

 mport com.tw ter.ml.featurestore.l b.dynam c.BaseDynam cHydrat onConf g
 mport com.tw ter.ml.featurestore.l b.dynam c.BaseGatedFeatures
 mport com.tw ter.ml.featurestore.l b.dynam c.Dynam cFeatureStoreCl ent
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  FeatureStoreV1Dynam cCl entBu lder {
  def bu ld[Query <: P pel neQuery](
    dynam cHydrat onConf g: BaseDynam cHydrat onConf g[Query, _ <: BaseGatedFeatures[Query]]
  ): Dynam cFeatureStoreCl ent[Query]
}
