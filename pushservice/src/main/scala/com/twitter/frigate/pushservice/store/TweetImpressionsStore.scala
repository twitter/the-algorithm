package com.tw ter.fr gate.pushserv ce.store

 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.ut l.Future

/**
 * Store to get  nbound T et  mpress ons count for a spec f c T et  d.
 */
class T et mpress onsStore(stratoCl ent: StratoCl ent) extends ReadableStore[Long, Str ng] {

  pr vate val column = "rux/ mpress on.T et"
  pr vate val store = StratoFetchableStore.w hUn V ew[Long, Str ng](stratoCl ent, column)

  def getCounts(t et d: Long): Future[Opt on[Long]] = {
    store.get(t et d).map(_.map(_.toLong))
  }
}
