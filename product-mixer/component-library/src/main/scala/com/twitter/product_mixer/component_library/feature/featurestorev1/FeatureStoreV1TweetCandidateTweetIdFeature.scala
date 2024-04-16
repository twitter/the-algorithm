package com.tw ter.product_m xer.component_l brary.feature.featurestorev1

 mport com.tw ter.ml.ap .transform.FeatureRena Transform
 mport com.tw ter.ml.featurestore.catalog.ent  es
 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.ml.featurestore.l b.T et d
 mport com.tw ter.ml.featurestore.l b.ent y.Ent y
 mport com.tw ter.ml.featurestore.l b.ent y.Ent yW h d
 mport com.tw ter.ml.featurestore.l b.feature.T  l nesAggregat onFra workFeatureGroup
 mport com.tw ter.ml.featurestore.l b.feature.{Feature => FSv1Feature}
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featurestorev1._
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .FSParam

object FeatureStoreV1T etCand dateT et dFeature {
  def apply[Query <: P pel neQuery, Cand date <: BaseT etCand date, Value](
    feature: FSv1Feature[T et d, Value],
    legacyNa : Opt on[Str ng] = None,
    defaultValue: Opt on[Value] = None,
    enabledParam: Opt on[FSParam[Boolean]] = None
  ): FeatureStoreV1Cand dateFeature[Query, Cand date, _ <: Ent y d, Value] =
    FeatureStoreV1Cand dateFeature(
      feature,
      T etCand dateT et dEnt y,
      legacyNa ,
      defaultValue,
      enabledParam)
}

object FeatureStoreV1T etCand dateT et dAggregateFeature {
  def apply[Query <: P pel neQuery, Cand date <: BaseT etCand date](
    featureGroup: T  l nesAggregat onFra workFeatureGroup[T et d],
    enabledParam: Opt on[FSParam[Boolean]] = None,
    keepLegacyNa s: Boolean = false,
    featureNa Transform: Opt on[FeatureRena Transform] = None
  ): FeatureStoreV1Cand dateFeatureGroup[Query, Cand date, _ <: Ent y d] =
    FeatureStoreV1Cand dateFeatureGroup(
      featureGroup,
      T etCand dateT et dEnt y,
      enabledParam,
      keepLegacyNa s,
      featureNa Transform
    )
}

object T etCand dateT et dEnt y
    extends FeatureStoreV1Cand dateEnt y[P pel neQuery, BaseT etCand date, T et d] {
  overr de val ent y: Ent y[T et d] = ent  es.core.T et

  overr de def ent yW h d(
    query: P pel neQuery,
    t et: BaseT etCand date,
    ex st ngFeatures: FeatureMap
  ): Ent yW h d[T et d] =
    ent y.w h d(T et d(t et. d))
}
