package com.tw ter.servo

 mport com.tw ter.f nagle.part  on ng.Part  onNode
 mport com.tw ter.servo.ut l.Transfor r
 mport com.tw ter.ut l.Try

package object cac  {
  type Cac dValue = thr ftscala.Cac dValue
  val Cac dValue = thr ftscala.Cac dValue
  type Cac dValueStatus = thr ftscala.Cac dValueStatus
  val Cac dValueStatus = thr ftscala.Cac dValueStatus

  type KeyTransfor r[K] = K => Str ng
  type CsKeyValueResult[K, V] = KeyValueResult[K, (Try[V], C cksum)]

  type KeyValueResult[K, V] = keyvalue.KeyValueResult[K, V]
  val KeyValueResult = keyvalue.KeyValueResult

  @deprecated("Use com.tw ter.f nagle.part  on ng.Part  onNode  nstead", "1/7/2013")
  type   ghtedHost = Part  onNode

  type Ser al zer[T] = Transfor r[T, Array[Byte]]

  /**
   * L ke a compan on object, but for a type al as!
   */
  val Ser al zer = Ser al zers

  type  mcac Factory = (() =>  mcac )
}

package cac  {
  package object constants {
    val Colon = ":"
  }
}
