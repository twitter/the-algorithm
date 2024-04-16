package com.tw ter.product_m xer.core.funct onal_component.transfor r

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er

/**
 * [[FeatureTransfor r]] allow   to populate a [[com.tw ter.product_m xer.core.feature.Feature]]s
 * value wh ch  s already ava lable or can be der ved w hout mak ng an RPC.
 *
 * A [[FeatureTransfor r]] transforms a g ven [[ nputs]]  nto a [[FeatureMap]].
 * T  transfor r must spec fy wh ch [[com.tw ter.product_m xer.core.feature.Feature]]s   w ll populate us ng t  `features` f eld
 * and t  returned [[FeatureMap]] must always have t  spec f ed [[com.tw ter.product_m xer.core.feature.Feature]]s populated.
 *
 * @note Unl ke [[com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.FeatureHydrator]]  mple ntat ons,
 *       an except on thrown  n a [[FeatureTransfor r]] w ll not be added to t  [[FeatureMap]] and w ll  nstead be
 *       bubble up to t  call ng p pel ne's [[com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lureClass f er]].
 */
tra  FeatureTransfor r[- nputs] extends Transfor r[ nputs, FeatureMap] {

  def features: Set[Feature[_, _]]

  overr de val  dent f er: Transfor r dent f er

  /** Hydrates a [[FeatureMap]] for a g ven [[ nputs]] */
  overr de def transform( nput:  nputs): FeatureMap
}
