package com.tw ter.servo.cac 

 mport com.tw ter.ut l.{Durat on, Future}

/**
 * a Cac  that takes a TTL per set
 */
tra  TtlCac [K, V] extends ReadCac [K, V] {
  def add(key: K, value: V, ttl: Durat on): Future[Boolean]

  def c ckAndSet(key: K, value: V, c cksum: C cksum, ttl: Durat on): Future[Boolean]

  def set(key: K, value: V, ttl: Durat on): Future[Un ]

  /**
   * Replaces t  value for an ex st ng key.   f t  key doesn't ex st, t  has no effect.
   * @return true  f replaced, false  f not found
   */
  def replace(key: K, value: V, ttl: Durat on): Future[Boolean]

  /**
   * Deletes a value from cac .
   * @return true  f deleted, false  f not found
   */
  def delete(key: K): Future[Boolean]
}

/**
 * allows one TtlCac  to wrap anot r
 */
tra  TtlCac Wrapper[K, V] extends TtlCac [K, V] w h ReadCac Wrapper[K, V, TtlCac [K, V]] {
  overr de def add(key: K, value: V, ttl: Durat on) = underly ngCac .add(key, value, ttl)

  overr de def c ckAndSet(key: K, value: V, c cksum: C cksum, ttl: Durat on) =
    underly ngCac .c ckAndSet(key, value, c cksum, ttl)

  overr de def set(key: K, value: V, ttl: Durat on) = underly ngCac .set(key, value, ttl)

  overr de def replace(key: K, value: V, ttl: Durat on) = underly ngCac .replace(key, value, ttl)

  overr de def delete(key: K) = underly ngCac .delete(key)
}

class PerturbedTtlCac [K, V](
  overr de val underly ngCac : TtlCac [K, V],
  perturbTtl: Durat on => Durat on)
    extends TtlCac Wrapper[K, V] {
  overr de def add(key: K, value: V, ttl: Durat on) =
    underly ngCac .add(key, value, perturbTtl(ttl))

  overr de def c ckAndSet(key: K, value: V, c cksum: C cksum, ttl: Durat on) =
    underly ngCac .c ckAndSet(key, value, c cksum, perturbTtl(ttl))

  overr de def set(key: K, value: V, ttl: Durat on) =
    underly ngCac .set(key, value, perturbTtl(ttl))

  overr de def replace(key: K, value: V, ttl: Durat on) =
    underly ngCac .replace(key, value, perturbTtl(ttl))
}

/**
 * an adaptor to wrap a Cac [K, V]  nterface around a TtlCac [K, V]
 */
class TtlCac ToCac [K, V](overr de val underly ngCac : TtlCac [K, V], ttl: (K, V) => Durat on)
    extends Cac [K, V]
    w h ReadCac Wrapper[K, V, TtlCac [K, V]] {
  overr de def add(key: K, value: V) = underly ngCac .add(key, value, ttl(key, value))

  overr de def c ckAndSet(key: K, value: V, c cksum: C cksum) =
    underly ngCac .c ckAndSet(key, value, c cksum, ttl(key, value))

  overr de def set(key: K, value: V) = underly ngCac .set(key, value, ttl(key, value))

  overr de def replace(key: K, value: V) = underly ngCac .replace(key, value, ttl(key, value))

  overr de def delete(key: K) = underly ngCac .delete(key)
}

/**
 * use a s ngle TTL for all objects
 */
class S mpleTtlCac ToCac [K, V](underly ngTtlCac : TtlCac [K, V], ttl: Durat on)
    extends TtlCac ToCac [K, V](underly ngTtlCac , (k: K, v: V) => ttl)

/**
 * use a value-based TTL funct on
 */
class ValueBasedTtlCac ToCac [K, V](underly ngTtlCac : TtlCac [K, V], ttl: V => Durat on)
    extends TtlCac ToCac [K, V](underly ngTtlCac , (k: K, v: V) => ttl(v))

/**
 * use a key-based TTL funct on
 */
class KeyBasedTtlCac ToCac [K, V](underly ngTtlCac : TtlCac [K, V], ttl: K => Durat on)
    extends TtlCac ToCac [K, V](underly ngTtlCac , (k: K, v: V) => ttl(k))
