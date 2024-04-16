package com.tw ter.servo.store

 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.ut l.Future

/**
 * models a wr e-store of key/values
 */
tra  Store[K, V] {
  def create(value: V): Future[V]
  def update(value: V): Future[Un ]
  def destroy(key: K): Future[Un ]
}

object Store {

  /**
   * F lter store operat ons based on e  r t  key or t  value.  f t  gate passes t n forward
   * t  operat on to t  underly ng store,  f not t n forward t  operat on to a null store
   * (effect vely a no-op)
   */
  def f ltered[K, V](store: Store[K, V], f lterKey: Gate[K], f lterValue: Gate[V]) =
    new GatedStore(store, new NullStore[K, V], f lterKey, f lterValue)

  /**
   * A store type that selects bet en one of two underly ng stores based on t  key/value of t 
   * operat on.  f t  key/value gate passes, forward t  operat on to t  pr mary store, ot rw se
   * forward t  operat on to t  secondary store.
   */
  def gated[K, V](
    pr mary: Store[K, V],
    secondary: Store[K, V],
    usePr maryKey: Gate[K],
    usePr maryValue: Gate[V]
  ) = new GatedStore(pr mary, secondary, usePr maryKey, usePr maryValue)

  /**
   * A store type that selects bet en one of two underly ng stores based on a pred cat ve value,
   * wh ch may change dynam cally at runt  .
   */
  def dec derable[K, V](
    pr mary: Store[K, V],
    backup: Store[K, V],
    pr mary sAva lable: => Boolean
  ) = new Dec derableStore(pr mary, backup, pr mary sAva lable)
}

tra  StoreWrapper[K, V] extends Store[K, V] {
  def underly ngStore: Store[K, V]

  overr de def create(value: V) = underly ngStore.create(value)
  overr de def update(value: V) = underly ngStore.update(value)
  overr de def destroy(key: K) = underly ngStore.destroy(key)
}

class NullStore[K, V] extends Store[K, V] {
  overr de def create(value: V) = Future.value(value)
  overr de def update(value: V) = Future.Done
  overr de def destroy(key: K) = Future.Done
}

/**
 * A Store type that selects bet en one of two underly ng stores based
 * on t  key/value, wh ch may change dynam cally at runt  .
 */
pr vate[servo] class GatedStore[K, V](
  pr mary: Store[K, V],
  secondary: Store[K, V],
  usePr maryKey: Gate[K],
  usePr maryValue: Gate[V])
    extends Store[K, V] {
  pr vate[t ] def p ck[T]( em: T, gate: Gate[T]) =  f (gate( em)) pr mary else secondary

  overr de def create(value: V) = p ck(value, usePr maryValue).create(value)
  overr de def update(value: V) = p ck(value, usePr maryValue).update(value)
  overr de def destroy(key: K) = p ck(key, usePr maryKey).destroy(key)
}

/**
 * A Store type that selects bet en one of two underly ng stores based
 * on a pred cat ve value, wh ch may change dynam cally at runt  .
 */
class Dec derableStore[K, V](
  pr mary: Store[K, V],
  backup: Store[K, V],
  pr mary sAva lable: => Boolean)
    extends Store[K, V] {
  pr vate[t ] def p ck =  f (pr mary sAva lable) pr mary else backup

  overr de def create(value: V) = p ck.create(value)
  overr de def update(value: V) = p ck.update(value)
  overr de def destroy(key: K) = p ck.destroy(key)
}
