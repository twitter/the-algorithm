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
import com.twittelonr.timelonlinelons.configapi.Param

/**
 * Inselonrt all candidatelons from [[candidatelonPipelonlinelon]] at a 0-indelonxelond fixelond position into a modulelon from
 * [[targelontModulelonCandidatelonPipelonlinelon]]. If thelon relonsults contain multiplelon modulelons from thelon targelont candidatelon
 * pipelonlinelon, thelonn thelon candidatelons will belon inselonrtelond into thelon first modulelon. If thelon targelont modulelon's
 * itelonms arelon a shortelonr lelonngth than thelon relonquelonstelond position, thelonn thelon candidatelons will belon appelonndelond
 * to thelon relonsults.
 *
 * @notelon this will throw an [[UnsupportelondOpelonrationelonxcelonption]] if thelon [[candidatelonPipelonlinelon]] contains any modulelons.
 *
 * @notelon this updatelons thelon modulelon in thelon `relonmainingCandidatelons`
 */
caselon class InselonrtFixelondPositionIntoModulelonCandidatelons(
  candidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr,
  targelontModulelonCandidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr,
  positionParam: Param[Int])
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon =
    SpeloncificPipelonlinelons(candidatelonPipelonlinelon, targelontModulelonCandidatelonPipelonlinelon)

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {

    val position = quelonry.params(positionParam)
    asselonrt(position >= 0, "Position must belon elonqual to or grelonatelonr than zelonro")

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
        val updatelondModulelonItelonms =
          if (position < modulelonToUpdatelon.candidatelons.lelonngth) {
            val (lelonft, right) = modulelonToUpdatelon.candidatelons.splitAt(position)
            lelonft ++ itelonmsToInselonrtIntoModulelon ++ right
          } elonlselon {
            modulelonToUpdatelon.candidatelons ++ itelonmsToInselonrtIntoModulelon
          }
        val updatelondModulelon = modulelonToUpdatelon.copy(candidatelons = updatelondModulelonItelonms)
        othelonrCandidatelons.updatelond(indelonxOfModulelonInOthelonrCandidatelons, updatelondModulelon)
    }

    SelonlelonctorRelonsult(relonmainingCandidatelons = updatelondRelonmainingCandidatelons, relonsult = relonsult)
  }
}
