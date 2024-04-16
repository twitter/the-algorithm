package com.tw ter.t etyp e.storage

 mport com.tw ter.logg ng.Logger
 mport com.tw ter.scrooge.TF eldBlob
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.storage.cl ent.manhattan.kv.Den edManhattanExcept on
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanExcept on
 mport com.tw ter.t etyp e.storage.Response._
 mport com.tw ter.t etyp e.storage_ nternal.thr ftscala.StoredT et
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try

object T etUt ls {
  val log: Logger = Logger("com.tw ter.t etyp e.storage.T etStorageL brary")
   mport F eldResponseCodec.ValueNotFoundExcept on

  /**
   *  's rare, but   have seen t ets w h user d=0, wh ch  s l kely t  result of a
   * fa led/part al delete. Treat t se as  nval d t ets, wh ch are returned to callers
   * as not found.
   */
  def  sVal d(t et: StoredT et): Boolean =
    t et.user d.ex sts(_ != 0) && t et.text.nonEmpty &&
      t et.createdV a.nonEmpty && t et.createdAtSec.nonEmpty

  /**
   *  lper funct on to extract Scrubbed f eld  ds from t  result returned by read ng ent re t et pref x
   * funct on.
   *
   * @param records T  sequence of MH records for t  g ven t et d
   *
   * @return T  set of scrubbed f eld  ds
   */
  pr vate[t etyp e] def extractScrubbedF elds(records: Seq[T etManhattanRecord]): Set[Short] =
    records
      .map(r => r.lkey)
      .collect { case T etKey.LKey.ScrubbedF eldKey(f eld d) => f eld d }
      .toSet

  pr vate[t etyp e] val expectedF elds =
    T etF elds.requ redF eld ds.toSet - T etF elds.t et dF eld

  /**
   * F nd t  t  stamp from a t et d and a l st of MH records. T   s used w n
   *   need a t  stamp and   aren't sure that t et d  s a snowflake  d.
   *
   * @param t et d A t et d   want t  t  stamp for.
   * @param records Tb rd_mh records keyed on t et d, one of wh ch should be t 
   * core f elds record.
   * @return A m ll seconds t  stamp  f one could be found.
   */
  pr vate[t etyp e] def creat onT  FromT et dOrMHRecords(
    t et d: Long,
    records: Seq[T etManhattanRecord]
  ): Opt on[Long] =
    Snowflake d
      .un xT  M ll sOptFrom d(t et d).orElse({
        records
          .f nd(_.lkey == T etKey.LKey.CoreF eldsKey)
          .flatMap { coreF elds =>
            CoreF eldsCodec
              .fromTF eldBlob(
                TF eldBlobCodec.fromByteBuffer(coreF elds.value.contents)
              ).createdAtSec.map(seconds => seconds * 1000)
          }
      })

  /**
   *  lper funct on used to parse manhattan results for f elds  n a t et (g ven  n t  form of
   * Sequence of (F eldKey, Try[Un ]) pa rs) and bu ld a T etResponse object.
   *
   * @param callerNa  T  na  of t  caller funct on. Used for error  ssages
   * @param t et d  d of t  T et for wh ch T etResponse  s be ng bu lt
   * @param f eldResults Sequence of (F eldKey, Try[Un ]).
   *
   * @return T etResponse object
   */
  pr vate[t etyp e] def bu ldT etResponse(
    callerNa : Str ng,
    t et d: Long,
    f eldResults: Map[F eld d, Try[Un ]]
  ): T etResponse = {
    // Count Found/Not Found
    val successCount =
      f eldResults.foldLeft(0) {
        case (count, (_, Return(_))) => count + 1
        case (count, (_, Throw(_: ValueNotFoundExcept on))) => count + 1
        case (count, _) => count
      }

    val f eldResponsesMap = getF eldResponses(callerNa , t et d, f eldResults)

    val overallCode =  f (successCount > 0 && successCount == f eldResults.s ze) {
      T etResponseCode.Success
    } else {

      //  f any f eld was rate l m ed, t n   cons der t  ent re t et to be rate l m ed. So f rst   scan
      // t  f eld results to c ck such an occurrence.
      val wasRateL m ed = f eldResults.ex sts { f eldResult =>
        f eldResult._2 match {
          case Throw(e: Den edManhattanExcept on) => true
          case _ => false
        }
      }

      //  re   rate l m ed for any of t  add  onal f elds?
       f (wasRateL m ed) {
        T etResponseCode.OverCapac y
      } else  f (successCount == 0) {
        // successCount  s < f eldResults.s ze at t  po nt. So  f allOrNone  s true or
        //  f successCount == 0 ( .e fa led on all F elds), t  overall code should be 'Fa lure'
        T etResponseCode.Fa lure
      } else {
        // allOrNone == false AND successCount > 0 at t  po nt. Clearly t  overallCode should be Part al
        T etResponseCode.Part al
      }
    }

    T etResponse(t et d, overallCode, So (f eldResponsesMap))

  }

