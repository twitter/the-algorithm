package com.tw ter.tsp.ut ls

 mport com.tw ter.storehaus.AbstractReadableStore
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

class ReadableStoreW hMapOpt onValues[K, V1, V2](rs: ReadableStore[K, V1]) {

  def mapOpt onValues(
    fn: V1 => Opt on[V2]
  ): ReadableStore[K, V2] = {
    val self = rs
    new AbstractReadableStore[K, V2] {
      overr de def get(k: K): Future[Opt on[V2]] = self.get(k).map(_.flatMap(fn))

      overr de def mult Get[K1 <: K](ks: Set[K1]): Map[K1, Future[Opt on[V2]]] =
        self.mult Get(ks).mapValues(_.map(_.flatMap(fn)))
    }
  }
}
