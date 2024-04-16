package com.tw ter.t etyp e
package store

 mport com.tw ter.t  l neserv ce.fanout.thr ftscala.FanoutServ ce
 mport com.tw ter.t etyp e.thr ftscala._

tra  FanoutServ ceStore extends T etStoreBase[FanoutServ ceStore] w h Async nsertT et.Store {
  def wrap(w: T etStore.Wrap): FanoutServ ceStore =
    new T etStoreWrapper(w, t ) w h FanoutServ ceStore w h Async nsertT et.StoreWrapper
}

object FanoutServ ceStore {
  val Act on: AsyncWr eAct on.FanoutDel very.type = AsyncWr eAct on.FanoutDel very

  def apply(
    fanoutCl ent: FanoutServ ce. thodPerEndpo nt,
    stats: StatsRece ver
  ): FanoutServ ceStore =
    new FanoutServ ceStore {
      overr de val async nsertT et: FutureEffect[Async nsertT et.Event] =
        FutureEffect[Async nsertT et.Event] { event =>
          fanoutCl ent.t etCreateEvent2(
            T etCreateEvent(
              t et = event.t et,
              user = event.user,
              s ceT et = event.s ceT et,
              s ceUser = event.s ceUser,
              add  onalContext = event.add  onalContext,
              trans entContext = event.trans entContext
            )
          )
        }

      overr de val retryAsync nsertT et: FutureEffect[
        T etStoreRetryEvent[Async nsertT et.Event]
      ] = T etStore.retry(Act on, async nsertT et)
    }
}
