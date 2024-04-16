package com.tw ter.t etyp e.core

 mport com.tw ter.servo.cac 
 mport com.tw ter.servo.cac .Cac dSer al zer
 mport com.tw ter.t etyp e.thr ftscala
 mport com.tw ter.t etyp e.thr ftscala.Cac dT et
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport org.apac .thr ft.protocol.TCompactProtocol

/**
 * A conta ner object for ser al zers.
 * Creates a ser al zer for every object type cac d by t  t etyp e serv ce
 */
object Ser al zer {
  lazy val CompactProtocolFactory: TCompactProtocol.Factory = new TCompactProtocol.Factory

  def toCac d[T](underly ng: cac .Ser al zer[T]): cac .Cac dSer al zer[T] =
    new cac .Cac dSer al zer(underly ng, CompactProtocolFactory)

  object T et {
    lazy val Compact: cac .Thr ftSer al zer[thr ftscala.T et] =
      new cac .Thr ftSer al zer(thr ftscala.T et, CompactProtocolFactory)
    lazy val Cac dCompact: Cac dSer al zer[T et] = toCac d(Compact)
  }

  object Cac dT et {
    lazy val Compact: cac .Thr ftSer al zer[thr ftscala.Cac dT et] =
      new cac .Thr ftSer al zer(thr ftscala.Cac dT et, CompactProtocolFactory)
    lazy val Cac dCompact: Cac dSer al zer[Cac dT et] = toCac d(Compact)
  }
}
