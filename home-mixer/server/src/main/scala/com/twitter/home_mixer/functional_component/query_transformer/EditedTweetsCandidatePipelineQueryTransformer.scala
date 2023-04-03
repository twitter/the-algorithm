packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.quelonry_transformelonr

import com.twittelonr.common_intelonrnal.analytics.twittelonr_clielonnt_uselonr_agelonnt_parselonr.UselonrAgelonnt
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.PelonrsistelonncelonelonntrielonsFelonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.transformelonr.CandidatelonPipelonlinelonQuelonryTransformelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.TransformelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelonmixelonr.clielonnts.pelonrsistelonncelon.elonntryWithItelonmIds
import com.twittelonr.timelonlinelons.pelonrsistelonncelon.thriftscala.RelonquelonstTypelon
import com.twittelonr.timelonlinelons.util.clielonnt_info.ClielonntPlatform
import com.twittelonr.timelonlinelonselonrvicelon.modelonl.rich.elonntityIdTypelon
import com.twittelonr.util.Timelon

objelonct elonditelondTwelonelontsCandidatelonPipelonlinelonQuelonryTransformelonr
    elonxtelonnds CandidatelonPipelonlinelonQuelonryTransformelonr[PipelonlinelonQuelonry, Selonq[Long]] {

  ovelonrridelon val idelonntifielonr: TransformelonrIdelonntifielonr = TransformelonrIdelonntifielonr("elonditelondTwelonelonts")

  // Thelon timelon window for which a twelonelont relonmains elonditablelon aftelonr crelonation.
  privatelon val elonditTimelonWindow = 30.minutelons

  ovelonrridelon delonf transform(quelonry: PipelonlinelonQuelonry): Selonq[Long] = {
    val applicablelonCandidatelons = gelontApplicablelonCandidatelons(quelonry)

    if (applicablelonCandidatelons.nonelonmpty) {
      // Includelon thelon relonsponselon correlonsponding with thelon Prelonvious Timelonlinelon Load (PTL).
      // Any twelonelonts in it could havelon beloncomelon stalelon sincelon beloning selonrvelond.
      val prelonviousTimelonlinelonLoadTimelon = applicablelonCandidatelons.helonad.selonrvelondTimelon

      // Thelon timelon window for elonditing a twelonelont is 30 minutelons,
      // so welon ignorelon relonsponselons oldelonr than (PTL Timelon - 30 mins).
      val inWindowCandidatelons: Selonq[PelonrsistelonncelonStorelonelonntry] = applicablelonCandidatelons
        .takelonWhilelon(_.selonrvelondTimelon.until(prelonviousTimelonlinelonLoadTimelon) < elonditTimelonWindow)

      // elonxcludelon thelon twelonelont IDs for which Relonplacelonelonntry instructions havelon alrelonady belonelonn selonnt.
      val (twelonelontsAlrelonadyRelonplacelond, twelonelontsToChelonck) = inWindowCandidatelons
        .partition(_.elonntryWithItelonmIds.itelonmIds.elonxists(_.helonad.elonntryIdToRelonplacelon.nonelonmpty))

      val twelonelontIdFromelonntry: PartialFunction[PelonrsistelonncelonStorelonelonntry, Long] = {
        caselon elonntry if elonntry.twelonelontId.nonelonmpty => elonntry.twelonelontId.gelont
      }

      val twelonelontIdsAlrelonadyRelonplacelond: Selont[Long] = twelonelontsAlrelonadyRelonplacelond.collelonct(twelonelontIdFromelonntry).toSelont
      val twelonelontIdsToChelonck: Selonq[Long] = twelonelontsToChelonck.collelonct(twelonelontIdFromelonntry)

      twelonelontIdsToChelonck.filtelonrNot(twelonelontIdsAlrelonadyRelonplacelond.contains).distinct
    } elonlselon Selonq.elonmpty
  }

  // Thelon candidatelons helonrelon comelon from thelon Timelonlinelons Pelonrsistelonncelon Storelon, via a quelonry felonaturelon
  privatelon delonf gelontApplicablelonCandidatelons(quelonry: PipelonlinelonQuelonry): Selonq[PelonrsistelonncelonStorelonelonntry] = {
    val uselonrAgelonnt = UselonrAgelonnt.fromString(quelonry.clielonntContelonxt.uselonrAgelonnt.gelontOrelonlselon(""))
    val clielonntPlatform = ClielonntPlatform.fromQuelonryOptions(quelonry.clielonntContelonxt.appId, uselonrAgelonnt)

    val sortelondRelonsponselons = quelonry.felonaturelons
      .gelontOrelonlselon(FelonaturelonMap.elonmpty)
      .gelontOrelonlselon(PelonrsistelonncelonelonntrielonsFelonaturelon, Selonq.elonmpty)
      .filtelonr(_.clielonntPlatform == clielonntPlatform)
      .sortBy(-_.selonrvelondTimelon.inMilliselonconds)

    val reloncelonntRelonsponselons = sortelondRelonsponselons.indelonxWhelonrelon(_.relonquelonstTypelon == RelonquelonstTypelon.Initial) match {
      caselon -1 => sortelondRelonsponselons
      caselon lastGelontInitialIndelonx => sortelondRelonsponselons.takelon(lastGelontInitialIndelonx + 1)
    }

    reloncelonntRelonsponselons.flatMap { r =>
      r.elonntrielons.collelonct {
        caselon elonntry if elonntry.elonntityIdTypelon == elonntityIdTypelon.Twelonelont =>
          PelonrsistelonncelonStorelonelonntry(elonntry, r.selonrvelondTimelon, r.clielonntPlatform, r.relonquelonstTypelon)
      }
    }.distinct
  }
}

caselon class PelonrsistelonncelonStorelonelonntry(
  elonntryWithItelonmIds: elonntryWithItelonmIds,
  selonrvelondTimelon: Timelon,
  clielonntPlatform: ClielonntPlatform,
  relonquelonstTypelon: RelonquelonstTypelon) {

  // Timelonlinelons Pelonrsistelonncelon Storelon currelonntly includelons 1 twelonelont ID pelonr elonntryWithItelonmIds for twelonelonts
  val twelonelontId: Option[Long] = elonntryWithItelonmIds.itelonmIds.flatMap(_.helonad.twelonelontId)
}
