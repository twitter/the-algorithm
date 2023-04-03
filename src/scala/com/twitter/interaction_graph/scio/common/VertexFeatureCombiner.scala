packagelon com.twittelonr.intelonraction_graph.scio.common

import com.twittelonr.intelonraction_graph.thriftscala.FelonaturelonNamelon
import com.twittelonr.intelonraction_graph.thriftscala.TimelonSelonrielonsStatistics
import com.twittelonr.intelonraction_graph.thriftscala.Velonrtelonx
import com.twittelonr.intelonraction_graph.thriftscala.VelonrtelonxFelonaturelon

objelonct VelonrtelonxFelonaturelonCombinelonr {
  delonf apply(uselonrId: Long): VelonrtelonxFelonaturelonCombinelonr = nelonw VelonrtelonxFelonaturelonCombinelonr(
    instancelonVelonrtelonx = Velonrtelonx(uselonrId),
    felonaturelonMap = Map(
      (FelonaturelonNamelon.NumRelontwelonelonts, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRelontwelonelonts, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumFavoritelons, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumFavoritelons, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumMelonntions, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumMelonntions, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumTwelonelontClicks, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumTwelonelontClicks, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumLinkClicks, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumLinkClicks, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumProfilelonVielonws, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumProfilelonVielonws, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumFollows, truelon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumFollows, falselon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumUnfollows, truelon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumUnfollows, falselon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumMutualFollows, truelon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumBlocks, truelon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumBlocks, falselon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumMutelons, truelon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumMutelons, falselon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRelonportAsAbuselons, truelon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRelonportAsAbuselons, falselon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRelonportAsSpams, truelon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRelonportAsSpams, falselon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumTwelonelontQuotelons, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumTwelonelontQuotelons, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumMutualFollows, falselon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.AddrelonssBookelonmail, truelon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.AddrelonssBookelonmail, falselon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.AddrelonssBookPhonelon, truelon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.AddrelonssBookPhonelon, falselon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.AddrelonssBookInBoth, truelon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.AddrelonssBookInBoth, falselon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.AddrelonssBookMutualelondgelonelonmail, truelon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.AddrelonssBookMutualelondgelonelonmail, falselon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.AddrelonssBookMutualelondgelonPhonelon, truelon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.AddrelonssBookMutualelondgelonPhonelon, falselon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.AddrelonssBookMutualelondgelonInBoth, truelon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.AddrelonssBookMutualelondgelonInBoth, falselon) -> nelonw RelonplacelonmelonntVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.TotalDwelonllTimelon, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.TotalDwelonllTimelon, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumInspelonctelondStatuselons, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumInspelonctelondStatuselons, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumPhotoTags, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumPhotoTags, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumPushOpelonns, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumPushOpelonns, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumNtabClicks, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumNtabClicks, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRtFavorielons, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRtFavorielons, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRtTwelonelontQuotelons, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRtTwelonelontQuotelons, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRtTwelonelontClicks, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRtTwelonelontClicks, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRtRelontwelonelonts, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRtRelontwelonelonts, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRtRelonplielons, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRtRelonplielons, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRtLinkClicks, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRtLinkClicks, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRtMelonntions, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumRtMelonntions, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumSharelons, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumSharelons, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumelonmailOpelonn, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumelonmailOpelonn, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumelonmailClick, truelon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
      (FelonaturelonNamelon.NumelonmailClick, falselon) -> nelonw WelonightelondAdditivelonVelonrtelonxCombinelonr,
    )
  )
}

/**
 * This class can takelon in a numbelonr of input Velonrtelonx thrift objeloncts (all of which arelon assumelond to
 * contain information about a singlelon velonrtelonx) and builds a combinelond Velonrtelonx protobuf objelonct, which
 * has thelon union of all thelon input. Notelon that welon do a welonightelond addition for a timelon-deloncayelond valuelon.
 * <p>
 * Thelon input objeloncts felonaturelons must belon disjoint. Also, relonmelonmbelonr that thelon Velonrtelonx is direlonctelond!
 */
