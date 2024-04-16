package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.ho _m xer.model.Ho Features. nNetworkFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SGSVal dFollo dByUser dsFeature
 mport com.tw ter.ho _m xer.product.follow ng.model.Ho M xerExternalStr ngs
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.soc al_context.BaseSoc alContextBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata._
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport javax. nject. nject
 mport javax. nject.Prov der
 mport javax. nject.S ngleton

@S ngleton
case class Follo dBySoc alContextBu lder @ nject() (
  externalStr ngs: Ho M xerExternalStr ngs,
  @ProductScoped str ngCenterProv der: Prov der[Str ngCenter])
    extends BaseSoc alContextBu lder[P pel neQuery, T etCand date] {

  pr vate val str ngCenter = str ngCenterProv der.get()

  pr vate val engagerSoc alContextBu lder = EngagerSoc alContextBu lder(
    contextType = FollowGeneralContextType,
    str ngCenter = str ngCenter,
    oneUserStr ng = externalStr ngs.soc alContextOneUserFollowsStr ng,
    twoUsersStr ng = externalStr ngs.soc alContextTwoUsersFollowStr ng,
    moreUsersStr ng = externalStr ngs.soc alContextMoreUsersFollowStr ng,
    t  l neT le = externalStr ngs.soc alContextFollo dByT  l neT le
  )

  def apply(
    query: P pel neQuery,
    cand date: T etCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[Soc alContext] = {
    // Only apply follo d-by soc al context for OON T ets
    val  nNetwork = cand dateFeatures.getOrElse( nNetworkFeature, true)
     f (! nNetwork) {
      val val dFollo dByUser ds =
        cand dateFeatures.getOrElse(SGSVal dFollo dByUser dsFeature, N l)
      engagerSoc alContextBu lder(
        soc alContext ds = val dFollo dByUser ds,
        query = query,
        cand dateFeatures = cand dateFeatures
      )
    } else {
      None
    }
  }
}
