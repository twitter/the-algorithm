package com.tw ter.product_m xer.component_l brary.f lter

 mport com.tw ter.product_m xer.component_l brary.f lter.FeatureCond  onalF lter. dent f er nf x
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * Pred cate to apply to cand date feature, to determ ne w t r to apply f lter.
 * True  nd cates   w ll apply t  f lter. False  nd cates to keep cand date and not apply f lter.
 * @tparam FeatureValue
 */
tra  ShouldApplyF lter[FeatureValue] {
  def apply(feature: FeatureValue): Boolean
}

/**
 * A f lter that appl es t  [[f lter]] for cand dates for wh ch [[shouldApplyF lter]]  s true, and keeps t  ot rs
 * @param feature feature to determ ne w t r to apply underyl ng f lter
 * @param shouldApplyF lter funct on to determ ne w t r to apply f lter
 * @param f lter t  actual f lter to apply  f shouldApplyF lter  s True
 * @tparam Query T  doma n model for t  query or request
 * @tparam Cand date T  type of t  cand dates
 * @tparam FeatureValueType
 */
case class FeatureValueCond  onalF lter[
  -Query <: P pel neQuery,
  Cand date <: Un versalNoun[Any],
  FeatureValueType
](
  feature: Feature[Cand date, FeatureValueType],
  shouldApplyF lter: ShouldApplyF lter[FeatureValueType],
  f lter: F lter[Query, Cand date])
    extends F lter[Query, Cand date] {
  overr de val  dent f er: F lter dent f er = F lter dent f er(
    feature.toStr ng +  dent f er nf x + f lter. dent f er.na 
  )

  overr de val alerts: Seq[Alert] = f lter.alerts

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[F lterResult[Cand date]] = {
    val (cand datesToF lter, cand datesToKeep) = cand dates.part  on { cand date =>
      shouldApplyF lter(cand date.features.get(feature))
    }
    f lter.apply(query, cand datesToF lter).map { f lterResult =>
      F lterResult(
        kept = f lterResult.kept ++ cand datesToKeep.map(_.cand date),
        removed = f lterResult.removed)
    }
  }
}

object FeatureCond  onalF lter {
  val  dent f er nf x = "FeatureCond  onal"
}
