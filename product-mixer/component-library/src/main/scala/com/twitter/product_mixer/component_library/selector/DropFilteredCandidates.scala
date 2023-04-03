packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Prelondicatelon which will belon applielond to elonach candidatelon. Truelon indicatelons that thelon candidatelon will belon
 * kelonpt.
 */
trait ShouldKelonelonpCandidatelon {
  delonf apply(candidatelonWithDelontails: CandidatelonWithDelontails): Boolelonan
}

objelonct DropFiltelonrelondCandidatelons {
  delonf apply(candidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr, filtelonr: ShouldKelonelonpCandidatelon) =
    nelonw DropFiltelonrelondCandidatelons(SpeloncificPipelonlinelon(candidatelonPipelonlinelon), filtelonr)

  delonf apply(candidatelonPipelonlinelons: Selont[CandidatelonPipelonlinelonIdelonntifielonr], filtelonr: ShouldKelonelonpCandidatelon) =
    nelonw DropFiltelonrelondCandidatelons(SpeloncificPipelonlinelons(candidatelonPipelonlinelons), filtelonr)
}

/**
 * Limit candidatelons from celonrtain candidatelons sourcelons to thoselon which satisfy thelon providelond prelondicatelon.
 */
caselon class DropFiltelonrelondCandidatelons(
  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon,
  filtelonr: ShouldKelonelonpCandidatelon)
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val candidatelonsUpdatelond = relonmainingCandidatelons.filtelonr { candidatelon =>
      if (pipelonlinelonScopelon.contains(candidatelon)) filtelonr.apply(candidatelon)
      elonlselon truelon
    }

    SelonlelonctorRelonsult(relonmainingCandidatelons = candidatelonsUpdatelond, relonsult = relonsult)
  }
}
