package com.tw ter.t etyp e
package store

object Flush extends T etStore.SyncModule {

  case class Event(
    t et ds: Seq[T et d],
    flushT ets: Boolean = true,
    flushCounts: Boolean = true,
    logEx st ng: Boolean = true)
      extends SyncT etStoreEvent("flush")

  tra  Store {
    val flush: FutureEffect[Event]
  }

  tra  StoreWrapper extends Store { self: T etStoreWrapper[Store] =>
    overr de val flush: FutureEffect[Event] = wrap(underly ng.flush)
  }

  object Store {
    def apply(
      cach ngT etStore: Cach ngT etStore,
      t etCountsUpdat ngStore: T etCountsCac Updat ngStore
    ): Store =
      new Store {
        overr de val flush: FutureEffect[Event] =
          FutureEffect. nParallel(
            cach ngT etStore.flush,
            t etCountsUpdat ngStore.flush
          )
      }
  }
}
