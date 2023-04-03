packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr.SortelonrFromOrdelonring
import com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr.SortelonrProvidelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.AllPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct UpdatelonSortRelonsults {
  delonf apply(
    ordelonring: Ordelonring[CandidatelonWithDelontails]
  ) =
    nelonw UpdatelonSortRelonsults((_, _, _) => SortelonrFromOrdelonring(ordelonring))
}

/**
 * Sort itelonm and modulelon (not itelonms insidelon modulelons) relonsults.
 *
 * For elonxamplelon, welon could speloncify thelon following ordelonring to sort by scorelon delonscelonnding:
 * Ordelonring
 *   .by[CandidatelonWithDelontails, Doublelon](_.felonaturelons.gelont(ScorelonFelonaturelon) match {
 *     caselon Scorelond(scorelon) => scorelon
 *     caselon _ => Doublelon.MinValuelon
 *   }).relonvelonrselon
 */
caselon class UpdatelonSortRelonsults(
  sortelonrProvidelonr: SortelonrProvidelonr,
  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon = AllPipelonlinelons)
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val updatelondRelonsult = sortelonrProvidelonr.sortelonr(quelonry, relonmainingCandidatelons, relonsult).sort(relonsult)

    SelonlelonctorRelonsult(relonmainingCandidatelons = relonmainingCandidatelons, relonsult = updatelondRelonsult)
  }
}
