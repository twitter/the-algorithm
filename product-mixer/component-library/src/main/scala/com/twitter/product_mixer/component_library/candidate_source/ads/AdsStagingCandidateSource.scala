package com.tw ter.product_m xer.component_l brary.cand date_s ce.ads

 mport com.tw ter.adserver.thr ftscala.Ad mpress on
 mport com.tw ter.adserver.thr ftscala.AdRequestParams
 mport com.tw ter.adserver.thr ftscala.AdRequestResponse
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.strato.StratoKeyFetc rS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.generated.cl ent.ads.adm xer.MakeAdRequestStag ngCl entColumn
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class AdsStag ngCand dateS ce @ nject() (adsCl ent: MakeAdRequestStag ngCl entColumn)
    extends StratoKeyFetc rS ce[
      AdRequestParams,
      AdRequestResponse,
      Ad mpress on
    ] {
  overr de val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er("AdsStag ng")

  overr de val fetc r: Fetc r[AdRequestParams, Un , AdRequestResponse] = adsCl ent.fetc r

  overr de protected def stratoResultTransfor r(
    stratoResult: AdRequestResponse
  ): Seq[Ad mpress on] =
    stratoResult. mpress ons
}
