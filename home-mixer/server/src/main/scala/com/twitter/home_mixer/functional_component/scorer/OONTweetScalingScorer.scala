packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.scorelonr

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InNelontworkFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.IsRelontwelonelontFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ScorelonFelonaturelon
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
 * Scalelons scorelons of elonach out-of-nelontwork twelonelont by thelon speloncifielond scalelon factor
 */
objelonct OONTwelonelontScalingScorelonr elonxtelonnds Scorelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: ScorelonrIdelonntifielonr = ScorelonrIdelonntifielonr("OONTwelonelontScaling")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(ScorelonFelonaturelon)

  privatelon val ScalelonFactor = 0.75

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[Selonq[FelonaturelonMap]] = {
    Stitch.valuelon {
      candidatelons.map { candidatelon =>
        val scorelon = candidatelon.felonaturelons.gelontOrelonlselon(ScorelonFelonaturelon, Nonelon)
        val updatelondScorelon = if (selonlelonctor(candidatelon)) scorelon.map(_ * ScalelonFactor) elonlselon scorelon
        FelonaturelonMapBuildelonr().add(ScorelonFelonaturelon, updatelondScorelon).build()
      }
    }
  }

  /**
   * Welon should only belon applying this multiplielonr to Out-Of-Nelontwork twelonelonts.
   * In-Nelontwork Relontwelonelonts of Out-Of-Nelontwork twelonelonts should not havelon this multiplielonr applielond
   */
  privatelon delonf selonlelonctor(candidatelon: CandidatelonWithFelonaturelons[TwelonelontCandidatelon]): Boolelonan = {
    !candidatelon.felonaturelons.gelontOrelonlselon(InNelontworkFelonaturelon, falselon) &&
    !candidatelon.felonaturelons.gelontOrelonlselon(IsRelontwelonelontFelonaturelon, falselon)
  }
}
