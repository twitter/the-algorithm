package com.tw ter.fr gate.pushserv ce.take. tory

 mport com.tw ter.eventbus.cl ent.EventBusPubl s r
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.Not f cat onScr beUt l
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.fr gate.scr be.thr ftscala.Not f cat onScr be
 mport com.tw ter.fr gate.thr ftscala.Fr gateNot f cat on

class EventBusWr er(
  eventBusPubl s r: EventBusPubl s r[Not f cat onScr be],
  stats: StatsRece ver) {
  pr vate def wr eSendEventToEventBus(
    target: PushTypes.Target,
    not f cat onScr be: Not f cat onScr be
  ): Un  = {
     f (target.params(PushParams.EnablePushSendEventBus)) {
      val result = eventBusPubl s r.publ sh(not f cat onScr be)
      result.onFa lure { _ => stats.counter("push_send_eventbus_fa lure"). ncr() }
    }
  }

  def wr eToEventBus(
    cand date: PushCand date,
    fr gateNot f cat onForPers stence: Fr gateNot f cat on
  ): Un  = {
    val not f cat onScr be = Not f cat onScr beUt l.getNot f cat onScr be(
      target d = cand date.target.target d,
       mpress on d = cand date. mpress on d,
      fr gateNot f cat on = fr gateNot f cat onForPers stence,
      createdAt = cand date.createdAt
    )
    wr eSendEventToEventBus(cand date.target, not f cat onScr be)
  }
}
