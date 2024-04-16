package com.tw ter.t etyp e.storage

 mport com.google.common.base.CaseFormat
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.scrooge.TF eldBlob
 mport com.tw ter.scrooge.Thr ftStructF eld nfo
 mport com.tw ter.st ch.St ch
 mport com.tw ter.storage.cl ent.manhattan.kv._
 mport com.tw ter.t etyp e.add  onalf elds.Add  onalF elds
 mport com.tw ter.t etyp e.storage.ManhattanOperat ons.Read
 mport com.tw ter.t etyp e.storage.T etUt ls._
 mport com.tw ter.t etyp e.storage_ nternal.thr ftscala.StoredT et
 mport com.tw ter.t etyp e.thr ftscala.{T et => T etyp eT et}
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport d ffshow.Conta ner
 mport d ffshow.D ffShow
 mport d ffshow.Expr
 mport org.apac .commons.codec.b nary.Base64
 mport scala.ut l.Try
 mport shapeless.Cac d
 mport shapeless.Str ct

// T  class  s used by t  T etyp e Console to  nspect t et f eld content  n Manhattan
class  nspectF elds(svc dent f er: Serv ce dent f er) {
  val mhAppl cat on d = "tb rd_mh"
  val mhDatasetNa  = "tb rd_mh"
  val mhDest nat onNa  = "/s/manhattan/cylon.nat ve-thr ft"
  val mhT  out: Durat on = 5000.m ll seconds

  val localMhEndpo nt: ManhattanKVEndpo nt =
    ManhattanKVEndpo ntBu lder(
      ManhattanKVCl ent(
        mhAppl cat on d,
        mhDest nat onNa ,
        ManhattanKVCl entMtlsParams(svc dent f er)))
      .defaultGuarantee(Guarantee.SoftDcRead Wr es)
      .defaultMaxT  out(mhT  out)
      .bu ld()

  val readOperat on: Read = (new ManhattanOperat ons(mhDatasetNa , localMhEndpo nt)).read

  def lookup(t et d: Long): Future[Str ng] = {
    val result = readOperat on(t et d).l ftToTry.map {
      case Return(mhRecords) =>
        prettyPr ntManhattanRecords(t et d, T etKey.padT et dStr(t et d), mhRecords)
      case Throw(e) => e.toStr ng
    }

    St ch.run(result)
  }

  def storedT et(t et d: Long): Future[StoredT et] = {
    val result = readOperat on(t et d).l ftToTry.map {
      case Return(mhRecords) =>
        bu ldStoredT et(t et d, mhRecords)
      case Throw(e) =>
        throw e
    }

    St ch.run(result)
  }

  pr vate[t ] def prettyPr ntManhattanRecords(
    t et d: Long,
    pkey: Str ng,
    mhRecords: Seq[T etManhattanRecord]
  ): Str ng = {
     f (mhRecords. sEmpty) {
      "Not Found"
    } else {
      val formattedRecords = getFormattedManhattanRecords(t et d, mhRecords)
      val keyF eldW dth = formattedRecords.map(_.key.length).max + 2
      val f eldNa F eldW dth = formattedRecords.map(_.f eldNa .length).max + 2

      val formatStr ng = s"    %-${keyF eldW dth}s %-${f eldNa F eldW dth}s %s"

      val recordsStr ng =
        formattedRecords
          .map { record =>
            val content = record.content.replaceAll("\n", "\n" + formatStr ng.format("", "", ""))
            formatStr ng.format(record.key, record.f eldNa , content)
          }
          .mkStr ng("\n")

      "/tb rd_mh/" + pkey + "/" + "\n" + recordsStr ng
    }
  }

  pr vate[t ] def getFormattedManhattanRecords(
    t et d: Long,
    mhRecords: Seq[T etManhattanRecord]
  ): Seq[FormattedManhattanRecord] = {
    val storedT et = bu ldStoredT et(t et d, mhRecords).copy(updatedAt = None)
    val t etyp eT et: Opt on[T etyp eT et] =
      Try(StorageConvers ons.fromStoredT et(storedT et)).toOpt on

    val blobMap: Map[Str ng, TF eldBlob] = getStoredT etBlobs(mhRecords).map { blob =>
      getF eldNa (blob.f eld. d) -> blob
    }.toMap

    mhRecords
      .map {
        case T etManhattanRecord(fullKey, mhValue) =>
          FormattedManhattanRecord(
            key = fullKey.lKey.toStr ng,
            f eldNa  = getF eldNa (fullKey.lKey),
            content = prettyPr ntManhattanValue(
              fullKey.lKey,
              mhValue,
              storedT et,
              t etyp eT et,
              t et d,
              blobMap
            )
          )
      }
      .sortBy(_.key.replace("external", "xternal")) // sort by key, w h  nternal f rst
  }

