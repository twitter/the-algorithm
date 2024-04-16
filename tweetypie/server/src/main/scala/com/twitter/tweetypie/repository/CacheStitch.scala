package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.servo.repos ory._
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Try

object Cac St ch {

  /**
   * Cac able def nes a funct on that takes a cac  query and a Try value,
   * and returns what should be wr ten to cac , as a Opt on[St chLock ngCac .Val].
   *
   * None s gn f es that t  value should not be wr ten to cac .
   *
   * Val can be one of Found[V], NotFound, and Deleted. T  funct on w ll determ ne what k nds
   * of values and except ons (captured  n t  Try) correspond to wh ch k nd of cac d values.
   */
  type Cac able[Q, V] = (Q, Try[V]) => Opt on[St chLock ngCac .Val[V]]

  // Cac  successful values as Found, st ch.NotFound as NotFound, and don't cac  ot r except ons
  def cac FoundAndNotFound[K, V]: Cac St ch.Cac able[K, V] =
    (_, t: Try[V]) =>
      t match {
        // Wr e successful values as Found
        case Return(v) => So (St chLock ngCac .Val.Found[V](v))

        // Wr e st ch.NotFound as NotFound
        case Throw(com.tw ter.st ch.NotFound) => So (St chLock ngCac .Val.NotFound)

        // Don't wr e ot r except ons back to cac 
        case _ => None
      }
}

case class Cac St ch[Q, K, V](
  repo: Q => St ch[V],
  cac : St chLock ngCac [K, V],
  queryToKey: Q => K,
  handler: Cac dResult.Handler[K, V],
  cac able: Cac St ch.Cac able[Q, V])
    extends (Q => St ch[V]) {
   mport com.tw ter.servo.repos ory.Cac dResultAct on._

  pr vate[t ] def getFromCac (key: K): St ch[Cac dResult[K, V]] = {
    cac 
      .get(key)
      .handle {
        case t => Cac dResult.Fa led(key, t)
      }
  }

  // Exposed for test ng
  pr vate[repos ory] def readThrough(query: Q): St ch[V] =
    repo(query).l ftToTry.applyEffect { value: Try[V] =>
      cac able(query, value) match {
        case So (v) =>
          // cac able returned So  of a St chLock ngCac .Val to cac 
          //
          // T   s async to ensure that   don't wa  for t  cac 
          // update to complete before return ng. T  also  gnores
          // any except ons from sett ng t  value.
          St ch.async(cac .lockAndSet(queryToKey(query), v))
        case None =>
          // cac able returned None so don't cac 
          St ch.Un 
      }
    }.lo rFromTry

  pr vate[t ] def handle(query: Q, act on: Cac dResultAct on[V]): St ch[V] =
    act on match {
      case HandleAsFound(value) => St ch(value)
      case HandleAsM ss => readThrough(query)
      case HandleAsDoNotCac  => repo(query)
      case HandleAsFa led(t) => St ch.except on(t)
      case HandleAsNotFound => St ch.NotFound
      case t: TransformSubAct on[V] => handle(query, t.act on).map(t.f)
      case SoftExp rat on(subAct on) =>
        St ch
          .async(readThrough(query))
          .flatMap { _ => handle(query, subAct on) }
    }

  overr de def apply(query: Q): St ch[V] =
    getFromCac (queryToKey(query))
      .flatMap { result: Cac dResult[K, V] => handle(query, handler(result)) }
}
