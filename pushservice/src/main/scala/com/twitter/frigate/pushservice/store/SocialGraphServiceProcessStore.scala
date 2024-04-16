package com.tw ter.fr gate.pushserv ce.store

 mport com.tw ter.fr gate.pushserv ce.params.PushQPSL m Constants.Soc alGraphServ ceBatchS ze
 mport com.tw ter. rm .pred cate.soc algraph.Relat onEdge
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

case class Soc alGraphServ ceProcessStore(edgeStore: ReadableStore[Relat onEdge, Boolean])
    extends ReadableStore[Relat onEdge, Boolean] {
  overr de def mult Get[T <: Relat onEdge](
    relat onEdges: Set[T]
  ): Map[T, Future[Opt on[Boolean]]] = {
    val spl Set = relat onEdges.grouped(Soc alGraphServ ceBatchS ze).toSet
    spl Set
      .map { relat onsh p =>
        edgeStore.mult Get(relat onsh p)
      }.foldLeft(Map.empty[T, Future[Opt on[Boolean]]]) { (map1, map2) =>
        map1 ++ map2
      }
  }
}
