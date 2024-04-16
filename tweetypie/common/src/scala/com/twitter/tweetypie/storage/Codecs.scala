package com.tw ter.t etyp e.storage

 mport com.tw ter.b ject on.Convers on.as thod
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.scrooge.TF eldBlob
 mport com.tw ter.storage.cl ent.manhattan.kv._
 mport com.tw ter.t etyp e.storage.Response.F eldResponse
 mport com.tw ter.t etyp e.storage.Response.F eldResponseCode
 mport com.tw ter.t etyp e.storage_ nternal.thr ftscala.CoreF elds
 mport com.tw ter.t etyp e.storage_ nternal.thr ftscala. nternalT et
 mport com.tw ter.t etyp e.storage_ nternal.thr ftscala.StoredT et
 mport java. o.ByteArrayOutputStream
 mport java.n o.ByteBuffer
 mport org.apac .thr ft.protocol.TB naryProtocol
 mport org.apac .thr ft.transport.T OStreamTransport
 mport org.apac .thr ft.transport.T mory nputTransport
 mport scala.collect on. mmutable
 mport scala.ut l.control.NoStackTrace

// NOTE: All f eld  ds and T et structure  n t  f le correspond to t  StoredT et struct ONLY

object ByteArrayCodec {
  def toByteBuffer(byteArray: Array[Byte]): ByteBuffer = byteArray.as[ByteBuffer]
  def fromByteBuffer(buffer: ByteBuffer): Array[Byte] = buffer.as[Array[Byte]]
}

object Str ngCodec {
  pr vate val str ng2ByteBuffer =  nject on.connect[Str ng, Array[Byte], ByteBuffer]
  def toByteBuffer(strValue: Str ng): ByteBuffer = str ng2ByteBuffer(strValue)
  def fromByteBuffer(buffer: ByteBuffer): Str ng = str ng2ByteBuffer. nvert(buffer).get
}

/**
 * Term nology
 * -----------
 * T et  d f eld             : T  f eld number of 't et d'  n t  'T et' thr ft structure ( .e "1")
 *
 * F rst Add  onalF eld  d   : T   D  f t  f rst add  onal f eld  n 'T et' thr ft structure. All f eld  ds less than t  are
 *                              cons dered  nternal and all t   ds greater than or equal to t  f eld  d are cons dered 'Add  onal f elds'.
 *                              T   s set to 100.
 *
 *  nternal F elds            : F elds w h  ds [1 to f rstAdd  onalF eld d) (exclud ng f rstAdd  onalF eld d)
 *
 * Core f elds                : (Subset of  nternal f elds)- F elds w h  ds [1 to 8, 19]. T se f elds are "packed" toget r and stored
 *                              under a s ngle key. T  key  s referred to as "CoreF eldsKey" (see @T etKeyType.CoreF eldsKey).
 *                              Note: Actually f eld 1  s sk pped w n pack ng as t  f eld  s t  t et  d and   need not be
 *                              expl c ly stored s nce t  pkey already conta ns t  t et  d)
 *
 * Root Core f eld  d         : T  f eld  d under wh ch t  packed core f elds are stored  n Manhattan. (T   s f eld  d "1")
 *
 * Requ red f elds            : (Subset of Core f elds) - F elds w h  ds [1 to 5] that MUST be present on every t et.
 *
 * Add  onal F elds          : All f elds w h f eld  ds >= 'f rstAdd  onalF eld d'
 *
 * Comp led Add  onal f elds : (Subset of Add  onal F elds) - All f elds that t  storage l brary knows about
 *                              ( .e present on t  latest storage_ nternal.thr ft that  s comp led- n).
 *
 * Passthrough f elds         : (Subset of Add  onal F elds) - T  f elds on storage_ nternal.thr ft that t  storage l brary  s NOT aware of
 *                              T se f eld  ds are  s obta ned look ng at t  "_passThroughF elds"  mber of t  scrooge-generated
 *                             'T et' object.
 *
 * coreF elds d n nternalT et: T   s t  f eld  d of t  core f elds (t  only f eld)  n t   nternal T et struct
 */
object T etF elds {
  val f rstAdd  onalF eld d: Short = 100
  val t et dF eld: Short = 1
  val geoF eld d: Short = 9

  // T  f eld under wh ch all t  core f eld values are stored ( n ser al zed form).
  val rootCoreF eld d: Short = 1

