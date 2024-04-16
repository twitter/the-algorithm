package com.tw ter.ho _m xer.product.for_ 

 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.param_gated.ParamGatedCand dateFeatureHydrator
 mport com.tw ter.adserver.{thr ftscala => ads}
 mport com.tw ter.ho _m xer.funct onal_component.decorator.bu lder.Ho AdsCl entEventDeta lsBu lder
 mport com.tw ter.ho _m xer.funct onal_component.gate.ExcludeSoftUserGate
 mport com.tw ter.ho _m xer.param.Ho GlobalParams
 mport com.tw ter.ho _m xer.param.Ho GlobalParams.EnableAdvert serBrandSafetySett ngsFeatureHydratorParam
 mport com.tw ter.ho _m xer.product.for_ .model.For Query
 mport com.tw ter.ho _m xer.product.for_ .param.For Param.AdsNumOrgan c emsParam
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.ads.AdsProdThr ftCand dateS ce
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.Urt emCand dateDecorator
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.contextual_ref.ContextualT etRefBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.ad.AdsCand dateUrt emBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. tadata.Cl entEvent nfoBu lder
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.ads.Advert serBrandSafetySett ngsFeatureHydrator
 mport com.tw ter.product_m xer.component_l brary.model.cand date.ads.AdsCand date
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.ads.AdsCand dateP pel neConf g
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.ads.AdsCand dateP pel neConf gBu lder
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.ads.Stat cAdsD splayLocat onBu lder
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.ads.Val dAd mpress on dF lter
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.gate.ParamNotGate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.response.rtf.safety_level.T  l neHo PromotedHydrat onSafetyLevel
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.contextual_ref.T etHydrat onContext
 mport com.tw ter.t  l nes. nject on.scr be. nject onScr beUt l
 mport com.tw ter.t  l neserv ce.suggests.{thr ftscala => st}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class For AdsCand dateP pel neBu lder @ nject() (
  adsCand dateP pel neConf gBu lder: AdsCand dateP pel neConf gBu lder,
  adsCand dateS ce: AdsProdThr ftCand dateS ce,
  advert serBrandSafetySett ngsFeatureHydrator: Advert serBrandSafetySett ngsFeatureHydrator[
    For Query,
    AdsCand date
  ]) {

  pr vate val  dent f er: Cand dateP pel ne dent f er = Cand dateP pel ne dent f er("For Ads")

  pr vate val suggestType = st.SuggestType.Promoted

  pr vate val cl entEvent nfoBu lder = Cl entEvent nfoBu lder(
    component =  nject onScr beUt l.scr beComponent(suggestType).get,
    deta lsBu lder = So (Ho AdsCl entEventDeta lsBu lder(So (suggestType.na )))
  )

  pr vate val contextualT etRefBu lder = ContextualT etRefBu lder(
    T etHydrat onContext(
      safetyLevelOverr de = So (T  l neHo PromotedHydrat onSafetyLevel),
      outerT etContext = None
    ))

  pr vate val decorator = Urt emCand dateDecorator(
    AdsCand dateUrt emBu lder(
      t etCl entEvent nfoBu lder = So (cl entEvent nfoBu lder),
      contextualT etRefBu lder = So (contextualT etRefBu lder)
    ))

  pr vate val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(),
    Ho M xerAlertConf g.Bus nessH s.defaultEmptyResponseRateAlert()
  )

  def bu ld(
    organ cCand dateP pel nes: Opt on[Cand dateScope] = None
  ): AdsCand dateP pel neConf g[For Query] =
    adsCand dateP pel neConf gBu lder.bu ld[For Query](
      adsCand dateS ce = adsCand dateS ce,
       dent f er =  dent f er,
      adsD splayLocat onBu lder = Stat cAdsD splayLocat onBu lder(ads.D splayLocat on.T  l neHo ),
      est mateNumOrgan c ems = _.params(AdsNumOrgan c emsParam).toShort,
      gates = Seq(
        ParamNotGate(
          na  = "AdsD sable nject onBasedOnUserRole",
          param = Ho GlobalParams.AdsD sable nject onBasedOnUserRoleParam
        ),
        ExcludeSoftUserGate
      ),
      f lters = Seq(Val dAd mpress on dF lter),
      postF lterFeatureHydrat on = Seq(
        ParamGatedCand dateFeatureHydrator(
          EnableAdvert serBrandSafetySett ngsFeatureHydratorParam,
          advert serBrandSafetySett ngsFeatureHydrator
        )
      ),
      decorator = So (decorator),
      alerts = alerts,
      urtRequest = So (true),
    )
}
