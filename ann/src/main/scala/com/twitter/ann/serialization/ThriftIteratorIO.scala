package com.tw ter.ann.ser al zat on

 mport com.tw ter.scrooge.{Thr ftStruct, Thr ftStructCodec}
 mport java. o.{ nputStream, OutputStream}
 mport org.apac .thr ft.protocol.TB naryProtocol
 mport org.apac .thr ft.transport.{T OStreamTransport, TTransportExcept on}

/**
 * Class that can ser al ze and deser al ze an  erator of thr ft objects.
 * T  class can do th ngs laz ly so t re  s no need to have all t  object  nto  mory.
 */
class Thr ft erator O[T <: Thr ftStruct](
  codec: Thr ftStructCodec[T]) {
  def toOutputStream(
     erator:  erator[T],
    outputStream: OutputStream
  ): Un  = {
    val protocol = (new TB naryProtocol.Factory).getProtocol(new T OStreamTransport(outputStream))
     erator.foreach { thr ftObject =>
      codec.encode(thr ftObject, protocol)
    }
  }

  /**
   * Returns an  erator that laz ly reads from an  nputStream.
   * @return
   */
  def from nputStream(
     nputStream:  nputStream
  ):  erator[T] = {
    Thr ft erator O.get erator(codec,  nputStream)
  }
}

object Thr ft erator O {
  pr vate def get erator[T <: Thr ftStruct](
    codec: Thr ftStructCodec[T],
     nputStream:  nputStream
  ):  erator[T] = {
    val protocol = (new TB naryProtocol.Factory).getProtocol(new T OStreamTransport( nputStream))

    def getNext: Opt on[T] =
      try {
        So (codec.decode(protocol))
      } catch {
        case e: TTransportExcept on  f e.getType == TTransportExcept on.END_OF_F LE =>
           nputStream.close()
          None
      }

     erator
      .cont nually[Opt on[T]](getNext)
      .takeWh le(_. sDef ned)
      //   should be safe to call get on  re s nce   are only take t  def ned ones.
      .map(_.get)
  }
}
