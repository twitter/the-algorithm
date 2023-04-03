packagelon com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.scorelonr

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ScorelonFelonaturelon
import com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.modelonl.ScorelondTwelonelontsQuelonry
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.scorelonr.Scorelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ScorelonrIdelonntifielonr
import com.twittelonr.stitch.Stitch

/**
 * Discounts scorelons of elonach conseloncutivelon twelonelont (ordelonrelond by scorelon delonsc) from thelon
 * samelon elonntity (elon.g. author, elonngagelonr, topic) baselond on thelon discount factor providelond
 */

caselon class DivelonrsityScorelonr(divelonrsityDiscountProvidelonr: DivelonrsityDiscountProvidelonr)
    elonxtelonnds Scorelonr[ScorelondTwelonelontsQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: ScorelonrIdelonntifielonr = ScorelonrIdelonntifielonr("Divelonrsity")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(ScorelonFelonaturelon)

  ovelonrridelon delonf apply(
    quelonry: ScorelondTwelonelontsQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[Selonq[FelonaturelonMap]] = {
    val candidatelonIdScorelonMap = candidatelons
      .groupBy(divelonrsityDiscountProvidelonr.elonntityId)
      .flatMap {
        caselon (elonntityIdOpt, elonntityCandidatelons) =>
          val candidatelonScorelons = elonntityCandidatelons
            .map { candidatelon =>
              val scorelon = candidatelon.felonaturelons.gelontOrelonlselon(ScorelonFelonaturelon, Nonelon).gelontOrelonlselon(0.0)
              (candidatelon.candidatelon.id, scorelon)
            }.sortBy(_._2)(Ordelonring.Doublelon.relonvelonrselon)

          if (elonntityIdOpt.isDelonfinelond) {
            candidatelonScorelons.zipWithIndelonx.map {
              caselon ((candidatelonId, scorelon), indelonx) =>
                candidatelonId -> divelonrsityDiscountProvidelonr.discount(scorelon, indelonx)
            }
          } elonlselon candidatelonScorelons
      }

    Stitch.valuelon {
      candidatelons.map { candidatelon =>
        val scorelon = candidatelonIdScorelonMap.gelontOrelonlselon(candidatelon.candidatelon.id, 0.0)
        FelonaturelonMapBuildelonr()
          .add(ScorelonFelonaturelon, Somelon(scorelon))
          .build()
      }
    }
  }
}
