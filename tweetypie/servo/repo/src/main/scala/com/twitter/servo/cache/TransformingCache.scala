package com.tw ter.servo.cac 

 mport com.tw ter.servo.ut l.Transfor r
 mport com.tw ter.ut l.{Durat on, Future, Return, Throw}
 mport scala.collect on.mutable.ArrayBuffer
 mport scala.collect on.{breakOut, mutable}

/**
 * Adaptor from a ReadCac [K, V1] to an underly ng ReadCac [K, V2]
 *
 * a Transfor r  s used to map bet en value types
 */
class ValueTransform ngReadCac [K, V1, V2](
  underly ngCac : ReadCac [K, V2],
  transfor r: Transfor r[V1, V2])
    extends ReadCac [K, V1] {
  // overr dden to avo d mapp ng t  unneeded keyMap
  overr de def get(keys: Seq[K]): Future[KeyValueResult[K, V1]] = {
    underly ngCac .get(keys) map { lr =>
      // fold lr.found  nto found/deser al zat on fa lures
      val found = mutable.Map.empty[K, V1]
      val fa led = mutable.Map.empty[K, Throwable]

      lr.found foreach {
        case (key, value) =>
          transfor r.from(value) match {
            case Return(v) => found += key -> v
            case Throw(t) => fa led += key -> t
          }
      }

      lr.copy(found = found.toMap, fa led = lr.fa led ++ fa led.toMap)
    } handle {
      case t =>
        KeyValueResult(fa led = keys.map(_ -> t).toMap)
    }
  }

  // overr dden to avo d mapp ng t  unneeded keyMap
  overr de def getW hC cksum(keys: Seq[K]): Future[CsKeyValueResult[K, V1]] = {
    underly ngCac .getW hC cksum(keys) map { clr =>
      clr.copy(found = clr.found map {
        case (key, (value, c cksum)) =>
          key -> (value flatMap { transfor r.from(_) }, c cksum)
      })
    } handle {
      case t =>
        KeyValueResult(fa led = keys.map(_ -> t).toMap)
    }
  }

  overr de def release() = underly ngCac .release()
}

/**
 * Adaptor from a ReadCac [K, V1] to an underly ng ReadCac [K2, V2]
 *
 * a Transfor r  s used to map bet en value types, and a
 * one-way mapp ng  s used for keys, mak ng   poss ble to
 * store data  n t  underly ng cac  us ng keys that can't
 * eas ly be reverse-mapped.
 */
class KeyValueTransform ngReadCac [K1, K2, V1, V2](
  underly ngCac : ReadCac [K2, V2],
  transfor r: Transfor r[V1, V2],
  underly ngKey: K1 => K2)
    extends ReadCac [K1, V1] {

  // make keymapp ng for key recovery later
  pr vate[t ] def mappedKeys(
    keys: Seq[K1]
  ): ( ndexedSeq[K2], Map[K2, K1]) = {
    val k2s = new ArrayBuffer[K2](keys.s ze)
    val k2k1s: Map[K2, K1] =
      keys.map { key =>
        val k2 = underly ngKey(key)
        k2s += k2
        k2 -> key
      }(breakOut)
    (k2s, k2k1s)
  }

  overr de def get(keys: Seq[K1]): Future[KeyValueResult[K1, V1]] = {
    val (k2s, kMap) = mappedKeys(keys)

    underly ngCac 
      .get(k2s)
      .map { lr =>
        // fold lr.found  nto found/deser al zat on fa lures
        val found = Map.newBu lder[K1, V1]
        val fa led = Map.newBu lder[K1, Throwable]

        lr.found.foreach {
          case (key, value) =>
            transfor r.from(value) match {
              case Return(v) => found += kMap(key) -> v
              case Throw(t) => fa led += kMap(key) -> t
            }
        }

        lr.fa led.foreach {
          case (k, t) =>
            fa led += kMap(k) -> t
        }

        KeyValueResult(
          found.result(),
          lr.notFound.map { kMap(_) },
          fa led.result()
        )
      }
      .handle {
        case t =>
          KeyValueResult(fa led = keys.map(_ -> t).toMap)
      }
  }

  overr de def getW hC cksum(keys: Seq[K1]): Future[CsKeyValueResult[K1, V1]] = {
    val (k2s, kMap) = mappedKeys(keys)

    underly ngCac 
      .getW hC cksum(k2s)
      .map { clr =>
        KeyValueResult(
          clr.found.map {
            case (key, (value, c cksum)) =>
              kMap(key) -> (value.flatMap(transfor r.from), c cksum)
          },
          clr.notFound map { kMap(_) },
          clr.fa led map {
            case (key, t) =>
              kMap(key) -> t
          }
        )
      }
      .handle {
        case t =>
          KeyValueResult(fa led = keys.map(_ -> t).toMap)
      }
  }

  overr de def release(): Un  = underly ngCac .release()
}

