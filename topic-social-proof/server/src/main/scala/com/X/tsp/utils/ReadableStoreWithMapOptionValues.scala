package com.X.tsp.utils

import com.X.storehaus.AbstractReadableStore
import com.X.storehaus.ReadableStore
import com.X.util.Future

class ReadableStoreWithMapOptionValues[K, V1, V2](rs: ReadableStore[K, V1]) {

  def mapOptionValues(
    fn: V1 => Option[V2]
  ): ReadableStore[K, V2] = {
    val self = rs
    new AbstractReadableStore[K, V2] {
      override def get(k: K): Future[Option[V2]] = self.get(k).map(_.flatMap(fn))

      override def multiGet[K1 <: K](ks: Set[K1]): Map[K1, Future[Option[V2]]] =
        self.multiGet(ks).mapValues(_.map(_.flatMap(fn)))
    }
  }
}
