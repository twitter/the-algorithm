package com.tw ter.follow_recom ndat ons.common.cl ents.cac 

 mport com.tw ter.b ject on.B ject on
 mport com.tw ter. o.Buf
 mport com.tw ter.scrooge.CompactThr ftSer al zer
 mport com.tw ter.scrooge.Thr ftEnum
 mport com.tw ter.scrooge.Thr ftStruct
 mport java.n o.ByteBuffer

abstract class Thr ftB ject on[T <: Thr ftStruct] extends B ject on[Buf, T] {
  val ser al zer: CompactThr ftSer al zer[T]

  overr de def apply(b: Buf): T = {
    val byteArray = Buf.ByteArray.Owned.extract(b)
    ser al zer.fromBytes(byteArray)
  }

  overr de def  nvert(a: T): Buf = {
    val byteArray = ser al zer.toBytes(a)
    Buf.ByteArray.Owned(byteArray)
  }
}

abstract class Thr ftOpt onB ject on[T <: Thr ftStruct] extends B ject on[Buf, Opt on[T]] {
  val ser al zer: CompactThr ftSer al zer[T]

  overr de def apply(b: Buf): Opt on[T] = {
     f (b. sEmpty) {
      None
    } else {
      val byteArray = Buf.ByteArray.Owned.extract(b)
      So (ser al zer.fromBytes(byteArray))
    }
  }

  overr de def  nvert(a: Opt on[T]): Buf = {
    a match {
      case So (t) =>
        val byteArray = ser al zer.toBytes(t)
        Buf.ByteArray.Owned(byteArray)
      case None => Buf.Empty
    }
  }
}

class Thr ftEnumB ject on[T <: Thr ftEnum](constructor:  nt => T) extends B ject on[Buf, T] {
  overr de def apply(b: Buf): T = {    
    val byteArray = Buf.ByteArray.Owned.extract(b)
    val byteBuffer = ByteBuffer.wrap(byteArray)
    constructor(byteBuffer.get nt())
  }

  overr de def  nvert(a: T): Buf = {      
    val byteBuffer: ByteBuffer = ByteBuffer.allocate(4)
    byteBuffer.put nt(a.getValue)
    Buf.ByteArray.Owned(byteBuffer.array())
  }
}

class Thr ftEnumOpt onB ject on[T <: Thr ftEnum](constructor:  nt => T) extends B ject on[Buf, Opt on[T]] {
  overr de def apply(b: Buf): Opt on[T] = {      
     f (b. sEmpty) {
      None
    } else {
      val byteArray = Buf.ByteArray.Owned.extract(b)
      val byteBuffer = ByteBuffer.wrap(byteArray)
      So (constructor(byteBuffer.get nt()))
    }
  }

  overr de def  nvert(a: Opt on[T]): Buf = {
    a match {
      case So (obj) => {
        val byteBuffer: ByteBuffer = ByteBuffer.allocate(4)
        byteBuffer.put nt(obj.getValue)
        Buf.ByteArray.Owned(byteBuffer.array())
      }
      case None => Buf.Empty
    }
  }
}
