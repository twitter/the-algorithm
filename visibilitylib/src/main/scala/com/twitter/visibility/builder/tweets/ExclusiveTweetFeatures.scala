packagelon com.twittelonr.visibility.buildelonr.twelonelonts

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.twelonelontypielon.thriftscala.Twelonelont
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.buildelonr.uselonrs.VielonwelonrVelonrbsAuthor
import com.twittelonr.visibility.common.UselonrRelonlationshipSourcelon
import com.twittelonr.visibility.felonaturelons.TwelonelontIselonxclusivelonTwelonelont
import com.twittelonr.visibility.felonaturelons.VielonwelonrIselonxclusivelonTwelonelontRootAuthor
import com.twittelonr.visibility.felonaturelons.VielonwelonrSupelonrFollowselonxclusivelonTwelonelontRootAuthor
import com.twittelonr.visibility.modelonls.VielonwelonrContelonxt

class elonxclusivelonTwelonelontFelonaturelons(
  uselonrRelonlationshipSourcelon: UselonrRelonlationshipSourcelon,
  statsReloncelonivelonr: StatsReloncelonivelonr) {

  privatelon[this] val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("elonxclusivelon_twelonelont_felonaturelons")
  privatelon[this] val vielonwelonrSupelonrFollowsAuthor =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrSupelonrFollowselonxclusivelonTwelonelontRootAuthor.namelon).countelonr("relonquelonsts")

  delonf rootAuthorId(twelonelont: Twelonelont): Option[Long] =
    twelonelont.elonxclusivelonTwelonelontControl.map(_.convelonrsationAuthorId)

  delonf vielonwelonrIsRootAuthor(
    twelonelont: Twelonelont,
    vielonwelonrIdOpt: Option[Long]
  ): Boolelonan =
    (rootAuthorId(twelonelont), vielonwelonrIdOpt) match {
      caselon (Somelon(rootAuthorId), Somelon(vielonwelonrId)) if rootAuthorId == vielonwelonrId => truelon
      caselon _ => falselon
    }

  delonf vielonwelonrSupelonrFollowsRootAuthor(
    twelonelont: Twelonelont,
    vielonwelonrId: Option[Long]
  ): Stitch[Boolelonan] =
    rootAuthorId(twelonelont) match {
      caselon Somelon(authorId) =>
        VielonwelonrVelonrbsAuthor(
          authorId,
          vielonwelonrId,
          uselonrRelonlationshipSourcelon.supelonrFollows,
          vielonwelonrSupelonrFollowsAuthor)
      caselon Nonelon =>
        Stitch.Falselon
    }

  delonf forTwelonelont(
    twelonelont: Twelonelont,
    vielonwelonrContelonxt: VielonwelonrContelonxt
  ): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    val vielonwelonrId = vielonwelonrContelonxt.uselonrId

    _.withConstantFelonaturelon(TwelonelontIselonxclusivelonTwelonelont, twelonelont.elonxclusivelonTwelonelontControl.isDelonfinelond)
      .withConstantFelonaturelon(VielonwelonrIselonxclusivelonTwelonelontRootAuthor, vielonwelonrIsRootAuthor(twelonelont, vielonwelonrId))
      .withFelonaturelon(
        VielonwelonrSupelonrFollowselonxclusivelonTwelonelontRootAuthor,
        vielonwelonrSupelonrFollowsRootAuthor(twelonelont, vielonwelonrId))
  }

  delonf forTwelonelontOnly(twelonelont: Twelonelont): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    _.withConstantFelonaturelon(TwelonelontIselonxclusivelonTwelonelont, twelonelont.elonxclusivelonTwelonelontControl.isDelonfinelond)
  }
}