  val coreF eld ds:  mmutable. ndexedSeq[F eld d] = {
    val quotedT etF eld d: Short = 19
    (1 to 8).map(_.toShort) ++ Seq(quotedT etF eld d)
  }
  val requ redF eld ds:  mmutable. ndexedSeq[F eld d] = (1 to 5).map(_.toShort)

  val coreF elds d n nternalT et: Short = 1

  val comp ledAdd  onalF eld ds: Seq[F eld d] =
    StoredT et. taData.f elds.f lter(_. d >= f rstAdd  onalF eld d).map(_. d)
  val  nternalF eld ds: Seq[F eld d] =
    StoredT et. taData.f elds.f lter(_. d < f rstAdd  onalF eld d).map(_. d)
  val nonCore nternalF elds: Seq[F eld d] =
    ( nternalF eld ds.toSet -- coreF eld ds.toSet).toSeq
  def getAdd  onalF eld ds(t et: StoredT et): Seq[F eld d] =
    comp ledAdd  onalF eld ds ++ t et._passthroughF elds.keys.toSeq
}

/**
 *  lper object to convert TF eldBlob to ByteBuffer that gets stored  n Manhattan.
 *
 * T  follow ng  s t  format  n wh ch t  TF eldBlob gets stored:
 *    [Vers on][TF eld][TF eldBlob]
 */
object TF eldBlobCodec {
  val B naryProtocolFactory: TB naryProtocol.Factory = new TB naryProtocol.Factory()
  val FormatVers on = 1.0

  def toByteBuffer(tF eldBlob: TF eldBlob): ByteBuffer = {
    val baos = new ByteArrayOutputStream()
    val prot = B naryProtocolFactory.getProtocol(new T OStreamTransport(baos))

    prot.wr eDouble(FormatVers on)
    prot.wr eF eldBeg n(tF eldBlob.f eld)
    prot.wr eB nary(ByteArrayCodec.toByteBuffer(tF eldBlob.data))

    ByteArrayCodec.toByteBuffer(baos.toByteArray)
  }

  def fromByteBuffer(buffer: ByteBuffer): TF eldBlob = {
    val byteArray = ByteArrayCodec.fromByteBuffer(buffer)
    val prot = B naryProtocolFactory.getProtocol(new T mory nputTransport(byteArray))

    val vers on = prot.readDouble()
     f (vers on != FormatVers on) {
      throw new Vers onM smatchError(
        "Vers on m smatch  n decod ng ByteBuffer to TF eldBlob. " +
          "Actual vers on: " + vers on + ". Expected vers on: " + FormatVers on
      )
    }

    val tF eld = prot.readF eldBeg n()
    val dataBuffer = prot.readB nary()
    val data = ByteArrayCodec.fromByteBuffer(dataBuffer)

    TF eldBlob(tF eld, data)
  }
}

/**
 *  lper object to  lp convert 'CoreF elds' object to/from TF eldBlob (and also to construct
 * 'CoreF elds' object from a 'StoredT et' object)
 *
 * More deta ls:
 * - A subset of f elds on t  'StoredT et' thr ft structure (2-8,19) are 'packaged' and stored
 *   toget r as a ser al zed TF eldBlob object under a s ngle key  n Manhattan (see T etKeyCodec
 *    lper object above for more deta ls).
 *
 * - To make t  pack ng/unpack ng t  f elds to/from TF eldBlob object,   created t  follow ng
 *   two  lper thr ft structures 'CoreF elds' and ' nternalT et'
 *
 *       // T  f eld  ds and types  re MUST exactly match f eld  ds on 'StoredT et' thr ft structure.
 *       struct CoreF elds {
 *          2: opt onal  64 user_ d
 *          ...
 *          8: opt onal  64 contr butor_ d
 *          ...
 *          19: opt onal StoredQuotedT et stored_quoted_t et
 *
 *       }
 *
 *       // T  f eld  d of core f elds MUST be "1"
 *       struct  nternalT et {
 *         1: CoreF elds coreF elds
 *       }
 *
 * - G ven t  above two structures, pack ng/unpack ng f elds (2-8,19) on StoredT et object  nto a TF eldBlob
 *   beco s very tr v al:
 *     For pack ng:
 *       ( ) Copy f elds (2-8,19) from StoredT et object to a new CoreF elds object
 *      (  ) Create a new  nternalT et object w h t  'CoreF elds' object constructed  n step ( ) above
 *     (   ) Extract f eld "1" as a TF eldBlob from  nternalF eld (by call ng t  scrooge generated "getF eldBlob(1)"
 *           funct on on t   nternalF eld objecton
 *
 *     For unpack ng:
 *       ( ) Create an empty ' nternalF eld' object
 *      (  ) Call scrooge-generated 'setF eld' by pass ng t  tF eldBlob blob (created by pack ng steps above)
 *     (   ) Do ng step (  ) above w ll create a hydrated 'CoreF eld' object that can be accessed by 'coreF elds'
 *            mber of ' nternalT et' object.
 */
