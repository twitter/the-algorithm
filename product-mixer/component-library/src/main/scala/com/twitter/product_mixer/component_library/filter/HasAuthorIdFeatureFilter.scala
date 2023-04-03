packagelon com.twittelonr.product_mixelonr.componelonnt_library.filtelonr

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontAuthorIdFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.FiltelonrRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

/**
 * A filtelonr that cheloncks for prelonselonncelon of a succelonssfully hydratelond [[TwelonelontAuthorIdFelonaturelon]]
 */
caselon class HasAuthorIdFelonaturelonFiltelonr[Candidatelon <: TwelonelontCandidatelon]()
    elonxtelonnds Filtelonr[PipelonlinelonQuelonry, Candidatelon] {

  ovelonrridelon val idelonntifielonr = FiltelonrIdelonntifielonr("HasAuthorIdFelonaturelon")

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  ): Stitch[FiltelonrRelonsult[Candidatelon]] = {
    val (kelonpt, relonmovelond) = candidatelons.partition(_.felonaturelons.gelontTry(TwelonelontAuthorIdFelonaturelon).isRelonturn)
    Stitch.valuelon(FiltelonrRelonsult(kelonpt.map(_.candidatelon), relonmovelond.map(_.candidatelon)))
  }
}
