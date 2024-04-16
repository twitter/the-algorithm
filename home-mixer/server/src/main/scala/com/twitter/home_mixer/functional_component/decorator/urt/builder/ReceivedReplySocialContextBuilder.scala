package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.FocalT et nNetworkFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nNetworkFeature
 mport com.tw ter.ho _m xer.model.Ho Features.RealNa sFeature
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
 * Use '@A rece ved a reply' as soc al context w n t  root T et  s  n network and t  focal t et  s OON.
 *
 * T  funct on should only be called for t  root T et of convo modules. T   s enforced by
 * [[Ho T etSoc alContextBu lder]].
 */
@S ngleton
case class Rece vedReplySoc alContextBu lder @ nject() (
  externalStr ngs: Ho M xerExternalStr ngs,
  @ProductScoped str ngCenterProv der: Prov der[Str ngCenter])
    extends BaseSoc alContextBu lder[P pel neQuery, T etCand date] {

  pr vate val str ngCenter = str ngCenterProv der.get()
  pr vate val rece vedReplyStr ng = externalStr ngs.soc alContextRece vedReply

  def apply(
    query: P pel neQuery,
    cand date: T etCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[Soc alContext] = {

    //  f t se values are m ss ng default to not show ng a rece ved a reply banner
    val  nNetwork = cand dateFeatures.getOrElse( nNetworkFeature, false)
    val  nNetworkFocalT et =
      cand dateFeatures.getOrElse(FocalT et nNetworkFeature, None).getOrElse(true)

     f ( nNetwork && ! nNetworkFocalT et) {

      val author dOpt = cand dateFeatures.getOrElse(Author dFeature, None)
      val realNa s = cand dateFeatures.getOrElse(RealNa sFeature, Map.empty[Long, Str ng])
      val authorNa Opt = author dOpt.flatMap(realNa s.get)

      (author dOpt, authorNa Opt) match {
        case (So (author d), So (authorNa )) =>
          So (
            GeneralContext(
              contextType = Conversat onGeneralContextType,
              text = str ngCenter
                .prepare(rece vedReplyStr ng, placeholders = Map("user1" -> authorNa )),
              url = None,
              context mageUrls = None,
              land ngUrl = So (
                Url(
                  urlType = DeepL nk,
                  url = "",
                  urtEndpo ntOpt ons = None
                )
              )
            )
          )
        case _ => None
      }
    } else {
      None
    }
  }
}
