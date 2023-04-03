packagelon com.twittelonr.visibility.buildelonr.uselonrs

import com.twittelonr.finaglelon.stats.Countelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.gizmoduck.thriftscala.Labelonl
import com.twittelonr.gizmoduck.thriftscala.Safelonty
import com.twittelonr.gizmoduck.thriftscala.UnivelonrsalQualityFiltelonring
import com.twittelonr.gizmoduck.thriftscala.Uselonr
import com.twittelonr.gizmoduck.thriftscala.UselonrTypelon
import com.twittelonr.stitch.NotFound
import com.twittelonr.stitch.Stitch
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.common.UselonrId
import com.twittelonr.visibility.common.UselonrSourcelon
import com.twittelonr.visibility.felonaturelons._
import com.twittelonr.visibility.intelonrfacelons.common.blelonndelonr.BlelonndelonrVFRelonquelonstContelonxt
import com.twittelonr.visibility.intelonrfacelons.common.selonarch.SelonarchVFRelonquelonstContelonxt
import com.twittelonr.visibility.modelonls.UselonrAgelon
import com.twittelonr.visibility.modelonls.VielonwelonrContelonxt

class VielonwelonrFelonaturelons(uselonrSourcelon: UselonrSourcelon, statsReloncelonivelonr: StatsReloncelonivelonr) {
  privatelon[this] val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("vielonwelonr_felonaturelons")

  privatelon[this] val relonquelonsts = scopelondStatsReloncelonivelonr.countelonr("relonquelonsts")

