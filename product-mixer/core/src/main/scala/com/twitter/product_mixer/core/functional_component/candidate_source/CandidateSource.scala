packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon

import com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch

selonalelond trait BaselonCandidatelonSourcelon[-Relonquelonst, +Candidatelon] elonxtelonnds Componelonnt {

  /** @selonelon [[CandidatelonSourcelonIdelonntifielonr]] */
  val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr
}

/**
 * A [[CandidatelonSourcelon]] relonturns a Selonq of ''potelonntial'' contelonnt
 *
 * @notelon [[CandidatelonSourcelon]]s that relonturn a singlelon valuelon nelonelond to transform
 *       it into a Selonq, elonithelonr by doing `Selonq(valuelon)` or elonxtracting
 *       candidatelons from thelon valuelon.
 *
 * @tparam Relonquelonst argumelonnts to gelont thelon potelonntial contelonnt
 * @tparam Candidatelon thelon potelonntial contelonnt
 */
trait CandidatelonSourcelon[-Relonquelonst, +Candidatelon] elonxtelonnds BaselonCandidatelonSourcelon[Relonquelonst, Candidatelon] {

  /** relonturns a Selonq of ''potelonntial'' contelonnt */
  delonf apply(relonquelonst: Relonquelonst): Stitch[Selonq[Candidatelon]]
}

/**
 * A [[CandidatelonSourcelonWithelonxtractelondFelonaturelons]] relonturns a relonsult containing both a Selonq of
 * ''potelonntial'' candidatelons as welonll as an elonxtractelond felonaturelon map that will latelonr belon appelonndelond
 * to thelon pipelonlinelon's [[com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry]] felonaturelon map. This is
 * uselonful for candidatelon sourcelons that relonturn felonaturelons that might belon uselonful latelonr on without nelonelonding
 * to relon-hydratelon thelonm.
 *
 * @notelon [[CandidatelonSourcelon]]s that relonturn a singlelon valuelon nelonelond to transform
 *       it into a Selonq, elonithelonr by doing `Selonq(valuelon)` or elonxtracting
 *       candidatelons from thelon valuelon.
 *
 * @tparam Relonquelonst argumelonnts to gelont thelon potelonntial contelonnt
 * @tparam Candidatelon thelon potelonntial contelonnt
 */
trait CandidatelonSourcelonWithelonxtractelondFelonaturelons[-Relonquelonst, +Candidatelon]
    elonxtelonnds BaselonCandidatelonSourcelon[Relonquelonst, Candidatelon] {

  /** relonturns a relonsult containing a selonq of ''potelonntial'' contelonnt and elonxtractelond felonaturelons
   * from thelon candidatelon sourcelon.
   * */
  delonf apply(relonquelonst: Relonquelonst): Stitch[CandidatelonsWithSourcelonFelonaturelons[Candidatelon]]
}
