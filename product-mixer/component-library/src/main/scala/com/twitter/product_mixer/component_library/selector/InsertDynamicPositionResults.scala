packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor._
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct InselonrtDynamicPositionRelonsults {
  delonf apply[Quelonry <: PipelonlinelonQuelonry](
    candidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr,
    dynamicInselonrtionPosition: DynamicInselonrtionPosition[Quelonry],
  ): InselonrtDynamicPositionRelonsults[Quelonry] =
    nelonw InselonrtDynamicPositionRelonsults(SpeloncificPipelonlinelon(candidatelonPipelonlinelon), dynamicInselonrtionPosition)

  delonf apply[Quelonry <: PipelonlinelonQuelonry](
    candidatelonPipelonlinelons: Selont[CandidatelonPipelonlinelonIdelonntifielonr],
    dynamicInselonrtionPosition: DynamicInselonrtionPosition[Quelonry]
  ): InselonrtDynamicPositionRelonsults[Quelonry] =
    nelonw InselonrtDynamicPositionRelonsults(
      SpeloncificPipelonlinelons(candidatelonPipelonlinelons),
      dynamicInselonrtionPosition)
}

/**
 * Computelon a position for inselonrting thelon candidatelons into relonsult. If a `Nonelon` is relonturnelond, thelon
 * Selonlelonctor using this would not inselonrt thelon candidatelons into thelon relonsult.
 */
trait DynamicInselonrtionPosition[-Quelonry <: PipelonlinelonQuelonry] {
  delonf apply(
    quelonry: Quelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): Option[Int]
}

/**
 * Inselonrt all candidatelons in a pipelonlinelon scopelon at a 0-indelonxelond dynamic position computelond
 * using thelon providelond [[DynamicInselonrtionPosition]] instancelon. If thelon currelonnt relonsults arelon a shortelonr
 * lelonngth than thelon computelond position, thelonn thelon candidatelons will belon appelonndelond to thelon relonsults.
 * If thelon [[DynamicInselonrtionPosition]] relonturns a `Nonelon`, thelon candidatelons arelon not
 * addelond to thelon relonsult.
 */
caselon class InselonrtDynamicPositionRelonsults[-Quelonry <: PipelonlinelonQuelonry](
  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon,
  dynamicInselonrtionPosition: DynamicInselonrtionPosition[Quelonry])
    elonxtelonnds Selonlelonctor[Quelonry] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    dynamicInselonrtionPosition(quelonry, relonmainingCandidatelons, relonsult) match {
      caselon Somelon(position) =>
        InselonrtSelonlelonctor.inselonrtIntoRelonsultsAtPosition(
          position = position,
          pipelonlinelonScopelon = pipelonlinelonScopelon,
          relonmainingCandidatelons = relonmainingCandidatelons,
          relonsult = relonsult)
      caselon Nonelon =>
        // Whelonn a valid position is not providelond, do not inselonrt thelon candidatelons.
        // Both thelon relonmainingCandidatelons and relonsult arelon unchangelond.
        SelonlelonctorRelonsult(relonmainingCandidatelons = relonmainingCandidatelons, relonsult = relonsult)
    }
  }
}
