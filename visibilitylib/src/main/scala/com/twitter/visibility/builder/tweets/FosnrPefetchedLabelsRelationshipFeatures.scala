packagelon com.twittelonr.visibility.buildelonr.twelonelonts

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.buildelonr.uselonrs.VielonwelonrVelonrbsAuthor
import com.twittelonr.visibility.common.UselonrId
import com.twittelonr.visibility.common.UselonrRelonlationshipSourcelon
import com.twittelonr.visibility.felonaturelons._
import com.twittelonr.visibility.modelonls.TwelonelontSafelontyLabelonl
import com.twittelonr.visibility.modelonls.ViolationLelonvelonl

class FosnrPelonfelontchelondLabelonlsRelonlationshipFelonaturelons(
  uselonrRelonlationshipSourcelon: UselonrRelonlationshipSourcelon,
  statsReloncelonivelonr: StatsReloncelonivelonr) {

  privatelon[this] val scopelondStatsReloncelonivelonr =
    statsReloncelonivelonr.scopelon("fonsr_prelonfelontchelond_relonlationship_felonaturelons")

  privatelon[this] val relonquelonsts = scopelondStatsReloncelonivelonr.countelonr("relonquelonsts")

  privatelon[this] val vielonwelonrFollowsAuthorOfViolatingTwelonelont =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrFollowsAuthorOfViolatingTwelonelont.namelon).countelonr("relonquelonsts")

  privatelon[this] val vielonwelonrDoelonsNotFollowAuthorOfViolatingTwelonelont =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrDoelonsNotFollowAuthorOfViolatingTwelonelont.namelon).countelonr("relonquelonsts")

  delonf forNonFosnr(): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    relonquelonsts.incr()
    _.withConstantFelonaturelon(VielonwelonrFollowsAuthorOfViolatingTwelonelont, falselon)
      .withConstantFelonaturelon(VielonwelonrDoelonsNotFollowAuthorOfViolatingTwelonelont, falselon)
  }
  delonf forTwelonelontWithSafelontyLabelonlsAndAuthorId(
    safelontyLabelonls: Selonq[TwelonelontSafelontyLabelonl],
    authorId: Long,
    vielonwelonrId: Option[Long]
  ): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    relonquelonsts.incr()
    _.withFelonaturelon(
      VielonwelonrFollowsAuthorOfViolatingTwelonelont,
      vielonwelonrFollowsAuthorOfViolatingTwelonelont(safelontyLabelonls, authorId, vielonwelonrId))
      .withFelonaturelon(
        VielonwelonrDoelonsNotFollowAuthorOfViolatingTwelonelont,
        vielonwelonrDoelonsNotFollowAuthorOfViolatingTwelonelont(safelontyLabelonls, authorId, vielonwelonrId))
  }
  delonf vielonwelonrFollowsAuthorOfViolatingTwelonelont(
    safelontyLabelonls: Selonq[TwelonelontSafelontyLabelonl],
    authorId: UselonrId,
    vielonwelonrId: Option[UselonrId]
  ): Stitch[Boolelonan] = {
    if (safelontyLabelonls
        .map(ViolationLelonvelonl.fromTwelonelontSafelontyLabelonlOpt).collelonct {
          caselon Somelon(lelonvelonl) => lelonvelonl
        }.iselonmpty) {
      relonturn Stitch.Falselon
    }
    VielonwelonrVelonrbsAuthor(
      authorId,
      vielonwelonrId,
      uselonrRelonlationshipSourcelon.follows,
      vielonwelonrFollowsAuthorOfViolatingTwelonelont)
  }
  delonf vielonwelonrDoelonsNotFollowAuthorOfViolatingTwelonelont(
    safelontyLabelonls: Selonq[TwelonelontSafelontyLabelonl],
    authorId: UselonrId,
    vielonwelonrId: Option[UselonrId]
  ): Stitch[Boolelonan] = {
    if (safelontyLabelonls
        .map(ViolationLelonvelonl.fromTwelonelontSafelontyLabelonlOpt).collelonct {
          caselon Somelon(lelonvelonl) => lelonvelonl
        }.iselonmpty) {
      relonturn Stitch.Falselon
    }
    VielonwelonrVelonrbsAuthor(
      authorId,
      vielonwelonrId,
      uselonrRelonlationshipSourcelon.follows,
      vielonwelonrDoelonsNotFollowAuthorOfViolatingTwelonelont).map(following => !following)
  }

}
