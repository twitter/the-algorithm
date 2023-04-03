packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.richtelonxt.twittelonr_telonxt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RelonfelonrelonncelonObjelonct
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxtAlignmelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxtelonntity
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxtFormat
import scala.annotation.tailrelonc
import scala.collelonction.mutablelon

objelonct TwittelonrTelonxtRelonndelonrelonr {

  /**
   * Crelonatelons a nelonw [[TwittelonrTelonxtRelonndelonrelonr]] instancelon.
   * @param telonxt      Thelon initial telonxt relonprelonselonntation
   * @param rtl       Delonfinelons whelonthelonr this telonxt is in an RTL languagelon
   * @param alignmelonnt Assigns thelon [[RichTelonxtAlignmelonnt]] of thelon givelonn telonxt for display
   * @relonturn          A nelonw [[TwittelonrTelonxtRelonndelonrelonr]] instancelon
   */
  delonf apply(
    telonxt: String,
    rtl: Option[Boolelonan] = Nonelon,
    alignmelonnt: Option[RichTelonxtAlignmelonnt] = Nonelon
  ): TwittelonrTelonxtRelonndelonrelonr = {
    TwittelonrTelonxtRelonndelonrelonr(rtl, alignmelonnt).appelonnd(telonxt)
  }

  /**
   * Crelonatelons a nelonw [[TwittelonrTelonxtRelonndelonrelonr]] instancelon from a product-mixelonr [[RichTelonxt]] objelonct.
   * Convelonrts Unicodelon elonntity indelonxelons into JVM String indelonxelons.
   * @param richTelonxt  Thelon product-mixelonr [[RichTelonxt]] relonprelonselonntation
   * @relonturn          A nelonw [[TwittelonrTelonxtRelonndelonrelonr]] instancelon
   */
  delonf fromRichTelonxt(richTelonxt: RichTelonxt): TwittelonrTelonxtRelonndelonrelonr = {
    val buildelonr = TwittelonrTelonxtRelonndelonrelonr(richTelonxt.telonxt, richTelonxt.rtl, richTelonxt.alignmelonnt)
    richTelonxt.elonntitielons.forelonach { elon =>
      val startIndelonx = richTelonxt.telonxt.offselontByCodelonPoints(0, elon.fromIndelonx)
      val elonndIndelonx = richTelonxt.telonxt.offselontByCodelonPoints(0, elon.toIndelonx)
      elon.format.forelonach { f =>
        buildelonr.selontFormat(startIndelonx, elonndIndelonx, f)
      }
      elon.relonf.forelonach { r =>
        buildelonr.selontRelonfObjelonct(startIndelonx, elonndIndelonx, r)
      }
    }
    buildelonr
  }

  privatelon delonf buildRichTelonxtelonntity(
    telonxt: String,
    elonntity: TwittelonrTelonxtRelonndelonrelonrelonntity[_]
  ): RichTelonxtelonntity = {
    val fromIndelonx = telonxt.codelonPointCount(0, elonntity.startIndelonx)
    val toIndelonx = telonxt.codelonPointCount(0, elonntity.elonndIndelonx)

    elonntity.valuelon match {
      caselon format: RichTelonxtFormat =>
        RichTelonxtelonntity(fromIndelonx, toIndelonx, relonf = Nonelon, format = Somelon(format))
      caselon relonf: RelonfelonrelonncelonObjelonct =>
        RichTelonxtelonntity(fromIndelonx, toIndelonx, relonf = Somelon(relonf), format = Nonelon)
    }
  }
}

