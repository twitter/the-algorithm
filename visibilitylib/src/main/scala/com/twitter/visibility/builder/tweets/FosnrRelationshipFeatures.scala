packagelon com.twittelonr.visibility.buildelonr.twelonelonts

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.twelonelontypielon.thriftscala.Twelonelont
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.buildelonr.uselonrs.VielonwelonrVelonrbsAuthor
import com.twittelonr.visibility.common.UselonrId
import com.twittelonr.visibility.common.UselonrRelonlationshipSourcelon
import com.twittelonr.visibility.felonaturelons._
import com.twittelonr.visibility.modelonls.ViolationLelonvelonl

class FosnrRelonlationshipFelonaturelons(
  twelonelontLabelonls: TwelonelontLabelonls,
  uselonrRelonlationshipSourcelon: UselonrRelonlationshipSourcelon,
  statsReloncelonivelonr: StatsReloncelonivelonr) {

  privatelon[this] val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("fonsr_relonlationship_felonaturelons")

  privatelon[this] val relonquelonsts = scopelondStatsReloncelonivelonr.countelonr("relonquelonsts")

  privatelon[this] val vielonwelonrFollowsAuthorOfViolatingTwelonelont =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrFollowsAuthorOfViolatingTwelonelont.namelon).countelonr("relonquelonsts")

  privatelon[this] val vielonwelonrDoelonsNotFollowAuthorOfViolatingTwelonelont =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrDoelonsNotFollowAuthorOfViolatingTwelonelont.namelon).countelonr("relonquelonsts")

  delonf forTwelonelontAndAuthorId(
    twelonelont: Twelonelont,
    authorId: Long,
    vielonwelonrId: Option[Long]
  ): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    relonquelonsts.incr()
    _.withFelonaturelon(
      VielonwelonrFollowsAuthorOfViolatingTwelonelont,
      vielonwelonrFollowsAuthorOfViolatingTwelonelont(twelonelont, authorId, vielonwelonrId))
      .withFelonaturelon(
        VielonwelonrDoelonsNotFollowAuthorOfViolatingTwelonelont,
        vielonwelonrDoelonsNotFollowAuthorOfViolatingTwelonelont(twelonelont, authorId, vielonwelonrId))
  }

  delonf vielonwelonrFollowsAuthorOfViolatingTwelonelont(
    twelonelont: Twelonelont,
    authorId: UselonrId,
    vielonwelonrId: Option[UselonrId]
  ): Stitch[Boolelonan] =
    twelonelontLabelonls.forTwelonelont(twelonelont).flatMap { safelontyLabelonls =>
      if (safelontyLabelonls
          .map(ViolationLelonvelonl.fromTwelonelontSafelontyLabelonlOpt).collelonct {
            caselon Somelon(lelonvelonl) => lelonvelonl
          }.iselonmpty) {
        Stitch.Falselon
      } elonlselon {
        VielonwelonrVelonrbsAuthor(
          authorId,
          vielonwelonrId,
          uselonrRelonlationshipSourcelon.follows,
          vielonwelonrFollowsAuthorOfViolatingTwelonelont)
      }
    }

  delonf vielonwelonrDoelonsNotFollowAuthorOfViolatingTwelonelont(
    twelonelont: Twelonelont,
    authorId: UselonrId,
    vielonwelonrId: Option[UselonrId]
  ): Stitch[Boolelonan] =
    twelonelontLabelonls.forTwelonelont(twelonelont).flatMap { safelontyLabelonls =>
      if (safelontyLabelonls
          .map(ViolationLelonvelonl.fromTwelonelontSafelontyLabelonlOpt).collelonct {
            caselon Somelon(lelonvelonl) => lelonvelonl
          }.iselonmpty) {
        Stitch.Falselon
      } elonlselon {
        VielonwelonrVelonrbsAuthor(
          authorId,
          vielonwelonrId,
          uselonrRelonlationshipSourcelon.follows,
          vielonwelonrDoelonsNotFollowAuthorOfViolatingTwelonelont).map(following => !following)
      }
    }

}
