package com.tw ter.ho _m xer.ut l

 mport com.tw ter.b ject on. nject on
 mport com.tw ter. o.Buf
 mport com.tw ter.servo.ut l.Transfor r
 mport com.tw ter.storage.cl ent.manhattan.b ject ons.B ject ons
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Try
 mport java.n o.ByteBuffer

object  nject onTransfor r mpl c s {
   mpl c  class ByteArray nject onToByteBufferTransfor r[A](ba nj:  nject on[A, Array[Byte]]) {

    pr vate val bb nj:  nject on[A, ByteBuffer] = ba nj
      .andT n(B ject ons.byteArray2Buf)
      .andT n(B ject ons.byteBuffer2Buf. nverse)

    def toByteBufferTransfor r(): Transfor r[A, ByteBuffer] = new  nject onTransfor r(bb nj)
    def toByteArrayTransfor r(): Transfor r[A, Array[Byte]] = new  nject onTransfor r(ba nj)
  }

   mpl c  class Buf nject onToByteBufferTransfor r[A](buf nj:  nject on[A, Buf]) {

    pr vate val bb nj:  nject on[A, ByteBuffer] = buf nj.andT n(B ject ons.byteBuffer2Buf. nverse)
    pr vate val ba nj:  nject on[A, Array[Byte]] = buf nj.andT n(B ject ons.byteArray2Buf. nverse)

    def toByteBufferTransfor r(): Transfor r[A, ByteBuffer] = new  nject onTransfor r(bb nj)
    def toByteArrayTransfor r(): Transfor r[A, Array[Byte]] = new  nject onTransfor r(ba nj)
  }

   mpl c  class ByteBuffer nject onToByteBufferTransfor r[A](bb nj:  nject on[A, ByteBuffer]) {

    pr vate val ba nj:  nject on[A, Array[Byte]] = bb nj.andT n(B ject ons.bb2ba)

    def toByteBufferTransfor r(): Transfor r[A, ByteBuffer] = new  nject onTransfor r(bb nj)
    def toByteArrayTransfor r(): Transfor r[A, Array[Byte]] = new  nject onTransfor r(ba nj)
  }
}

class  nject onTransfor r[A, B]( nj:  nject on[A, B]) extends Transfor r[A, B] {
  overr de def to(a: A): Try[B] = Return( nj(a))
  overr de def from(b: B): Try[A] = Try.fromScala( nj. nvert(b))
}
