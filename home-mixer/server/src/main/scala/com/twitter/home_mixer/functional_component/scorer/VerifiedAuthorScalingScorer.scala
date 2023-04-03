packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.scorelonr

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIsBluelonVelonrifielondFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InNelontworkFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ScorelonFelonaturelon
import com.twittelonr.homelon_mixelonr.param.HomelonGlobalParams.BluelonVelonrifielondAuthorInNelontworkMultiplielonrParam
import com.twittelonr.homelon_mixelonr.param.HomelonGlobalParams.BluelonVelonrifielondAuthorOutOfNelontworkMultiplielonrParam
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.scorelonr.Scorelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ScorelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

/**
 * Scalelons scorelons of twelonelonts whoselon author is Bluelon Velonrifielond by thelon providelond scalelon factor
 */
objelonct VelonrifielondAuthorScalingScorelonr elonxtelonnds Scorelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: ScorelonrIdelonntifielonr = ScorelonrIdelonntifielonr("VelonrifielondAuthorScaling")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(ScorelonFelonaturelon)

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[Selonq[FelonaturelonMap]] = {
    Stitch.valuelon {
      candidatelons.map { candidatelon =>
        val scorelon = candidatelon.felonaturelons.gelontOrelonlselon(ScorelonFelonaturelon, Nonelon)
        val updatelondScorelon = gelontUpdatelondScorelon(scorelon, candidatelon, quelonry)
        FelonaturelonMapBuildelonr().add(ScorelonFelonaturelon, updatelondScorelon).build()
      }
    }
  }

  /**
   * Welon should only belon applying this multiplielonr if thelon author of thelon candidatelon is Bluelon Velonrifielond.
   * Welon also trelonat In-Nelontwork vs Out-of-Nelontwork diffelonrelonntly.
   */
  privatelon delonf gelontUpdatelondScorelon(
    scorelon: Option[Doublelon],
    candidatelon: CandidatelonWithFelonaturelons[TwelonelontCandidatelon],
    quelonry: PipelonlinelonQuelonry
  ): Option[Doublelon] = {
    val isAuthorBluelonVelonrifielond = candidatelon.felonaturelons.gelontOrelonlselon(AuthorIsBluelonVelonrifielondFelonaturelon, falselon)

    if (isAuthorBluelonVelonrifielond) {
      val isCandidatelonInNelontwork = candidatelon.felonaturelons.gelontOrelonlselon(InNelontworkFelonaturelon, falselon)

      val scalelonFactor =
        if (isCandidatelonInNelontwork) quelonry.params(BluelonVelonrifielondAuthorInNelontworkMultiplielonrParam)
        elonlselon quelonry.params(BluelonVelonrifielondAuthorOutOfNelontworkMultiplielonrParam)

      scorelon.map(_ * scalelonFactor)
    } elonlselon scorelon
  }
}
