package com.tw ter.product_m xer.core.feature.featurestorev1

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .transform.FeatureRena Transform
 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.ml.featurestore.l b.dynam c.BaseGatedFeatures
 mport com.tw ter.ml.featurestore.l b.feature.BoundFeature
 mport com.tw ter.ml.featurestore.l b.feature.BoundFeatureSet
 mport com.tw ter.ml.featurestore.l b.feature.T  l nesAggregat onFra workFeatureGroup
 mport com.tw ter.ml.featurestore.l b.feature.{Feature => FSv1Feature}
 mport com.tw ter.product_m xer.core.feature.ModelFeatureNa 
 mport com.tw ter.product_m xer.core.feature.datarecord.FeatureStoreDataRecordFeature
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.servo.ut l.{Gate => ServoGate}
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport scala.reflect.ClassTag

/**
 * T  base tra  for all feature store features on ProM x. T  should not be constructed d rectly
 * and should  nstead be used through t  ot r  mple ntat ons below
 * @tparam Query Product M xer Query Type
 * @tparam  nput T   nput type t  feature should be keyed on, t   s sa  as Query for query
 *               features and
 * @tparam FeatureStoreEnt y d Feature Store Ent y Type
 * @tparam Value T  type of t  value of t  feature.
 */
sealed tra  BaseFeatureStoreV1Feature[
  -Query <: P pel neQuery,
  - nput,
  FeatureStoreEnt y d <: Ent y d,
  Value]
    extends FeatureStoreDataRecordFeature[ nput, Value]
    w h BaseGatedFeatures[Query] {
  val fsv1Feature: FSv1Feature[FeatureStoreEnt y d, Value]

  val ent y: FeatureStoreV1Ent y[Query,  nput, FeatureStoreEnt y d]

  val enabledParam: Opt on[FSParam[Boolean]]

  overr de f nal lazy val gate: ServoGate[Query] = enabledParam
    .map { param =>
      new ServoGate[P pel neQuery] {
        overr de def apply[U](query: U)( mpl c  asT: <:<[U, P pel neQuery]): Boolean = {
          query.params(param)
        }
      }
    }.getOrElse(ServoGate.True)

  overr de f nal lazy val boundFeatureSet: BoundFeatureSet = new BoundFeatureSet(Set(boundFeature))

  val boundFeature: BoundFeature[FeatureStoreEnt y d, Value]

  /**
   * S nce t  tra   s normally constructed  nl ne, avo d t  anonymous toStr ng and use t  bounded feature na .
   */
  overr de lazy val toStr ng: Str ng = boundFeature.na 
}

/**
 * A un ary (non-aggregate group) feature store feature  n ProM x. T  should be constructed us ng
 * [[FeatureStoreV1Cand dateFeature]] or [[FeatureStoreV1QueryFeature]].
 * @tparam Query Product M xer Query Type
 * @tparam  nput T   nput type t  feature should be keyed on, t   s sa  as Query for query
 *               features and
 * @tparam FeatureStoreEnt y d Feature Store Ent y Type
 * @tparam Value T  type of t  value of t  feature.
 */
sealed tra  FeatureStoreV1Feature[
  -Query <: P pel neQuery,
  - nput,
  FeatureStoreEnt y d <: Ent y d,
  Value]
    extends BaseFeatureStoreV1Feature[Query,  nput, FeatureStoreEnt y d, Value]
    w h ModelFeatureNa  {

  val legacyNa : Opt on[Str ng]
  val defaultValue: Opt on[Value]

  overr de lazy val featureNa : Str ng = boundFeature.na 

  overr de f nal lazy val boundFeature = (legacyNa , defaultValue) match {
    case (So (legacyNa ), So (defaultValue)) =>
      fsv1Feature.b nd(ent y.ent y).w hLegacyNa (legacyNa ).w hDefault(defaultValue)
    case (So (legacyNa ), _) =>
      fsv1Feature.b nd(ent y.ent y).w hLegacyNa (legacyNa )
    case (_, So (defaultValue)) =>
      fsv1Feature.b nd(ent y.ent y).w hDefault(defaultValue)
    case _ =>
      fsv1Feature.b nd(ent y.ent y)
  }

  def fromDataRecordValue(recordValue: boundFeature.feature.mfc.V): Value =
    boundFeature.feature.mfc.fromDataRecordValue(recordValue)
}

