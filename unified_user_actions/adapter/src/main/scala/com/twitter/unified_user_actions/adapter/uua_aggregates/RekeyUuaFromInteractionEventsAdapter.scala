package com.tw ter.un f ed_user_act ons.adapter.uua_aggregates

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. es ce.thr ftscala.Cl entEventContext
 mport com.tw ter. es ce.thr ftscala.Engag ngContext
 mport com.tw ter.un f ed_user_act ons.adapter.AbstractAdapter
 mport com.tw ter. es ce.thr ftscala. nteract onType
 mport com.tw ter. es ce.thr ftscala. nteract onEvent
 mport com.tw ter.un f ed_user_act ons.adapter.common.AdapterUt ls
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Event tadata
 mport com.tw ter.un f ed_user_act ons.thr ftscala.KeyedUuaT et
 mport com.tw ter.un f ed_user_act ons.thr ftscala.S ceL neage
 mport com.tw ter.un f ed_user_act ons.thr ftscala.User dent f er

/**
 * T   s to read d rectly from  nteract onEvents
 */
class RekeyUuaFrom nteract onEventsAdapter
    extends AbstractAdapter[ nteract onEvent, Long, KeyedUuaT et] {

   mport RekeyUuaFrom nteract onEventsAdapter._
  overr de def adaptOneToKeyedMany(
     nput:  nteract onEvent,
    statsRece ver: StatsRece ver = NullStatsRece ver
  ): Seq[(Long, KeyedUuaT et)] =
    adaptEvent( nput, statsRece ver).map { e => (e.t et d, e) }
}

object RekeyUuaFrom nteract onEventsAdapter {

  def adaptEvent(
    e:  nteract onEvent,
    statsRece ver: StatsRece ver = NullStatsRece ver
  ): Seq[KeyedUuaT et] =
    Opt on(e).flatMap { e =>
      e. nteract onType.flatMap {
        case  nteract onType.T etRender mpress on  f ! sDeta l mpress on(e.engag ngContext) =>
          getRekeyedUUA(
             nput = e,
            act onType = Act onType.Cl entT etRender mpress on,
            s ceL neage = S ceL neage.Cl entEvents,
            statsRece ver = statsRece ver)
        case _ => None
      }
    }.toSeq

  def getRekeyedUUA(
     nput:  nteract onEvent,
    act onType: Act onType,
    s ceL neage: S ceL neage,
    statsRece ver: StatsRece ver = NullStatsRece ver
  ): Opt on[KeyedUuaT et] =
     nput.engag ngUser d match {
      // please see https://docs.google.com/docu nt/d/1-fy2S-8-YMRQgEN0Sco0OLT O Udqg Z5G1KwTHt2g/ed #
      //  n order to w hstand of potent al attacks,   f lter out t  logged-out users.
      // C ck ng user  d  s 0  s t  reverse eng neer ng of
      // https://s cegraph.tw ter.b z/g .tw ter.b z/s ce/-/blob/ es ce/thr ft/src/ma n/thr ft/com/tw ter/ es ce/ nteract on_event.thr ft?L220
      // https://s cegraph.tw ter.b z/g .tw ter.b z/s ce/-/blob/ es ce/common/src/ma n/scala/com/tw ter/ es ce/common/converters/cl ent/LogEventConverter.scala?L198
      case 0L =>
        statsRece ver.counter("loggedOutEvents"). ncr()
        None
      case _ =>
        So (
          KeyedUuaT et(
            t et d =  nput.target d,
            act onType = act onType,
            user dent f er = User dent f er(user d = So ( nput.engag ngUser d)),
            event tadata = Event tadata(
              s ceT  stampMs =  nput.tr ggeredT  stampM ll s.getOrElse( nput.t  stampM ll s),
              rece vedT  stampMs = AdapterUt ls.currentT  stampMs,
              s ceL neage = s ceL neage
            )
          ))
    }

  def  sDeta l mpress on(engag ngContext: Engag ngContext): Boolean =
    engag ngContext match {
      case Engag ngContext.Cl entEventContext(
            Cl entEventContext(_, _, _, _, _, _, _, So ( sDeta ls mpress on), _)
          )  f  sDeta ls mpress on =>
        true
      case _ => false
    }
}
