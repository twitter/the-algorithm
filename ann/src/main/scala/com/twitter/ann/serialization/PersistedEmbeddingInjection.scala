package com.tw ter.ann.ser al zat on

 mport com.tw ter.ann.common.Ent yEmbedd ng
 mport com.tw ter.ann.common.Embedd ngType._
 mport com.tw ter.ann.ser al zat on.thr ftscala.Pers stedEmbedd ng
 mport com.tw ter.b ject on. nject on
 mport com.tw ter. d aserv ces.commons.codec.ArrayByteBufferCodec
 mport java.n o.ByteBuffer
 mport scala.ut l.Try

/**
 *  nject on that converts from t  ann.common.Embedd ng to t  thr ft Pers stedEmbedd ng.
 */
class Pers stedEmbedd ng nject on[T](
   dByte nject on:  nject on[T, Array[Byte]])
    extends  nject on[Ent yEmbedd ng[T], Pers stedEmbedd ng] {
  overr de def apply(ent y: Ent yEmbedd ng[T]): Pers stedEmbedd ng = {
    val byteBuffer = ByteBuffer.wrap( dByte nject on(ent y. d))
    Pers stedEmbedd ng(byteBuffer, embedd ngSerDe.toThr ft(ent y.embedd ng))
  }

  overr de def  nvert(pers stedEmbedd ng: Pers stedEmbedd ng): Try[Ent yEmbedd ng[T]] = {
    val  dTry =  dByte nject on. nvert(ArrayByteBufferCodec.decode(pers stedEmbedd ng. d))
     dTry.map {  d =>
      Ent yEmbedd ng( d, embedd ngSerDe.fromThr ft(pers stedEmbedd ng.embedd ng))
    }
  }
}