/**
 * A feature store aggregated group feature  n ProM x. T  should be constructed us ng
 * [[FeatureStoreV1Cand dateFeatureGroup]] or [[FeatureStoreV1QueryFeatureGroup]].
 *
 * @tparam Query Product M xer Query Type
 * @tparam  nput T   nput type t  feature should be keyed on, t   s sa  as Query for query
 *               features and
 * @tparam FeatureStoreEnt y d Feature Store Ent y Type
 */
abstract class FeatureStoreV1FeatureGroup[
  -Query <: P pel neQuery,
  - nput,
  FeatureStoreEnt y d <: Ent y d: ClassTag]
    extends BaseFeatureStoreV1Feature[Query,  nput, FeatureStoreEnt y d, DataRecord] {
  val keepLegacyNa s: Boolean
  val featureNa Transform: Opt on[FeatureRena Transform]

  val featureGroup: T  l nesAggregat onFra workFeatureGroup[FeatureStoreEnt y d]

  overr de lazy val fsv1Feature: FSv1Feature[FeatureStoreEnt y d, DataRecord] =
    featureGroup.FeaturesAsDataRecord

  overr de f nal lazy val boundFeature = (keepLegacyNa s, featureNa Transform) match {
    case (_, So (transform)) =>
      fsv1Feature.b nd(ent y.ent y).w hLegacy nd v dualFeatureNa s(transform)
    case (true, _) =>
      fsv1Feature.b nd(ent y.ent y).keepLegacyNa s
    case _ =>
      fsv1Feature.b nd(ent y.ent y)
  }
}

sealed tra  BaseFeatureStoreV1QueryFeature[
  -Query <: P pel neQuery,
  FeatureStoreEnt y d <: Ent y d,
  Value]
    extends BaseFeatureStoreV1Feature[Query, Query, FeatureStoreEnt y d, Value] {

  overr de val ent y: FeatureStoreV1QueryEnt y[Query, FeatureStoreEnt y d]
}

tra  FeatureStoreV1QueryFeature[-Query <: P pel neQuery, FeatureStoreEnt y d <: Ent y d, Value]
    extends FeatureStoreV1Feature[Query, Query, FeatureStoreEnt y d, Value]
    w h BaseFeatureStoreV1QueryFeature[Query, FeatureStoreEnt y d, Value]

tra  FeatureStoreV1QueryFeatureGroup[-Query <: P pel neQuery, FeatureStoreEnt y d <: Ent y d]
    extends FeatureStoreV1FeatureGroup[Query, Query, FeatureStoreEnt y d]
    w h BaseFeatureStoreV1QueryFeature[Query, FeatureStoreEnt y d, DataRecord]

object FeatureStoreV1QueryFeature {

  /**
   * Query-based Feature Store backed feature
   * @param feature T  underl ng feature store feature t  represents.
   * @param _ent y T  ent y for b nd ng t  Feature Store features
   * @param _legacyNa  Feature Store legacy na   f requ red
   * @param _defaultValue T  default value to return for t  feature  f not hydrated.
   * @param _enabledParam T  Feature Sw ch Param to gate t  feature, always enabled  f none.
   * @tparam Query T  Product M xer query type t  feature  s keyed on.
   * @tparam FeatureStoreEnt y d Feature Store Ent y  D
   * @tparam Value T  type of t  value t  feature conta ns.
   * @return Product M xer Feature
   */
  def apply[Query <: P pel neQuery, FeatureStoreEnt y d <: Ent y d, Value](
    feature: FSv1Feature[FeatureStoreEnt y d, Value],
    _ent y: FeatureStoreV1QueryEnt y[Query, FeatureStoreEnt y d],
    _legacyNa : Opt on[Str ng] = None,
    _defaultValue: Opt on[Value] = None,
    _enabledParam: Opt on[FSParam[Boolean]] = None
  ): FeatureStoreV1QueryFeature[Query, FeatureStoreEnt y d, Value] =
    new FeatureStoreV1QueryFeature[Query, FeatureStoreEnt y d, Value] {
      overr de val fsv1Feature: FSv1Feature[FeatureStoreEnt y d, Value] = feature
      overr de val ent y: FeatureStoreV1QueryEnt y[Query, FeatureStoreEnt y d] = _ent y
      overr de val legacyNa : Opt on[Str ng] = _legacyNa 
      overr de val defaultValue: Opt on[Value] = _defaultValue
      overr de val enabledParam: Opt on[FSParam[Boolean]] = _enabledParam
    }
}