object CoreF eldsCodec {
  val coreF eld ds: Seq[F eld d] = CoreF elds. taData.f elds.map(_. d)

  // "Pack" t  core f elds  .e converts 'CoreF elds' object to "packed" tF eldBlob (See descr pt on
  // above for more deta ls)
  def toTF eldBlob(coreF elds: CoreF elds): TF eldBlob = {
     nternalT et(So (coreF elds)).getF eldBlob(T etF elds.coreF elds d n nternalT et).get
  }

  // "Unpack" t  core f elds from a packed TF eldBlob  nto a CoreF elds object (see descr pt on above for
  // more deta ls)
  def fromTF eldBlob(tF eldBlob: TF eldBlob): CoreF elds = {
     nternalT et().setF eld(tF eldBlob).coreF elds.get
  }

  // "Unpack" t  core f elds from a packed TF eldBlob  nto a Map of core-f eld d-> TF eldBlob
  def unpackF elds(tF eldBlob: TF eldBlob): Map[Short, TF eldBlob] =
    fromTF eldBlob(tF eldBlob).getF eldBlobs(coreF eld ds)

  // Create a 'CoreF elds' thr ft object from 'T et' thr ft object.
  def fromT et(t et: StoredT et): CoreF elds = {
    // As  nt oned above, t  f eld  ds and types on t  'CoreF elds' struct exactly match t 
    // correspond ng f elds on StoredT et structure. So    s safe to call .getF eld() on T et object and
    // and pass t  returned tFleldBlob a 'setF eld' on 'CoreF elds' object.
    coreF eld ds.foldLeft(CoreF elds()) {
      case (core, f eld d) =>
        t et.getF eldBlob(f eld d) match {
          case None => core
          case So (tF eldBlob) => core.setF eld(tF eldBlob)
        }
    }
  }
}

/**
 *  lper object to convert ManhattanExcept on to F eldResponseCode thr ft object
 */
object F eldResponseCodeCodec {
   mport F eldResponseCodec.ValueNotFoundExcept on

  def fromManhattanExcept on(mhExcept on: ManhattanExcept on): F eldResponseCode = {
    mhExcept on match {
      case _: ValueNotFoundExcept on => F eldResponseCode.ValueNotFound
      case _:  nternalErrorManhattanExcept on => F eldResponseCode.Error
      case _:  nval dRequestManhattanExcept on => F eldResponseCode. nval dRequest
      case _: Den edManhattanExcept on => F eldResponseCode.Error
      case _: Unsat sf ableManhattanExcept on => F eldResponseCode.Error
      case _: T  outManhattanExcept on => F eldResponseCode.T  out
    }
  }
}

/**
 *  lper object to construct F eldResponse thr ft object from an Except on.
 * T   s typ cally called to convert 'ManhattanExcept on' object to 'F eldResponse' thr ft object
 */
object F eldResponseCodec {
  class ValueNotFoundExcept on extends ManhattanExcept on("Value not found!") w h NoStackTrace
  pr vate[storage] val NotFound = new ValueNotFoundExcept on

  def fromThrowable(e: Throwable, add  onalMsg: Opt on[Str ng] = None): F eldResponse = {
    val (respCode, errMsg) = e match {
      case mhExcept on: ManhattanExcept on =>
        (F eldResponseCodeCodec.fromManhattanExcept on(mhExcept on), mhExcept on.get ssage)
      case _ => (F eldResponseCode.Error, e.get ssage)
    }

    val respMsg = add  onalMsg.map(_ + ". " + errMsg).orElse(So (errMsg.toStr ng))
    F eldResponse(respCode, respMsg)
  }
}
