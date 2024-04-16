package com.tw ter.un f ed_user_act ons.adapter.t etyp e_event

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter.un f ed_user_act ons.adapter.AbstractAdapter
 mport com.tw ter.t etyp e.thr ftscala.T etEvent
 mport com.tw ter.t etyp e.thr ftscala.T etEventData
 mport com.tw ter.t etyp e.thr ftscala.T etCreateEvent
 mport com.tw ter.t etyp e.thr ftscala.T etDeleteEvent
 mport com.tw ter.t etyp e.thr ftscala.T etEventFlags
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on

class T etyp eEventAdapter extends AbstractAdapter[T etEvent, UnKeyed, Un f edUserAct on] {
   mport T etyp eEventAdapter._
  overr de def adaptOneToKeyedMany(
    t etEvent: T etEvent,
    statsRece ver: StatsRece ver = NullStatsRece ver
  ): Seq[(UnKeyed, Un f edUserAct on)] =
    adaptEvent(t etEvent).map(e => (UnKeyed, e))
}

object T etyp eEventAdapter {
  def adaptEvent(t etEvent: T etEvent): Seq[Un f edUserAct on] = {
    Opt on(t etEvent).flatMap { e =>
      e.data match {
        case T etEventData.T etCreateEvent(t etCreateEvent: T etCreateEvent) =>
          getUUAFromT etCreateEvent(t etCreateEvent, e.flags)
        case T etEventData.T etDeleteEvent(t etDeleteEvent: T etDeleteEvent) =>
          getUUAFromT etDeleteEvent(t etDeleteEvent, e.flags)
        case _ => None
      }
    }.toSeq
  }

  def getUUAFromT etCreateEvent(
    t etCreateEvent: T etCreateEvent,
    t etEventFlags: T etEventFlags
  ): Opt on[Un f edUserAct on] = {
    val t etTypeOpt = T etyp eEventUt ls.t etTypeFromT et(t etCreateEvent.t et)

    t etTypeOpt.flatMap { t etType =>
      t etType match {
        case T etTypeReply =>
          T etyp eReplyEvent.getUn f edUserAct on(t etCreateEvent, t etEventFlags)
        case T etTypeRet et =>
          T etyp eRet etEvent.getUn f edUserAct on(t etCreateEvent, t etEventFlags)
        case T etTypeQuote =>
          T etyp eQuoteEvent.getUn f edUserAct on(t etCreateEvent, t etEventFlags)
        case T etTypeDefault =>
          T etyp eCreateEvent.getUn f edUserAct on(t etCreateEvent, t etEventFlags)
        case T etTypeEd  =>
          T etyp eEd Event.getUn f edUserAct on(t etCreateEvent, t etEventFlags)
      }
    }
  }

  def getUUAFromT etDeleteEvent(
    t etDeleteEvent: T etDeleteEvent,
    t etEventFlags: T etEventFlags
  ): Opt on[Un f edUserAct on] = {
    val t etTypeOpt = T etyp eEventUt ls.t etTypeFromT et(t etDeleteEvent.t et)

    t etTypeOpt.flatMap { t etType =>
      t etType match {
        case T etTypeRet et =>
          T etyp eUnret etEvent.getUn f edUserAct on(t etDeleteEvent, t etEventFlags)
        case T etTypeReply =>
          T etyp eUnreplyEvent.getUn f edUserAct on(t etDeleteEvent, t etEventFlags)
        case T etTypeQuote =>
          T etyp eUnquoteEvent.getUn f edUserAct on(t etDeleteEvent, t etEventFlags)
        case T etTypeDefault | T etTypeEd  =>
          T etyp eDeleteEvent.getUn f edUserAct on(t etDeleteEvent, t etEventFlags)
      }
    }
  }

}
