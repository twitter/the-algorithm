package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.ho _m xer.model.Ho Features.FocalT etAuthor dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.FocalT et nNetworkFeature
 mport com.tw ter.ho _m xer.model.Ho Features.FocalT etRealNa sFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nNetworkFeature
 mport com.tw ter.ho _m xer.product.follow ng.model.Ho M xerExternalStr ngs
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.soc al_context.BaseSoc alContextBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Soc alContext
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata._
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport javax. nject. nject
 mport javax. nject.Prov der
 mport javax. nject.S ngleton

/**
 * Use '@A repl ed' w n t  root t et  s out-of-network and t  reply  s  n network.
 *
 * T  funct on should only be called for t  root T et of convo modules. T   s enforced by
 * [[Ho T etSoc alContextBu lder]].
 */
@S ngleton
case class ExtendedReplySoc alContextBu lder @ nject() (
  externalStr ngs: Ho M xerExternalStr ngs,
  @ProductScoped str ngCenterProv der: Prov der[Str ngCenter])
    extends BaseSoc alContextBu lder[P pel neQuery, T etCand date] {

  pr vate val str ngCenter = str ngCenterProv der.get()
  pr vate val extendedReplyStr ng = externalStr ngs.soc alContextExtendedReply

  def apply(
    query: P pel neQuery,
    cand date: T etCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[Soc alContext] = {

    //  f t se values are m ss ng default to not show ng an extended reply banner
    val  nNetworkRoot = cand dateFeatures.getOrElse( nNetworkFeature, true)

    val  nNetworkFocalT et =
      cand dateFeatures.getOrElse(FocalT et nNetworkFeature, None).getOrElse(false)

     f (! nNetworkRoot &&  nNetworkFocalT et) {

      val focalT etAuthor dOpt = cand dateFeatures.getOrElse(FocalT etAuthor dFeature, None)
      val focalT etRealNa s =
        cand dateFeatures
          .getOrElse(FocalT etRealNa sFeature, None).getOrElse(Map.empty[Long, Str ng])
      val focalT etAuthorNa Opt = focalT etAuthor dOpt.flatMap(focalT etRealNa s.get)

      (focalT etAuthor dOpt, focalT etAuthorNa Opt) match {
        case (So (focalT etAuthor d), So (focalT etAuthorNa )) =>
          So (
            GeneralContext(
              contextType = Conversat onGeneralContextType,
              text = str ngCenter
                .prepare(extendedReplyStr ng, placeholders = Map("user1" -> focalT etAuthorNa )),
              url = None,
              context mageUrls = None,
              land ngUrl = So (
                Url(
                  urlType = DeepL nk,
                  url = "",
                  urtEndpo ntOpt ons = None
                ))
            ))
        case _ =>
          None
      }
    } else {
      None
    }
  }
}
