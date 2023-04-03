packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.twelonelontconvosvc

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ModulelonCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Takelons a convelonrsation modulelon itelonm and truncatelons it to belon at most thelon focal twelonelont, thelon focal twelonelont's
 * in relonply to twelonelont and optionally, thelon root convelonrsation twelonelont if delonsirelond.
 * @param pipelonlinelonScopelon What pipelonlinelon scopelons to includelon in this.
 * @param includelonRootTwelonelont Whelonthelonr to includelon thelon root twelonelont at thelon top of thelon convelonrsation or not.
 * @tparam Quelonry
 */
caselon class DropMaxConvelonrsationModulelonItelonmCandidatelons[-Quelonry <: PipelonlinelonQuelonry](
  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon,
  includelonRootTwelonelont: Boolelonan)
    elonxtelonnds Selonlelonctor[Quelonry] {
  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val updatelondCandidatelons = relonmainingCandidatelons.collelonct {
      caselon modulelonCandidatelon: ModulelonCandidatelonWithDelontails if pipelonlinelonScopelon.contains(modulelonCandidatelon) =>
        updatelonConvelonrsationModulelon(modulelonCandidatelon, includelonRootTwelonelont)
      caselon candidatelons => candidatelons
    }
    SelonlelonctorRelonsult(relonmainingCandidatelons = updatelondCandidatelons, relonsult = relonsult)
  }

  privatelon delonf updatelonConvelonrsationModulelon(
    modulelon: ModulelonCandidatelonWithDelontails,
    includelonRootTwelonelont: Boolelonan
  ): ModulelonCandidatelonWithDelontails = {
    // If thelon threlonad is only thelon root twelonelont & a focal twelonelont relonplying to it, no truncation can belon donelon.
    if (modulelon.candidatelons.lelonngth <= 2) {
      modulelon
    } elonlselon {
      // If a threlonad is morelon 3 or morelon twelonelonts, welon optionally kelonelonp thelon root twelonelont if delonsirelond, and takelon
      // thelon focal twelonelont twelonelont and its direlonct ancelonstor (thelon onelon it would havelon relonplielond to) and relonturn
      // thoselon.
      val twelonelontCandidatelons = modulelon.candidatelons
      val relonplyAndFocalTwelonelont = twelonelontCandidatelons.takelonRight(2)
      val updatelondConvelonrsation = if (includelonRootTwelonelont) {
        twelonelontCandidatelons.helonadOption ++ relonplyAndFocalTwelonelont
      } elonlselon {
        relonplyAndFocalTwelonelont
      }
      modulelon.copy(candidatelons = updatelondConvelonrsation.toSelonq)
    }
  }
}
