package com.tw ter.product_m xer.component_l brary.feature.featurestorev1

 mport com.tw ter.ml.featurestore.catalog.ent  es
 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.ml.featurestore.l b.User d
 mport com.tw ter.ml.featurestore.l b.ent y.Ent y
 mport com.tw ter.ml.featurestore.l b.ent y.Ent yW h d
 mport com.tw ter.ml.featurestore.l b.feature.{Feature => FSv1Feature}
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseUserCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featurestorev1._
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .FSParam

object FeatureStoreV1UserCand dateUser dFeature {
  def apply[Query <: P pel neQuery, Cand date <: BaseUserCand date, Value](
    feature: FSv1Feature[User d, Value],
    legacyNa : Opt on[Str ng] = None,
    defaultValue: Opt on[Value] = None,
    enabledParam: Opt on[FSParam[Boolean]] = None
  ): FeatureStoreV1Cand dateFeature[Query, Cand date, _ <: Ent y d, Value] =
    FeatureStoreV1Cand dateFeature(
      feature,
      UserCand dateUser dEnt y,
      legacyNa ,
      defaultValue,
      enabledParam)
}

object UserCand dateUser dEnt y
    extends FeatureStoreV1Cand dateEnt y[P pel neQuery, BaseUserCand date, User d] {
  overr de val ent y: Ent y[User d] = ent  es.core.User

  overr de def ent yW h d(
    query: P pel neQuery,
    user: BaseUserCand date,
    ex st ngFeatures: FeatureMap
  ): Ent yW h d[User d] =
    ent y.w h d(User d(user. d))
}