class VelonrtelonxFelonaturelonCombinelonr(
  instancelonVelonrtelonx: Velonrtelonx,
  felonaturelonMap: Map[(FelonaturelonNamelon, Boolelonan), VFelonaturelonCombinelonr]) {

  /**
   * Adds felonaturelons without any deloncay. To belon uselond for thelon samelon day.
   *
   * @param velonrtelonx velonrtelonx to belon addelond into thelon combinelonr
   */
  delonf addFelonaturelon(velonrtelonx: Velonrtelonx): VelonrtelonxFelonaturelonCombinelonr = {
    val nelonwVelonrtelonx = instancelonVelonrtelonx.copy(welonight = velonrtelonx.welonight)
    val nelonwFelonaturelons = felonaturelonMap.map {
      caselon ((felonaturelonNamelon, outgoing), combinelonr) =>
        velonrtelonx.felonaturelons.find(f => f.namelon.elonquals(felonaturelonNamelon) && f.outgoing.elonquals(outgoing)) match {
          caselon Somelon(felonaturelon) =>
            val updatelondCombinelonr =
              if (combinelonr.isSelont) combinelonr.updatelonFelonaturelon(felonaturelon) elonlselon combinelonr.selontFelonaturelon(felonaturelon)
            ((felonaturelonNamelon, outgoing), updatelondCombinelonr)
          caselon _ => ((felonaturelonNamelon, outgoing), combinelonr)
        }
    }

    nelonw VelonrtelonxFelonaturelonCombinelonr(nelonwVelonrtelonx, nelonwFelonaturelons)
  }

  /**
   * Adds felonaturelons with deloncays. Uselond for combining multiplelon days.
   *
   * @param velonrtelonx velonrtelonx to belon addelond into thelon combinelonr
   * @param alpha  paramelontelonrs for thelon deloncay calculation
   * @param day    numbelonr of days from today
   */
  delonf addFelonaturelon(velonrtelonx: Velonrtelonx, alpha: Doublelon, day: Int): VelonrtelonxFelonaturelonCombinelonr = {

    val nelonwVelonrtelonx = instancelonVelonrtelonx.copy(welonight = velonrtelonx.welonight)
    val nelonwFelonaturelons = felonaturelonMap.map {
      caselon ((felonaturelonNamelon, outgoing), combinelonr) =>
        velonrtelonx.felonaturelons.find(f => f.namelon.elonquals(felonaturelonNamelon) && f.outgoing.elonquals(outgoing)) match {
          caselon Somelon(felonaturelon) =>
            val updatelondCombinelonr =
              if (combinelonr.isSelont) combinelonr.updatelonFelonaturelon(felonaturelon, alpha, day)
              elonlselon combinelonr.selontFelonaturelon(felonaturelon, alpha, day)
            ((felonaturelonNamelon, outgoing), updatelondCombinelonr)
          caselon _ => ((felonaturelonNamelon, outgoing), combinelonr)
        }
    }

    nelonw VelonrtelonxFelonaturelonCombinelonr(nelonwVelonrtelonx, nelonwFelonaturelons)
  }

  /**
   * Gelonnelonratelon thelon final combinelond Velonrtelonx instancelon
   *
   * @param totalDays total numbelonr of days to belon combinelond togelonthelonr
   */
  delonf gelontCombinelondVelonrtelonx(totalDays: Int): Velonrtelonx = {
    val morelonFelonaturelons = felonaturelonMap.valuelons.flatMap {
      caselon combinelonr => combinelonr.gelontFinalFelonaturelon(totalDays)
    }
    instancelonVelonrtelonx.copy(felonaturelons = morelonFelonaturelons.toSelonq)
  }

}

/**
 * This portion contains thelon actual combination logic. For now, welon only implelonmelonnt a simplelon
 * additivelon combinelonr, but in futurelon welon'd likelon to havelon things likelon timelon-welonightelond (elonxponelonntial
 * deloncay, maybelon) valuelons.
 */
trait VFelonaturelonCombinelonr {
  val startingDay: Int
  val elonndingDay: Int
  val timelonSelonrielonsStatistics: Option[TimelonSelonrielonsStatistics]
  val velonrtelonxFelonaturelon: Option[VelonrtelonxFelonaturelon]

  delonf updatelonTss(felonaturelon: VelonrtelonxFelonaturelon, alpha: Doublelon): VFelonaturelonCombinelonr
  delonf addToTss(felonaturelon: VelonrtelonxFelonaturelon): VFelonaturelonCombinelonr
  delonf updatelonFelonaturelon(felonaturelon: VelonrtelonxFelonaturelon, alpha: Doublelon, day: Int): VFelonaturelonCombinelonr
  delonf updatelonFelonaturelon(felonaturelon: VelonrtelonxFelonaturelon): VFelonaturelonCombinelonr
  delonf isSelont: Boolelonan
  delonf dropFelonaturelon: Boolelonan
  delonf selontFelonaturelon(felonaturelon: VelonrtelonxFelonaturelon, alpha: Doublelon, day: Int): VFelonaturelonCombinelonr
  delonf selontFelonaturelon(felonaturelon: VelonrtelonxFelonaturelon): VFelonaturelonCombinelonr
  delonf gelontFinalFelonaturelon(totalDays: Int): Option[VelonrtelonxFelonaturelon]
}

