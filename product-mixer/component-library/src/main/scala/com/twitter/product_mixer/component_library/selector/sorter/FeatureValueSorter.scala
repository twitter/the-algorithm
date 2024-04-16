package com.tw ter.product_m xer.component_l brary.selector.sorter

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport scala.reflect.runt  .un verse._

object FeatureValueSorter {

  /**
   * Sort by a feature value ascend ng.  f t  feature fa led or  s m ss ng, use an  nferred default
   * based on t  type of [[FeatureValue]]. For Nu r c values t   s t  M nValue
   * (e.g. Long.M nValue, Double.M nValue).
   *
   * @param feature feature w h value to sort by
   * @param dum  mpl c  due to type erasure,  mpl c  used to d samb guate `def ascend ng()`
   *                      bet en def w h param `feature: Feature[Cand date, FeatureValue]`
   *                      from def w h param `feature: Feature[Cand date, Opt on[FeatureValue]]`
   * @param typeTag allows for  nferr ng default value from t  FeatureValue type.
   *                See [[featureValueSortDefaultValue]]
   * @tparam Cand date cand date for t  feature
   * @tparam FeatureValue feature value w h an [[Order ng]] context bound
   */
  def ascend ng[Cand date <: Un versalNoun[Any], FeatureValue: Order ng](
    feature: Feature[Cand date, FeatureValue]
  )(
     mpl c  dum  mpl c : Dum  mpl c ,
    typeTag: TypeTag[FeatureValue]
  ): SorterProv der = {
    val defaultFeatureValue: FeatureValue = featureValueSortDefaultValue(feature, Ascend ng)

    ascend ng(feature, defaultFeatureValue)
  }

  /**
   * Sort by a feature value ascend ng.  f t  feature fa led or  s m ss ng, use t  prov ded
   * default.
   *
   * @param feature feature w h value to sort by
   * @param dum  mpl c  due to type erasure,  mpl c  used to d samb guate `def ascend ng()`
   *                      bet en def w h param `feature: Feature[Cand date, FeatureValue]`
   *                      from def w h param `feature: Feature[Cand date, Opt on[FeatureValue]]`
   * @tparam Cand date cand date for t  feature
   * @tparam FeatureValue feature value w h an [[Order ng]] context bound
   */
  def ascend ng[Cand date <: Un versalNoun[Any], FeatureValue: Order ng](
    feature: Feature[Cand date, FeatureValue],
    defaultFeatureValue: FeatureValue
  )(
     mpl c  dum  mpl c : Dum  mpl c 
  ): SorterProv der = {
    val order ng = Order ng.by[Cand dateW hDeta ls, FeatureValue](
      _.features.getOrElse(feature, defaultFeatureValue))

    SorterFromOrder ng(order ng, Ascend ng)
  }

  /**
   * Sort by an opt onal feature value ascend ng.  f t  feature fa led or  s m ss ng, use an
   *  nferred default based on t  type of [[FeatureValue]]. For Nu r c values t   s t  M nValue
   * (e.g. Long.M nValue, Double.M nValue).
   *
   * @param feature feature w h value to sort by
   * @param typeTag allows for  nferr ng default value from t  FeatureValue type.
   *                See [[featureOpt onalValueSortDefaultValue]]
   * @tparam Cand date cand date for t  feature
   * @tparam FeatureValue feature value w h an [[Order ng]] context bound
   */
  def ascend ng[Cand date <: Un versalNoun[Any], FeatureValue: Order ng](
    feature: Feature[Cand date, Opt on[FeatureValue]]
  )(
     mpl c  typeTag: TypeTag[FeatureValue]
  ): SorterProv der = {
    val defaultFeatureValue: FeatureValue = featureOpt onalValueSortDefaultValue(feature, Ascend ng)

    ascend ng(feature, defaultFeatureValue)
  }