  pr vate[t ] def getF eldNa FromThr ft(
    f eld d: Short,
    f eld nfos: L st[Thr ftStructF eld nfo]
  ): Str ng =
    f eld nfos
      .f nd( nfo =>  nfo.tf eld. d == f eld d)
      .map(_.tf eld.na )
      .getOrElse("<UNKNOWN F ELD>")

  pr vate[t ] def  sLkeyScrubbedF eld(lkey: Str ng): Boolean =
    lkey.spl ("/")(1) == "scrubbed_f elds"

  pr vate[t ] def getF eldNa (lkey: T etKey.LKey): Str ng =
    lkey match {
      case f eldKey: T etKey.LKey.F eldKey => getF eldNa (f eldKey.f eld d)
      case _ => ""
    }

  pr vate[t ] def getF eldNa (f eld d: Short): Str ng =
     f (f eld d == 1) {
      "core_f elds"
    } else  f (Add  onalF elds. sAdd  onalF eld d(f eld d)) {
      getF eldNa FromThr ft(f eld d, T etyp eT et.f eld nfos)
    } else {
      getF eldNa FromThr ft(f eld d, StoredT et.f eld nfos)
    }

  pr vate[t ] def prettyPr ntManhattanValue(
    lkey: T etKey.LKey,
    mhValue: T etManhattanValue,
    storedT et: StoredT et,
    t etyp eT et: Opt on[T etyp eT et],
    t et d: Long,
    tf eldBlobs: Map[Str ng, TF eldBlob]
  ): Str ng = {
    val decoded = lkey match {
      case _: T etKey.LKey. tadataKey =>
        decode tadata(mhValue)

      case f eldKey: T etKey.LKey.F eldKey =>
        tf eldBlobs
          .get(getF eldNa (f eldKey.f eld d))
          .map(blob => decodeF eld(t et d, blob, storedT et, t etyp eT et))

      case _ =>
        None
    }

    decoded.getOrElse { //  f all else fa ls, encode t  data as a base64 str ng
      val contents = mhValue.contents.array
       f (contents. sEmpty) {
        "<NO DATA>"
      } else {
        Base64.encodeBase64Str ng(contents)
      }
    }
  }

  pr vate[t ] def decode tadata(mhValue: T etManhattanValue): Opt on[Str ng] = {
    val byteArray = ByteArrayCodec.fromByteBuffer(mhValue.contents)
    Try(Json.decode(byteArray).toStr ng).toOpt on
  }

  pr vate[t ] def decodeF eld(
    t et d: Long,
    blob: TF eldBlob,
    storedT et: StoredT et,
    t etyp eT et: Opt on[T etyp eT et]
  ): Str ng = {
    val f eld d = blob.f eld. d

     f (f eld d == 1) {
      coreF elds(storedT et)
    } else  f (Add  onalF elds. sAdd  onalF eld d(f eld d)) {
      decodeT etW hOneF eld(T etyp eT et(t et d).setF eld(blob))
    } else {
      decodeT etW hOneF eld(StoredT et(t et d).setF eld(blob))
    }
  }

  // Takes a T et or StoredT et w h a s ngle f eld set and returns t  value of that f eld
  pr vate[t ] def decodeT etW hOneF eld[T](
    t etW hOneF eld: T
  )(
     mpl c  ev: Cac d[Str ct[D ffShow[T]]]
  ): Str ng = {
    val conf g = d ffshow.Conf g(h deF eldW hEmptyVal = true)
    val tree: Expr = conf g.transform(D ffShow.show(t etW hOneF eld))

    // matc s a T et or StoredT et w h two values, t  f rst be ng t   d
    val value = tree.transform {
      case Conta ner(_, L st(d ffshow.F eld(" d", _), d ffshow.F eld(_, value))) => value
    }

    conf g.exprPr nter.apply(value, w dth = 80).render
  }

  pr vate[t ] def coreF elds(storedT et: StoredT et): Str ng =
    d ffshow.show(CoreF eldsCodec.fromT et(storedT et), h deF eldW hEmptyVal = true)

  pr vate[t ] def toCa lCase(s: Str ng): Str ng =
    CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s)
}

case class FormattedManhattanRecord(key: Str ng, f eldNa : Str ng, content: Str ng)
