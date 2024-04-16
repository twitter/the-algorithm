package com.tw ter.tsp.ut ls

 mport com.tw ter.b ject on. nject on
 mport java. o.ByteArray nputStream
 mport java. o.ByteArrayOutputStream
 mport java. o.Object nputStream
 mport java. o.ObjectOutputStream
 mport java. o.Ser al zable
 mport scala.ut l.Try

/**
 * @tparam T must be a ser al zable class
 */
case class SeqObject nject on[T <: Ser al zable]() extends  nject on[Seq[T], Array[Byte]] {

  overr de def apply(seq: Seq[T]): Array[Byte] = {
    val byteStream = new ByteArrayOutputStream()
    val outputStream = new ObjectOutputStream(byteStream)
    outputStream.wr eObject(seq)
    outputStream.close()
    byteStream.toByteArray
  }

  overr de def  nvert(bytes: Array[Byte]): Try[Seq[T]] = {
    Try {
      val  nputStream = new Object nputStream(new ByteArray nputStream(bytes))
      val seq =  nputStream.readObject().as nstanceOf[Seq[T]]
       nputStream.close()
      seq
    }
  }
}
