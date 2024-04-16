package com.tw ter.t etyp e.ut l

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.logg ng.Logger
 mport com.tw ter. d aserv ces.commons. d a nformat on.thr ftscala.UserDef nedProduct tadata
 mport com.tw ter.scrooge.B naryThr ftStructSer al zer
 mport com.tw ter.servo.cac .ScopedCac Key
 mport com.tw ter.servo.ut l.Transfor r
 mport com.tw ter.t etyp e.thr ftscala.PostT etRequest
 mport com.tw ter.ut l.Base64Long
 mport com.tw ter.ut l.T  
 mport java.n o.ByteBuffer
 mport java.secur y. ssageD gest
 mport org.apac .commons.codec.b nary.Base64
 mport scala.collect on. mmutable.SortedMap

object T etCreat onLock {
  case class Key pr vate (user d: User d, typeCode: Str ng,  dOrMd5: Str ng)
      extends ScopedCac Key("t", "locker", 2, Base64Long.toBase64(user d), typeCode,  dOrMd5) {
    def un queness d: Opt on[Str ng] =
       f (typeCode == Key.TypeCode.Un queness d) So ( dOrMd5) else None
  }

  object Key {
    pr vate[t ] val log = Logger(getClass)

    object TypeCode {
      val S ceT et d = "r"
      val Un queness d = "u"
      val PostT etRequest = "p"
    }

    pr vate[t ] val ser al zer = B naryThr ftStructSer al zer(PostT etRequest)

    // normal ze t  representat on of no  d a  ds.
    pr vate[ut l] def san  ze d aUpload ds( d aUpload ds: Opt on[Seq[Long]]) =
       d aUpload ds.f lter(_.nonEmpty)

    /**
     * Request dedupl cat on depends on t  hash of a ser al zed Thr ft value.
     *
     *  n order to guarantee that a Map has a reproduc ble ser al zed form,
     *  's necessary to f x t  order ng of  s keys.
     */
    pr vate[ut l] def san  ze d a tadata(
       d a tadata: Opt on[scala.collect on.Map[ d a d, UserDef nedProduct tadata]]
    ): Opt on[scala.collect on.Map[ d a d, UserDef nedProduct tadata]] =
       d a tadata.map(m => SortedMap(m.toSeq: _*))

    /**
     *  Make sure to san  ze request f elds w h map/set s nce ser al zed
     *  bytes order ng  s not guaranteed for sa  thr ft values.
     */
    pr vate[ut l] def san  zeRequest(request: PostT etRequest): PostT etRequest =
      PostT etRequest(
        user d = request.user d,
        text = request.text,
        createdV a = "",
         nReplyToT et d = request. nReplyToT et d,
        geo = request.geo,
         d aUpload ds = san  ze d aUpload ds(request. d aUpload ds),
        narrowcast = request.narrowcast,
        nullcast = request.nullcast,
        add  onalF elds = request.add  onalF elds,
        attach ntUrl = request.attach ntUrl,
         d a tadata = san  ze d a tadata(request. d a tadata),
        conversat onControl = request.conversat onControl,
        underly ngCreat vesConta ner d = request.underly ngCreat vesConta ner d,
        ed Opt ons = request.ed Opt ons,
        noteT etOpt ons = request.noteT etOpt ons
      )

    def byS ceT et d(user d: User d, s ceT et d: T et d): Key =
      Key(user d, TypeCode.S ceT et d, Base64Long.toBase64(s ceT et d))

    def byRequest(request: PostT etRequest): Key =
      request.un queness d match {
        case So (uq d) =>
          byUn queness d(request.user d, uq d)
        case None =>
          val san  zed = san  zeRequest(request)
          val san  zedBytes = ser al zer.toBytes(san  zed)
          val d gested =  ssageD gest.get nstance("SHA-256").d gest(san  zedBytes)
          val base64D gest = Base64.encodeBase64Str ng(d gested)
          val key = Key(request.user d, TypeCode.PostT etRequest, base64D gest)
          log. fDebug(s"Generated key $key from request:\n${san  zed}")
          key
      }

