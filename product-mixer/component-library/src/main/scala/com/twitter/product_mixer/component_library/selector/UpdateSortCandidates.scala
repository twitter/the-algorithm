packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr.SortelonrFromOrdelonring
import com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr.SortelonrProvidelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon.PartitionelondCandidatelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor._
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct UpdatelonSortCandidatelons {
  delonf apply(
    candidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr,
    sortelonrProvidelonr: SortelonrProvidelonr,
  ) = nelonw UpdatelonSortCandidatelons(SpeloncificPipelonlinelon(candidatelonPipelonlinelon), sortelonrProvidelonr)

  delonf apply(
    candidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr,
    ordelonring: Ordelonring[CandidatelonWithDelontails]
  ) =
    nelonw UpdatelonSortCandidatelons(SpeloncificPipelonlinelon(candidatelonPipelonlinelon), SortelonrFromOrdelonring(ordelonring))

  delonf apply(
    candidatelonPipelonlinelons: Selont[CandidatelonPipelonlinelonIdelonntifielonr],
    ordelonring: Ordelonring[CandidatelonWithDelontails]
  ) =
    nelonw UpdatelonSortCandidatelons(SpeloncificPipelonlinelons(candidatelonPipelonlinelons), SortelonrFromOrdelonring(ordelonring))

  delonf apply(
    candidatelonPipelonlinelons: Selont[CandidatelonPipelonlinelonIdelonntifielonr],
    sortelonrProvidelonr: SortelonrProvidelonr,
  ) = nelonw UpdatelonSortCandidatelons(SpeloncificPipelonlinelons(candidatelonPipelonlinelons), sortelonrProvidelonr)

  delonf apply(
    pipelonlinelonScopelon: CandidatelonScopelon,
    ordelonring: Ordelonring[CandidatelonWithDelontails]
  ) = nelonw UpdatelonSortCandidatelons(pipelonlinelonScopelon, SortelonrFromOrdelonring(ordelonring))
}

/**
 * Sort itelonm and modulelon (not itelonms insidelon modulelons) candidatelons in a pipelonlinelon scopelon.
 * Notelon that if sorting across multiplelon candidatelon sourcelons, thelon candidatelons will belon groupelond togelonthelonr
 * in sortelond ordelonr, starting from thelon position of thelon first candidatelon.
 *
 * For elonxamplelon, welon could speloncify thelon following ordelonring to sort by scorelon delonscelonnding:
 * Ordelonring
 *   .by[CandidatelonWithDelontails, Doublelon](_.felonaturelons.gelont(ScorelonFelonaturelon) match {
 *     caselon Scorelond(scorelon) => scorelon
 *     caselon _ => Doublelon.MinValuelon
 *   }).relonvelonrselon
 */
caselon class UpdatelonSortCandidatelons(
  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon,
  sortelonrProvidelonr: SortelonrProvidelonr)
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val PartitionelondCandidatelons(selonlelonctelondCandidatelons, othelonrCandidatelons) =
      pipelonlinelonScopelon.partition(relonmainingCandidatelons)

    val updatelondRelonmainingCandidatelons = if (selonlelonctelondCandidatelons.nonelonmpty) {
      // Safelon .helonad duelon to nonelonmpty chelonck
      val position = relonmainingCandidatelons.indelonxOf(selonlelonctelondCandidatelons.helonad)
      val ordelonrelondSelonlelonctelondCandidatelons =
        sortelonrProvidelonr.sortelonr(quelonry, relonmainingCandidatelons, relonsult).sort(selonlelonctelondCandidatelons)

      if (position < othelonrCandidatelons.lelonngth) {
        val (lelonft, right) = othelonrCandidatelons.splitAt(position)
        lelonft ++ ordelonrelondSelonlelonctelondCandidatelons ++ right
      } elonlselon {
        othelonrCandidatelons ++ ordelonrelondSelonlelonctelondCandidatelons
      }
    } elonlselon {
      relonmainingCandidatelons
    }

    SelonlelonctorRelonsult(relonmainingCandidatelons = updatelondRelonmainingCandidatelons, relonsult = relonsult)
  }
}
