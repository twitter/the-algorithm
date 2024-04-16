package com.tw ter.product_m xer.core.feature.featuremap.featurestorev1

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.M ss ngFeatureExcept on
 mport com.tw ter.product_m xer.core.feature.featurestorev1.FeatureStoreV1Cand dateFeature
 mport com.tw ter.product_m xer.core.feature.featurestorev1.FeatureStoreV1Cand dateFeatureGroup
 mport com.tw ter.product_m xer.core.feature.featurestorev1.FeatureStoreV1QueryFeature
 mport com.tw ter.product_m xer.core.feature.featurestorev1.FeatureStoreV1QueryFeatureGroup
 mport com.tw ter.product_m xer.core.feature.featurestorev1.featurevalue.FeatureStoreV1ResponseFeature
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.ut l.Try

object FeatureStoreV1FeatureMap {

  /**
   *  mpl c ly add conven ence accessors for FeatureStoreV1 features  n [[FeatureMap]]. Note that
   *   cannot add t se  thods d rectly to [[FeatureMap]] because   would  ntroduce a c rcular
   * dependency ([[P pel neQuery]] depends on [[FeatureMap]], and t   thods below depend on
   * [[P pel neQuery]])
   *
   * @param featureMap t  featureMap   are wrapp ng
   * @note T  FeatureStoreV1Feature::defaultValue set on t  BoundFeature  s only used and set
   *       dur ng Pred ct onRecord to DataRecord convers on. T refore, t  default w ll not be set
   *       on t  Pred ct onRecord value  f read ng from   d rectly, and as such for conven ence
   *       t  defaultValue  s manually returned dur ng retr eval from Pred ct onRecord.
   * @note t  Value gener c type on t   thods below cannot be passed to
   *       FeatureStoreV1QueryFeature's Value gener c type. Wh le t   s actually t  sa  type,
   *       (note t  expl c  type cast back to Value),   must  nstead use an ex stent al on
   *       FeatureStoreV1QueryFeature s nce    s constructed w h an ex stent al for t  Value
   *       gener c (see [[FeatureStoreV1QueryFeature]] and [[FeatureStoreV1Cand dateFeature]])
   */
   mpl c  class FeatureStoreV1FeatureMapAccessors(pr vate val featureMap: FeatureMap) {

    def getFeatureStoreV1QueryFeature[Query <: P pel neQuery, Value](
      feature: FeatureStoreV1QueryFeature[Query, _ <: Ent y d, Value]
    ): Value =
      getOrElseFeatureStoreV1QueryFeature(
        feature,
        feature.defaultValue.getOrElse {
          throw M ss ngFeatureExcept on(feature)
        })

    def getFeatureStoreV1QueryFeatureTry[Query <: P pel neQuery, Value](
      feature: FeatureStoreV1QueryFeature[Query, _ <: Ent y d, Value]
    ): Try[Value] =
      Try(getFeatureStoreV1QueryFeature(feature))

    def getOrElseFeatureStoreV1QueryFeature[Query <: P pel neQuery, Value](
      feature: FeatureStoreV1QueryFeature[Query, _ <: Ent y d, Value],
      default: => Value
    ): Value = {

      /**
       * FeatureStoreV1ResponseFeature should never be m ss ng from t  FeatureMap as FSv1  s
       * guaranteed to return a pred ct on record per feature store request. Ho ver, t  may be
       * called on cand dates that never hydrated FSv1 features. For example by
       * [[com.tw ter.product_m xer.component_l brary.selector.sorter.featurestorev1.FeatureStoreV1FeatureValueSorter]]
       */
      val featureStoreV1FeatureValueOpt = featureMap.getTry(FeatureStoreV1ResponseFeature).toOpt on

      val dataRecordValue: Opt on[Value] = featureStoreV1FeatureValueOpt.flatMap {
        featureStoreV1FeatureValue =>
          featureStoreV1FeatureValue.r chDataRecord.getFeatureValueOpt(
            feature.boundFeature.mlAp Feature)(feature.fromDataRecordValue)
      }

      dataRecordValue.getOrElse(default)
    }

    def getFeatureStoreV1Cand dateFeature[
      Query <: P pel neQuery,
      Cand date <: Un versalNoun[Any],
      Value
    ](
      feature: FeatureStoreV1Cand dateFeature[Query, Cand date, _ <: Ent y d, Value]
    ): Value =
      getOrElseFeatureStoreV1Cand dateFeature(
        feature,
        feature.defaultValue.getOrElse {
          throw M ss ngFeatureExcept on(feature)
        })

    def getFeatureStoreV1Cand dateFeatureTry[
      Query <: P pel neQuery,
      Cand date <: Un versalNoun[Any],
      Value
    ](
      feature: FeatureStoreV1Cand dateFeature[Query, Cand date, _ <: Ent y d, Value]
    ): Try[Value] =
      Try(getFeatureStoreV1Cand dateFeature(feature))

    def getOrElseFeatureStoreV1Cand dateFeature[
      Query <: P pel neQuery,
      Cand date <: Un versalNoun[Any],
      Value
    ](
      feature: FeatureStoreV1Cand dateFeature[Query, Cand date, _ <: Ent y d, Value],
      default: => Value
    ): Value = {

      /**
       * FeatureStoreV1ResponseFeature should never be m ss ng from t  FeatureMap as FSv1  s
       * guaranteed to return a pred ct on record per feature store request. Ho ver, t  may be
       * called on cand dates that never hydrated FSv1 features. For example by
       * [[com.tw ter.product_m xer.component_l brary.selector.sorter.featurestorev1.FeatureStoreV1FeatureValueSorter]]
       */
      val featureStoreV1FeatureValueOpt = featureMap.getTry(FeatureStoreV1ResponseFeature).toOpt on

      val dataRecordValue: Opt on[Value] = featureStoreV1FeatureValueOpt.flatMap {
        featureStoreV1FeatureValue =>
          featureStoreV1FeatureValue.r chDataRecord.getFeatureValueOpt(
            feature.boundFeature.mlAp Feature)(feature.fromDataRecordValue)
      }

      dataRecordValue.getOrElse(default)
    }

    /**
     * Get queryFeatureGroup, wh ch  s store  n t  featureMap as a DataRecord nAFeature
     *   doesn't have t  mlAp Feature as ot r regular FeatureStoreV1 features
     * Please refer to [[com.tw ter.product_m xer.core.feature.datarecord.DataRecord nAFeature]] scaladoc for more deta ls
     */
    def getFeatureStoreV1QueryFeatureGroup[Query <: P pel neQuery](
      featureGroup: FeatureStoreV1QueryFeatureGroup[Query, _ <: Ent y d]
    ): DataRecord =
      getOrElseFeatureStoreV1QueryFeatureGroup(
        featureGroup,
        throw M ss ngFeatureExcept on(featureGroup)
      )

    def getFeatureStoreV1Cand dateFeatureGroupTry[Query <: P pel neQuery](
      featureGroup: FeatureStoreV1QueryFeatureGroup[Query, _ <: Ent y d]
    ): Try[DataRecord] =
      Try(getFeatureStoreV1QueryFeatureGroup(featureGroup))

    def getOrElseFeatureStoreV1QueryFeatureGroup[Query <: P pel neQuery](
      featureGroup: FeatureStoreV1QueryFeatureGroup[Query, _ <: Ent y d],
      default: => DataRecord
    ): DataRecord = {
      featureMap.getTry(featureGroup).toOpt on.getOrElse(default)
    }

    /**
     * Get cand dateFeatureGroup, wh ch  s store  n t  featureMap as a DataRecord nAFeature
     *   doesn't have t  mlAp Feature as ot r regular FeatureStoreV1 features
     * Please refer to [[com.tw ter.product_m xer.core.feature.datarecord.DataRecord nAFeature]] scaladoc for more deta ls
     */
    def getFeatureStoreV1Cand dateFeatureGroup[
      Query <: P pel neQuery,
      Cand date <: Un versalNoun[Any]
    ](
      featureGroup: FeatureStoreV1Cand dateFeatureGroup[Query, Cand date, _ <: Ent y d]
    ): DataRecord =
      getOrElseFeatureStoreV1Cand dateFeatureGroup(
        featureGroup,
        throw M ss ngFeatureExcept on(featureGroup)
      )

    def getFeatureStoreV1Cand dateFeatureGroupTry[
      Query <: P pel neQuery,
      Cand date <: Un versalNoun[Any]
    ](
      featureGroup: FeatureStoreV1Cand dateFeatureGroup[Query, Cand date, _ <: Ent y d]
    ): Try[DataRecord] =
      Try(getFeatureStoreV1Cand dateFeatureGroup(featureGroup))

    def getOrElseFeatureStoreV1Cand dateFeatureGroup[
      Query <: P pel neQuery,
      Cand date <: Un versalNoun[Any]
    ](
      featureGroup: FeatureStoreV1Cand dateFeatureGroup[Query, Cand date, _ <: Ent y d],
      default: => DataRecord
    ): DataRecord = {
      featureMap.getTry(featureGroup).toOpt on.getOrElse(default)
    }

    def getOrElseFeatureStoreV1FeatureDataRecord(
      default: => DataRecord
    ) = {
      val featureStoreV1FeatureValueOpt = featureMap.getTry(FeatureStoreV1ResponseFeature).toOpt on

      featureStoreV1FeatureValueOpt
        .map { featureStoreV1FeatureValue =>
          featureStoreV1FeatureValue.r chDataRecord.getRecord
        }.getOrElse(default)
    }
  }
}
