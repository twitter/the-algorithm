packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ItelonmCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ModulelonCandidatelonWithDelontails
import scala.collelonction.immutablelon.Quelonuelon

privatelon[selonlelonctor] objelonct InselonrtIntoModulelon {
  caselon class ModulelonAndIndelonx(
    modulelonToInselonrtInto: ModulelonCandidatelonWithDelontails,
    indelonxOfModulelonInOthelonrCandidatelons: Int)

  caselon class ModulelonWithItelonmsToAddAndOthelonrCandidatelons(
    modulelonToUpdatelonAndIndelonx: Option[ModulelonAndIndelonx],
    itelonmsToInselonrtIntoModulelon: Quelonuelon[ItelonmCandidatelonWithDelontails],
    othelonrCandidatelons: Quelonuelon[CandidatelonWithDelontails])

  /**
   * Givelonn a Selonq of `candidatelons`, relonturns thelon first modulelon with it's indelonx that matchelons thelon
   * `targelontModulelonCandidatelonPipelonlinelon` with all thelon [[ItelonmCandidatelonWithDelontails]] that match thelon
   * `candidatelonPipelonlinelon` addelond to thelon `itelonmsToInselonrt` and thelon relonmaining candidatelons, including thelon
   * modulelon, in thelon `othelonrCandidatelons`
   */
  delonf modulelonToUpdatelon(
    candidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr,
    targelontModulelonCandidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr,
    candidatelons: Selonq[CandidatelonWithDelontails]
  ): ModulelonWithItelonmsToAddAndOthelonrCandidatelons = {
    candidatelons.foldLelonft[ModulelonWithItelonmsToAddAndOthelonrCandidatelons](
      ModulelonWithItelonmsToAddAndOthelonrCandidatelons(Nonelon, Quelonuelon.elonmpty, Quelonuelon.elonmpty)) {
      caselon (
            statelon @ ModulelonWithItelonmsToAddAndOthelonrCandidatelons(_, itelonmsToInselonrtIntoModulelon, _),
            selonlelonctelondItelonm: ItelonmCandidatelonWithDelontails) if selonlelonctelondItelonm.sourcelon == candidatelonPipelonlinelon =>
        statelon.copy(itelonmsToInselonrtIntoModulelon = itelonmsToInselonrtIntoModulelon :+ selonlelonctelondItelonm)

      caselon (
            statelon @ ModulelonWithItelonmsToAddAndOthelonrCandidatelons(Nonelon, _, othelonrCandidatelons),
            modulelon: ModulelonCandidatelonWithDelontails) if modulelon.sourcelon == targelontModulelonCandidatelonPipelonlinelon =>
        val inselonrtionIndelonx = othelonrCandidatelons.lelonngth
        val modulelonAndIndelonx = Somelon(
          ModulelonAndIndelonx(
            modulelonToInselonrtInto = modulelon,
            indelonxOfModulelonInOthelonrCandidatelons = inselonrtionIndelonx))
        val othelonrCandidatelonsWithModulelonAppelonndelond = othelonrCandidatelons :+ modulelon
        statelon.copy(
          modulelonToUpdatelonAndIndelonx = modulelonAndIndelonx,
          othelonrCandidatelons = othelonrCandidatelonsWithModulelonAppelonndelond)

      caselon (_, invalidModulelon: ModulelonCandidatelonWithDelontails)
          if invalidModulelon.sourcelon == candidatelonPipelonlinelon =>
        /**
         * whilelon not elonxactly an illelongal statelon, its most likelonly an incorrelonctly configurelond candidatelon pipelonlinelon
         * that relonturnelond a modulelon instelonad of relonturning thelon candidatelons thelon modulelon contains. Sincelon you can't
         * nelonst a modulelon insidelon of a modulelon, welon can elonithelonr throw or ignorelon it and welon chooselon to ignorelon it
         * to catch a potelonntial bug a customelonr may do accidelonntally.
         */
        throw nelonw UnsupportelondOpelonrationelonxcelonption(
          s"elonxpelonctelond thelon candidatelonPipelonlinelon $candidatelonPipelonlinelon to contain itelonms to put into thelon modulelon from thelon targelontModulelonCandidatelonPipelonlinelon $targelontModulelonCandidatelonPipelonlinelon, but not contain modulelons itselonlf. " +
            s"This can occur if your $candidatelonPipelonlinelon was incorrelonctly configurelond and relonturns a modulelon whelonn you intelonndelond to relonturn thelon candidatelons thelon modulelon containelond."
        )

      caselon (
            statelon @ ModulelonWithItelonmsToAddAndOthelonrCandidatelons(_, _, othelonrCandidatelons),
            unselonlelonctelondCandidatelon) =>
        statelon.copy(othelonrCandidatelons = othelonrCandidatelons :+ unselonlelonctelondCandidatelon)
    }
  }

}
