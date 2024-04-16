package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.ho _m xer.model.Ho Features. nNetworkFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Top cContextFunct onal yTypeFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Top c dSoc alContextFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.soc al_context.BaseSoc alContextBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Soc alContext
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Top cContext
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class Top cSoc alContextBu lder @ nject() ()
    extends BaseSoc alContextBu lder[P pel neQuery, T etCand date] {

  def apply(
    query: P pel neQuery,
    cand date: T etCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[Soc alContext] = {
    val  nNetwork = cand dateFeatures.getOrElse( nNetworkFeature, true)
     f (! nNetwork) {
      val top c dSoc alContextOpt = cand dateFeatures.getOrElse(Top c dSoc alContextFeature, None)
      val top cContextFunct onal yTypeOpt =
        cand dateFeatures.getOrElse(Top cContextFunct onal yTypeFeature, None)
      (top c dSoc alContextOpt, top cContextFunct onal yTypeOpt) match {
        case (So (top c d), So (top cContextFunct onal yType)) =>
          So (
            Top cContext(
              top c d = top c d.toStr ng,
              funct onal yType = So (top cContextFunct onal yType)
            ))
        case _ => None
      }
    } else {
      None
    }
  }
}
