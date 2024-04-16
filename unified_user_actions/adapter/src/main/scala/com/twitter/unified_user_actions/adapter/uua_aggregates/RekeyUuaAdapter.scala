package com.tw ter.un f ed_user_act ons.adapter.uua_aggregates

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.un f ed_user_act ons.adapter.AbstractAdapter
 mport com.tw ter.un f ed_user_act ons.thr ftscala._

/**
 * T  ma n purpose of t  rekey adapter and t  rekey serv ce  s to not break t  ex st ng
 * custo rs w h t  ex st ng Unkeyed and also mak ng t  value as a super l ght-  ght sc ma.
 * After   rekey from Unkeyed to Long (t et d), downstream KafkaStreams can d rectly consu 
 * w hout repart  on ng.
 */
class RekeyUuaAdapter extends AbstractAdapter[Un f edUserAct on, Long, KeyedUuaT et] {

   mport RekeyUuaAdapter._
  overr de def adaptOneToKeyedMany(
     nput: Un f edUserAct on,
    statsRece ver: StatsRece ver = NullStatsRece ver
  ): Seq[(Long, KeyedUuaT et)] =
    adaptEvent( nput).map { e => (e.t et d, e) }
}

object RekeyUuaAdapter {
  def adaptEvent(e: Un f edUserAct on): Seq[KeyedUuaT et] =
    Opt on(e).flatMap { e =>
      e.act onType match {
        case Act onType.Cl entT etRender mpress on =>
          Cl entT etRender mpress onUua.getRekeyedUUA(e)
        case _ => None
      }
    }.toSeq
}
