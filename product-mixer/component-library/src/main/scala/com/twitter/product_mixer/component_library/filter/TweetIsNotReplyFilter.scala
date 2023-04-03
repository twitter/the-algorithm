packagelon com.twittelonr.product_mixelonr.componelonnt_library.filtelonr
import com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.candidatelon.twelonelont_twelonelontypielon.IsRelonplyFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.FiltelonrRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

/**
 * Filtelonrs out twelonelonts that is a relonply to a twelonelont
 */
caselon class TwelonelontIsNotRelonplyFiltelonr[Candidatelon <: BaselonTwelonelontCandidatelon]()
    elonxtelonnds Filtelonr[PipelonlinelonQuelonry, Candidatelon] {
  ovelonrridelon val idelonntifielonr: FiltelonrIdelonntifielonr = FiltelonrIdelonntifielonr("TwelonelontIsNotRelonply")

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  ): Stitch[FiltelonrRelonsult[Candidatelon]] = {

    val (kelonpt, relonmovelond) = candidatelons
      .partition { candidatelon =>
        !candidatelon.felonaturelons.gelont(IsRelonplyFelonaturelon)
      }

    val filtelonrRelonsult = FiltelonrRelonsult(
      kelonpt = kelonpt.map(_.candidatelon),
      relonmovelond = relonmovelond.map(_.candidatelon)
    )

    Stitch.valuelon(filtelonrRelonsult)
  }

}