object FeatureStoreV1QueryFeatureGroup {

  /**
   * Query-based Feature Store Aggregated group backed feature
   *
   * @param featureGroup  T  underl ng aggregat on group feature t  represents.
   * @param _ent y       T  ent y for b nd ng t  Feature Store features
   * @param _enabledParam T  Feature Sw ch Param to gate t  feature, always enabled  f none.
   * @param _keepLegacyNa s W t r to keep t  legacy na s as  s for t  ent re group
   * @param _featureNa Transform Rena  t  ent re group's legacy na s us ng t  [[FeatureRena Transform]]
   * @tparam Query                T  Product M xer query type t  feature  s keyed on.
   * @tparam FeatureStoreEnt y d Feature Store Ent y  D
   *
   * @return Product M xer Feature
   */
  def apply[Query <: P pel neQuery, FeatureStoreEnt y d <: Ent y d: ClassTag](
    _featureGroup: T  l nesAggregat onFra workFeatureGroup[FeatureStoreEnt y d],
    _ent y: FeatureStoreV1QueryEnt y[Query, FeatureStoreEnt y d],
    _enabledParam: Opt on[FSParam[Boolean]] = None,
    _keepLegacyNa s: Boolean = false,
    _featureNa Transform: Opt on[FeatureRena Transform] = None
  ): FeatureStoreV1QueryFeatureGroup[Query, FeatureStoreEnt y d] =
    new FeatureStoreV1QueryFeatureGroup[Query, FeatureStoreEnt y d] {
      overr de val ent y: FeatureStoreV1QueryEnt y[Query, FeatureStoreEnt y d] = _ent y
      overr de val featureGroup: T  l nesAggregat onFra workFeatureGroup[
        FeatureStoreEnt y d
      ] = _featureGroup

      overr de val enabledParam: Opt on[FSParam[Boolean]] = _enabledParam

      overr de val keepLegacyNa s: Boolean = _keepLegacyNa s
      overr de val featureNa Transform: Opt on[FeatureRena Transform] = _featureNa Transform
    }
}

sealed tra  BaseFeatureStoreV1Cand dateFeature[
  -Query <: P pel neQuery,
  - nput <: Un versalNoun[Any],
  FeatureStoreEnt y d <: Ent y d,
  Value]
    extends BaseFeatureStoreV1Feature[Query,  nput, FeatureStoreEnt y d, Value] {

  overr de val ent y: FeatureStoreV1Cand dateEnt y[Query,  nput, FeatureStoreEnt y d]
}

tra  FeatureStoreV1Cand dateFeature[
  -Query <: P pel neQuery,
  - nput <: Un versalNoun[Any],
  FeatureStoreEnt y d <: Ent y d,
  Value]
    extends FeatureStoreV1Feature[Query,  nput, FeatureStoreEnt y d, Value]
    w h BaseFeatureStoreV1Cand dateFeature[Query,  nput, FeatureStoreEnt y d, Value]

tra  FeatureStoreV1Cand dateFeatureGroup[
  -Query <: P pel neQuery,
  - nput <: Un versalNoun[Any],
  FeatureStoreEnt y d <: Ent y d]
    extends FeatureStoreV1FeatureGroup[Query,  nput, FeatureStoreEnt y d]
    w h BaseFeatureStoreV1Cand dateFeature[Query,  nput, FeatureStoreEnt y d, DataRecord]

object FeatureStoreV1Cand dateFeature {

