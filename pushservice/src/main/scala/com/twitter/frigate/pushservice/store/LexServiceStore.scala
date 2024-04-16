package com.tw ter.fr gate.pushserv ce.store

 mport com.tw ter.l vev deo.common. ds.Event d
 mport com.tw ter.l vev deo.t  l ne.cl ent.v2.L veV deoT  l neCl ent
 mport com.tw ter.l vev deo.t  l ne.doma n.v2.Event
 mport com.tw ter.l vev deo.t  l ne.doma n.v2.LookupContext
 mport com.tw ter.st ch.storehaus.ReadableStoreOfSt ch
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.storehaus.ReadableStore

case class EventRequest(event d: Long, lookupContext: LookupContext = LookupContext.default)

object LexServ ceStore {
  def apply(
    l veV deoT  l neCl ent: L veV deoT  l neCl ent
  ): ReadableStore[EventRequest, Event] = {
    ReadableStoreOfSt ch { eventRequest =>
      l veV deoT  l neCl ent.getEvent(
        Event d(eventRequest.event d),
        eventRequest.lookupContext) rescue {
        case NotFound => St ch.NotFound
      }
    }
  }
}
