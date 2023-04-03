packagelon com.twittelonr.visibility.buildelonr.uselonrs

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.common.UselonrId
import com.twittelonr.visibility.common.UselonrSelonarchSafelontySourcelon
import com.twittelonr.visibility.felonaturelons.VielonwelonrId
import com.twittelonr.visibility.felonaturelons.VielonwelonrOptInBlocking
import com.twittelonr.visibility.felonaturelons.VielonwelonrOptInFiltelonring

class VielonwelonrSelonarchSafelontyFelonaturelons(
  uselonrSelonarchSafelontySourcelon: UselonrSelonarchSafelontySourcelon,
  statsReloncelonivelonr: StatsReloncelonivelonr) {
  privatelon[this] val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("vielonwelonr_selonarch_safelonty_felonaturelons")

  privatelon[this] val relonquelonsts = scopelondStatsReloncelonivelonr.countelonr("relonquelonsts")

  privatelon[this] val vielonwelonrOptInBlockingRelonquelonsts =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrOptInBlocking.namelon).countelonr("relonquelonsts")

  privatelon[this] val vielonwelonrOptInFiltelonringRelonquelonsts =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrOptInFiltelonring.namelon).countelonr("relonquelonsts")

  delonf forVielonwelonrId(vielonwelonrId: Option[UselonrId]): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = { buildelonr =>
    relonquelonsts.incr()

    buildelonr
      .withConstantFelonaturelon(VielonwelonrId, vielonwelonrId)
      .withFelonaturelon(VielonwelonrOptInBlocking, vielonwelonrOptInBlocking(vielonwelonrId))
      .withFelonaturelon(VielonwelonrOptInFiltelonring, vielonwelonrOptInFiltelonring(vielonwelonrId))
  }

  delonf vielonwelonrOptInBlocking(vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] = {
    vielonwelonrOptInBlockingRelonquelonsts.incr()
    vielonwelonrId match {
      caselon Somelon(uselonrId) => uselonrSelonarchSafelontySourcelon.optInBlocking(uselonrId)
      caselon _ => Stitch.Falselon
    }
  }

  delonf vielonwelonrOptInFiltelonring(vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] = {
    vielonwelonrOptInFiltelonringRelonquelonsts.incr()
    vielonwelonrId match {
      caselon Somelon(uselonrId) => uselonrSelonarchSafelontySourcelon.optInFiltelonring(uselonrId)
      caselon _ => Stitch.Falselon
    }
  }
}