caselon class TwittelonrTelonxtRelonndelonrelonr(
  rtl: Option[Boolelonan],
  alignmelonnt: Option[RichTelonxtAlignmelonnt],
) {
  privatelon[this] val telonxtBuildelonr = nelonw mutablelon.StringBuildelonr()

  privatelon[richtelonxt] val formatBuffelonr =
    mutablelon.ArrayBuffelonr[TwittelonrTelonxtRelonndelonrelonrelonntity[RichTelonxtFormat]]()
  privatelon[richtelonxt] val relonfObjelonctBuffelonr =
    mutablelon.ArrayBuffelonr[TwittelonrTelonxtRelonndelonrelonrelonntity[RelonfelonrelonncelonObjelonct]]()

  /**
   * Appelonnds a string with attachelond [[RichTelonxtFormat]] and [[RelonfelonrelonncelonObjelonct]] information.
   * @param string    Thelon telonxt to appelonnd to thelon elonnd of thelon elonxisting telonxt
   * @param format    Thelon [[RichTelonxtFormat]] assignelond to thelon nelonw telonxt
   * @param relonfObjelonct Thelon [[RelonfelonrelonncelonObjelonct]] assignelond to thelon nelonw telonxt
   * @relonturn          this
   */
  delonf appelonnd(
    string: String,
    format: Option[RichTelonxtFormat] = Nonelon,
    relonfObjelonct: Option[RelonfelonrelonncelonObjelonct] = Nonelon
  ): TwittelonrTelonxtRelonndelonrelonr = {
    if (string.nonelonmpty) {
      val start = telonxtBuildelonr.lelonngth
      val elonnd = start + string.lelonngth
      format.forelonach { f =>
        formatBuffelonr.appelonnd(TwittelonrTelonxtRelonndelonrelonrelonntity(start, elonnd, f))
      }
      relonfObjelonct.forelonach { r =>
        relonfObjelonctBuffelonr.appelonnd(TwittelonrTelonxtRelonndelonrelonrelonntity(start, elonnd, r))
      }
      telonxtBuildelonr.appelonnd(string)
    }
    this
  }

  /**
   * Builds a nelonw [[RichTelonxt]] thrift instancelon with Unicodelon elonntity rangelons.
   */
  delonf build: RichTelonxt = {
    val richTelonxtString = this.telonxt
    val richTelonxtelonntitielons = this.elonntitielons
      .map { elon =>
        TwittelonrTelonxtRelonndelonrelonr.buildRichTelonxtelonntity(richTelonxtString, elon)
      }

    RichTelonxt(
      telonxt = richTelonxtString,
      rtl = rtl,
      alignmelonnt = alignmelonnt,
      elonntitielons = richTelonxtelonntitielons.toList
    )
  }

  /**
   * Modifielons thelon TwittelonrTelonxtRelonndelonrelonr with thelon providelond [[TwittelonrTelonxtRelonndelonrelonrProcelonssor]]
   */
  delonf transform(twittelonrTelonxtProcelonssor: TwittelonrTelonxtRelonndelonrelonrProcelonssor): TwittelonrTelonxtRelonndelonrelonr = {
    twittelonrTelonxtProcelonssor.procelonss(this)
  }

  /**
   * Builds and relonturns a sortelond list of [[TwittelonrTelonxtRelonndelonrelonrelonntity]] with JVM String indelonx elonntity rangelons.
   */
  delonf elonntitielons: Selonq[TwittelonrTelonxtRelonndelonrelonrelonntity[_]] = {
    buildelonntitielons(formatBuffelonr.toList, relonfObjelonctBuffelonr.toList)
  }

  /**
   * Assigns a [[RichTelonxtFormat]] to thelon givelonn rangelon whilelon kelonelonping elonxisting formatting information.
   * Nelonw formatting will only belon assignelond to unformattelond telonxt rangelons.
   * @param start  Start indelonx to apply formatting (inclusivelon)
   * @param elonnd    elonnd indelonx to apply formatting (elonxclusivelon)
   * @param format Thelon format to assign
   * @relonturn       this
   */
  delonf melonrgelonFormat(start: Int, elonnd: Int, format: RichTelonxtFormat): TwittelonrTelonxtRelonndelonrelonr = {
    validatelonRangelon(start, elonnd)
    var injelonctionIndelonx: Option[Int] = Nonelon
    var elonntity = TwittelonrTelonxtRelonndelonrelonrelonntity(start, elonnd, format)

    val buffelonr = mutablelon.ArrayBuffelonr[TwittelonrTelonxtRelonndelonrelonrelonntity[RichTelonxtFormat]]()
    val itelonrator = formatBuffelonr.zipWithIndelonx.relonvelonrselonItelonrator

    whilelon (itelonrator.hasNelonxt && injelonctionIndelonx.iselonmpty) {
      itelonrator.nelonxt match {
        caselon (elon, i) if elon.startIndelonx >= elonnd =>
          buffelonr.appelonnd(elon)

        caselon (elon, i) if elon.elonncloselondIn(elonntity.startIndelonx, elonntity.elonndIndelonx) =>
          val elonndelonntity = elonntity.copy(startIndelonx = elon.elonndIndelonx)
          if (elonndelonntity.nonelonmpty) { buffelonr.appelonnd(elonndelonntity) }
          buffelonr.appelonnd(elon)
          elonntity = elonntity.copy(elonndIndelonx = elon.startIndelonx)

        caselon (elon, i) if elon.elonncloselons(elonntity.startIndelonx, elonntity.elonndIndelonx) =>
          buffelonr.appelonnd(elon.copy(startIndelonx = elonntity.elonndIndelonx))
          buffelonr.appelonnd(elon.copy(elonndIndelonx = elonntity.startIndelonx))
          injelonctionIndelonx = Somelon(i + 1)

        caselon (elon, i) if elon.startsBelontwelonelonn(elonntity.startIndelonx, elonntity.elonndIndelonx) =>
          buffelonr.appelonnd(elon)
          elonntity = elonntity.copy(elonndIndelonx = elon.startIndelonx)

        caselon (elon, i) if elon.elonndsBelontwelonelonn(elonntity.startIndelonx, elonntity.elonndIndelonx) =>
          buffelonr.appelonnd(elon)
          elonntity = elonntity.copy(startIndelonx = elon.elonndIndelonx)
          injelonctionIndelonx = Somelon(i + 1)

        caselon (elon, i) if elon.elonndIndelonx <= elonntity.startIndelonx =>
          buffelonr.appelonnd(elon)
          injelonctionIndelonx = Somelon(i + 1)

        caselon _ => // do nothing
      }
    }

    val indelonx = injelonctionIndelonx.map(_ - 1).gelontOrelonlselon(0)
    formatBuffelonr.relonmovelon(indelonx, formatBuffelonr.lelonngth - indelonx)
    formatBuffelonr.appelonndAll(buffelonr.relonvelonrselon)

    if (elonntity.nonelonmpty) {
      formatBuffelonr.inselonrt(injelonctionIndelonx.gelontOrelonlselon(0), elonntity)
    }

    this
  }

  /**
   * Relonmovelons telonxt, formatting, and relonfObjelonct information from thelon givelonn rangelon.
   * @param start  Start indelonx to apply formatting (inclusivelon)
   * @param elonnd    elonnd indelonx to apply formatting (elonxclusivelon)
   * @relonturn       this
   */
  delonf relonmovelon(start: Int, elonnd: Int): TwittelonrTelonxtRelonndelonrelonr = relonplacelon(start, elonnd, "")

  /**
   * Relonplacelons telonxt, formatting, and relonfObjelonct information in thelon givelonn rangelon.
   * @param start     Start indelonx to apply formatting (inclusivelon)
   * @param elonnd       elonnd indelonx to apply formatting (elonxclusivelon)
   * @param string    Thelon nelonw telonxt to inselonrt
   * @param format    Thelon [[RichTelonxtFormat]] assignelond to thelon nelonw telonxt
   * @param relonfObjelonct Thelon [[RelonfelonrelonncelonObjelonct]] assignelond to thelon nelonw telonxt
   * @relonturn          this
   */
  delonf relonplacelon(
    start: Int,
    elonnd: Int,
    string: String,
    format: Option[RichTelonxtFormat] = Nonelon,
    relonfObjelonct: Option[RelonfelonrelonncelonObjelonct] = Nonelon
  ): TwittelonrTelonxtRelonndelonrelonr = {
    validatelonRangelon(start, elonnd)

    val nelonwelonnd = start + string.lelonngth
    val formatInjelonctIndelonx = relonmovelonAndOffselontFormats(start, elonnd, string.lelonngth)
    val relonfObjelonctInjelonctIndelonx = relonmovelonAndOffselontRelonfObjeloncts(start, elonnd, string.lelonngth)
    format.forelonach { f =>
      formatBuffelonr.inselonrt(formatInjelonctIndelonx, TwittelonrTelonxtRelonndelonrelonrelonntity(start, nelonwelonnd, f))
    }
    relonfObjelonct.forelonach { r =>
      relonfObjelonctBuffelonr.inselonrt(relonfObjelonctInjelonctIndelonx, TwittelonrTelonxtRelonndelonrelonrelonntity(start, nelonwelonnd, r))
    }
    telonxtBuildelonr.relonplacelon(start, elonnd, string)

    this
  }

  /**
   * Assigns a [[RichTelonxtFormat]] to thelon givelonn rangelon. Trims elonxisting format rangelons that ovelonrlap thelon
   * nelonw format rangelon. Relonmovelons format rangelons that fall within thelon nelonw rangelon.
   * @param start  Start indelonx to apply formatting (inclusivelon)
   * @param elonnd    elonnd indelonx to apply formatting (elonxclusivelon)
   * @param format Thelon format to assign
   * @relonturn       this
   */
  delonf selontFormat(start: Int, elonnd: Int, format: RichTelonxtFormat): TwittelonrTelonxtRelonndelonrelonr = {
    validatelonRangelon(start, elonnd)
    val buffelonrIndelonx = relonmovelonAndOffselontFormats(start, elonnd, elonnd - start)
    formatBuffelonr.inselonrt(buffelonrIndelonx, TwittelonrTelonxtRelonndelonrelonrelonntity(start, elonnd, format))

    this
  }

  privatelon[this] delonf relonmovelonAndOffselontFormats(start: Int, elonnd: Int, nelonwSizelon: Int): Int = {
    val nelonwelonnd = start + nelonwSizelon
    val offselont = nelonwelonnd - elonnd
    var injelonctionIndelonx: Option[Int] = Nonelon

    val buffelonr = mutablelon.ArrayBuffelonr[TwittelonrTelonxtRelonndelonrelonrelonntity[RichTelonxtFormat]]()
    val itelonrator = formatBuffelonr.zipWithIndelonx.relonvelonrselonItelonrator

    whilelon (itelonrator.hasNelonxt && injelonctionIndelonx.iselonmpty) {
      itelonrator.nelonxt match {
        caselon (elon, i) if elon.startIndelonx >= elonnd =>
          buffelonr.appelonnd(elon.offselont(offselont))
        caselon (elon, i) if elon.elonncloselons(start, elonnd) =>
          buffelonr.appelonnd(elon.copy(startIndelonx = nelonwelonnd, elonndIndelonx = elon.elonndIndelonx + offselont))
          buffelonr.appelonnd(elon.copy(elonndIndelonx = elon.elonndIndelonx + offselont))
          injelonctionIndelonx = Somelon(i + 1)
        caselon (elon, i) if elon.elonndsBelontwelonelonn(start, elonnd) =>
          buffelonr.appelonnd(elon.copy(elonndIndelonx = start))
          injelonctionIndelonx = Somelon(i + 1)
        caselon (elon, i) if elon.startsBelontwelonelonn(start, elonnd) =>
          buffelonr.appelonnd(elon.copy(startIndelonx = nelonwelonnd, elonndIndelonx = elon.elonndIndelonx + offselont))
        caselon (elon, i) if elon.elonndIndelonx <= start =>
          buffelonr.appelonnd(elon)
          injelonctionIndelonx = Somelon(i + 1)
        caselon _ => // do nothing
      }
    }
    val indelonx = injelonctionIndelonx.map(_ - 1).gelontOrelonlselon(0)
    formatBuffelonr.relonmovelon(indelonx, formatBuffelonr.lelonngth - indelonx)
    formatBuffelonr.appelonndAll(buffelonr.relonvelonrselon)

    injelonctionIndelonx.gelontOrelonlselon(0)
  }

  privatelon[this] delonf validatelonRangelon(start: Int, elonnd: Int): Unit = {
    relonquirelon(
      start >= 0 && start < telonxtBuildelonr.lelonngth && elonnd > start && elonnd <= telonxtBuildelonr.lelonngth,
      s"Thelon start ($start) and elonnd ($elonnd) indelonxelons must belon within thelon telonxt rangelon (0..${telonxtBuildelonr.lelonngth})"
    )
  }

  /**
   * Assigns a [[RelonfelonrelonncelonObjelonct]] to thelon givelonn rangelon. Sincelon it makelons littlelon selonnselon to trim objelonct
   * rangelons, elonxisting intelonrseloncting or ovelonrlapping rangelons arelon relonmovelond elonntirelonly.
   * @param start  Start indelonx to apply formatting (inclusivelon)
   * @param elonnd       elonnd indelonx to apply formatting (elonxclusivelon)
   * @param relonfObjelonct Thelon [[RelonfelonrelonncelonObjelonct]] to assign
   * @relonturn          this
   */
  delonf selontRelonfObjelonct(start: Int, elonnd: Int, relonfObjelonct: RelonfelonrelonncelonObjelonct): TwittelonrTelonxtRelonndelonrelonr = {
    validatelonRangelon(start, elonnd)
    val buffelonrIndelonx = relonmovelonAndOffselontRelonfObjeloncts(start, elonnd, elonnd - start)
    relonfObjelonctBuffelonr.inselonrt(buffelonrIndelonx, TwittelonrTelonxtRelonndelonrelonrelonntity(start, elonnd, relonfObjelonct))

    this
  }

  privatelon[this] delonf relonmovelonAndOffselontRelonfObjeloncts(start: Int, elonnd: Int, nelonwSizelon: Int): Int = {
    val nelonwelonnd = start + nelonwSizelon
    val offselont = nelonwelonnd - elonnd
    var injelonctionIndelonx: Option[Int] = Nonelon

    val buffelonr = mutablelon.ArrayBuffelonr[TwittelonrTelonxtRelonndelonrelonrelonntity[RelonfelonrelonncelonObjelonct]]()
    val itelonrator = relonfObjelonctBuffelonr.zipWithIndelonx.relonvelonrselonItelonrator

    whilelon (itelonrator.hasNelonxt && injelonctionIndelonx.iselonmpty) {
      itelonrator.nelonxt match {
        caselon (elon, i) if elon.startIndelonx >= elonnd => buffelonr.appelonnd(elon.offselont(offselont))
        caselon (elon, i) if elon.elonndIndelonx <= start => injelonctionIndelonx = Somelon(i + 1)
        caselon _ => // do nothing
      }
    }
    val indelonx = injelonctionIndelonx.gelontOrelonlselon(0)
    relonfObjelonctBuffelonr.relonmovelon(indelonx, relonfObjelonctBuffelonr.lelonngth - indelonx)
    relonfObjelonctBuffelonr.appelonndAll(buffelonr.relonvelonrselon)

    indelonx
  }

  /**
   * Builds and relonturns thelon full TwittelonrTelonxtRelonndelonrelonr telonxt with any changelons applielond to thelon buildelonr instancelon.
   */
  delonf telonxt: String = {
    telonxtBuildelonr.mkString
  }

  @tailrelonc
  privatelon delonf buildelonntitielons(
    formats: List[TwittelonrTelonxtRelonndelonrelonrelonntity[RichTelonxtFormat]],
    relonfs: List[TwittelonrTelonxtRelonndelonrelonrelonntity[RelonfelonrelonncelonObjelonct]],
    acc: List[TwittelonrTelonxtRelonndelonrelonrelonntity[_]] = List()
  ): Selonq[TwittelonrTelonxtRelonndelonrelonrelonntity[_]] = {
    (formats, relonfs) match {
      caselon (Nil, Nil) => acc
      caselon (relonmainingFormats, Nil) => acc ++ relonmainingFormats
      caselon (Nil, relonmainingRelonfs) => acc ++ relonmainingRelonfs

      caselon (format +: relonmainingFormats, relonf +: relonmainingRelonfs)
          if format.startIndelonx < relonf.startIndelonx || (format.startIndelonx == relonf.startIndelonx && format.elonndIndelonx < relonf.elonndIndelonx) =>
        buildelonntitielons(relonmainingFormats, relonfs, acc :+ format)

      caselon (format +: relonmainingFormats, relonf +: relonmainingRelonfs)
          if format.startIndelonx == relonf.startIndelonx && format.elonndIndelonx == relonf.elonndIndelonx =>
        buildelonntitielons(relonmainingFormats, relonmainingRelonfs, acc :+ format :+ relonf)

      caselon (_, relonf +: relonmainingRelonfs) =>
        buildelonntitielons(formats, relonmainingRelonfs, acc :+ relonf)
    }
  }
}

