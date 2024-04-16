package com.tw ter.un f ed_user_act ons.adapter.ads_callback_engage nts

 mport com.tw ter.ads.spendserver.thr ftscala.SpendServerEvent
 mport com.tw ter.un f ed_user_act ons.thr ftscala._

abstract class BaseTrendAdsCallbackEngage nt(act onType: Act onType)
    extends BaseAdsCallbackEngage nt(act onType = act onType) {

  overr de protected def get em( nput: SpendServerEvent): Opt on[ em] = {
     nput.engage ntEvent.flatMap { e =>
      e. mpress onData.flatMap {   =>
         .promotedTrend d.map { promotedTrend d =>
           em.Trend nfo(Trend nfo(act onTrend d = promotedTrend d))
        }
      }
    }
  }
}
