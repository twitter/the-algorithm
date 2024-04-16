package com.tw ter.product_m xer.component_l brary.selector.sorter.featurestorev1

 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.product_m xer.component_l brary.selector.sorter.Ascend ng
 mport com.tw ter.product_m xer.component_l brary.selector.sorter.Descend ng
 mport com.tw ter.product_m xer.component_l brary.selector.sorter.FeatureValueSorter.featureValueSortDefaultValue
 mport com.tw ter.product_m xer.component_l brary.selector.sorter.SorterFromOrder ng
 mport com.tw ter.product_m xer.component_l brary.selector.sorter.SorterProv der
 mport com.tw ter.product_m xer.core.feature.featuremap.featurestorev1.FeatureStoreV1FeatureMap._
 mport com.tw ter.product_m xer.core.feature.featurestorev1.FeatureStoreV1Cand dateFeature
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport scala.reflect.runt  .un verse._

/**
 * Feature Store v1 vers on of [[com.tw ter.product_m xer.component_l brary.selector.sorter.FeatureValueSorter]]
 */
object FeatureStoreV1FeatureValueSorter {

  /**
   * Sort by a Feature Store v1 feature value ascend ng.  f t  feature fa led or  s m ss ng, use an
   *  nferred default based on t  type of [[FeatureValue]]. For Nu r c values t   s t  M nValue
   * (e.g. Long.M nValue, Double.M nValue).
   *
   * @param feature Feature Store v1 feature w h value to sort by
   * @param typeTag allows for  nferr ng default value from t  FeatureValue type.
   *                See [[com.tw ter.product_m xer.component_l brary.selector.sorter.FeatureValueSorter.featureValueSortDefaultValue]]
   * @tparam Cand date cand date for t  feature
   * @tparam FeatureValue feature value w h an [[Order ng]] context bound
   */
  def ascend ng[Cand date <: Un versalNoun[Any], FeatureValue: Order ng](
    feature: FeatureStoreV1Cand dateFeature[P pel neQuery, Cand date, _ <: Ent y d, FeatureValue]
  )(
     mpl c  typeTag: TypeTag[FeatureValue]
  ): SorterProv der = {
    val defaultFeatureValue: FeatureValue = featureValueSortDefaultValue(feature, Ascend ng)

    ascend ng(feature, defaultFeatureValue)
  }

  /**
   * Sort by a Feature Store v1 feature value ascend ng.  f t  feature fa led or  s m ss ng, use
   * t  prov ded default.
   *
   * @param feature Feature Store v1 feature w h value to sort by
   * @tparam Cand date cand date for t  feature
   * @tparam FeatureValue feature value w h an [[Order ng]] context bound
   */
  def ascend ng[Cand date <: Un versalNoun[Any], FeatureValue: Order ng](
    feature: FeatureStoreV1Cand dateFeature[P pel neQuery, Cand date, _ <: Ent y d, FeatureValue],
    defaultFeatureValue: FeatureValue
  ): SorterProv der = {
    val order ng = Order ng.by[Cand dateW hDeta ls, FeatureValue](
      _.features.getOrElseFeatureStoreV1Cand dateFeature(feature, defaultFeatureValue))

    SorterFromOrder ng(order ng, Ascend ng)
  }

  /**
   * Sort by a Feature Store v1 feature value descend ng.  f t  feature fa led or  s m ss ng, use
   * an  nferred default based on t  type of [[FeatureValue]]. For Nu r c values t   s t 
   * MaxValue (e.g. Long.MaxValue, Double.MaxValue).
   *
   * @param feature Feature Store v1 feature w h value to sort by
   * @param typeTag allows for  nferr ng default value from t  FeatureValue type.
   *                See [[com.tw ter.product_m xer.component_l brary.selector.sorter.FeatureValueSorter.featureValueSortDefaultValue]]
   * @tparam Cand date cand date for t  feature
   * @tparam FeatureValue feature value w h an [[Order ng]] context bound
   */
  def descend ng[Cand date <: Un versalNoun[Any], FeatureValue: Order ng](
    feature: FeatureStoreV1Cand dateFeature[P pel neQuery, Cand date, _ <: Ent y d, FeatureValue]
  )(
     mpl c  typeTag: TypeTag[FeatureValue]
  ): SorterProv der = {
    val defaultFeatureValue: FeatureValue = featureValueSortDefaultValue(feature, Descend ng)

    descend ng(feature, defaultFeatureValue)
  }

  /**
   * Sort by a Feature Store v1 feature value descend ng.  f t  feature fa led or  s m ss ng, use
   * t  prov ded default.
   *
   * @param feature Feature Store v1 feature w h value to sort by
   * @tparam Cand date cand date for t  feature
   * @tparam FeatureValue feature value w h an [[Order ng]] context bound
   */
  def descend ng[Cand date <: Un versalNoun[Any], FeatureValue: Order ng](
    feature: FeatureStoreV1Cand dateFeature[P pel neQuery, Cand date, _ <: Ent y d, FeatureValue],
    defaultFeatureValue: FeatureValue
  ): SorterProv der = {
    val order ng = Order ng.by[Cand dateW hDeta ls, FeatureValue](
      _.features.getOrElseFeatureStoreV1Cand dateFeature(feature, defaultFeatureValue))

    SorterFromOrder ng(order ng, Descend ng)
  }
}
