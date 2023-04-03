packagelon com.twittelonr.visibility.buildelonr.uselonrs

import com.twittelonr.finaglelon.stats.Countelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.gizmoduck.thriftscala.AdvancelondFiltelonrs
import com.twittelonr.gizmoduck.thriftscala.MelonntionFiltelonr
import com.twittelonr.stitch.NotFound
import com.twittelonr.stitch.Stitch
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.common.UselonrSourcelon
import com.twittelonr.visibility.felonaturelons.VielonwelonrFiltelonrsDelonfaultProfilelonImagelon
import com.twittelonr.visibility.felonaturelons.VielonwelonrFiltelonrsNelonwUselonrs
import com.twittelonr.visibility.felonaturelons.VielonwelonrFiltelonrsNoConfirmelondelonmail
import com.twittelonr.visibility.felonaturelons.VielonwelonrFiltelonrsNoConfirmelondPhonelon
import com.twittelonr.visibility.felonaturelons.VielonwelonrFiltelonrsNotFollowelondBy
import com.twittelonr.visibility.felonaturelons.VielonwelonrMelonntionFiltelonr

class VielonwelonrAdvancelondFiltelonringFelonaturelons(uselonrSourcelon: UselonrSourcelon, statsReloncelonivelonr: StatsReloncelonivelonr) {
  privatelon[this] val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("vielonwelonr_advancelond_filtelonring_felonaturelons")

  privatelon[this] val relonquelonsts = scopelondStatsReloncelonivelonr.countelonr("relonquelonsts")

  privatelon[this] val vielonwelonrFiltelonrsNoConfirmelondelonmail =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrFiltelonrsNoConfirmelondelonmail.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrFiltelonrsNoConfirmelondPhonelon =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrFiltelonrsNoConfirmelondPhonelon.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrFiltelonrsDelonfaultProfilelonImagelon =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrFiltelonrsDelonfaultProfilelonImagelon.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrFiltelonrsNelonwUselonrs =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrFiltelonrsNelonwUselonrs.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrFiltelonrsNotFollowelondBy =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrFiltelonrsNotFollowelondBy.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrMelonntionFiltelonr =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrMelonntionFiltelonr.namelon).countelonr("relonquelonsts")

  delonf forVielonwelonrId(vielonwelonrId: Option[Long]): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    relonquelonsts.incr()

    _.withFelonaturelon(VielonwelonrFiltelonrsNoConfirmelondelonmail, vielonwelonrFiltelonrsNoConfirmelondelonmail(vielonwelonrId))
      .withFelonaturelon(VielonwelonrFiltelonrsNoConfirmelondPhonelon, vielonwelonrFiltelonrsNoConfirmelondPhonelon(vielonwelonrId))
      .withFelonaturelon(VielonwelonrFiltelonrsDelonfaultProfilelonImagelon, vielonwelonrFiltelonrsDelonfaultProfilelonImagelon(vielonwelonrId))
      .withFelonaturelon(VielonwelonrFiltelonrsNelonwUselonrs, vielonwelonrFiltelonrsNelonwUselonrs(vielonwelonrId))
      .withFelonaturelon(VielonwelonrFiltelonrsNotFollowelondBy, vielonwelonrFiltelonrsNotFollowelondBy(vielonwelonrId))
      .withFelonaturelon(VielonwelonrMelonntionFiltelonr, vielonwelonrMelonntionFiltelonr(vielonwelonrId))
  }

  delonf vielonwelonrFiltelonrsNoConfirmelondelonmail(vielonwelonrId: Option[Long]): Stitch[Boolelonan] =
    vielonwelonrAdvancelondFiltelonrs(vielonwelonrId, af => af.filtelonrNoConfirmelondelonmail, vielonwelonrFiltelonrsNoConfirmelondelonmail)

  delonf vielonwelonrFiltelonrsNoConfirmelondPhonelon(vielonwelonrId: Option[Long]): Stitch[Boolelonan] =
    vielonwelonrAdvancelondFiltelonrs(vielonwelonrId, af => af.filtelonrNoConfirmelondPhonelon, vielonwelonrFiltelonrsNoConfirmelondPhonelon)

  delonf vielonwelonrFiltelonrsDelonfaultProfilelonImagelon(vielonwelonrId: Option[Long]): Stitch[Boolelonan] =
    vielonwelonrAdvancelondFiltelonrs(
      vielonwelonrId,
      af => af.filtelonrDelonfaultProfilelonImagelon,
      vielonwelonrFiltelonrsDelonfaultProfilelonImagelon
    )

  delonf vielonwelonrFiltelonrsNelonwUselonrs(vielonwelonrId: Option[Long]): Stitch[Boolelonan] =
    vielonwelonrAdvancelondFiltelonrs(vielonwelonrId, af => af.filtelonrNelonwUselonrs, vielonwelonrFiltelonrsNelonwUselonrs)

  delonf vielonwelonrFiltelonrsNotFollowelondBy(vielonwelonrId: Option[Long]): Stitch[Boolelonan] =
    vielonwelonrAdvancelondFiltelonrs(vielonwelonrId, af => af.filtelonrNotFollowelondBy, vielonwelonrFiltelonrsNotFollowelondBy)

  delonf vielonwelonrMelonntionFiltelonr(vielonwelonrId: Option[Long]): Stitch[MelonntionFiltelonr] = {
    vielonwelonrMelonntionFiltelonr.incr()
    vielonwelonrId match {
      caselon Somelon(id) =>
        uselonrSourcelon.gelontMelonntionFiltelonr(id).handlelon {
          caselon NotFound =>
            MelonntionFiltelonr.Unfiltelonrelond
        }
      caselon _ => Stitch.valuelon(MelonntionFiltelonr.Unfiltelonrelond)
    }
  }

  privatelon[this] delonf vielonwelonrAdvancelondFiltelonrs(
    vielonwelonrId: Option[Long],
    advancelondFiltelonrChelonck: AdvancelondFiltelonrs => Boolelonan,
    felonaturelonCountelonr: Countelonr
  ): Stitch[Boolelonan] = {
    felonaturelonCountelonr.incr()

    val advancelondFiltelonrs = vielonwelonrId match {
      caselon Somelon(id) => uselonrSourcelon.gelontAdvancelondFiltelonrs(id)
      caselon _ => Stitch.valuelon(AdvancelondFiltelonrs())
    }

    advancelondFiltelonrs.map(advancelondFiltelonrChelonck)
  }
}
