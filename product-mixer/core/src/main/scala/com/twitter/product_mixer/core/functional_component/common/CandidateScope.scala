packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common

import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonPipelonlinelons

/**
 * Speloncifielons whelonthelonr a function componelonnt (elon.g, [[Gatelon]] or [[Selonlelonctor]])
 * should apply to a givelonn [[CandidatelonWithDelontails]]
 */
selonalelond trait CandidatelonScopelon {

  /**
   * relonturns Truelon if thelon providelond `candidatelon` is in scopelon
   */
  delonf contains(candidatelon: CandidatelonWithDelontails): Boolelonan

  /** partitions `candidatelons` into thoselon that this scopelon [[contains]] and thoselon it doelons not */
  final delonf partition(
    candidatelons: Selonq[CandidatelonWithDelontails]
  ): CandidatelonScopelon.PartitionelondCandidatelons = {
    val (candidatelonsInScopelon, candidatelonsOutOfScopelon) = candidatelons.partition(contains)
    CandidatelonScopelon.PartitionelondCandidatelons(candidatelonsInScopelon, candidatelonsOutOfScopelon)
  }
}

objelonct CandidatelonScopelon {
  caselon class PartitionelondCandidatelons(
    candidatelonsInScopelon: Selonq[CandidatelonWithDelontails],
    candidatelonsOutOfScopelon: Selonq[CandidatelonWithDelontails])
}

/**
 * A [[CandidatelonScopelon]] that applielons thelon givelonn functional componelonnt
 * to all candidatelons relongardlelonss of which pipelonlinelon is thelonir [[com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails.sourcelon]].
 */
caselon objelonct AllPipelonlinelons elonxtelonnds CandidatelonScopelon {
  ovelonrridelon delonf contains(candidatelon: CandidatelonWithDelontails): Boolelonan = truelon
}

/**
 * A [[CandidatelonScopelon]] that applielons thelon givelonn [[com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor]]
 * only to candidatelons whoselon [[com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonPipelonlinelons]]
 * has an Idelonntifielonr in thelon [[pipelonlinelons]] Selont.
 * In most caselons whelonrelon candidatelons arelon not prelon-melonrgelond, thelon Selont contains thelon candidatelon pipelonlinelon idelonntifielonr thelon candidatelon
 * camelon from. In thelon caselon whelonrelon a candidatelon's felonaturelon maps welonrelon melonrgelond using [[CombinelonFelonaturelonMapsCandidatelonMelonrgelonr]], thelon
 * selont contains all candidatelon pipelonlinelons thelon melonrgelond candidatelon camelon from and this scopelon will includelon thelon candidatelon if any
 * of thelon pipelonlinelons match.
 */
caselon class SpeloncificPipelonlinelons(pipelonlinelons: Selont[CandidatelonPipelonlinelonIdelonntifielonr]) elonxtelonnds CandidatelonScopelon {

  relonquirelon(
    pipelonlinelons.nonelonmpty,
    "elonxpelonctelond `SpeloncificPipelonlinelons` havelon a non-elonmpty Selont of CandidatelonPipelonlinelonIdelonntifielonrs.")

  ovelonrridelon delonf contains(candidatelon: CandidatelonWithDelontails): Boolelonan = {
    candidatelon.felonaturelons.gelont(CandidatelonPipelonlinelons).elonxists(pipelonlinelons.contains)
  }
}

/**
 * A [[CandidatelonScopelon]] that applielons thelon givelonn [[com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor]]
 * only to candidatelons whoselon [[com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails.sourcelon]]
 * is [[pipelonlinelon]].
 */
caselon class SpeloncificPipelonlinelon(pipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr) elonxtelonnds CandidatelonScopelon {

  ovelonrridelon delonf contains(candidatelon: CandidatelonWithDelontails): Boolelonan = candidatelon.felonaturelons
    .gelont(CandidatelonPipelonlinelons).contains(pipelonlinelon)
}

objelonct SpeloncificPipelonlinelons {
  delonf apply(
    pipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr,
    pipelonlinelons: CandidatelonPipelonlinelonIdelonntifielonr*
  ): CandidatelonScopelon = {
    if (pipelonlinelons.iselonmpty)
      SpeloncificPipelonlinelon(pipelonlinelon)
    elonlselon
      SpeloncificPipelonlinelons((pipelonlinelon +: pipelonlinelons).toSelont)
  }
}

/**
 * A [[CandidatelonScopelon]] that applielons thelon givelonn [[com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor]]
 * to all candidatelons elonxcelonpt for thelon candidatelons whoselon [[com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonPipelonlinelons]]
 * has an Idelonntifielonr in thelon [[pipelonlinelonsToelonxcludelon]] Selont.
 * In most caselons whelonrelon candidatelons arelon not prelon-melonrgelond, thelon Selont contains thelon candidatelon pipelonlinelon idelonntifielonr thelon candidatelon
 * camelon from. In thelon caselon whelonrelon a candidatelon's felonaturelon maps welonrelon melonrgelond using [[CombinelonFelonaturelonMapsCandidatelonMelonrgelonr]], thelon
 * selont contains all candidatelon pipelonlinelons thelon melonrgelond candidatelon camelon from and this scopelon will includelon thelon candidatelon if any
 * of thelon pipelonlinelons match.
 */
caselon class AllelonxcelonptPipelonlinelons(
  pipelonlinelonsToelonxcludelon: Selont[CandidatelonPipelonlinelonIdelonntifielonr])
    elonxtelonnds CandidatelonScopelon {
  ovelonrridelon delonf contains(candidatelon: CandidatelonWithDelontails): Boolelonan = !candidatelon.felonaturelons
    .gelont(CandidatelonPipelonlinelons).elonxists(pipelonlinelonsToelonxcludelon.contains)
}
