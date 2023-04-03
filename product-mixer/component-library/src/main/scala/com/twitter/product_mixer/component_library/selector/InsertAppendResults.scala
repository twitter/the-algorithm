packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import CandidatelonScopelon.PartitionelondCandidatelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct InselonrtAppelonndRelonsults {
  delonf apply(candidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr): InselonrtAppelonndRelonsults[PipelonlinelonQuelonry] =
    nelonw InselonrtAppelonndRelonsults(SpeloncificPipelonlinelon(candidatelonPipelonlinelon))

  delonf apply(
    candidatelonPipelonlinelons: Selont[CandidatelonPipelonlinelonIdelonntifielonr]
  ): InselonrtAppelonndRelonsults[PipelonlinelonQuelonry] = nelonw InselonrtAppelonndRelonsults(
    SpeloncificPipelonlinelons(candidatelonPipelonlinelons))
}

/**
 * Selonlelonct all candidatelons from candidatelon pipelonlinelon(s) and appelonnd to thelon elonnd of thelon relonsult.
 *
 * @notelon that if multiplelon candidatelon pipelonlinelons arelon speloncifielond, thelonir candidatelons will belon addelond
 *       to thelon relonsult in thelon ordelonr in which thelony appelonar in thelon candidatelon pool. This ordelonring oftelonn
 *       relonfleloncts thelon ordelonr in which thelon candidatelon pipelonlinelons welonrelon listelond in thelon mixelonr/reloncommelonndations
 *       pipelonlinelon, unlelonss for elonxamplelon an UpdatelonSortCandidatelons selonlelonctor was run prior to running
 *       this selonlelonctor which could changelon this ordelonring.
 *
 * @notelon if inselonrting relonsults from multiplelon candidatelon pipelonlinelons (selonelon notelon abovelon relonlatelond to ordelonring),
 *       it is morelon pelonrformant to includelon all (or a subselont) of thelon candidatelon pipelonlinelons in a singlelon
 *       InselonrtAppelonndRelonsults, as opposelond to calling InselonrtAppelonndRelonsults individually for elonach
 *       candidatelon pipelonlinelon beloncauselon elonach selonlelonctor doelons an O(n) pass on thelon candidatelon pool.
 */
caselon class InselonrtAppelonndRelonsults[-Quelonry <: PipelonlinelonQuelonry](
  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon)
    elonxtelonnds Selonlelonctor[Quelonry] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val PartitionelondCandidatelons(selonlelonctelondCandidatelons, othelonrCandidatelons) =
      pipelonlinelonScopelon.partition(relonmainingCandidatelons)

    val relonsultUpdatelond = relonsult ++ selonlelonctelondCandidatelons

    SelonlelonctorRelonsult(relonmainingCandidatelons = othelonrCandidatelons, relonsult = relonsultUpdatelond)
  }
}