  privatelon[this] val vielonwelonrIdCount =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrId.namelon).countelonr("relonquelonsts")
  privatelon[this] val relonquelonstCountryCodelon =
    scopelondStatsReloncelonivelonr.scopelon(RelonquelonstCountryCodelon.namelon).countelonr("relonquelonsts")
  privatelon[this] val relonquelonstIsVelonrifielondCrawlelonr =
    scopelondStatsReloncelonivelonr.scopelon(RelonquelonstIsVelonrifielondCrawlelonr.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrUselonrLabelonls =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrUselonrLabelonls.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrIsDelonactivatelond =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrIsDelonactivatelond.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrIsProtelonctelond =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrIsProtelonctelond.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrIsSuspelonndelond =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrIsSuspelonndelond.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrRolelons =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrRolelons.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrCountryCodelon =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrCountryCodelon.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrAgelon =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrAgelon.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrHasUnivelonrsalQualityFiltelonrelonnablelond =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrHasUnivelonrsalQualityFiltelonrelonnablelond.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrIsSoftUselonrCtr =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrIsSoftUselonr.namelon).countelonr("relonquelonsts")

  delonf forVielonwelonrBlelonndelonrContelonxt(
    blelonndelonrContelonxt: BlelonndelonrVFRelonquelonstContelonxt,
    vielonwelonrContelonxt: VielonwelonrContelonxt
  ): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr =
    forVielonwelonrContelonxt(vielonwelonrContelonxt)
      .andThelonn(
        _.withConstantFelonaturelon(
          VielonwelonrOptInBlocking,
          blelonndelonrContelonxt.uselonrSelonarchSafelontySelonttings.optInBlocking)
          .withConstantFelonaturelon(
            VielonwelonrOptInFiltelonring,
            blelonndelonrContelonxt.uselonrSelonarchSafelontySelonttings.optInFiltelonring)
      )

  delonf forVielonwelonrSelonarchContelonxt(
    selonarchContelonxt: SelonarchVFRelonquelonstContelonxt,
    vielonwelonrContelonxt: VielonwelonrContelonxt
  ): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr =
    forVielonwelonrContelonxt(vielonwelonrContelonxt)
      .andThelonn(
        _.withConstantFelonaturelon(
          VielonwelonrOptInBlocking,
          selonarchContelonxt.uselonrSelonarchSafelontySelonttings.optInBlocking)
          .withConstantFelonaturelon(
            VielonwelonrOptInFiltelonring,
            selonarchContelonxt.uselonrSelonarchSafelontySelonttings.optInFiltelonring)
      )

  delonf forVielonwelonrContelonxt(vielonwelonrContelonxt: VielonwelonrContelonxt): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr =
    forVielonwelonrId(vielonwelonrContelonxt.uselonrId)
      .andThelonn(
        _.withConstantFelonaturelon(RelonquelonstCountryCodelon, relonquelonstCountryCodelon(vielonwelonrContelonxt))
      ).andThelonn(
        _.withConstantFelonaturelon(RelonquelonstIsVelonrifielondCrawlelonr, relonquelonstIsVelonrifielondCrawlelonr(vielonwelonrContelonxt))
      )

  delonf forVielonwelonrId(vielonwelonrId: Option[UselonrId]): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = { buildelonr =>
    relonquelonsts.incr()

    val buildelonrWithFelonaturelons = buildelonr
      .withConstantFelonaturelon(VielonwelonrId, vielonwelonrId)
      .withFelonaturelon(VielonwelonrIsProtelonctelond, vielonwelonrIsProtelonctelond(vielonwelonrId))
      .withFelonaturelon(
        VielonwelonrHasUnivelonrsalQualityFiltelonrelonnablelond,
        vielonwelonrHasUnivelonrsalQualityFiltelonrelonnablelond(vielonwelonrId)
      )
      .withFelonaturelon(VielonwelonrIsSuspelonndelond, vielonwelonrIsSuspelonndelond(vielonwelonrId))
      .withFelonaturelon(VielonwelonrIsDelonactivatelond, vielonwelonrIsDelonactivatelond(vielonwelonrId))
      .withFelonaturelon(VielonwelonrUselonrLabelonls, vielonwelonrUselonrLabelonls(vielonwelonrId))
      .withFelonaturelon(VielonwelonrRolelons, vielonwelonrRolelons(vielonwelonrId))
      .withFelonaturelon(VielonwelonrAgelon, vielonwelonrAgelonInYelonars(vielonwelonrId))
      .withFelonaturelon(VielonwelonrIsSoftUselonr, vielonwelonrIsSoftUselonr(vielonwelonrId))

    vielonwelonrId match {
      caselon Somelon(_) =>
        vielonwelonrIdCount.incr()
        buildelonrWithFelonaturelons
          .withFelonaturelon(VielonwelonrCountryCodelon, vielonwelonrCountryCodelon(vielonwelonrId))

      caselon _ =>
        buildelonrWithFelonaturelons
    }
  }

  delonf forVielonwelonrNoDelonfaults(vielonwelonrOpt: Option[Uselonr]): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    buildelonr =>
      relonquelonsts.incr()

      vielonwelonrOpt match {
        caselon Somelon(vielonwelonr) =>
          buildelonr
            .withConstantFelonaturelon(VielonwelonrId, vielonwelonr.id)
            .withConstantFelonaturelon(VielonwelonrIsProtelonctelond, vielonwelonrIsProtelonctelondOpt(vielonwelonr))
            .withConstantFelonaturelon(VielonwelonrIsSuspelonndelond, vielonwelonrIsSuspelonndelondOpt(vielonwelonr))
            .withConstantFelonaturelon(VielonwelonrIsDelonactivatelond, vielonwelonrIsDelonactivatelondOpt(vielonwelonr))
            .withConstantFelonaturelon(VielonwelonrCountryCodelon, vielonwelonrCountryCodelon(vielonwelonr))
        caselon Nonelon =>
          buildelonr
            .withConstantFelonaturelon(VielonwelonrIsProtelonctelond, falselon)
            .withConstantFelonaturelon(VielonwelonrIsSuspelonndelond, falselon)
            .withConstantFelonaturelon(VielonwelonrIsDelonactivatelond, falselon)
      }
  }

  privatelon delonf chelonckSafelontyValuelon(
    vielonwelonrId: Option[UselonrId],
    safelontyChelonck: Safelonty => Boolelonan,
    felonaturelonCountelonr: Countelonr
  ): Stitch[Boolelonan] =
    vielonwelonrId match {
      caselon Somelon(id) =>
        uselonrSourcelon.gelontSafelonty(id).map(safelontyChelonck).elonnsurelon {
          felonaturelonCountelonr.incr()
        }
      caselon Nonelon => Stitch.Falselon
    }

  privatelon delonf chelonckSafelontyValuelon(
    vielonwelonr: Uselonr,
    safelontyChelonck: Safelonty => Boolelonan
  ): Boolelonan = {
    vielonwelonr.safelonty.elonxists(safelontyChelonck)
  }

  delonf vielonwelonrIsProtelonctelond(vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    chelonckSafelontyValuelon(vielonwelonrId, s => s.isProtelonctelond, vielonwelonrIsProtelonctelond)

  delonf vielonwelonrIsProtelonctelond(vielonwelonr: Uselonr): Boolelonan =
    chelonckSafelontyValuelon(vielonwelonr, s => s.isProtelonctelond)

  delonf vielonwelonrIsProtelonctelondOpt(vielonwelonr: Uselonr): Option[Boolelonan] =
    vielonwelonr.safelonty.map(_.isProtelonctelond)

  delonf vielonwelonrIsDelonactivatelond(vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    chelonckSafelontyValuelon(vielonwelonrId, s => s.delonactivatelond, vielonwelonrIsDelonactivatelond)

  delonf vielonwelonrIsDelonactivatelond(vielonwelonr: Uselonr): Boolelonan =
    chelonckSafelontyValuelon(vielonwelonr, s => s.delonactivatelond)

  delonf vielonwelonrIsDelonactivatelondOpt(vielonwelonr: Uselonr): Option[Boolelonan] =
    vielonwelonr.safelonty.map(_.delonactivatelond)

  delonf vielonwelonrHasUnivelonrsalQualityFiltelonrelonnablelond(vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    chelonckSafelontyValuelon(
      vielonwelonrId,
      s => s.univelonrsalQualityFiltelonring == UnivelonrsalQualityFiltelonring.elonnablelond,
      vielonwelonrHasUnivelonrsalQualityFiltelonrelonnablelond
    )

  delonf vielonwelonrUselonrLabelonls(vielonwelonrIdOpt: Option[UselonrId]): Stitch[Selonq[Labelonl]] =
    vielonwelonrIdOpt match {
      caselon Somelon(vielonwelonrId) =>
        uselonrSourcelon
          .gelontLabelonls(vielonwelonrId).map(_.labelonls)
          .handlelon {
            caselon NotFound => Selonq.elonmpty
          }.elonnsurelon {
            vielonwelonrUselonrLabelonls.incr()
          }
      caselon _ => Stitch.valuelon(Selonq.elonmpty)
    }

  delonf vielonwelonrAgelonInYelonars(vielonwelonrId: Option[UselonrId]): Stitch[UselonrAgelon] =
    (vielonwelonrId match {
      caselon Somelon(id) =>
        uselonrSourcelon
          .gelontelonxtelonndelondProfilelon(id).map(_.agelonInYelonars)
          .handlelon {
            caselon NotFound => Nonelon
          }.elonnsurelon {
            vielonwelonrAgelon.incr()
          }
      caselon _ => Stitch.valuelon(Nonelon)
    }).map(UselonrAgelon)

  delonf vielonwelonrIsSoftUselonr(vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] = {
    vielonwelonrId match {
      caselon Somelon(id) =>
        uselonrSourcelon
          .gelontUselonrTypelon(id).map { uselonrTypelon =>
            uselonrTypelon == UselonrTypelon.Soft
          }.elonnsurelon {
            vielonwelonrIsSoftUselonrCtr.incr()
          }
      caselon _ => Stitch.Falselon
    }
  }

  delonf relonquelonstCountryCodelon(vielonwelonrContelonxt: VielonwelonrContelonxt): Option[String] = {
    relonquelonstCountryCodelon.incr()
    vielonwelonrContelonxt.relonquelonstCountryCodelon
  }

  delonf relonquelonstIsVelonrifielondCrawlelonr(vielonwelonrContelonxt: VielonwelonrContelonxt): Boolelonan = {
    relonquelonstIsVelonrifielondCrawlelonr.incr()
    vielonwelonrContelonxt.isVelonrifielondCrawlelonr
  }

  delonf vielonwelonrCountryCodelon(vielonwelonrId: Option[UselonrId]): Stitch[String] =
    vielonwelonrId match {
      caselon Somelon(id) =>
        uselonrSourcelon
          .gelontAccount(id).map(_.countryCodelon).flatMap {
            caselon Somelon(countryCodelon) => Stitch.valuelon(countryCodelon.toLowelonrCaselon)
            caselon _ => Stitch.NotFound
          }.elonnsurelon {
            vielonwelonrCountryCodelon.incr()
          }

      caselon _ => Stitch.NotFound
    }

  delonf vielonwelonrCountryCodelon(vielonwelonr: Uselonr): Option[String] =
    vielonwelonr.account.flatMap(_.countryCodelon)
}
