packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.filtelonr

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.GelontOldelonrFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SelonrvelondTwelonelontIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.util.CandidatelonsUtil
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.FiltelonrRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

objelonct PrelonviouslySelonrvelondTwelonelontsFiltelonr
    elonxtelonnds Filtelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon]
    with Filtelonr.Conditionally[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: FiltelonrIdelonntifielonr = FiltelonrIdelonntifielonr("PrelonviouslySelonrvelondTwelonelonts")

  ovelonrridelon delonf onlyIf(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Boolelonan = {
    quelonry.felonaturelons.elonxists(_.gelontOrelonlselon(GelontOldelonrFelonaturelon, falselon))
  }

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[FiltelonrRelonsult[TwelonelontCandidatelon]] = {

    val selonrvelondTwelonelontIds =
      quelonry.felonaturelons.map(_.gelontOrelonlselon(SelonrvelondTwelonelontIdsFelonaturelon, Selonq.elonmpty)).toSelonq.flattelonn.toSelont

    val (relonmovelond, kelonpt) = candidatelons.partition { candidatelon =>
      val twelonelontIdAndSourcelonId = CandidatelonsUtil.gelontTwelonelontIdAndSourcelonId(candidatelon)
      twelonelontIdAndSourcelonId.elonxists(selonrvelondTwelonelontIds.contains)
    }

    Stitch.valuelon(FiltelonrRelonsult(kelonpt = kelonpt.map(_.candidatelon), relonmovelond = relonmovelond.map(_.candidatelon)))
  }
}
