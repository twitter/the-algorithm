package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.ads

 mport com.tw ter.adserver.thr ftscala.Ad mpress on
 mport com.tw ter.adserver.thr ftscala.AdRequestParams
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.Urt emCand dateDecorator
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.ad.AdsCand dateUrt emBu lder
 mport com.tw ter.product_m xer.component_l brary.model.cand date.ads.AdsCand date
 mport com.tw ter.product_m xer.component_l brary.model.query.ads.AdsQuery
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.gate.BaseGate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derParam
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class AdsDependentCand dateP pel neConf gBu lder @ nject() () {

  /**
   * Bu ld a AdsDependentCand dateP pel neConf g
   */
  def bu ld[Query <: P pel neQuery w h AdsQuery](
    adsCand dateS ce: Cand dateS ce[AdRequestParams, Ad mpress on],
     dent f er: Cand dateP pel ne dent f er = Cand dateP pel ne dent f er("Ads"),
    adsD splayLocat onBu lder: AdsD splayLocat onBu lder[Query],
    getOrgan c em ds: GetOrgan c em ds = EmptyOrgan c em ds,
    countNumOrgan c ems: CountNumOrgan c ems[Query] = CountAllCand dates,
    enabledDec derParam: Opt on[Dec derParam[Boolean]] = None,
    supportedCl entParam: Opt on[FSParam[Boolean]] = None,
    gates: Seq[BaseGate[Query]] = Seq.empty,
    f lters: Seq[F lter[Query, AdsCand date]] = Seq.empty,
    postF lterFeatureHydrat on: Seq[BaseCand dateFeatureHydrator[Query, AdsCand date, _]] =
      Seq.empty,
    decorator: Opt on[Cand dateDecorator[Query, AdsCand date]] =
      So (Urt emCand dateDecorator(AdsCand dateUrt emBu lder())),
    alerts: Seq[Alert] = Seq.empty,
    urtRequest: Opt on[Boolean] = None,
  ): AdsDependentCand dateP pel neConf g[Query] = new AdsDependentCand dateP pel neConf g[Query](
     dent f er =  dent f er,
    enabledDec derParam = enabledDec derParam,
    supportedCl entParam = supportedCl entParam,
    gates = gates,
    cand dateS ce = adsCand dateS ce,
    f lters = f lters,
    postF lterFeatureHydrat on = postF lterFeatureHydrat on,
    decorator = decorator,
    alerts = alerts,
    adsD splayLocat onBu lder = adsD splayLocat onBu lder,
    getOrgan c em ds = getOrgan c em ds,
    countNumOrgan c ems = countNumOrgan c ems,
    urtRequest = urtRequest)
}