class KeyTransform ngCac [K1, K2, V](underly ngCac : Cac [K2, V], underly ngKey: K1 => K2)
    extends KeyValueTransform ngCac [K1, K2, V, V](
      underly ngCac ,
      Transfor r. dent y,
      underly ngKey
    )

/**
 * Adaptor from a Cac [K, V1] to an underly ng Cac [K, V2]
 *
 * a Transfor r  s used to map bet en value types
 */
class ValueTransform ngCac [K, V1, V2](
  underly ngCac : Cac [K, V2],
  transfor r: Transfor r[V1, V2])
    extends ValueTransform ngReadCac [K, V1, V2](underly ngCac , transfor r)
    w h Cac [K, V1] {
  pr vate[t ] def to(v1: V1): Future[V2] = Future.const(transfor r.to(v1))

  overr de def add(key: K, value: V1): Future[Boolean] =
    to(value) flatMap { underly ngCac .add(key, _) }

  overr de def c ckAndSet(key: K, value: V1, c cksum: C cksum): Future[Boolean] =
    to(value) flatMap { underly ngCac .c ckAndSet(key, _, c cksum) }

  overr de def set(key: K, value: V1): Future[Un ] =
    to(value) flatMap { underly ngCac .set(key, _) }

  overr de def replace(key: K, value: V1): Future[Boolean] =
    to(value) flatMap { underly ngCac .replace(key, _) }

  overr de def delete(key: K): Future[Boolean] =
    underly ngCac .delete(key)
}

/**
 * Adaptor from a Cac [K1, V1] to an underly ng Cac [K2, V2]
 *
 * a Transfor r  s used to map bet en value types, and a
 * one-way mapp ng  s used for keys, mak ng   poss ble to
 * store data  n t  underly ng cac  us ng keys that can't
 * eas ly be reverse-mapped.
 */
class KeyValueTransform ngCac [K1, K2, V1, V2](
  underly ngCac : Cac [K2, V2],
  transfor r: Transfor r[V1, V2],
  underly ngKey: K1 => K2)
    extends KeyValueTransform ngReadCac [K1, K2, V1, V2](
      underly ngCac ,
      transfor r,
      underly ngKey
    )
    w h Cac [K1, V1] {
  pr vate[t ] def to(v1: V1): Future[V2] = Future.const(transfor r.to(v1))

  overr de def add(key: K1, value: V1): Future[Boolean] =
    to(value) flatMap { underly ngCac .add(underly ngKey(key), _) }

  overr de def c ckAndSet(key: K1, value: V1, c cksum: C cksum): Future[Boolean] =
    to(value) flatMap { underly ngCac .c ckAndSet(underly ngKey(key), _, c cksum) }

  overr de def set(key: K1, value: V1): Future[Un ] =
    to(value) flatMap { underly ngCac .set(underly ngKey(key), _) }

  overr de def replace(key: K1, value: V1): Future[Boolean] =
    to(value) flatMap { underly ngCac .replace(underly ngKey(key), _) }

  overr de def delete(key: K1): Future[Boolean] =
    underly ngCac .delete(underly ngKey(key))
}

/**
 * Adaptor from a TtlCac [K, V1] to an underly ng TtlCac [K, V2]
 *
 * a Transfor r  s used to map bet en value types
 */
