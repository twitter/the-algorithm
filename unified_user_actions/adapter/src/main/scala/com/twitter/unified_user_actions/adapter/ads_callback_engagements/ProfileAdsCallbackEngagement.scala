package com.tw ter.un f ed_user_act ons.adapter.ads_callback_engage nts

 mport com.tw ter.ads.spendserver.thr ftscala.SpendServerEvent
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Prof le nfo

abstract class Prof leAdsCallbackEngage nt(act onType: Act onType)
    extends BaseAdsCallbackEngage nt(act onType) {

  overr de protected def get em( nput: SpendServerEvent): Opt on[ em] = {
     nput.engage ntEvent.flatMap { e =>
      e. mpress onData.flatMap {   =>
        getProf le nfo( .advert ser d)
      }
    }
  }

  protected def getProf le nfo(advert ser d: Long): Opt on[ em] = {
    So (
       em.Prof le nfo(
        Prof le nfo(
          act onProf le d = advert ser d
        )))
  }
}
