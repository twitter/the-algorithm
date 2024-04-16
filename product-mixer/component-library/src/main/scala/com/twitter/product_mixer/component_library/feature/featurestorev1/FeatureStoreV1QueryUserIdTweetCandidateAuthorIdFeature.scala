package com.tw ter.product_m xer.component_l brary.feature.featurestorev1

 mport com.tw ter.ml.ap .transform.FeatureRena Transform
 mport com.tw ter.ml.featurestore.catalog.ent  es
 mport com.tw ter.ml.featurestore.l b.EdgeEnt y d
 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.ml.featurestore.l b.User d
 mport com.tw ter.ml.featurestore.l b.ent y.Ent y
 mport com.tw ter.ml.featurestore.l b.ent y.Ent yW h d
 mport com.tw ter.ml.featurestore.l b.feature.T  l nesAggregat onFra workFeatureGroup
 mport com.tw ter.ml.featurestore.l b.feature.{Feature => FSv1Feature}
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etAuthor dFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featurestorev1._
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport scala.reflect.ClassTag

object FeatureStoreV1QueryUser dT etCand dateAuthor dFeature {
  def apply[Query <: P pel neQuery, Value](
    feature: FSv1Feature[EdgeEnt y d[User d, User d], Value],
    legacyNa : Opt on[Str ng] = None,
    defaultValue: Opt on[Value] = None,
    enabledParam: Opt on[FSParam[Boolean]] = None
  ): FeatureStoreV1Cand dateFeature[Query, T etCand date, _ <: Ent y d, Value] =
    FeatureStoreV1Cand dateFeature(
      feature,
      QueryUser dT etCand dateAuthor dEnt y,
      legacyNa ,
      defaultValue,
      enabledParam)
}

object FeatureStoreV1QueryUser dT etCand dateAuthor dAggregateFeature {
  def apply[Query <: P pel neQuery](
    featureGroup: T  l nesAggregat onFra workFeatureGroup[EdgeEnt y d[User d, User d]],
    enabledParam: Opt on[FSParam[Boolean]] = None,
    keepLegacyNa s: Boolean = false,
    featureNa Transform: Opt on[FeatureRena Transform] = None
  ): FeatureStoreV1Cand dateFeatureGroup[Query, T etCand date, _ <: Ent y d] =
    FeatureStoreV1Cand dateFeatureGroup(
      featureGroup,
      QueryUser dT etCand dateAuthor dEnt y,
      enabledParam,
      keepLegacyNa s,
      featureNa Transform
    )( mpl c ly[ClassTag[EdgeEnt y d[User d, User d]]])
}

object QueryUser dT etCand dateAuthor dEnt y
    extends FeatureStoreV1Cand dateEnt y[
      P pel neQuery,
      T etCand date,
      EdgeEnt y d[User d, User d]
    ] {
  overr de val ent y: Ent y[EdgeEnt y d[User d, User d]] = ent  es.core.UserAuthor

  overr de def ent yW h d(
    query: P pel neQuery,
    t et: T etCand date,
    ex st ngFeatures: FeatureMap
  ): Ent yW h d[EdgeEnt y d[User d, User d]] =
    ent y.w h d(
      EdgeEnt y d(
        User d(query.getUser dLoggedOutSupport),
        User d(ex st ngFeatures.get(T etAuthor dFeature))))
}
