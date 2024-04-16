/**
 * Copyr ght 2021 Tw ter,  nc.
 * SPDX-L cense- dent f er: Apac -2.0
 */
package com.tw ter.un f ed_user_act ons.kafka.serde. nternal

 mport com.google.common.ut l.concurrent.RateL m er
 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport java.ut l
 mport com.tw ter.scrooge.CompactThr ftSer al zer
 mport com.tw ter.scrooge.Thr ftStruct
 mport com.tw ter.scrooge.Thr ftStructCodec
 mport com.tw ter.scrooge.Thr ftStructSer al zer
 mport org.apac .kafka.common.ser al zat on.Deser al zer
 mport org.apac .kafka.common.ser al zat on.Serde
 mport org.apac .kafka.common.ser al zat on.Ser al zer
 mport com.tw ter.ut l.logg ng.Logg ng
 mport org.apac .thr ft.protocol.TB naryProtocol

abstract class AbstractScroogeSerDe[T <: Thr ftStruct: Man fest](nullCounter: Counter)
    extends Serde[T]
    w h Logg ng {

  pr vate val rateL m er = RateL m er.create(1.0) // at most 1 log  ssage per second

  pr vate def rateL m edLogError(e: Except on): Un  =
     f (rateL m er.tryAcqu re()) {
      logger.error(e.get ssage, e)
    }

  pr vate[kafka] val thr ftStructSer al zer: Thr ftStructSer al zer[T] = {
    val clazz = man fest.runt  Class.as nstanceOf[Class[T]]
    val codec = Thr ftStructCodec.forStructClass(clazz)

    constructThr ftStructSer al zer(clazz, codec)
  }

  pr vate val _deser al zer = new Deser al zer[T] {
    overr de def conf gure(conf gs: ut l.Map[Str ng, _],  sKey: Boolean): Un  = {}

    overr de def close(): Un  = {}

    overr de def deser al ze(top c: Str ng, data: Array[Byte]): T = {
       f (data == null) {
        null.as nstanceOf[T]
      } else {
        try {
          thr ftStructSer al zer.fromBytes(data)
        } catch {
          case e: Except on =>
            nullCounter. ncr()
            rateL m edLogError(e)
            null.as nstanceOf[T]
        }
      }
    }
  }

  pr vate val _ser al zer = new Ser al zer[T] {
    overr de def conf gure(conf gs: ut l.Map[Str ng, _],  sKey: Boolean): Un  = {}

    overr de def ser al ze(top c: Str ng, data: T): Array[Byte] = {
       f (data == null) {
        null
      } else {
        thr ftStructSer al zer.toBytes(data)
      }
    }

    overr de def close(): Un  = {}
  }

  /* Publ c */

  overr de def conf gure(conf gs: ut l.Map[Str ng, _],  sKey: Boolean): Un  = {}

  overr de def close(): Un  = {}

  overr de def deser al zer: Deser al zer[T] = {
    _deser al zer
  }

  overr de def ser al zer: Ser al zer[T] = {
    _ser al zer
  }

  /**
   * Subclasses should  mple nt t   thod and prov de a concrete Thr ftStructSer al zer
   */
  protected[t ] def constructThr ftStructSer al zer(
    thr ftStructClass: Class[T],
    thr ftStructCodec: Thr ftStructCodec[T]
  ): Thr ftStructSer al zer[T]
}

class Thr ftSerDe[T <: Thr ftStruct: Man fest](nullCounter: Counter = NullStatsRece ver.NullCounter)
    extends AbstractScroogeSerDe[T](nullCounter = nullCounter) {
  protected[t ] overr de def constructThr ftStructSer al zer(
    thr ftStructClass: Class[T],
    thr ftStructCodec: Thr ftStructCodec[T]
  ): Thr ftStructSer al zer[T] = {
    new Thr ftStructSer al zer[T] {
      overr de val protocolFactory = new TB naryProtocol.Factory
      overr de def codec: Thr ftStructCodec[T] = thr ftStructCodec
    }
  }
}

class CompactThr ftSerDe[T <: Thr ftStruct: Man fest](
  nullCounter: Counter = NullStatsRece ver.NullCounter)
    extends AbstractScroogeSerDe[T](nullCounter = nullCounter) {
  overr de protected[t ] def constructThr ftStructSer al zer(
    thr ftStructClass: Class[T],
    thr ftStructCodec: Thr ftStructCodec[T]
  ): Thr ftStructSer al zer[T] = {
    new CompactThr ftSer al zer[T] {
      overr de def codec: Thr ftStructCodec[T] = thr ftStructCodec
    }
  }
}
