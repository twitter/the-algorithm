package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.ho _m xer.model.Ho Features.Perspect veF lteredL kedByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SGSVal dL kedByUser dsFeature
 mport com.tw ter.ho _m xer.product.follow ng.model.Ho M xerExternalStr ngs
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.soc al_context.BaseSoc alContextBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.L keGeneralContextType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Soc alContext
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport javax. nject. nject
 mport javax. nject.Prov der
 mport javax. nject.S ngleton

@S ngleton
case class L kedBySoc alContextBu lder @ nject() (
  externalStr ngs: Ho M xerExternalStr ngs,
  @ProductScoped str ngCenterProv der: Prov der[Str ngCenter])
    extends BaseSoc alContextBu lder[P pel neQuery, T etCand date] {

  pr vate val str ngCenter = str ngCenterProv der.get()

  pr vate val engagerSoc alContextBu lder = EngagerSoc alContextBu lder(
    contextType = L keGeneralContextType,
    str ngCenter = str ngCenter,
    oneUserStr ng = externalStr ngs.soc alContextOneUserL kedStr ng,
    twoUsersStr ng = externalStr ngs.soc alContextTwoUsersL kedStr ng,
    moreUsersStr ng = externalStr ngs.soc alContextMoreUsersL kedStr ng,
    t  l neT le = externalStr ngs.soc alContextL kedByT  l neT le
  )

  def apply(
    query: P pel neQuery,
    cand date: T etCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[Soc alContext] = {

    // L ked by users are val d only  f t y pass both t  SGS and Perspect ve f lters.
    val val dL kedByUser ds =
      cand dateFeatures
        .getOrElse(SGSVal dL kedByUser dsFeature, N l)
        .f lter(
          cand dateFeatures.getOrElse(Perspect veF lteredL kedByUser dsFeature, N l).toSet.conta ns)

    engagerSoc alContextBu lder(
      soc alContext ds = val dL kedByUser ds,
      query = query,
      cand dateFeatures = cand dateFeatures
    )
  }
}
