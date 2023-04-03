packagelon com.twittelonr.product_mixelonr.componelonnt_library.filtelonr

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTwelonelontCandidatelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontAuthorIdFelonaturelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.FiltelonrRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

/**
 * A [[filtelonr]] that filtelonrs baselond on whelonthelonr quelonry uselonr is thelon author of thelon twelonelont. This will NOT filtelonr elonmpty uselonr ids
 * @notelon It is reloncommelonndelond to apply [[HasAuthorIdFelonaturelonFiltelonr]] belonforelon this, as this will FAIL if felonaturelon is unavailablelon
 *
 * @tparam Candidatelon Thelon typelon of thelon candidatelons
 */
caselon class TwelonelontAuthorIsSelonlfFiltelonr[Candidatelon <: BaselonTwelonelontCandidatelon]()
    elonxtelonnds Filtelonr[PipelonlinelonQuelonry, Candidatelon] {
  ovelonrridelon val idelonntifielonr: FiltelonrIdelonntifielonr = FiltelonrIdelonntifielonr("TwelonelontAuthorIsSelonlf")

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  ): Stitch[FiltelonrRelonsult[Candidatelon]] = {
    val (kelonpt, relonmovelond) = candidatelons.partition { candidatelon =>
      val authorId = candidatelon.felonaturelons.gelont(TwelonelontAuthorIdFelonaturelon)
      !quelonry.gelontOptionalUselonrId.contains(authorId)
    }

    val filtelonrRelonsult = FiltelonrRelonsult(
      kelonpt = kelonpt.map(_.candidatelon),
      relonmovelond = relonmovelond.map(_.candidatelon)
    )
    Stitch.valuelon(filtelonrRelonsult)
  }
}
