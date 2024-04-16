package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.ho _m xer.model.Ho Features.UserScreenNa Feature
 mport com.tw ter.ho _m xer.product.follow ng.model.Ho M xerExternalStr ngs
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.soc al_context.BaseSoc alContextBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Soc alContext
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata._
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport com.tw ter.t  l neserv ce.suggests.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.Prov der
 mport javax. nject.S ngleton

/**
 * "Y  L sts" w ll be rendered for t  context and a url l nk for y  l sts.
 */
@S ngleton
case class L stsSoc alContextBu lder @ nject() (
  externalStr ngs: Ho M xerExternalStr ngs,
  @ProductScoped str ngCenterProv der: Prov der[Str ngCenter])
    extends BaseSoc alContextBu lder[P pel neQuery, T etCand date] {

  pr vate val str ngCenter = str ngCenterProv der.get()
  pr vate val l stStr ng = externalStr ngs.ownedSubscr bedL stsModule aderStr ng

  def apply(
    query: P pel neQuery,
    cand date: T etCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[Soc alContext] = {
    cand dateFeatures.get(SuggestTypeFeature) match {
      case So (suggestType)  f suggestType == t.SuggestType.RankedL stT et =>
        val userNa  = query.features.flatMap(_.getOrElse(UserScreenNa Feature, None))
        So (
          GeneralContext(
            contextType = L stGeneralContextType,
            text = str ngCenter.prepare(l stStr ng),
            url = userNa .map(na  => ""),
            context mageUrls = None,
            land ngUrl = None
          ))
      case _ => None
    }
  }
}
