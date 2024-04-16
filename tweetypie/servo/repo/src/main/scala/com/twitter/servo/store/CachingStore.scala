package com.tw ter.servo.store

 mport com.tw ter.servo.cac .{Cac d, Cac dValueStatus, Lock ngCac }
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.ut l.{Future, T  }

/**
 * Wraps a cac  around an underly ng store.
 *
 * Cach ngStore  s a spec al zat on of Transform ngCach ngStore w re t  store and cac  are
 * assu d to have t  sa  key and value types. See Transform ngCach ngStore for a d scuss on
 * of t  argu nts to Cach ngStore.
 */
class Cach ngStore[K, V](
  cac : Lock ngCac [K, Cac d[V]],
  underly ng: Store[K, V],
  valueP cker: Lock ngCac .P cker[Cac d[V]],
  key: V => K)
    extends Transform ngCach ngStore[K, V, K, V](
      cac ,
      underly ng,
      valueP cker,
      key,
       dent y,
       dent y
    )

/**
 * Wraps a cac  of d ffer ng key/value types around an underly ng store.
 *
 * Updates are appl ed f rst (unmod f ed) to t  underly ng store and t n
 * t  cac   s updated after runn ng t  key/value through a one-way funct on
 * to der ve t  key/value as expected by t  cac .
 *
 * @param cac 
 *   t  wrapp ng cac 
 *
 * @param underly ng
 *   t  underly ng store
 *
 * @param valueP cker
 *   chooses bet en ex st ng and new value
 *
 * @param key
 *   computes a key from t  value be ng stored
 *
 * @param cac Key
 *   transforms t  store's key type to t  cac 's key type
 *
 * @param cac Value
 *   transforms t  store's value type to t  cac 's value type
 */
class Transform ngCach ngStore[K, V, Cac K, Cac V](
  cac : Lock ngCac [Cac K, Cac d[Cac V]],
  underly ng: Store[K, V],
  valueP cker: Lock ngCac .P cker[Cac d[Cac V]],
  key: V => K,
  cac Key: K => Cac K,
  cac Value: V => Cac V)
    extends Store[K, V] {
  protected[t ] val log = Logger.get(getClass.getS mpleNa )

  overr de def create(value: V): Future[V] = {
    cha nCac Op[V](
      underly ng.create(value),
      result => cac (key(result), So (result), Cac dValueStatus.Found, "new")
    )
  }

  overr de def update(value: V): Future[Un ] = {
    cha nCac Op[Un ](
      underly ng.update(value),
      _ => cac (key(value), So (value), Cac dValueStatus.Found, "updated")
    )
  }

  overr de def destroy(key: K): Future[Un ] = {
    cha nCac Op[Un ](
      underly ng.destroy(key),
      _ => cac (key, None, Cac dValueStatus.Deleted, "deleted")
    )
  }

  /**
   * Subclasses may overr de t  to alter t  relat onsh p bet en t  result
   * of t  underly ng Store operat on and t  result of t  Cac  operat on.
   * By default, t  cac  operat on occurs asynchronously and only upon success
   * of t  store operat on. Cac  operat on fa lures are logged but ot rw se
   *  gnored.
   */
  protected[t ] def cha nCac Op[Result](
    storeOp: Future[Result],
    cac Op: Result => Future[Un ]
  ): Future[Result] = {
    storeOp onSuccess { cac Op(_) }
  }

  protected[t ] def cac (
    key: K,
    value: Opt on[V],
    status: Cac dValueStatus,
    desc: Str ng
  ): Future[Un ] = {
    val now = T  .now
    val cac d = Cac d(value map { cac Value(_) }, status, now, None, So (now))
    val handler = Lock ngCac .P ck ngHandler(cac d, valueP cker)
    cac .lockAndSet(cac Key(key), handler).un  onFa lure {
      case t =>
        log.error(t, "except on caught wh le cach ng %s value", desc)
    }
  }
}
