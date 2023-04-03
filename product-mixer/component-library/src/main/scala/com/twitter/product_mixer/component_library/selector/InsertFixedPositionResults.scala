packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.Param

objelonct InselonrtFixelondPositionRelonsults {
  delonf apply(
    candidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr,
    positionParam: Param[Int],
  ): InselonrtFixelondPositionRelonsults =
    nelonw InselonrtFixelondPositionRelonsults(SpeloncificPipelonlinelon(candidatelonPipelonlinelon), positionParam)

  delonf apply(
    candidatelonPipelonlinelons: Selont[CandidatelonPipelonlinelonIdelonntifielonr],
    positionParam: Param[Int]
  ): InselonrtFixelondPositionRelonsults =
    nelonw InselonrtFixelondPositionRelonsults(SpeloncificPipelonlinelons(candidatelonPipelonlinelons), positionParam)
}

/**
 * Inselonrt all candidatelons in a pipelonlinelon scopelon at a 0-indelonxelond fixelond position. If thelon currelonnt
 * relonsults arelon a shortelonr lelonngth than thelon relonquelonstelond position, thelonn thelon candidatelons will belon appelonndelond
 * to thelon relonsults.
 */
caselon class InselonrtFixelondPositionRelonsults(
  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon,
  positionParam: Param[Int])
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = InselonrtSelonlelonctor.inselonrtIntoRelonsultsAtPosition(
    position = quelonry.params(positionParam),
    pipelonlinelonScopelon = pipelonlinelonScopelon,
    relonmainingCandidatelons = relonmainingCandidatelons,
    relonsult = relonsult)
}
