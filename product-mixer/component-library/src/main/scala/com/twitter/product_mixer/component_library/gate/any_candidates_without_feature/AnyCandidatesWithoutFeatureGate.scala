packagelon com.twittelonr.product_mixelonr.componelonnt_library.gatelon.any_candidatelons_without_felonaturelon

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.QuelonryAndCandidatelonGatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.GatelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

/**
 * A gatelon that elonnablelons a componelonnt only if any candidatelons arelon missing a speloncific felonaturelon.
 * You can relonstrict which candidatelons to chelonck with thelon scopelon paramelontelonr.
 * This is most commonly uselond to do backfill scoring, whelonrelon you can havelon onelon Scoring Pipelonlinelon that
 * might relonturn a scorelon felonaturelon "FelonaturelonA" and anothelonr selonquelonntial pipelonlinelon that you only want to run
 * if thelon prelonvious scoring pipelonlinelon fails to hydratelon for all candidatelons.
 * @param idelonntifielonr Uniquelon idelonntifielonr for this gatelon. Typically, AnyCandidatelonsWithout{YourFelonaturelon}.
 * @param scopelon A [[CandidatelonScopelon]] to speloncify which candidatelons to chelonck.
 * @param missingFelonaturelon Thelon felonaturelon that should belon missing for any of thelon candidatelons for this gatelon to continuelon
 */
caselon class AnyCandidatelonsWithoutFelonaturelonGatelon(
  ovelonrridelon val idelonntifielonr: GatelonIdelonntifielonr,
  scopelon: CandidatelonScopelon,
  missingFelonaturelon: Felonaturelon[_, _])
    elonxtelonnds QuelonryAndCandidatelonGatelon[PipelonlinelonQuelonry] {

  ovelonrridelon delonf shouldContinuelon(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithDelontails]
  ): Stitch[Boolelonan] =
    Stitch.valuelon(scopelon.partition(candidatelons).candidatelonsInScopelon.elonxists { candidatelonWithDelontails =>
      !candidatelonWithDelontails.felonaturelons.gelontSuccelonssfulFelonaturelons.contains(missingFelonaturelon)
    })
}