  /**
   *  lper funct on to convert manhattan results  nto a Map[F eld d, F eldResponse]
   *
   * @param f eldResults Sequence of (T etKey, TF eldBlob).
   */
  pr vate[t etyp e] def getF eldResponses(
    callerNa : Str ng,
    t et d: T et d,
    f eldResults: Map[F eld d, Try[_]]
  ): Map[F eld d, F eldResponse] =
    f eldResults.map {
      case (f eld d, resp) =>
        def keyStr = T etKey.f eldKey(t et d, f eld d).toStr ng
        resp match {
          case Return(_) =>
            f eld d -> F eldResponse(F eldResponseCode.Success, None)
          case Throw(mhExcept on: ManhattanExcept on) =>
            val errMsg = s"Except on  n $callerNa . Key: $keyStr. Error: $mhExcept on"
            mhExcept on match {
              case _: ValueNotFoundExcept on => // ValueNotFound  s not an error
              case _ => log.error(errMsg)
            }
            f eld d -> F eldResponseCodec.fromThrowable(mhExcept on, So (errMsg))
          case Throw(e) =>
            val errMsg = s"Except on  n $callerNa . Key: $keyStr. Error: $e"
            log.error(errMsg)
            f eld d -> F eldResponse(F eldResponseCode.Error, So (errMsg))
        }
    }

  /**
   *  lper funct on to bu ld a T etResponse object w n be ng rate l m ed.  s poss ble that only so  of t  f elds
   * got rate l m ed, so    nd cate wh ch f elds got processed successfully, and wh ch encountered so  sort of error.
   *
   * @param t et d T et  d
   * @param callerNa  na  of AP  call ng t  funct on
   * @param f eldResponses f eld responses for t  case w re
   *
   * @return T  T etResponse object
   */
  pr vate[t etyp e] def bu ldT etOverCapac yResponse(
    callerNa : Str ng,
    t et d: Long,
    f eldResponses: Map[F eld d, Try[Un ]]
  ) = {
    val f eldResponsesMap = getF eldResponses(callerNa , t et d, f eldResponses)
    T etResponse(t et d, T etResponseCode.OverCapac y, So (f eldResponsesMap))
  }

  /**
   * Bu ld a StoredT et from a Seq of records. Core f elds are handled spec ally.
   */
  pr vate[t etyp e] def bu ldStoredT et(
    t et d: T et d,
    records: Seq[T etManhattanRecord],
     ncludeScrubbed: Boolean = false,
  ): StoredT et = {
    getStoredT etBlobs(records,  ncludeScrubbed)
      .flatMap { f eldBlob =>
        // W n f eld d == T etF elds.rootCoreF eld d,   have furt r work to do s nce t 
        // 'value'  s really ser al zed/packed vers on of all core f elds.  n t  case  'll have
        // to unpack    nto many TF eldBlobs.
         f (f eldBlob. d == T etF elds.rootCoreF eld d) {
          //   won't throw any error  n t  funct on and  nstead let t  caller funct on handle t 
          // cond  on ( .e  f t  caller funct on does not f nd any values for t  core-f elds  n
          // t  returned map,   should assu  that t  t et  s not found)
          CoreF eldsCodec.unpackF elds(f eldBlob).values.toSeq
        } else {
          Seq(f eldBlob)
        }
      }.foldLeft(StoredT et(t et d))(_.setF eld(_))
  }

  pr vate[t etyp e] def bu ldVal dStoredT et(
    t et d: T et d,
    records: Seq[T etManhattanRecord]
  ): Opt on[StoredT et] = {
    val storedT et = bu ldStoredT et(t et d, records)
     f (storedT et.getF eldBlobs(expectedF elds).nonEmpty &&  sVal d(storedT et)) {
      So (storedT et)
    } else {
      None
    }
  }

  /**
   * Return a TF eldBlob for each StoredT et f eld def ned  n t  set of records.
   * @param  ncludeScrubbed w n false, result w ll not  nclude scrubbed f elds even
   *                         f t  data  s present  n t  set of records.
   */
  pr vate[t etyp e] def getStoredT etBlobs(
    records: Seq[T etManhattanRecord],
     ncludeScrubbed: Boolean = false,
  ): Seq[TF eldBlob] = {
    val scrubbed = extractScrubbedF elds(records)

    records
      .flatMap { r =>
        // extract LKey.F eldKey records  f t y are not scrubbed and get t  r TF eldBlobs
        r.key match {
          case fullKey @ T etKey(_, key: T etKey.LKey.F eldKey)
               f  ncludeScrubbed || !scrubbed.conta ns(key.f eld d) =>
            try {
              val f eldBlob = TF eldBlobCodec.fromByteBuffer(r.value.contents)
               f (f eldBlob.f eld. d != key.f eld d) {
                throw new Assert onError(
                  s"Blob stored for $fullKey has unexpected  d ${f eldBlob.f eld. d}"
                )
              }
              So (f eldBlob)
            } catch {
              case e: Vers onM smatchError =>
                log.error(
                  s"Fa led to decode bytebuffer for $fullKey: ${e.get ssage}"
                )
                throw e
            }
          case _ => None
        }
      }
  }

  /**
   *  s  mportant to bubble up rate l m  ng except ons as t y would l kely be t  root cause for ot r  ssues
   * (t  outs etc.), so   scan for t  part cular except on, and  f found,   bubble that up spec f cally
   *
   * @param seqOfTr es T  sequence of tr es wh ch may conta n w h n   a rate l m  except on
   *
   * @return  f a rate l m  ng exn was detected, t  w ll be a Throw(e: Den edManhattanExcept on)
   *         ot rw se   w ll be a Return(_) only  f all  nd v dual tr es succeeded
   */
  pr vate[t etyp e] def collectW hRateL m C ck(seqOfTr es: Seq[Try[Un ]]): Try[Un ] = {
    val rateL m ThrowOpt = seqOfTr es.f nd {
      case Throw(e: Den edManhattanExcept on) => true
      case _ => false
    }

    rateL m ThrowOpt.getOrElse(
      Try.collect(seqOfTr es).map(_ => ())
    ) // Operat on  s cons dered successful only  f all t  delet ons are successful
  }
}
