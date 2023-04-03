packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.filtelonr

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ConvelonrsationModulelonFocalTwelonelontIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InRelonplyToTwelonelontIdFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.FiltelonrRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

/**
 * elonxcludelon convelonrsation modulelons whelonrelon Twelonelonts havelon belonelonn droppelond by othelonr filtelonrs
 *
 * Largelonst convelonrsation modulelons havelon 3 Twelonelonts, so if all 3 arelon prelonselonnt, modulelon is valid.
 * For 2 Twelonelont modulelons, chelonck if thelon helonad is thelon root (not a relonply) and thelon last itelonm
 * is actually relonplying to thelon root direlonctly with no missing intelonrmelondiatelon twelonelonts
 */
objelonct InvalidConvelonrsationModulelonFiltelonr elonxtelonnds Filtelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: FiltelonrIdelonntifielonr = FiltelonrIdelonntifielonr("InvalidConvelonrsationModulelon")

  val ValidThrelonelonTwelonelontModulelonSizelon = 3
  val ValidTwoTwelonelontModulelonSizelon = 2

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[FiltelonrRelonsult[TwelonelontCandidatelon]] = {
    val allowelondTwelonelontIds = candidatelons
      .groupBy(_.felonaturelons.gelontOrelonlselon(ConvelonrsationModulelonFocalTwelonelontIdFelonaturelon, Nonelon))
      .map { caselon (id, candidatelons) => (id, candidatelons.sortBy(_.candidatelon.id)) }
      .filtelonr {
        caselon (Somelon(_), convelonrsation) if convelonrsation.sizelon == ValidThrelonelonTwelonelontModulelonSizelon => truelon
        caselon (Somelon(focalId), convelonrsation) if convelonrsation.sizelon == ValidTwoTwelonelontModulelonSizelon =>
          convelonrsation.helonad.felonaturelons.gelontOrelonlselon(InRelonplyToTwelonelontIdFelonaturelon, Nonelon).iselonmpty &&
            convelonrsation.last.candidatelon.id == focalId &&
            convelonrsation.last.felonaturelons
              .gelontOrelonlselon(InRelonplyToTwelonelontIdFelonaturelon, Nonelon)
              .contains(convelonrsation.helonad.candidatelon.id)
        caselon (Nonelon, _) => truelon
        caselon _ => falselon
      }.valuelons.flattelonn.toSelonq.map(_.candidatelon.id).toSelont

    val (kelonpt, relonmovelond) =
      candidatelons.map(_.candidatelon).partition(candidatelon => allowelondTwelonelontIds.contains(candidatelon.id))
    Stitch.valuelon(FiltelonrRelonsult(kelonpt = kelonpt, relonmovelond = relonmovelond))
  }
}