    /**
     * Key for t ets that have a un queness  d set. T re  s only one
     * na space of un queness  ds, across all cl ents. T y are
     * expected to be Snowflake  ds,  n order to avo d cac 
     * coll s ons.
     */
    def byUn queness d(user d: User d, un queness d: Long): Key =
      Key(user d, TypeCode.Un queness d, Base64Long.toBase64(un queness d))
  }

  /**
   * T  state of t et creat on for a g ven Key (request).
   */
  sealed tra  State

  object State {

    /**
     * T re  s no t et creat on currently  n progress. (T  can
     * e  r be represented by no entry  n t  cac , or t  spec al
     * marker. T  lets us use c ckAndSet for delet on to avo d
     * acc dentally overwr  ng ot r process' values.)
     */
    case object Unlocked extends State

    /**
     * So  process  s attempt ng to create t  t et.
     */
    case class  nProgress(token: Long, t  stamp: T  ) extends State

    /**
     * T  t et has already been successfully created, and has t 
     * spec f ed  d.
     */
    case class AlreadyCreated(t et d: T et d, t  stamp: T  ) extends State

    /**
     * W n stored  n cac , each state  s pref xed by a byte
     *  nd cat ng t  type of t  entry.
     */
    object TypeCode {
      val Unlocked: Byte = 0.toByte
      val  nProgress: Byte = 1.toByte // + random long + t  stamp
      val AlreadyCreated: Byte = 2.toByte // + t et  d + t  stamp
    }

    pr vate[t ] val BufferS ze = 17 // type byte + 64-b  value + 64-b  t  stamp

    // Constant buffer to use for stor ng t  ser al zed form on
    // Unlocked.
    pr vate[t ] val UnlockedBuf = Array[Byte](TypeCode.Unlocked)

    // Store t  ser al zat on funct on  n a ThreadLocal so that   can
    // reuse t  buffer bet en  nvocat ons.
    pr vate[t ] val threadLocalSer al ze = new ThreadLocal[State => Array[Byte]] {
      overr de def  n  alValue(): State => Array[Byte] = {
        // Allocate t  thread-local state
        val ary = new Array[Byte](BufferS ze)
        val buf = ByteBuffer.wrap(ary)

        {
          case Unlocked => UnlockedBuf
          case  nProgress(token, t  stamp) =>
            buf.clear()
            buf
              .put(TypeCode. nProgress)
              .putLong(token)
              .putLong(t  stamp.s nceEpoch. nNanoseconds)
            ary
          case AlreadyCreated(t et d, t  stamp) =>
            buf.clear()
            buf
              .put(TypeCode.AlreadyCreated)
              .putLong(t et d)
              .putLong(t  stamp.s nceEpoch. nNanoseconds)
            ary
        }
      }
    }

    /**
     * Convert t  State to t  cac  representat on.
     */
    pr vate[t ] def toBytes(state: State): Array[Byte] =
      threadLocalSer al ze.get()(state)

    /**
     * Convert t  byte array  nto a LockState.
     *
     * @throws Runt  Except on  f t  buffer  s not of t  r ght s ze
     *   and format
     */
    pr vate[t ] def fromBytes(bytes: Array[Byte]): State = {
      val buf = ByteBuffer.wrap(bytes)
      val result = buf.get() match {
        case TypeCode.Unlocked => Unlocked
        case TypeCode. nProgress =>  nProgress(buf.getLong(), buf.getLong().nanoseconds.afterEpoch)
        case TypeCode.AlreadyCreated =>
          AlreadyCreated(buf.getLong(), buf.getLong().nanoseconds.afterEpoch)
        case ot r => throw new Runt  Except on(" nval d type code: " + ot r)
      }
       f (buf.rema n ng != 0) {
        throw new Runt  Except on("Extra data  n buffer: " + bytes)
      }
      result
    }

    /**
     * How to ser al ze t  State for storage  n cac .
     */
    val Ser al zer: Transfor r[State, Array[Byte]] =
      Transfor r[State, Array[Byte]](tTo = toBytes _, tFrom = fromBytes _)
  }
}
