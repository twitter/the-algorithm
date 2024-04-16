package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.soc al_context

 mport com.tw ter. rm .{thr ftscala => h}
 mport com.tw ter.product_m xer.component_l brary.model.cand date.UserCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.soc al_context.BaseSoc alContextBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FollowGeneralContextType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.GeneralContext
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.GeneralContextType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Locat onGeneralContextType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.NewUserGeneralContextType
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class WhoToFollowSoc alContextBu lder(
  soc alTextFeature: Feature[_, Opt on[Str ng]],
  contextTypeFeature: Feature[_, Opt on[h.ContextType]])
    extends BaseSoc alContextBu lder[P pel neQuery, UserCand date] {

  def apply(
    query: P pel neQuery,
    cand date: UserCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[GeneralContext] = {
    val soc alTextOpt = cand dateFeatures.getOrElse(soc alTextFeature, None)
    val contextTypeOpt = convertContextType(cand dateFeatures.getOrElse(contextTypeFeature, None))

    (soc alTextOpt, contextTypeOpt) match {
      case (So (soc alText), So (contextType))  f soc alText.nonEmpty =>
        So (
          GeneralContext(
            text = soc alText,
            contextType = contextType,
            url = None,
            context mageUrls = None,
            land ngUrl = None))
      case _ => None
    }
  }

  pr vate def convertContextType(contextType: Opt on[h.ContextType]): Opt on[GeneralContextType] =
    contextType match {
      case So (h.ContextType.Geo) => So (Locat onGeneralContextType)
      case So (h.ContextType.Soc al) => So (FollowGeneralContextType)
      case So (h.ContextType.NewUser) => So (NewUserGeneralContextType)
      case _ => None
    }
}
