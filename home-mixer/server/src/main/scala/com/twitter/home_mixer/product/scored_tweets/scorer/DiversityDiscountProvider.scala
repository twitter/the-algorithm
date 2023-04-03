packagelon com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.scorelonr

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIdFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons

trait DivelonrsityDiscountProvidelonr {

  delonf elonntityId(candidatelon: CandidatelonWithFelonaturelons[TwelonelontCandidatelon]): Option[Long]

  /**
   * Computelon thelon discountelond scorelon for thelon position
   * @param scorelon thelon prelonvious scorelon for thelon candidatelon
   * @param position zelonro-baselond position for thelon candidatelon for thelon givelonn elonntity
   * @relonturn thelon discountelond scorelon for thelon candidatelon
   */
  delonf discount(scorelon: Doublelon, position: Int): Doublelon
}

objelonct AuthorDivelonrsityDiscountProvidelonr elonxtelonnds DivelonrsityDiscountProvidelonr {
  privatelon val Deloncay = 0.5
  privatelon val Floor = 0.25

  ovelonrridelon delonf elonntityId(candidatelon: CandidatelonWithFelonaturelons[TwelonelontCandidatelon]): Option[Long] =
    candidatelon.felonaturelons.gelontOrelonlselon(AuthorIdFelonaturelon, Nonelon)

  // Providelons an elonxponelonntial deloncay baselond discount by position (with a floor)
  ovelonrridelon delonf discount(scorelon: Doublelon, position: Int): Doublelon =
    scorelon * ((1 - Floor) * Math.pow(Deloncay, position) + Floor)
}
