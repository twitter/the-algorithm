package com.tw ter.servo.cac 

 mport com.tw ter.ut l.{Future, Return}
 mport scala.collect on.mutable

/**
 * uses a forward ng cac  to lookup a value by a secondary  ndex.
 * f lters out values for wh ch t  requested secondary  ndex does not
 * match t  actual secondary  ndex (t se are treated as a m ss)
 */
class Forward ngCac [K, F, V](
  forward ngCac : Cac [K, Cac d[F]],
  underly ngCac : Secondary ndex ngCac [F, _, V],
  pr maryKey: V => F,
  secondaryKey: Secondary ndex ngCac . ndexMapp ng[K, V],
  lock ngCac Factory: Lock ngCac Factory)
    extends Lock ngCac [K, Cac d[V]] {
  protected[t ] case class Forward ngC cksum(
    forward ngC cksum: C cksum,
    underly ngC cksum: Opt on[C cksum])
      extends C cksum

  protected[t ] val lock ngUnderly ng = lock ngCac Factory(underly ngCac )
  protected[t ] val lock ngForward ng = lock ngCac Factory(forward ngCac )

  overr de def get(keys: Seq[K]): Future[KeyValueResult[K, Cac d[V]]] = {
    forward ngCac .get(keys) flatMap { flr =>
      val (tombstones, notTombstones) = {
        val tombstones = mutable.Map.empty[K, Cac d[F]]
        val notTombstones = mutable.Map.empty[F, K]
        // spl  results  nto tombstoned keys and non-tombstoned key/pKeys
        // wh le  're at  , produce a reverse-keymap of non-tombstones
        flr.found foreach {
          case (key, cac dPKey) =>
            cac dPKey.value match {
              case So (pKey) => notTombstones += pKey -> key
              case None => tombstones += key -> cac dPKey
            }
        }
        (tombstones.toMap, notTombstones.toMap)
      }

      // only make call to underly ngCac   f t re are keys to lookup
      val fromUnderly ng =  f (notTombstones. sEmpty) {
        KeyValueResult.emptyFuture
      } else {
        // get non-tombstoned values from underly ng cac 
        underly ngCac .get(notTombstones.keys.toSeq) map { lr =>
          val (goodValues, badValues) = lr.found part  on {
            case (pKey, cac dValue) =>
              // f lter out values that so how don't match t  pr mary key and secondary key
              cac dValue.value match {
                case So (value) =>
                  secondaryKey(value) match {
                    case Return(So (sKey)) =>
                      pKey == pr maryKey(value) && sKey == notTombstones(pKey)
                    case _ => false
                  }
                case None => true
              }
          }
          val found = goodValues map { case (k, v) => notTombstones(k) -> v }
          val notFound = (lr.notFound ++ badValues.keySet) map { notTombstones(_) }
          val fa led = lr.fa led map { case (k, t) => notTombstones(k) -> t }
          KeyValueResult(found, notFound, fa led)
        } handle {
          case t =>
            KeyValueResult(fa led = notTombstones.values map { _ -> t } toMap)
        }
      }

      fromUnderly ng map { lr =>
        // f ll  n tombstone values, copy ng t   tadata from t  Cac d[F]
        val w hTombstones = tombstones map {
          case (key, cac dPKey) =>
            key -> cac dPKey.copy[V](value = None)
        }
        val found = lr.found ++ w hTombstones
        val notFound = flr.notFound ++ lr.notFound
        val fa led = flr.fa led ++ lr.fa led
        KeyValueResult(found, notFound, fa led)
      }
    }
  }

  // s nce    mple nt lockAndSet d rectly,   don't support getW hC cksum and c ckAndSet.
  //   should cons der chang ng t  class h erarchy of Cac /Lock ngCac  so that t  can
  // be c cked at comp le t  .

  overr de def getW hC cksum(keys: Seq[K]): Future[CsKeyValueResult[K, Cac d[V]]] =
    Future.except on(new UnsupportedOperat onExcept on("Use lockAndSet d rectly"))

  overr de def c ckAndSet(key: K, cac dValue: Cac d[V], c cksum: C cksum): Future[Boolean] =
    Future.except on(new UnsupportedOperat onExcept on("Use lockAndSet d rectly"))

  protected[t ] def maybeAddForward ng ndex(
    key: K,
    cac dPr maryKey: Cac d[F],
    wasAdded: Boolean
  ): Future[Boolean] = {
     f (wasAdded)
      forward ngCac .set(key, cac dPr maryKey) map { _ =>
        true
      }
    else
      Future.value(false)
  }

  overr de def add(key: K, cac dValue: Cac d[V]): Future[Boolean] = {
    // copy t  cac   tadata to t  pr maryKey
    val cac dPr maryKey = cac dValue map { pr maryKey(_) }
    cac dPr maryKey.value match {
      case So (pKey) =>
        //  f a value can be der ved from t  key, use t  underly ng cac  to add  
        // t  underly ng cac  w ll create t  secondary  ndex as a s de-effect
        underly ngCac .add(pKey, cac dValue)
      case None =>
        // ot rw se,  're just wr  ng a tombstone, so   need to c ck  f   ex sts
        forward ngCac .add(key, cac dPr maryKey)
    }
  }

  overr de def lockAndSet(
    key: K,
    handler: Lock ngCac .Handler[Cac d[V]]
  ): Future[Opt on[Cac d[V]]] = {
    handler(None) match {
      case So (cac dValue) =>
        cac dValue.value match {
          case So (value) =>
            // set on t  underly ng cac , and let   take care of add ng
            // t  secondary  ndex
            val pKey = pr maryKey(value)
            lock ngUnderly ng.lockAndSet(pKey, handler)
          case None =>
            // no underly ng value to set, so just wr e t  forward ng entry.
            // secondary ndex ngCac  doesn't lock for t  set, so t re's
            // no po nt  n   do ng  . T re's a sl ght r sk of wr  ng an
            // errant tombstone  n a race, but t  only way to get around t 
            // would be to lock around *all* pr mary and secondary  ndexes,
            // wh ch could produce deadlocks, wh ch  s probably worse.
            val cac dEmptyPKey = cac dValue.copy[F](value = None)
            forward ngCac .set(key, cac dEmptyPKey) map { _ =>
              So (cac dValue)
            }
        }
      case None =>
        // noth ng to do  re
        Future.value(None)
    }
  }

  overr de def set(key: K, cac dValue: Cac d[V]): Future[Un ] = {
    cac dValue.value match {
      case So (value) =>
        // set on t  underly ng cac , and let   take care of add ng
        // t  secondary  ndex
        val pKey = pr maryKey(value)
        underly ngCac .set(pKey, cac dValue)
      case None =>
        // no underly ng value to set, so just wr e t  forward ng entry
        forward ngCac .set(key, cac dValue.copy[F](value = None))
    }
  }

  overr de def replace(key: K, cac dValue: Cac d[V]): Future[Boolean] = {
    cac dValue.value match {
      case So (value) =>
        // replace  n t  underly ng cac , and let   take care of add ng t  secondary  ndex
        val pKey = pr maryKey(value)
        underly ngCac .replace(pKey, cac dValue)
      case None =>
        // no underly ng value to set, so just wr e t  forward ng entry
        forward ngCac .replace(key, cac dValue.copy[F](value = None))
    }
  }

  overr de def delete(key: K): Future[Boolean] = {
    forward ngCac .delete(key)
  }

  overr de def release(): Un  = {
    forward ngCac .release()
    underly ngCac .release()
  }
}
