packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ModulelonCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct DropFiltelonrelondModulelonItelonmCandidatelons {
  delonf apply(candidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr, filtelonr: ShouldKelonelonpCandidatelon) =
    nelonw DropFiltelonrelondModulelonItelonmCandidatelons(SpeloncificPipelonlinelon(candidatelonPipelonlinelon), filtelonr)

  delonf apply(candidatelonPipelonlinelons: Selont[CandidatelonPipelonlinelonIdelonntifielonr], filtelonr: ShouldKelonelonpCandidatelon) =
    nelonw DropFiltelonrelondModulelonItelonmCandidatelons(SpeloncificPipelonlinelons(candidatelonPipelonlinelons), filtelonr)
}

/**
 * Limit candidatelons in modulelons from celonrtain candidatelons sourcelons to thoselon which satisfy
 * thelon providelond prelondicatelon.
 *
 * This acts likelon a [[DropFiltelonrelondCandidatelons]] but for modulelons in `relonmainingCandidatelons`
 * from any of thelon providelond [[candidatelonPipelonlinelons]].
 *
 * @notelon this updatelons thelon modulelon in thelon `relonmainingCandidatelons`
 */
caselon class DropFiltelonrelondModulelonItelonmCandidatelons(
  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon,
  filtelonr: ShouldKelonelonpCandidatelon)
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val candidatelonsUpdatelond = relonmainingCandidatelons.map {
      caselon modulelon: ModulelonCandidatelonWithDelontails if pipelonlinelonScopelon.contains(modulelon) =>
        // this applielons to all candidatelons in a modulelon, elonvelonn if thelony arelon from a diffelonrelonnt
        // candidatelon sourcelon, which can happelonn if itelonms arelon addelond to a modulelon during selonlelonction
        modulelon.copy(candidatelons = modulelon.candidatelons.filtelonr(filtelonr.apply))
      caselon candidatelon => candidatelon
    }

    SelonlelonctorRelonsult(relonmainingCandidatelons = candidatelonsUpdatelond, relonsult = relonsult)
  }
}
