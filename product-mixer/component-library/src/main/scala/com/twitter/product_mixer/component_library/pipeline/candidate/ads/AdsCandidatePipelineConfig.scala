package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.ads

 mport com.tw ter.adserver.{thr ftscala => ads}
 mport com.tw ter.product_m xer.component_l brary.model.cand date.ads.AdsCand date
 mport com.tw ter.product_m xer.component_l brary.model.query.ads.AdsQuery
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derParam
 mport javax. nject. nject

class AdsCand dateP pel neConf g[Query <: P pel neQuery w h AdsQuery] @ nject() (
  overr de val  dent f er: Cand dateP pel ne dent f er,
  overr de val enabledDec derParam: Opt on[Dec derParam[Boolean]],
  overr de val supportedCl entParam: Opt on[FSParam[Boolean]],
  overr de val gates: Seq[Gate[Query]],
  overr de val cand dateS ce: Cand dateS ce[
    ads.AdRequestParams,
    ads.Ad mpress on
  ],
  overr de val f lters: Seq[F lter[Query, AdsCand date]],
  overr de val postF lterFeatureHydrat on: Seq[
    BaseCand dateFeatureHydrator[Query, AdsCand date, _]
  ],
  overr de val decorator: Opt on[Cand dateDecorator[Query, AdsCand date]],
  overr de val alerts: Seq[Alert],
  adsD splayLocat onBu lder: AdsD splayLocat onBu lder[Query],
  est mateNumOrgan c ems: Est mateNumOrgan c ems[Query],
  urtRequest: Opt on[Boolean],
) extends Cand dateP pel neConf g[
      Query,
      ads.AdRequestParams,
      ads.Ad mpress on,
      AdsCand date
    ] {

  overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[Query, ads.AdRequestParams] =
    AdsCand dateP pel neQueryTransfor r(
      adsD splayLocat onBu lder = adsD splayLocat onBu lder,
      est matedNumOrgan c ems = est mateNumOrgan c ems,
      urtRequest = urtRequest)

  overr de val resultTransfor r: Cand dateP pel neResultsTransfor r[
    ads.Ad mpress on,
    AdsCand date
  ] = AdsCand dateP pel neResultsTransfor r
}
