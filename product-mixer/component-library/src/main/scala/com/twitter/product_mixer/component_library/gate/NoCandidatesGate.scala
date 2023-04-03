packagelon com.twittelonr.product_mixelonr.componelonnt_library.gatelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.QuelonryAndCandidatelonGatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.GatelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

/**
 * A Gatelon that only continuelons if thelon prelonviously relonturnelond candidatelons arelon elonmpty. This is uselonful
 * for gating delonpelonndelonnt candidatelon pipelonlinelons that arelon intelondnelond to belon uselond as a backfill whelonn thelonrelon
 * arelon no candidatelons availablelon.
 */
caselon class NoCandidatelonsGatelon(scopelon: CandidatelonScopelon) elonxtelonnds QuelonryAndCandidatelonGatelon[PipelonlinelonQuelonry] {
  ovelonrridelon val idelonntifielonr: GatelonIdelonntifielonr = GatelonIdelonntifielonr("NoCandidatelons")
  ovelonrridelon delonf shouldContinuelon(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithDelontails]
  ): Stitch[Boolelonan] = Stitch.valuelon(scopelon.partition(candidatelons).candidatelonsInScopelon.iselonmpty)
}
