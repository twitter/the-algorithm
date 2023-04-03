packagelon com.twittelonr.product_mixelonr.componelonnt_library.filtelonr.twelonelont_imprelonssion

import com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.quelonry.imprelonsselond_twelonelonts.ImprelonsselondTwelonelonts
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.FiltelonrRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

/**
 * Filtelonrs out twelonelonts that thelon uselonr has selonelonn
 */
caselon class TwelonelontImprelonssionFiltelonr[Candidatelon <: BaselonTwelonelontCandidatelon](
) elonxtelonnds Filtelonr[PipelonlinelonQuelonry, Candidatelon] {

  ovelonrridelon val idelonntifielonr: FiltelonrIdelonntifielonr = FiltelonrIdelonntifielonr("TwelonelontImprelonssion")

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  ): Stitch[FiltelonrRelonsult[Candidatelon]] = {

    // Selont of Twelonelonts that havelon imprelonsselond thelon uselonr
    val imprelonsselondTwelonelontsSelont: Selont[Long] = quelonry.felonaturelons match {
      caselon Somelon(felonaturelonMap) => felonaturelonMap.gelontOrelonlselon(ImprelonsselondTwelonelonts, Selonq.elonmpty).toSelont
      caselon Nonelon => Selont.elonmpty
    }

    val (kelonptCandidatelons, relonmovelondCandidatelons) = candidatelons.partition { filtelonrelondCandidatelon =>
      !imprelonsselondTwelonelontsSelont.contains(filtelonrelondCandidatelon.candidatelon.id)
    }

    Stitch.valuelon(FiltelonrRelonsult(kelonptCandidatelons.map(_.candidatelon), relonmovelondCandidatelons.map(_.candidatelon)))
  }
}
