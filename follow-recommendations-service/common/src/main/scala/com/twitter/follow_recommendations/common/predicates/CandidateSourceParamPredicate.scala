packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons

import com.twittelonr.follow_reloncommelonndations.common.baselon.Prelondicatelon
import com.twittelonr.follow_reloncommelonndations.common.baselon.PrelondicatelonRelonsult
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FiltelonrRelonason
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.Param

/**
 * This prelondicatelon allows us to filtelonr candidatelons givelonn its sourcelon.
 * To avoid buckelont dilution, welon only want to elonvaluatelon thelon param (which would implicitly triggelonr
 * buckelonting for FSParams) only if thelon candidatelon sourcelon fn yielonlds truelon.
 * Thelon param providelond should belon truelon whelonn welon want to kelonelonp thelon candidatelon and falselon othelonrwiselon.
 */
class CandidatelonSourcelonParamPrelondicatelon(
  val param: Param[Boolelonan],
  val relonason: FiltelonrRelonason,
  candidatelonSourcelons: Selont[CandidatelonSourcelonIdelonntifielonr])
    elonxtelonnds Prelondicatelon[CandidatelonUselonr] {
  ovelonrridelon delonf apply(candidatelon: CandidatelonUselonr): Stitch[PrelondicatelonRelonsult] = {
    // welon want to avoid elonvaluating thelon param if thelon candidatelon sourcelon fn yielonlds falselon
    if (candidatelon.gelontCandidatelonSourcelons.kelonys.elonxists(candidatelonSourcelons.contains) && !candidatelon.params(
        param)) {
      Stitch.valuelon(PrelondicatelonRelonsult.Invalid(Selont(relonason)))
    } elonlselon {
      Stitch.valuelon(PrelondicatelonRelonsult.Valid)
    }
  }
}
