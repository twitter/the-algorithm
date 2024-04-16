package com.tw ter.t etyp e
package store

 mport com.tw ter.t etyp e.thr ftscala._

/**
 * AsyncEnqueueStore converts certa ns T etStoreEvent types  nto t  r async-counterpart
 * events, and enqueues those to a deferredrpc-backed Thr ftT etServ ce  nstance.
 */
tra  AsyncEnqueueStore
    extends T etStoreBase[AsyncEnqueueStore]
    w h  nsertT et.Store
    w h DeleteT et.Store
    w h UndeleteT et.Store
    w h  ncrFavCount.Store
    w h  ncrBookmarkCount.Store
    w h SetAdd  onalF elds.Store
    w h SetRet etV s b l y.Store
    w h Takedown.Store
    w h DeleteAdd  onalF elds.Store
    w h UpdatePoss blySens  veT et.Store {
  def wrap(w: T etStore.Wrap): AsyncEnqueueStore =
    new T etStoreWrapper[AsyncEnqueueStore](w, t )
      w h AsyncEnqueueStore
      w h  nsertT et.StoreWrapper
      w h DeleteT et.StoreWrapper
      w h UndeleteT et.StoreWrapper
      w h  ncrFavCount.StoreWrapper
      w h  ncrBookmarkCount.StoreWrapper
      w h SetAdd  onalF elds.StoreWrapper
      w h SetRet etV s b l y.StoreWrapper
      w h Takedown.StoreWrapper
      w h DeleteAdd  onalF elds.StoreWrapper
      w h UpdatePoss blySens  veT et.StoreWrapper
}

object AsyncEnqueueStore {
  def apply(
    t etServ ce: Thr ftT etServ ce,
    scrubUser nAsync nserts: User => User,
    scrubS ceT et nAsync nserts: T et => T et,
    scrubS ceUser nAsync nserts: User => User
  ): AsyncEnqueueStore =
    new AsyncEnqueueStore {
      overr de val  nsertT et: FutureEffect[ nsertT et.Event] =
        FutureEffect[ nsertT et.Event] { e =>
          t etServ ce.async nsert(
            e.toAsyncRequest(
              scrubUser nAsync nserts,
              scrubS ceT et nAsync nserts,
              scrubS ceUser nAsync nserts
            )
          )
        }

      overr de val deleteT et: FutureEffect[DeleteT et.Event] =
        FutureEffect[DeleteT et.Event] { e => t etServ ce.asyncDelete(e.toAsyncRequest) }

      overr de val undeleteT et: FutureEffect[UndeleteT et.Event] =
        FutureEffect[UndeleteT et.Event] { e =>
          t etServ ce.asyncUndeleteT et(e.toAsyncUndeleteT etRequest)
        }

      overr de val  ncrFavCount: FutureEffect[ ncrFavCount.Event] =
        FutureEffect[ ncrFavCount.Event] { e => t etServ ce.async ncrFavCount(e.toAsyncRequest) }

      overr de val  ncrBookmarkCount: FutureEffect[ ncrBookmarkCount.Event] =
        FutureEffect[ ncrBookmarkCount.Event] { e =>
          t etServ ce.async ncrBookmarkCount(e.toAsyncRequest)
        }

      overr de val setAdd  onalF elds: FutureEffect[SetAdd  onalF elds.Event] =
        FutureEffect[SetAdd  onalF elds.Event] { e =>
          t etServ ce.asyncSetAdd  onalF elds(e.toAsyncRequest)
        }

      overr de val setRet etV s b l y: FutureEffect[SetRet etV s b l y.Event] =
        FutureEffect[SetRet etV s b l y.Event] { e =>
          t etServ ce.asyncSetRet etV s b l y(e.toAsyncRequest)
        }

      overr de val deleteAdd  onalF elds: FutureEffect[DeleteAdd  onalF elds.Event] =
        FutureEffect[DeleteAdd  onalF elds.Event] { e =>
          t etServ ce.asyncDeleteAdd  onalF elds(e.toAsyncRequest)
        }

      overr de val updatePoss blySens  veT et: FutureEffect[UpdatePoss blySens  veT et.Event] =
        FutureEffect[UpdatePoss blySens  veT et.Event] { e =>
          t etServ ce.asyncUpdatePoss blySens  veT et(e.toAsyncRequest)
        }

      overr de val takedown: FutureEffect[Takedown.Event] =
        FutureEffect[Takedown.Event] { e => t etServ ce.asyncTakedown(e.toAsyncRequest) }
    }
}
