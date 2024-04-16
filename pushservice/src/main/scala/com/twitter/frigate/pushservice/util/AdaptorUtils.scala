package com.tw ter.fr gate.pushserv ce.ut l

 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eResult
 mport com.tw ter.storehaus.FutureOps
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

object AdaptorUt ls {
  def getT etyP eResults(
    t et ds: Set[Long],
    t etyP eStore: ReadableStore[Long, T etyP eResult],
  ): Future[Map[Long, Opt on[T etyP eResult]]] =
    FutureOps
      .mapCollect(t etyP eStore.mult Get(t et ds))
}
