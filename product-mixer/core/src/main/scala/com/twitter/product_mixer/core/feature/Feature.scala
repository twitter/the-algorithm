package com.tw ter.product_m xer.core.feature

/**
 * A [[Feature]]  s a s ngle  asurable or computable property of an ent y.
 *
 * @note  f a [[Feature]]  s opt onal t n t  [[Value]] should be `Opt on[Value]`
 *
 * @note  f a [[Feature]]  s populated w h a [[com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.FeatureHydrator]]
 *       and t  hydrat on fa ls, a fa lure w ll be stored for t  [[Feature]].
 *        f that [[Feature]]  s accessed w h [[com.tw ter.product_m xer.core.feature.featuremap.FeatureMap.get]]
 *       t n t  stored except on w ll be thrown, essent ally fa l ng-closed.
 *         can use [[FeatureW hDefaultOnFa lure]] or [[com.tw ter.product_m xer.core.feature.featuremap.FeatureMap.getOrElse]]
 *        nstead to avo d t se  ssues and  nstead fa l-open.
 *        f correctly hydrat ng a Feature's value  s essent al to t  request be ng correct,
 *       t n   should fa l-closed on fa lure to hydrate   by extend ng [[Feature]] d rectly.
 *
 *       T  does not apply to [[Feature]]s from [[com.tw ter.product_m xer.core.funct onal_component.transfor r.FeatureTransfor r]]
 *       wh ch throw  n t  call ng P pel ne  nstead of stor ng t  r fa lures.
 *
 * @tparam Ent y T  type of ent y that t  feature works w h. T  could be a User, T et,
 *                Query, etc.
 * @tparam Value T  type of t  value of t  feature.
 */
tra  Feature[-Ent y, Value] { self =>
  overr de def toStr ng: Str ng = {
    Feature.getS mpleNa (self.getClass)
  }
}

/**
 * W h a [[Feature]],  f t  [[com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.FeatureHydrator]] fa ls,
 * t  fa lure w ll be caught by t  platform and stored  n t  [[com.tw ter.product_m xer.core.feature.featuremap.FeatureMap]].
 * Access ng a fa led feature v a [[com.tw ter.product_m xer.core.feature.featuremap.FeatureMap.get()]]
 * w ll throw t  except on that was caught wh le attempt ng to hydrate t  feature.  f t re's a
 * reasonable default for a [[Feature]] to fa l-open w h, t n throw ng t  except on at read t  
 * can be prevented by def n ng a `defaultValue` v a [[FeatureW hDefaultOnFa lure]]. W n access ng
 * a fa led feature v a [[com.tw ter.product_m xer.core.feature.featuremap.FeatureMap.get()]]
 * for a [[FeatureW hDefaultOnFa lure]], t  `defaultValue` w ll be returned.
 *
 *
 * @note [[com.tw ter.product_m xer.core.feature.featuremap.FeatureMap.getOrElse()]] can also be used
 *       to access a fa led feature w hout throw ng t  except on, by def n ng t  default v a t 
 *       [[com.tw ter.product_m xer.core.feature.featuremap.FeatureMap.getOrElse()]]  thod call
 *        nstead of as part of t  feature declarat on.
 * @note T  does not apply to [[FeatureW hDefaultOnFa lure]]s from [[com.tw ter.product_m xer.core.funct onal_component.transfor r.FeatureTransfor r]]
 *       wh ch throw  n t  call ng P pel ne  nstead of stor ng t  r fa lures.
 *
 * @tparam Ent y T  type of ent y that t  feature works w h. T  could be a User, T et,
 *                Query, etc.
 * @tparam Value T  type of t  value of t  feature.
 */
tra  FeatureW hDefaultOnFa lure[Ent y, Value] extends Feature[Ent y, Value] {

  /** T  default value a feature should return should   fa l to be hydrated */
  def defaultValue: Value
}

tra  ModelFeatureNa  { self: Feature[_, _] =>
  def featureNa : Str ng
}

object Feature {

  /**
   * Avo d `malfor d class na ` except ons due to t  presence of t  `$` character
   * Also str p off tra l ng $ s gns for readab l y
   */
  def getS mpleNa [T](c: Class[T]): Str ng = {
    c.getNa .str pSuff x("$").last ndexOf("$") match {
      case -1 => c.getS mpleNa .str pSuff x("$")
      case  ndex => c.getNa .substr ng( ndex + 1).str pSuff x("$")
    }
  }
}
