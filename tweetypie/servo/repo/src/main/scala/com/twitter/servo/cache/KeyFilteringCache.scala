package com.tw ter.servo.cac 

 mport com.tw ter.ut l.Future

/**
 * A cac  wrapper that makes t  underly ng cac  transparent to
 * certa n keys.
 */
class KeyF lter ngCac [K, V](val underly ngCac : Cac [K, V], keyPred cate: K => Boolean)
    extends Cac Wrapper[K, V] {
  overr de def get(keys: Seq[K]): Future[KeyValueResult[K, V]] =
    underly ngCac .get(keys f lter keyPred cate)

  overr de def getW hC cksum(keys: Seq[K]): Future[CsKeyValueResult[K, V]] =
    underly ngCac .getW hC cksum(keys f lter keyPred cate)

  overr de def add(key: K, value: V) =
     f (keyPred cate(key)) {
      underly ngCac .add(key, value)
    } else {
      Future.True
    }

  overr de def c ckAndSet(key: K, value: V, c cksum: C cksum) =
     f (keyPred cate(key)) {
      underly ngCac .c ckAndSet(key, value, c cksum)
    } else {
      Future.True
    }

  overr de def set(key: K, value: V) =
     f (keyPred cate(key)) {
      underly ngCac .set(key, value)
    } else {
      Future.Done
    }

  overr de def replace(key: K, value: V) =
     f (keyPred cate(key)) {
      underly ngCac .replace(key, value)
    } else {
      Future.True
    }

  overr de def delete(key: K) =
     f (keyPred cate(key)) {
      underly ngCac .delete(key)
    } else {
      Future.True
    }
}
