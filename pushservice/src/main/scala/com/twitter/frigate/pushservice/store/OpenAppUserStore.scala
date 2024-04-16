package com.tw ter.fr gate.pushserv ce.store

 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.strato.generated.cl ent.rux.open_app.Users nOpenAppDdgOnUserCl entColumn

object OpenAppUserStore {
  def apply(stratoCl ent: Cl ent): ReadableStore[Long, Boolean] = {
    val fetc r = new Users nOpenAppDdgOnUserCl entColumn(stratoCl ent).fetc r
    StratoFetchableStore.w hUn V ew(fetc r).mapValues(_ => true)
  }
}
