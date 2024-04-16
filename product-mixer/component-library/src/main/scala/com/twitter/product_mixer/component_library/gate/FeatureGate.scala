package com.tw ter.product_m xer.component_l brary.gate

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.M ss ngFeatureExcept on
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.funct onal_component.gate.GateResult
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.M sconf guredFeatureMapFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw

tra  ShouldCont nue[Value] {

  /** G ven t  [[Feature]] value, returns w t r t  execut on should cont nue */
  def apply(featureValue: Value): Boolean

  /**  f t  [[Feature]]  s a fa lure, use t  value */
  def onFa ledFeature(t: Throwable): GateResult = GateResult.Stop

  /**
   *  f t  [[Feature]], or [[com.tw ter.product_m xer.core.feature.featuremap.FeatureMap]],
   *  s m ss ng use t  value
   */
  def onM ss ngFeature: GateResult = GateResult.Stop
}

object FeatureGate {

  def fromFeature(
    feature: Feature[_, Boolean]
  ): FeatureGate[Boolean] =
    FeatureGate.fromFeature(Gate dent f er(feature.toStr ng), feature)

  def fromNegatedFeature(
    feature: Feature[_, Boolean]
  ): FeatureGate[Boolean] =
    FeatureGate.fromNegatedFeature(Gate dent f er(feature.toStr ng), feature)

  def fromFeature(
    gate dent f er: Gate dent f er,
    feature: Feature[_, Boolean]
  ): FeatureGate[Boolean] =
    FeatureGate[Boolean](gate dent f er, feature,  dent y)

  def fromNegatedFeature(
    gate dent f er: Gate dent f er,
    feature: Feature[_, Boolean]
  ): FeatureGate[Boolean] =
    FeatureGate[Boolean](gate dent f er, feature, ! dent y(_))

}

/**
 * A [[Gate]] that  s actuated based upon t  value of t  prov ded feature
 */
case class FeatureGate[Value](
  gate dent f er: Gate dent f er,
  feature: Feature[_, Value],
  cont nue: ShouldCont nue[Value])
    extends Gate[P pel neQuery] {

  overr de val  dent f er: Gate dent f er = gate dent f er

  overr de def shouldCont nue(query: P pel neQuery): St ch[Boolean] = {
    St ch
      .value(
        query.features.map(_.getTry(feature)) match {
          case So (Return(value)) => cont nue(value)
          case So (Throw(_: M ss ngFeatureExcept on)) => cont nue.onM ss ngFeature.cont nue
          case So (Throw(t)) => cont nue.onFa ledFeature(t).cont nue
          case None =>
            throw P pel neFa lure(
              M sconf guredFeatureMapFa lure,
              "Expected a FeatureMap to be present but none was found, ensure that y " +
                "P pel neQuery has a FeatureMap conf gured before gat ng on Feature values"
            )
        }
      )
  }
}