class ValueTransform ngTtlCac [K, V1, V2](
  underly ngCac : TtlCac [K, V2],
  transfor r: Transfor r[V1, V2])
    extends ValueTransform ngReadCac [K, V1, V2](underly ngCac , transfor r)
    w h TtlCac [K, V1] {
  pr vate[t ] def to(v1: V1): Future[V2] = Future.const(transfor r.to(v1))

  overr de def add(key: K, value: V1, ttl: Durat on): Future[Boolean] =
    to(value) flatMap { underly ngCac .add(key, _, ttl) }

  overr de def c ckAndSet(
    key: K,
    value: V1,
    c cksum: C cksum,
    ttl: Durat on
  ): Future[Boolean] =
    to(value) flatMap { underly ngCac .c ckAndSet(key, _, c cksum, ttl) }

  overr de def set(key: K, value: V1, ttl: Durat on): Future[Un ] =
    to(value) flatMap { underly ngCac .set(key, _, ttl) }

  overr de def replace(key: K, value: V1, ttl: Durat on): Future[Boolean] =
    to(value) flatMap { underly ngCac .replace(key, _, ttl) }

  overr de def delete(key: K): Future[Boolean] =
    underly ngCac .delete(key)
}

/**
 * Adaptor from a TtlCac [K1, V1] to an underly ng TtlCac [K2, V2]
 *
 * a Transfor r  s used to map bet en value types, and a
 * one-way mapp ng  s used for keys, mak ng   poss ble to
 * store data  n t  underly ng cac  us ng keys that can't
 * eas ly be reverse-mapped.
 */
class KeyValueTransform ngTtlCac [K1, K2, V1, V2](
  underly ngCac : TtlCac [K2, V2],
  transfor r: Transfor r[V1, V2],
  underly ngKey: K1 => K2)
    extends KeyValueTransform ngReadCac [K1, K2, V1, V2](
      underly ngCac ,
      transfor r,
      underly ngKey
    )
    w h TtlCac [K1, V1] {
  pr vate[t ] def to(v1: V1): Future[V2] = Future.const(transfor r.to(v1))

  overr de def add(key: K1, value: V1, ttl: Durat on): Future[Boolean] =
    to(value) flatMap { underly ngCac .add(underly ngKey(key), _, ttl) }

  overr de def c ckAndSet(
    key: K1,
    value: V1,
    c cksum: C cksum,
    ttl: Durat on
  ): Future[Boolean] =
    to(value) flatMap { underly ngCac .c ckAndSet(underly ngKey(key), _, c cksum, ttl) }

  overr de def set(key: K1, value: V1, ttl: Durat on): Future[Un ] =
    to(value) flatMap { underly ngCac .set(underly ngKey(key), _, ttl) }

  overr de def replace(key: K1, value: V1, ttl: Durat on): Future[Boolean] =
    to(value) flatMap { underly ngCac .replace(underly ngKey(key), _, ttl) }

  overr de def delete(key: K1): Future[Boolean] =
    underly ngCac .delete(underly ngKey(key))
}

class KeyTransform ngTtlCac [K1, K2, V](underly ngCac : TtlCac [K2, V], underly ngKey: K1 => K2)
    extends KeyValueTransform ngTtlCac [K1, K2, V, V](
      underly ngCac ,
      Transfor r. dent y,
      underly ngKey
    )

class KeyTransform ngLock ngCac [K1, K2, V](
  underly ngCac : Lock ngCac [K2, V],
  underly ngKey: K1 => K2)
    extends KeyValueTransform ngCac [K1, K2, V, V](
      underly ngCac ,
      Transfor r. dent y,
      underly ngKey
    )
    w h Lock ngCac [K1, V] {
   mport Lock ngCac ._

  overr de def lockAndSet(key: K1, handler: Handler[V]): Future[Opt on[V]] =
    underly ngCac .lockAndSet(underly ngKey(key), handler)
}

class KeyTransform ngCounterCac [K1, K2](
  underly ngCac : CounterCac [K2],
  underly ngKey: K1 => K2)
    extends KeyTransform ngCac [K1, K2, Long](underly ngCac , underly ngKey)
    w h CounterCac [K1] {
  overr de def  ncr(key: K1, delta:  nt = 1): Future[Opt on[Long]] = {
    underly ngCac . ncr(underly ngKey(key), delta)
  }

  overr de def decr(key: K1, delta:  nt = 1): Future[Opt on[Long]] = {
    underly ngCac .decr(underly ngKey(key), delta)
  }
}