  /**
   * Cand date-based Feature Store backed feature
   * @param feature T  underl ng feature store feature t  represents.
   * @param _ent y T  ent y for b nd ng t  Feature Store features
   * @param _legacyNa  Feature Store legacy na   f requ red
   * @param _defaultValue T  default value to return for t  feature  f not hydrated.
   * @param _enabledParam T  Feature Sw ch Param to gate t  feature, always enabled  f none.
   * @tparam Query T  Product M xer query type t  feature  s keyed on.
   * @tparam FeatureStoreEnt y d T  feature store ent y type
   * @tparam  nput T  type of t  cand date t  feature  s keyed on
   * @tparam Value T  type of value t  feature conta ns.
   * @return Product M xer Feature
   */
  def apply[
    Query <: P pel neQuery,
     nput <: Un versalNoun[Any],
    FeatureStoreEnt y d <: Ent y d,
    Value
  ](
    feature: FSv1Feature[FeatureStoreEnt y d, Value],
    _ent y: FeatureStoreV1Cand dateEnt y[Query,  nput, FeatureStoreEnt y d],
    _legacyNa : Opt on[Str ng] = None,
    _defaultValue: Opt on[Value] = None,
    _enabledParam: Opt on[FSParam[Boolean]] = None
  ): FeatureStoreV1Cand dateFeature[Query,  nput, FeatureStoreEnt y d, Value] =
    new FeatureStoreV1Cand dateFeature[Query,  nput, FeatureStoreEnt y d, Value] {
      overr de val fsv1Feature: FSv1Feature[FeatureStoreEnt y d, Value] = feature
      overr de val ent y: FeatureStoreV1Cand dateEnt y[Query,  nput, FeatureStoreEnt y d] =
        _ent y
      overr de val legacyNa : Opt on[Str ng] = _legacyNa 
      overr de val defaultValue: Opt on[Value] = _defaultValue
      overr de val enabledParam: Opt on[FSParam[Boolean]] = _enabledParam
    }
}

object FeatureStoreV1Cand dateFeatureGroup {

  /**
   * Cand date-based Feature Store Aggregated group backed feature
   *
   * @param featureGroup          T  underl ng aggregat on group feature t  represents.
   * @param _ent y               T  ent y for b nd ng t  Feature Store features
   * @param _enabledParam         T  Feature Sw ch Param to gate t  feature, always enabled  f none.
   * @param _keepLegacyNa s      W t r to keep t  legacy na s as  s for t  ent re group
   * @param _featureNa Transform Rena  t  ent re group's legacy na s us ng t  [[FeatureRena Transform]]
   * @tparam Query                T  Product M xer query type t  feature  s keyed on.
   * @tparam  nput T  type of t  cand date t  feature  s keyed on
   * @tparam FeatureStoreEnt y d Feature Store Ent y  D
   *
   * @return Product M xer Feature
   */
  def apply[
    Query <: P pel neQuery,
     nput <: Un versalNoun[Any],
    FeatureStoreEnt y d <: Ent y d: ClassTag,
  ](
    _featureGroup: T  l nesAggregat onFra workFeatureGroup[FeatureStoreEnt y d],
    _ent y: FeatureStoreV1Cand dateEnt y[Query,  nput, FeatureStoreEnt y d],
    _enabledParam: Opt on[FSParam[Boolean]] = None,
    _keepLegacyNa s: Boolean = false,
    _featureNa Transform: Opt on[FeatureRena Transform] = None
  ): FeatureStoreV1Cand dateFeatureGroup[Query,  nput, FeatureStoreEnt y d] =
    new FeatureStoreV1Cand dateFeatureGroup[Query,  nput, FeatureStoreEnt y d] {
      overr de val ent y: FeatureStoreV1Cand dateEnt y[Query,  nput, FeatureStoreEnt y d] =
        _ent y
      overr de val featureGroup: T  l nesAggregat onFra workFeatureGroup[
        FeatureStoreEnt y d
      ] = _featureGroup

      overr de val enabledParam: Opt on[FSParam[Boolean]] = _enabledParam

      overr de val keepLegacyNa s: Boolean = _keepLegacyNa s
      overr de val featureNa Transform: Opt on[FeatureRena Transform] = _featureNa Transform
    }
}
