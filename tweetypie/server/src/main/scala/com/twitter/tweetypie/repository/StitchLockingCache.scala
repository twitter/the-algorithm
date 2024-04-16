package com.tw ter.t etyp e.repos ory

 mport com.tw ter.servo.cac .{Cac dValueStatus => Status, Lock ngCac  => KVLock ngCac , _}
 mport com.tw ter.servo.repos ory.{Cac dResult => Result}
 mport com.tw ter.st ch.MapGroup
 mport com.tw ter.st ch.Group
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.T  
 mport com.tw ter.ut l.Try

/**
 * Adapts a key-value lock ng cac  to Arrow and
 * normal zes t  results to `Cac dResult`.
 */
tra  St chLock ngCac [K, V] {
  val get: K => St ch[Result[K, V]]
  val lockAndSet: (K, St chLock ngCac .Val[V]) => St ch[Un ]
  val delete: K => St ch[Boolean]
}

object St chLock ngCac  {

  /**
   * Value  ntended to be wr ten back to cac  us ng lockAndSet.
   *
   * Note that only a subset of Cac dValueStatus values are el g ble for wr  ng:
   *   Found, NotFound, and Deleted
   */
  sealed tra  Val[+V]
  object Val {
    case class Found[V](value: V) extends Val[V]
    case object NotFound extends Val[Noth ng]
    case object Deleted extends Val[Noth ng]
  }

  /**
   * A Group for batch ng get requests to a [[KVLock ngCac ]].
   */
  pr vate case class GetGroup[K, V](cac : KVLock ngCac [K, Cac d[V]], overr de val maxS ze:  nt)
      extends MapGroup[K, Result[K, V]] {

    pr vate[t ] def cac dToResult(key: K, cac d: Cac d[V]): Try[Result[K, V]] =
      cac d.status match {
        case Status.NotFound => Return(Result.Cac dNotFound(key, cac d.cac dAt))
        case Status.Deleted => Return(Result.Cac dDeleted(key, cac d.cac dAt))
        case Status.Ser al zat onFa led => Return(Result.Ser al zat onFa led(key))
        case Status.Deser al zat onFa led => Return(Result.Deser al zat onFa led(key))
        case Status.Ev cted => Return(Result.NotFound(key))
        case Status.DoNotCac  => Return(Result.DoNotCac (key, cac d.doNotCac Unt l))
        case Status.Found =>
          cac d.value match {
            case None => Return(Result.NotFound(key))
            case So (value) => Return(Result.Cac dFound(key, value, cac d.cac dAt))
          }
        case _ => Throw(new UnsupportedOperat onExcept on)
      }

    overr de protected def run(keys: Seq[K]): Future[K => Try[Result[K, V]]] =
      cac .get(keys).map { (result: KeyValueResult[K, Cac d[V]]) => key =>
        result.found.get(key) match {
          case So (cac d) => cac dToResult(key, cac d)
          case None =>
            result.fa led.get(key) match {
              case So (t) => Return(Result.Fa led(key, t))
              case None => Return(Result.NotFound(key))
            }
        }
      }
  }

  /**
   * Used  n t   mple ntat on of LockAndSetGroup. T   s just a
   * glor f ed tuple w h spec al equal y semant cs w re calls w h
   * t  sa  key w ll compare equal.  MapGroup w ll use t  as a key
   *  n a Map, wh ch w ll prevent dupl cate lockAndSet calls w h t 
   * sa  key.   don't care wh ch one   use
   */
  pr vate class LockAndSetCall[K, V](val key: K, val value: V) {
    overr de def equals(ot r: Any): Boolean =
      ot r match {
        case call: LockAndSetCall[_, _] => call.key == key
        case _ => false
      }

    overr de def hashCode():  nt = key.hashCode
  }

  /**
   * A Group for `lockAndSet` calls to a [[KVLock ngCac ]]. T   s
   * necessary to avo d wr  ng back a key mult ple t  s  f    s
   * appears more than once  n a batch. LockAndSetCall cons ders two
   * calls equal even  f t  values d ffer because mult ple lockAndSet
   * calls for t  sa  key w ll eventually result  n only one be ng
   * chosen by t  cac  anyway, and t  avo ds confl ct ng
   * lockAndSet calls.
   *
   * For example, cons der a t et that  nt ons @jack tw ce
   * w n @jack  s not  n cac . That w ll result  n two quer es to
   * load @jack, wh ch w ll be deduped by t  Group w n t  repo  s
   * called. Desp e t  fact that    s loaded only once, each of t 
   * two loads  s obl v ous to t  ot r, so each of t m attempts to
   * wr e t  value back to cac , result ng  n two `lockAndSet`
   * calls for @jack, so   have to dedupe t m aga n.
   */
  pr vate case class LockAndSetGroup[K, V](
    cac : KVLock ngCac [K, V],
    p cker: KVLock ngCac .P cker[V])
      extends MapGroup[LockAndSetCall[K, V], Opt on[V]] {

    overr de def run(
      calls: Seq[LockAndSetCall[K, V]]
    ): Future[LockAndSetCall[K, V] => Try[Opt on[V]]] =
      Future
        .collect {
          calls.map { call =>
            // T   s masked to prevent  nterrupts to t  overall
            // request from  nterrupt ng wr es back to cac .
            cac 
              .lockAndSet(call.key, KVLock ngCac .P ck ngHandler(call.value, p cker))
              .masked
              .l ftToTry
          }
        }
        .map(responses => calls.z p(responses).toMap)
  }

  def apply[K, V](
    underly ng: KVLock ngCac [K, Cac d[V]],
    p cker: KVLock ngCac .P cker[Cac d[V]],
    maxRequestS ze:  nt =  nt.MaxValue
  ): St chLock ngCac [K, V] =
    new St chLock ngCac [K, V] {
      overr de val get: K => St ch[Result[K, V]] = {
        val group: Group[K, Result[K, V]] = GetGroup(underly ng, maxRequestS ze)

        (key: K) => St ch.call(key, group)
      }

      overr de val lockAndSet: (K, Val[V]) => St ch[Un ] = {
        val group = LockAndSetGroup(underly ng, p cker)

        (key: K, value: Val[V]) => {
          val now = T  .now
          val cac d: Cac d[V] =
            value match {
              case Val.Found(v) => Cac d[V](So (v), Status.Found, now, So (now))
              case Val.NotFound => Cac d[V](None, Status.NotFound, now, So (now))
              case Val.Deleted => Cac d[V](None, Status.Deleted, now, So (now))
            }

          St ch.call(new LockAndSetCall(key, cac d), group).un 
        }
      }

      overr de val delete: K => St ch[Boolean] =
        (key: K) => St ch.callFuture(underly ng.delete(key))
    }
}