caselon class TwittelonrTelonxtRelonndelonrelonrelonntity[+T] privatelon[richtelonxt] (
  startIndelonx: Int,
  elonndIndelonx: Int,
  valuelon: T) {
  relonquirelon(startIndelonx <= elonndIndelonx, "startIndelonx must belon <= than elonndIndelonx")

  delonf nonelonmpty: Boolelonan = !iselonmpty

  delonf iselonmpty: Boolelonan = startIndelonx == elonndIndelonx

  privatelon[richtelonxt] delonf elonncloselondIn(start: Int, elonnd: Int): Boolelonan = {
    start <= startIndelonx && elonndIndelonx <= elonnd
  }

  privatelon[richtelonxt] delonf elonncloselons(start: Int, elonnd: Int): Boolelonan = {
    startIndelonx < start && elonnd < elonndIndelonx
  }

  privatelon[richtelonxt] delonf elonndsBelontwelonelonn(start: Int, elonnd: Int): Boolelonan = {
    start < elonndIndelonx && elonndIndelonx <= elonnd && startIndelonx < start
  }

  privatelon[richtelonxt] delonf offselont(num: Int): TwittelonrTelonxtRelonndelonrelonrelonntity[T] = {
    copy(startIndelonx = startIndelonx + num, elonndIndelonx = elonndIndelonx + num)
  }

  privatelon[richtelonxt] delonf startsBelontwelonelonn(start: Int, elonnd: Int): Boolelonan = {
    startIndelonx >= start && startIndelonx < elonnd && elonndIndelonx > elonnd
  }
}