  /**
   * Sort by an opt onal feature value ascend ng.  f t  feature fa led or  s m ss ng, use t 
   * prov ded default.
   *
   * @param feature feature w h value to sort by
   * @tparam Cand date cand date for t  feature
   * @tparam FeatureValue feature value w h an [[Order ng]] context bound
   */
  def ascend ng[Cand date <: Un versalNoun[Any], FeatureValue: Order ng](
    feature: Feature[Cand date, Opt on[FeatureValue]],
    defaultFeatureValue: FeatureValue
  ): SorterProv der = {
    val order ng = Order ng.by[Cand dateW hDeta ls, FeatureValue](
      _.features.getOrElse(feature, None).getOrElse(defaultFeatureValue))

    SorterFromOrder ng(order ng, Ascend ng)
  }

  /**
   * Sort by a feature value descend ng.  f t  feature fa led or  s m ss ng, use an  nferred
   * default based on t  type of [[FeatureValue]]. For Nu r c values t   s t  MaxValue
   * (e.g. Long.MaxValue, Double.MaxValue).
   *
   * @param feature feature w h value to sort by
   * @param dum  mpl c  due to type erasure,  mpl c  used to d samb guate `def descend ng()`
   *                      bet en def w h param `feature: Feature[Cand date, FeatureValue]`
   *                      from def w h param `feature: Feature[Cand date, Opt on[FeatureValue]]`
   * @param typeTag allows for  nferr ng default value from t  FeatureValue type.
   *                See [[featureValueSortDefaultValue]]
   * @tparam Cand date cand date for t  feature
   * @tparam FeatureValue feature value w h an [[Order ng]] context bound
   */
  def descend ng[Cand date <: Un versalNoun[Any], FeatureValue: Order ng](
    feature: Feature[Cand date, FeatureValue]
  )(
     mpl c  dum  mpl c : Dum  mpl c ,
    typeTag: TypeTag[FeatureValue]
  ): SorterProv der = {
    val defaultFeatureValue: FeatureValue = featureValueSortDefaultValue(feature, Descend ng)

    descend ng(feature, defaultFeatureValue)
  }

  /**
   * Sort by a feature value descend ng.  f t  feature fa led or  s m ss ng, use t  prov ded
   * default.
   *
   * @param feature feature w h value to sort by
   * @param dum  mpl c  due to type erasure,  mpl c  used to d samb guate `def descend ng()`
   *                      bet en def w h param `feature: Feature[Cand date, FeatureValue]`
   *                      from def w h param `feature: Feature[Cand date, Opt on[FeatureValue]]`
   * @tparam Cand date cand date for t  feature
   * @tparam FeatureValue feature value w h an [[Order ng]] context bound
   */
  def descend ng[Cand date <: Un versalNoun[Any], FeatureValue: Order ng](
    feature: Feature[Cand date, FeatureValue],
    defaultFeatureValue: FeatureValue
  )(
     mpl c  dum  mpl c : Dum  mpl c 
  ): SorterProv der = {
    val order ng = Order ng.by[Cand dateW hDeta ls, FeatureValue](
      _.features.getOrElse(feature, defaultFeatureValue))

    SorterFromOrder ng(order ng, Descend ng)
  }

  /**
   * Sort by an opt onal feature value descend ng.  f t  feature fa led or  s m ss ng, use an
   *  nferred default based on t  type of [[FeatureValue]]. For Nu r c values t   s t  MaxValue
   * (e.g. Long.MaxValue, Double.MaxValue).
   *
   * @param feature feature w h value to sort by
   * @param typeTag allows for  nferr ng default value from t  FeatureValue type.
   *                See [[featureOpt onalValueSortDefaultValue]]
   * @tparam Cand date cand date for t  feature
   * @tparam FeatureValue feature value w h an [[Order ng]] context bound
   */
  def descend ng[Cand date <: Un versalNoun[Any], FeatureValue: Order ng](
    feature: Feature[Cand date, Opt on[FeatureValue]]
  )(
     mpl c  typeTag: TypeTag[FeatureValue]
  ): SorterProv der = {
    val defaultFeatureValue: FeatureValue =
      featureOpt onalValueSortDefaultValue(feature, Descend ng)

    descend ng(feature, defaultFeatureValue)
  }

