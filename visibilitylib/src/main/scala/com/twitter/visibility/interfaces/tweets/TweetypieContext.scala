package com.tw ter.v s b l y. nterfaces.t ets

 mport com.tw ter.f nagle.context.Contexts
 mport com.tw ter. o.Buf
 mport com.tw ter. o.BufByteWr er
 mport com.tw ter. o.ByteReader
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try

case class T etyp eContext(
   sQuotedT et: Boolean,
   sRet et: Boolean,
  hydrateConversat onControl: Boolean)

object T etyp eContext {

  def let[U](value: T etyp eContext)(f: => Future[U]): Future[U] =
    Contexts.broadcast.let(T etyp eContextKey, value)(f)

  def get(): Opt on[T etyp eContext] =
    Contexts.broadcast.get(T etyp eContextKey)
}

object T etyp eContextKey
    extends Contexts.broadcast.Key[T etyp eContext](
      "com.tw ter.v s b l y. nterfaces.t ets.T etyp eContext"
    ) {

  overr de def marshal(value: T etyp eContext): Buf = {
    val bw = BufByteWr er.f xed(1)
    val byte =
      (( f (value. sQuotedT et) 1 else 0) << 0) |
        (( f (value. sRet et) 1 else 0) << 1) |
        (( f (value.hydrateConversat onControl) 1 else 0) << 2)
    bw.wr eByte(byte)
    bw.owned()
  }

  overr de def tryUnmarshal(buf: Buf): Try[T etyp eContext] = {
     f (buf.length != 1) {
      Throw(
        new  llegalArgu ntExcept on(
          s"Could not extract Boolean from Buf. Length ${buf.length} but requ red 1"
        )
      )
    } else {
      val byte: Byte = ByteReader(buf).readByte()
      Return(
        T etyp eContext(
           sQuotedT et = ((byte & 1) == 1),
           sRet et = ((byte & 2) == 2),
          hydrateConversat onControl = ((byte & 4) == 4)
        )
      )
    }
  }
}
