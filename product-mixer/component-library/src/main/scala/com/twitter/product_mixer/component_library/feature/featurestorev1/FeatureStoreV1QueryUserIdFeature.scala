package com.tw ter.product_m xer.component_l brary.feature.featurestorev1

 mport com.tw ter.ml.ap .transform.FeatureRena Transform
 mport com.tw ter.ml.featurestore.catalog.ent  es
 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.ml.featurestore.l b.User d
 mport com.tw ter.ml.featurestore.l b.ent y.Ent y
 mport com.tw ter.ml.featurestore.l b.ent y.Ent yW h d
 mport com.tw ter.ml.featurestore.l b.feature.T  l nesAggregat onFra workFeatureGroup
 mport com.tw ter.ml.featurestore.l b.feature.{Feature => FSv1Feature}
 mport com.tw ter.product_m xer.core.feature.featurestorev1._
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport scala.reflect.ClassTag
object FeatureStoreV1QueryUser dFeature {
  def apply[Query <: P pel neQuery, Value](
    feature: FSv1Feature[User d, Value],
    legacyNa : Opt on[Str ng] = None,
    defaultValue: Opt on[Value] = None,
    enabledParam: Opt on[FSParam[Boolean]] = None
  ): FeatureStoreV1Feature[Query, Query, _ <: Ent y d, Value]
    w h FeatureStoreV1QueryFeature[Query, _ <: Ent y d, Value] =
    FeatureStoreV1QueryFeature(feature, QueryUser dEnt y, legacyNa , defaultValue, enabledParam)
}

object FeatureStoreV1QueryUser dAggregateFeature {
  def apply[Query <: P pel neQuery](
    featureGroup: T  l nesAggregat onFra workFeatureGroup[User d],
    enabledParam: Opt on[FSParam[Boolean]] = None,
    keepLegacyNa s: Boolean = false,
    featureNa Transform: Opt on[FeatureRena Transform] = None
  ): FeatureStoreV1QueryFeatureGroup[Query, _ <: Ent y d] =
    FeatureStoreV1QueryFeatureGroup(
      featureGroup,
      QueryUser dEnt y,
      enabledParam,
      keepLegacyNa s,
      featureNa Transform)(( mpl c ly[ClassTag[User d]]))
}

object QueryUser dEnt y extends FeatureStoreV1QueryEnt y[P pel neQuery, User d] {
  overr de val ent y: Ent y[User d] = ent  es.core.User

  overr de def ent yW h d(query: P pel neQuery): Ent yW h d[User d] =
    ent y.w h d(User d(query.getUser dLoggedOutSupport))
}