  /**
   * Sort by an opt onal feature value descend ng.  f t  feature fa led or  s m ss ng, use t 
   * prov ded default.
   *
   * @param feature feature w h value to sort by
   * @tparam Cand date cand date for t  feature
   * @tparam FeatureValue feature value w h an [[Order ng]] context bound
   */
  def descend ng[Cand date <: Un versalNoun[Any], FeatureValue: Order ng](
    feature: Feature[Cand date, Opt on[FeatureValue]],
    defaultFeatureValue: FeatureValue
  ): SorterProv der = {
    val order ng = Order ng.by[Cand dateW hDeta ls, FeatureValue](
      _.features.getOrElse(feature, None).getOrElse(defaultFeatureValue))

    SorterFromOrder ng(order ng, Descend ng)
  }

  pr vate[sorter] def featureValueSortDefaultValue[FeatureValue: Order ng](
    feature: Feature[_, FeatureValue],
    sortOrder: SortOrder
  )(
     mpl c  typeTag: TypeTag[FeatureValue]
  ): FeatureValue = {
    val defaultValue = sortOrder match {
      case Descend ng =>
        typeOf[FeatureValue] match {
          case t  f t <:< typeOf[Short] => Short.M nValue
          case t  f t <:< typeOf[ nt] =>  nt.M nValue
          case t  f t <:< typeOf[Long] => Long.M nValue
          case t  f t <:< typeOf[Double] => Double.M nValue
          case t  f t <:< typeOf[Float] => Float.M nValue
          case _ =>
            throw new UnsupportedOperat onExcept on(s"Default value not supported for $feature")
        }
      case Ascend ng =>
        typeOf[FeatureValue] match {
          case t  f t <:< typeOf[Short] => Short.MaxValue
          case t  f t <:< typeOf[ nt] =>  nt.MaxValue
          case t  f t <:< typeOf[Long] => Long.MaxValue
          case t  f t <:< typeOf[Double] => Double.MaxValue
          case t  f t <:< typeOf[Float] => Float.MaxValue
          case _ =>
            throw new UnsupportedOperat onExcept on(s"Default value not supported for $feature")
        }
    }

    defaultValue.as nstanceOf[FeatureValue]
  }

  pr vate[sorter] def featureOpt onalValueSortDefaultValue[FeatureValue: Order ng](
    feature: Feature[_, Opt on[FeatureValue]],
    sortOrder: SortOrder
  )(
     mpl c  typeTag: TypeTag[FeatureValue]
  ): FeatureValue = {
    val defaultValue = sortOrder match {
      case Descend ng =>
        typeOf[Opt on[FeatureValue]] match {
          case t  f t <:< typeOf[Opt on[Short]] => Short.M nValue
          case t  f t <:< typeOf[Opt on[ nt]] =>  nt.M nValue
          case t  f t <:< typeOf[Opt on[Long]] => Long.M nValue
          case t  f t <:< typeOf[Opt on[Double]] => Double.M nValue
          case t  f t <:< typeOf[Opt on[Float]] => Float.M nValue
          case _ =>
            throw new UnsupportedOperat onExcept on(s"Default value not supported for $feature")
        }
      case Ascend ng =>
        typeOf[Opt on[FeatureValue]] match {
          case t  f t <:< typeOf[Opt on[Short]] => Short.MaxValue
          case t  f t <:< typeOf[Opt on[ nt]] =>  nt.MaxValue
          case t  f t <:< typeOf[Opt on[Long]] => Long.MaxValue
          case t  f t <:< typeOf[Opt on[Double]] => Double.MaxValue
          case t  f t <:< typeOf[Opt on[Float]] => Float.MaxValue
          case _ =>
            throw new UnsupportedOperat onExcept on(s"Default value not supported for $feature")
        }
    }

    defaultValue.as nstanceOf[FeatureValue]
  }
}