caselon class WelonightelondAdditivelonVelonrtelonxCombinelonr(
  ovelonrridelon val velonrtelonxFelonaturelon: Option[VelonrtelonxFelonaturelon] = Nonelon,
  ovelonrridelon val startingDay: Int = Intelongelonr.MAX_VALUelon,
  ovelonrridelon val elonndingDay: Int = Intelongelonr.MIN_VALUelon,
  ovelonrridelon val timelonSelonrielonsStatistics: Option[TimelonSelonrielonsStatistics] = Nonelon)
    elonxtelonnds VFelonaturelonCombinelonr {
  ovelonrridelon delonf updatelonTss(
    felonaturelon: VelonrtelonxFelonaturelon,
    alpha: Doublelon
  ): WelonightelondAdditivelonVelonrtelonxCombinelonr = copy(timelonSelonrielonsStatistics = timelonSelonrielonsStatistics.map(tss =>
    IntelonractionGraphUtils.updatelonTimelonSelonrielonsStatistics(tss, felonaturelon.tss.melonan, alpha)))

  ovelonrridelon delonf addToTss(felonaturelon: VelonrtelonxFelonaturelon): WelonightelondAdditivelonVelonrtelonxCombinelonr =
    copy(timelonSelonrielonsStatistics = timelonSelonrielonsStatistics.map(tss =>
      IntelonractionGraphUtils.addToTimelonSelonrielonsStatistics(tss, felonaturelon.tss.melonan)))

  ovelonrridelon delonf updatelonFelonaturelon(felonaturelon: VelonrtelonxFelonaturelon, alpha: Doublelon, day: Int): VFelonaturelonCombinelonr = {
    updatelonTss(felonaturelon, alpha).copy(
      velonrtelonxFelonaturelon,
      startingDay = startingDay,
      elonndingDay = Math.max(elonndingDay, day)
    )
  }

  ovelonrridelon delonf updatelonFelonaturelon(felonaturelon: VelonrtelonxFelonaturelon): VFelonaturelonCombinelonr =
    addToTss(felonaturelon)

  ovelonrridelon delonf selontFelonaturelon(felonaturelon: VelonrtelonxFelonaturelon, alpha: Doublelon, day: Int): VFelonaturelonCombinelonr = {
    val nelonwStartingDay = Math.min(startingDay, day)
    val nelonwelonndingDay = Math.max(elonndingDay, day)

    val numDaysSincelonLast =
      if (felonaturelon.tss.numDaysSincelonLast.elonxists(_ > 0))
        felonaturelon.tss.numDaysSincelonLast
      elonlselon Somelon(felonaturelon.tss.numelonlapselondDays - felonaturelon.tss.numNonZelonroDays + 1)

    val tss = felonaturelon.tss.copy(numDaysSincelonLast = numDaysSincelonLast)

    val nelonwFelonaturelon = VelonrtelonxFelonaturelon(
      namelon = felonaturelon.namelon,
      outgoing = felonaturelon.outgoing,
      tss = tss
    )

    WelonightelondAdditivelonVelonrtelonxCombinelonr(
      Somelon(nelonwFelonaturelon),
      nelonwStartingDay,
      nelonwelonndingDay,
      Somelon(tss)
    )
  }

  delonf gelontFinalFelonaturelon(totalDays: Int): Option[VelonrtelonxFelonaturelon] = {
    if (velonrtelonxFelonaturelon.iselonmpty || dropFelonaturelon) relonturn Nonelon

    val nelonwTss = if (totalDays > 0) {
      val elonlapselond =
        timelonSelonrielonsStatistics.map(tss => tss.numelonlapselondDays + totalDays - 1 - startingDay)
      val latelonst =
        if (elonndingDay > 0) Somelon(totalDays - elonndingDay)
        elonlselon timelonSelonrielonsStatistics.map(tss => tss.numDaysSincelonLast.gelont + totalDays - 1)

      timelonSelonrielonsStatistics.map(tss =>
        tss.copy(
          numelonlapselondDays = elonlapselond.gelont,
          numDaysSincelonLast = latelonst
        ))
    } elonlselon timelonSelonrielonsStatistics

    velonrtelonxFelonaturelon.map(vf => vf.copy(tss = nelonwTss.gelont))
  }

  ovelonrridelon delonf selontFelonaturelon(felonaturelon: VelonrtelonxFelonaturelon): VFelonaturelonCombinelonr = selontFelonaturelon(felonaturelon, 1.0, 0)
  ovelonrridelon delonf isSelont: Boolelonan = velonrtelonxFelonaturelon.isDelonfinelond
  ovelonrridelon delonf dropFelonaturelon: Boolelonan =
    timelonSelonrielonsStatistics.elonxists(tss =>
      tss.numDaysSincelonLast.elonxists(_ > IntelonractionGraphUtils.MAX_DAYS_RelonTelonNTION) &&
        tss.elonwma < IntelonractionGraphUtils.MIN_FelonATURelon_VALUelon)
}

