package com.tw ter.t etyp e
package store

 mport com.tw ter.tseng.w hhold ng.thr ftscala.TakedownReason
 mport com.tw ter.t etyp e.thr ftscala._

object QuotedT etTakedown extends T etStore.SyncModule {

  case class Event(
    quot ngT et d: T et d,
    quot ngUser d: User d,
    quotedT et d: T et d,
    quotedUser d: User d,
    takedownCountryCodes: Seq[Str ng],
    takedownReasons: Seq[TakedownReason],
    t  stamp: T  ,
    optUser: Opt on[User] = None)
      extends SyncT etStoreEvent("quoted_t et_takedown")
      w h T etStoreT etEvent {

    overr de def toT etEventData: Seq[T etEventData] =
      Seq(
        T etEventData.QuotedT etTakedownEvent(
          QuotedT etTakedownEvent(
            quot ngT et d = quot ngT et d,
            quot ngUser d = quot ngUser d,
            quotedT et d = quotedT et d,
            quotedUser d = quotedUser d,
            takedownCountryCodes = takedownCountryCodes,
            takedownReasons = takedownReasons
          )
        )
      )
  }

  tra  Store {
    val quotedT etTakedown: FutureEffect[Event]
  }

  tra  StoreWrapper extends Store { self: T etStoreWrapper[Store] =>
    overr de val quotedT etTakedown: FutureEffect[Event] = wrap(underly ng.quotedT etTakedown)
  }

  object Store {
    def apply(eventBusEnqueueStore: T etEventBusStore): Store =
      new Store {
        overr de val quotedT etTakedown: FutureEffect[Event] =
          eventBusEnqueueStore.quotedT etTakedown
      }
  }
}
