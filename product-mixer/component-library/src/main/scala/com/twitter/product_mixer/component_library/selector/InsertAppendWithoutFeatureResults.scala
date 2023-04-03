packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * A selonlelonctor that appelonnds all candidatelons missing a speloncific felonaturelon to thelon relonsults pool and kelonelonps
 * thelon relonst in thelon relonmaining candidatelons. This is uselonful for backfill scoring candidatelons without
 * a scorelon from a prelonvious scorelonr.
 * @param pipelonlinelonScopelon Thelon pipelonlinelon scopelon to chelonck
 * @param missingFelonaturelon Thelon missing felonaturelon to chelonck for.
 */
caselon class InselonrtAppelonndWithoutFelonaturelonRelonsults(
  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon,
  missingFelonaturelon: Felonaturelon[_, _])
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val (candidatelonsWithMissingFelonaturelon, candidatelonsWithFelonaturelon) = relonmainingCandidatelons.partition {
      candidatelon =>
        pipelonlinelonScopelon.contains(candidatelon) && !candidatelon.felonaturelons.gelontSuccelonssfulFelonaturelons
          .contains(missingFelonaturelon)
    }
    val updatelondRelonsults = relonsult ++ candidatelonsWithMissingFelonaturelon
    SelonlelonctorRelonsult(relonmainingCandidatelons = candidatelonsWithFelonaturelon, relonsult = updatelondRelonsults)
  }
}
