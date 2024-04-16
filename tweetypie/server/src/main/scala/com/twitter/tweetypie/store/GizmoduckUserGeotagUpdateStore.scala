package com.tw ter.t etyp e
package store

 mport com.tw ter.g zmoduck.thr ftscala.LookupContext
 mport com.tw ter.g zmoduck.thr ftscala.Mod f edAccount
 mport com.tw ter.g zmoduck.thr ftscala.Mod f edUser
 mport com.tw ter.t etyp e.backends.G zmoduck
 mport com.tw ter.t etyp e.thr ftscala._

tra  G zmoduckUserGeotagUpdateStore
    extends T etStoreBase[G zmoduckUserGeotagUpdateStore]
    w h Async nsertT et.Store
    w h ScrubGeoUpdateUserT  stamp.Store {
  def wrap(w: T etStore.Wrap): G zmoduckUserGeotagUpdateStore =
    new T etStoreWrapper(w, t )
      w h G zmoduckUserGeotagUpdateStore
      w h Async nsertT et.StoreWrapper
      w h ScrubGeoUpdateUserT  stamp.StoreWrapper
}

/**
 * A T etStore  mple ntat on that updates a G zmoduck user's user_has_geotagged_status flag.
 *  f a t et  s geotagged and t  user's flag  s not set, call out to G zmoduck to update  .
 */
object G zmoduckUserGeotagUpdateStore {
  val Act on: AsyncWr eAct on.UserGeotagUpdate.type = AsyncWr eAct on.UserGeotagUpdate

  def apply(
    mod fyAndGet: G zmoduck.Mod fyAndGet,
    stats: StatsRece ver
  ): G zmoduckUserGeotagUpdateStore = {
    // Counts t  number of t  s that t  scrubGeo actually cleared t 
    // hasGeotaggedStatuses b  for a user.
    val clearedCounter = stats.counter("has_geotag_cleared")

    // Counts t  number of t  s that async nsertT et actually set t 
    // hasGeotaggedStatuses b  for a user.
    val setCounter = stats.counter("has_geotag_set")

    def setHasGeotaggedStatuses(value: Boolean): FutureEffect[User d] = {
      val mod f edAccount = Mod f edAccount(hasGeotaggedStatuses = So (value))
      val mod f edUser = Mod f edUser(account = So (mod f edAccount))
      FutureEffect(user d => mod fyAndGet((LookupContext(), user d, mod f edUser)).un )
    }

    new G zmoduckUserGeotagUpdateStore {
      overr de val async nsertT et: FutureEffect[Async nsertT et.Event] =
        setHasGeotaggedStatuses(true)
          .contramap[Async nsertT et.Event](_.user. d)
          .onSuccess(_ => setCounter. ncr())
          .only f { e =>
            // only w h geo  nfo and an account that doesn't yet have geotagged statuses flag set
            hasGeo(e.t et) && (e.user.account.ex sts(!_.hasGeotaggedStatuses))
          }

      overr de val retryAsync nsertT et: FutureEffect[
        T etStoreRetryEvent[Async nsertT et.Event]
      ] =
        T etStore.retry(Act on, async nsertT et)

      overr de val scrubGeoUpdateUserT  stamp: FutureEffect[ScrubGeoUpdateUserT  stamp.Event] =
        setHasGeotaggedStatuses(false)
          .contramap[ScrubGeoUpdateUserT  stamp.Event](_.user d)
          .only f(_.m ghtHaveGeotaggedStatuses)
          .onSuccess(_ => clearedCounter. ncr())
    }
  }
}