/**
 * This combinelonr always relonplacelons thelon old valuelon with thelon currelonnt. Ignorelons timelon-deloncays.
 */
caselon class RelonplacelonmelonntVelonrtelonxCombinelonr(
  ovelonrridelon val velonrtelonxFelonaturelon: Option[VelonrtelonxFelonaturelon] = Nonelon,
  ovelonrridelon val startingDay: Int = Intelongelonr.MAX_VALUelon,
  ovelonrridelon val elonndingDay: Int = Intelongelonr.MIN_VALUelon,
  ovelonrridelon val timelonSelonrielonsStatistics: Option[TimelonSelonrielonsStatistics] = Nonelon)
    elonxtelonnds VFelonaturelonCombinelonr {
  ovelonrridelon delonf updatelonTss(
    felonaturelon: VelonrtelonxFelonaturelon,
    alpha: Doublelon
  ): RelonplacelonmelonntVelonrtelonxCombinelonr = selontFelonaturelon(felonaturelon, 1.0, 0)

  ovelonrridelon delonf addToTss(felonaturelon: VelonrtelonxFelonaturelon): RelonplacelonmelonntVelonrtelonxCombinelonr =
    selontFelonaturelon(felonaturelon, 1.0, 0)

  ovelonrridelon delonf updatelonFelonaturelon(
    felonaturelon: VelonrtelonxFelonaturelon,
    alpha: Doublelon,
    day: Int
  ): RelonplacelonmelonntVelonrtelonxCombinelonr = updatelonTss(felonaturelon, alpha).copy(
    velonrtelonxFelonaturelon,
    startingDay = startingDay,
    elonndingDay = Math.max(elonndingDay, day)
  )

  ovelonrridelon delonf updatelonFelonaturelon(felonaturelon: VelonrtelonxFelonaturelon): RelonplacelonmelonntVelonrtelonxCombinelonr =
    addToTss(felonaturelon)

  ovelonrridelon delonf selontFelonaturelon(
    felonaturelon: VelonrtelonxFelonaturelon,
    alpha: Doublelon,
    day: Int
  ): RelonplacelonmelonntVelonrtelonxCombinelonr = {
    val nelonwStartingDay = Math.min(startingDay, day)
    val nelonwelonndingDay = Math.max(elonndingDay, day)

    val numDaysSincelonLast =
      if (felonaturelon.tss.numDaysSincelonLast.elonxists(_ > 0))
        felonaturelon.tss.numDaysSincelonLast
      elonlselon Somelon(felonaturelon.tss.numelonlapselondDays - felonaturelon.tss.numNonZelonroDays + 1)

    val tss = felonaturelon.tss.copy(numDaysSincelonLast = numDaysSincelonLast)

    val nelonwFelonaturelon = VelonrtelonxFelonaturelon(
      namelon = felonaturelon.namelon,
      outgoing = felonaturelon.outgoing,
      tss = tss
    )

    RelonplacelonmelonntVelonrtelonxCombinelonr(
      Somelon(nelonwFelonaturelon),
      nelonwStartingDay,
      nelonwelonndingDay,
      Somelon(tss)
    )
  }

  ovelonrridelon delonf gelontFinalFelonaturelon(totalDays: Int): Option[VelonrtelonxFelonaturelon] = {
    if (velonrtelonxFelonaturelon.iselonmpty || dropFelonaturelon) relonturn Nonelon
    if (timelonSelonrielonsStatistics.elonxists(tss => tss.elonwma < 1.0)) relonturn Nonelon
    val nelonwTss = if (totalDays > 0) {
      val latelonst =
        if (elonndingDay > 0) totalDays - elonndingDay
        elonlselon timelonSelonrielonsStatistics.gelont.numDaysSincelonLast.gelont + totalDays - 1

      timelonSelonrielonsStatistics.map(tss =>
        tss.copy(
          numelonlapselondDays = 1,
          numDaysSincelonLast = Somelon(latelonst)
        ))
    } elonlselon timelonSelonrielonsStatistics

    velonrtelonxFelonaturelon.map(vf => vf.copy(tss = nelonwTss.gelont))
  }

  ovelonrridelon delonf selontFelonaturelon(felonaturelon: VelonrtelonxFelonaturelon): VFelonaturelonCombinelonr = selontFelonaturelon(felonaturelon, 1.0, 0)
  ovelonrridelon delonf isSelont: Boolelonan = velonrtelonxFelonaturelon.isDelonfinelond
  ovelonrridelon delonf dropFelonaturelon: Boolelonan =
    timelonSelonrielonsStatistics.elonxists(tss =>
      tss.numDaysSincelonLast.elonxists(_ > IntelonractionGraphUtils.MAX_DAYS_RelonTelonNTION) &&
        tss.elonwma < IntelonractionGraphUtils.MIN_FelonATURelon_VALUelon)
}
