packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.AllPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon.PartitionelondCandidatelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Drops all Candidatelons on thelon `relonmainingCandidatelons` sidelon which arelon in thelon [[pipelonlinelonScopelon]]
 *
 * This is typically uselond as a placelonholdelonr whelonn telonmplating out a nelonw pipelonlinelon or
 * as a simplelon filtelonr to drop candidatelons baselond only on thelon [[CandidatelonScopelon]]
 */
caselon class DropAllCandidatelons(ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon = AllPipelonlinelons)
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val PartitionelondCandidatelons(inScopelon, outOfScopelon) = pipelonlinelonScopelon.partition(relonmainingCandidatelons)

    SelonlelonctorRelonsult(relonmainingCandidatelons = outOfScopelon, relonsult = relonsult)
  }
}
