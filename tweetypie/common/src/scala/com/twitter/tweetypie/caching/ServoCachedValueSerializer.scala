package com.tw ter.t etyp e.cach ng

 mport com.tw ter. o.Buf
 mport com.tw ter.scrooge.CompactThr ftSer al zer
 mport com.tw ter.scrooge.Thr ftStruct
 mport com.tw ter.scrooge.Thr ftStructCodec
 mport com.tw ter.servo.cac .thr ftscala.Cac dValue
 mport com.tw ter.servo.cac .thr ftscala.Cac dValueStatus
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.T  
 mport com.tw ter.ut l.Try
 mport java.n o.ByteBuffer

object ServoCac dValueSer al zer {

  /**
   * Thrown w n t  f elds of t  servo Cac dValue struct do not
   * sat sfy t   nvar ants expected by t  ser al zat on code.
   */
  case class UnexpectedCac dValueState(cac dValue: Cac dValue) extends Except on {
    def  ssage: Str ng = s"Unexpected state for Cac dValue. Value was: $cac dValue"
  }

  val Cac dValueThr ftSer al zer: CompactThr ftSer al zer[Cac dValue] = CompactThr ftSer al zer(
    Cac dValue)
}

/**
 * A [[ValueSer al zer]] that  s compat ble w h t  use of
 * Servo's [[Cac dValue]] struct by t etyp e:
 *
 * - T  only [[Cac dValueStatus]] values that are cac able are
 *   [[Cac dValueStatus.Found]] and [[Cac dValueStatus.NotFound]].
 *
 * -   only track t  `cac dAtMsec` f eld, because t etyp e's cac 
 *    nteract on does not use t  ot r f elds, and t  values that
 *   are cac d t  way are never updated, so stor ng readThroughAt
 *   or wr tenThroughAt would not add any  nformat on.
 *
 * - W n values are present, t y are ser al zed us ng
 *   [[org.apac .thr ft.protocol.TCompactProtocol]].
 *
 * - T  Cac dValue struct  self  s also ser al zed us ng TCompactProtocol.
 *
 * T  ser al zer operates on [[Try]] values and w ll cac  [[Return]]
 * and `Throw(NotFound)` values.
 */
case class ServoCac dValueSer al zer[V <: Thr ftStruct](
  codec: Thr ftStructCodec[V],
  exp ry: Try[V] => T  ,
  softTtl: SoftTtl[Try[V]])
    extends ValueSer al zer[Try[V]] {
   mport ServoCac dValueSer al zer.UnexpectedCac dValueState
   mport ServoCac dValueSer al zer.Cac dValueThr ftSer al zer

  pr vate[t ] val ValueThr ftSer al zer = CompactThr ftSer al zer(codec)

  /**
   * Return an exp ry based on t  value and a
   * TCompactProtocol-encoded servo Cac dValue struct w h t 
   * follow ng f elds def ned:
   *
   * - `value`: [[None]]
   *   for {{{Throw(NotFound)}}, {{{So (encodedStruct)}}} for
   *   [[Return]], w re {{{encodedStruct}}}  s a
   *   TCompactProtocol-encod ng of t  value  ns de of t  Return.
   *
   * - `status`: [[Cac dValueStatus.Found]]  f t  value  s Return,
   *   and [[Cac dValueStatus.NotFound]]  f    s Throw(NotFound)
   *
   * - `cac dAtMsec`: T  current t  , accor ng to [[T  .now]]
   *
   * No ot r f elds w ll be def ned.
   *
   * @throws  llegalArgu ntExcept on  f called w h a value that
   *   should not be cac d.
   */
  overr de def ser al ze(value: Try[V]): Opt on[(T  , Buf)] = {
    def ser al zeCac dValue(payload: Opt on[ByteBuffer]) = {
      val cac dValue = Cac dValue(
        value = payload,
        status =  f (payload. sDef ned) Cac dValueStatus.Found else Cac dValueStatus.NotFound,
        cac dAtMsec = T  .now. nM ll seconds)

      val ser al zed = Buf.ByteArray.Owned(Cac dValueThr ftSer al zer.toBytes(cac dValue))

      (exp ry(value), ser al zed)
    }

    value match {
      case Throw(NotFound) =>
        So (ser al zeCac dValue(None))
      case Return(struct) =>
        val payload = So (ByteBuffer.wrap(ValueThr ftSer al zer.toBytes(struct)))
        So (ser al zeCac dValue(payload))
      case _ =>
        None
    }
  }

  /**
   * Deser al zes values ser al zed by [[ser al zeValue]]. T 
   * value w ll be [[Cac Result.Fresh]] or [[Cac Result.Stale]]
   * depend ng on t  result of {{{softTtl. sFresh}}}.
   *
   * @throws UnexpectedCac dValueState  f t  state of t 
   *   [[Cac dValue]] could not be produced by [[ser al ze]]
   */
  overr de def deser al ze(buf: Buf): Cac Result[Try[V]] = {
    val cac dValue = Cac dValueThr ftSer al zer.fromBytes(Buf.ByteArray.Owned.extract(buf))
    val hasValue = cac dValue.value. sDef ned
    val  sVal d =
      (hasValue && cac dValue.status == Cac dValueStatus.Found) ||
        (!hasValue && cac dValue.status == Cac dValueStatus.NotFound)

     f (! sVal d) {
      // Except ons thrown by deser al zat on are recorded and treated
      // as a cac  m ss by Cac Operat ons, so throw ng t 
      // except on w ll cause t  value  n cac  to be
      // overwr ten. T re w ll be stats recorded w never t 
      // happens.
      throw UnexpectedCac dValueState(cac dValue)
    }

    val value =
      cac dValue.value match {
        case So (valueBuffer) =>
          val valueBytes = new Array[Byte](valueBuffer.rema n ng)
          valueBuffer.dupl cate.get(valueBytes)
          Return(ValueThr ftSer al zer.fromBytes(valueBytes))

        case None =>
          Throw(NotFound)
      }

    softTtl.toCac Result(value, T  .fromM ll seconds(cac dValue.cac dAtMsec))
  }
}
