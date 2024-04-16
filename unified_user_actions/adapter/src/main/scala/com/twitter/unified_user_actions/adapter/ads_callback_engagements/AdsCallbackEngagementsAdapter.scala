package com.tw ter.un f ed_user_act ons.adapter.ads_callback_engage nts

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter.un f ed_user_act ons.adapter.AbstractAdapter
 mport com.tw ter.ads.spendserver.thr ftscala.SpendServerEvent
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on

class AdsCallbackEngage ntsAdapter
    extends AbstractAdapter[SpendServerEvent, UnKeyed, Un f edUserAct on] {

   mport AdsCallbackEngage ntsAdapter._

  overr de def adaptOneToKeyedMany(
     nput: SpendServerEvent,
    statsRece ver: StatsRece ver = NullStatsRece ver
  ): Seq[(UnKeyed, Un f edUserAct on)] =
    adaptEvent( nput).map { e => (UnKeyed, e) }
}

object AdsCallbackEngage ntsAdapter {
  def adaptEvent( nput: SpendServerEvent): Seq[Un f edUserAct on] = {
    val baseEngage nts: Seq[BaseAdsCallbackEngage nt] =
      Engage ntTypeMapp ngs.getEngage ntMapp ngs(Opt on( nput).flatMap(_.engage ntEvent))
    baseEngage nts.flatMap(_.getUUA( nput))
  }
}
