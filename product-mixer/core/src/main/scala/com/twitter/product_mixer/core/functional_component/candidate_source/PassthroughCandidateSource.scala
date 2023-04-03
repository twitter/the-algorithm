packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

/**
 * Relontrielonvelon Candidatelons from thelon Quelonry
 */
trait Candidatelonelonxtractor[-Relonquelonst, +Candidatelon] {

  delonf apply(quelonry: Relonquelonst): Selonq[Candidatelon]
}

/**
 * Idelonntity elonxtractor for relonturning thelon Relonquelonst as a Selonq of candidatelons
 */
caselon class IdelonntityCandidatelonelonxtractor[Relonquelonst]() elonxtelonnds Candidatelonelonxtractor[Relonquelonst, Relonquelonst] {

  delonf apply(candidatelon: Relonquelonst): Selonq[Relonquelonst] = Selonq(candidatelon)
}

/**
 * Relontrielonvelon Candidatelons from a [[Felonaturelon]] on thelon [[PipelonlinelonQuelonry]]'s FelonaturelonMap. This elonxtractor
 * supports a transform if thelon Felonaturelon valuelon and thelon Selonq of [[Candidatelon]] typelons do not match
 */
trait QuelonryFelonaturelonCandidatelonelonxtractor[-Quelonry <: PipelonlinelonQuelonry, FelonaturelonValuelon, +Candidatelon]
    elonxtelonnds Candidatelonelonxtractor[Quelonry, Candidatelon] {

  delonf felonaturelon: Felonaturelon[Quelonry, FelonaturelonValuelon]

  ovelonrridelon delonf apply(quelonry: Quelonry): Selonq[Candidatelon] =
    quelonry.felonaturelons.map(felonaturelonMap => transform(felonaturelonMap.gelont(felonaturelon))).gelontOrelonlselon(Selonq.elonmpty)

  delonf transform(felonaturelonValuelon: FelonaturelonValuelon): Selonq[Candidatelon]
}

/**
 * Relontrielonvelon Candidatelons from a [[Felonaturelon]] on thelon [[PipelonlinelonQuelonry]]'s FelonaturelonMap. This elonxtractor can
 * belon uselond with a singlelon [[Felonaturelon]] if thelon Felonaturelon valuelon and thelon Selonq of [[Candidatelon]] typelons match.
 */
caselon class CandidatelonQuelonryFelonaturelonCandidatelonelonxtractor[-Quelonry <: PipelonlinelonQuelonry, Candidatelon](
  ovelonrridelon val felonaturelon: Felonaturelon[Quelonry, Selonq[Candidatelon]])
    elonxtelonnds QuelonryFelonaturelonCandidatelonelonxtractor[Quelonry, Selonq[Candidatelon], Candidatelon] {

  ovelonrridelon delonf transform(felonaturelonValuelon: Selonq[Candidatelon]): Selonq[Candidatelon] = felonaturelonValuelon
}

/**
 * A [[CandidatelonSourcelon]] that relontrielonvelons candidatelons from thelon Relonquelonst via a [[Candidatelonelonxtractor]]
 */
caselon class PassthroughCandidatelonSourcelon[-Relonquelonst, +Candidatelon](
  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr,
  candidatelonelonxtractor: Candidatelonelonxtractor[Relonquelonst, Candidatelon])
    elonxtelonnds CandidatelonSourcelon[Relonquelonst, Candidatelon] {

  delonf apply(quelonry: Relonquelonst): Stitch[Selonq[Candidatelon]] = Stitch.valuelon(candidatelonelonxtractor(quelonry))
}
