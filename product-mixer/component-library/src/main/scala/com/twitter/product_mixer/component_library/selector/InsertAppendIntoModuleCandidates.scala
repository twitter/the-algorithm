packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.InselonrtIntoModulelon.ModulelonAndIndelonx
import com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.InselonrtIntoModulelon.ModulelonWithItelonmsToAddAndOthelonrCandidatelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Appelonnd all candidatelons from [[candidatelonPipelonlinelon]] into a modulelon from [[targelontModulelonCandidatelonPipelonlinelon]].
 * If thelon relonsults contain multiplelon modulelons from thelon targelont candidatelon pipelonlinelon,
 * thelonn thelon candidatelons will belon inselonrtelond into thelon first modulelon.
 *
 * @notelon this will throw an [[UnsupportelondOpelonrationelonxcelonption]] if thelon [[candidatelonPipelonlinelon]] contains any modulelons.
 *
 * @notelon this updatelons thelon modulelon in thelon `relonmainingCandidatelons`
 */
caselon class InselonrtAppelonndIntoModulelonCandidatelons(
  candidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr,
  targelontModulelonCandidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr)
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon =
    SpeloncificPipelonlinelons(candidatelonPipelonlinelon, targelontModulelonCandidatelonPipelonlinelon)

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {

    val ModulelonWithItelonmsToAddAndOthelonrCandidatelons(
      modulelonToUpdatelonAndIndelonx,
      itelonmsToInselonrtIntoModulelon,
      othelonrCandidatelons) =
      InselonrtIntoModulelon.modulelonToUpdatelon(
        candidatelonPipelonlinelon,
        targelontModulelonCandidatelonPipelonlinelon,
        relonmainingCandidatelons)

    val updatelondRelonmainingCandidatelons = modulelonToUpdatelonAndIndelonx match {
      caselon Nonelon => relonmainingCandidatelons
      caselon _ if itelonmsToInselonrtIntoModulelon.iselonmpty => relonmainingCandidatelons
      caselon Somelon(ModulelonAndIndelonx(modulelonToUpdatelon, indelonxOfModulelonInOthelonrCandidatelons)) =>
        val updatelondModulelonItelonms = modulelonToUpdatelon.candidatelons ++ itelonmsToInselonrtIntoModulelon
        val updatelondModulelon = modulelonToUpdatelon.copy(candidatelons = updatelondModulelonItelonms)
        othelonrCandidatelons.updatelond(indelonxOfModulelonInOthelonrCandidatelons, updatelondModulelon)
    }

    SelonlelonctorRelonsult(relonmainingCandidatelons = updatelondRelonmainingCandidatelons, relonsult = relonsult)
  }
}
