package com.tw ter.t etyp e.cach ng

 mport com.tw ter.st ch.MapGroup
 mport com.tw ter.st ch.Group
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Try

/**
 * Wrapper around [[Cac Operat ons]] prov d ng a [[St ch]] AP .
 */
case class St chCac Operat ons[K, V](operat ons: Cac Operat ons[K, V]) {
   mport St chCac Operat ons.SetCall

  pr vate[t ] val getGroup: Group[K, Cac Result[V]] =
    MapGroup[K, Cac Result[V]] { keys: Seq[K] =>
      operat ons
        .get(keys)
        .map(values => keys.z p(values).toMap.mapValues(Return(_)))
    }

  def get(key: K): St ch[Cac Result[V]] =
    St ch.call(key, getGroup)

  pr vate[t ] val setGroup: Group[SetCall[K, V], Un ] =
    new MapGroup[SetCall[K, V], Un ] {

      overr de def run(calls: Seq[SetCall[K, V]]): Future[SetCall[K, V] => Try[Un ]] =
        Future
          .collectToTry(calls.map(call => operat ons.set(call.key, call.value)))
          .map(tr es => calls.z p(tr es).toMap)
    }

  /**
   * Performs a [[Cac Operat ons.set]].
   */
  def set(key: K, value: V): St ch[Un ] =
    // T   s  mple nted as a St ch.call  nstead of a St ch.future
    //  n order to handle t  case w re a batch has a dupl cate
    // key. Each copy of t  dupl cate key w ll tr gger a wr e back
    // to cac , so   dedupe t  wr es  n order to avo d t 
    // extraneous RPC call.
    St ch.call(new St chCac Operat ons.SetCall(key, value), setGroup)
}

object St chCac Operat ons {

  /**
   * Used as t  "call" for [[SetGroup]]. T   s essent ally a tuple
   * w re equal y  s def ned only by t  key.
   */
  pr vate class SetCall[K, V](val key: K, val value: V) {
    overr de def equals(ot r: Any): Boolean =
      ot r match {
        case setCall: SetCall[_, _] => key == setCall.key
        case _ => false
      }

    overr de def hashCode:  nt = key.hashCode
  }
}
