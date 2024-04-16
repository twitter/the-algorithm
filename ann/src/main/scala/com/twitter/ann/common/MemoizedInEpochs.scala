package com.tw ter.ann.common

 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try
 mport com.tw ter.ut l.logg ng.Logg ng

//  mo zat on w h a tw st
// New epoch reuse K:V pa rs from prev ous and recycle everyth ng else
class  mo zed nEpochs[K, V](f: K => Try[V]) extends Logg ng {
  pr vate var  mo zedCalls: Map[K, V] = Map.empty

  def epoch(keys: Seq[K]): Seq[V] = {
    val newSet = keys.toSet
    val keysToBeComputed = newSet.d ff( mo zedCalls.keySet)
    val computedKeysAndValues = keysToBeComputed.map { key =>
       nfo(s" mo ze ${key}")
      (key, f(key))
    }
    val keysAndValuesAfterF lter ngFa lures = computedKeysAndValues
      .flatMap {
        case (key, Return(value)) => So ((key, value))
        case (key, Throw(e)) =>
          warn(s"Call ng f for ${key} has fa led", e)

          None
      }
    val keysReusedFromLastEpoch =  mo zedCalls.f lterKeys(newSet.conta ns)
     mo zedCalls = keysReusedFromLastEpoch ++ keysAndValuesAfterF lter ngFa lures

    debug(s"F nal  mo zat on  s ${ mo zedCalls.keys.mkStr ng(", ")}")

    keys.flatMap( mo zedCalls.get)
  }

  def currentEpochKeys: Set[K] =  mo zedCalls.keySet
}
