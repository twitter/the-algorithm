packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.filtelonr

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InNelontworkFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.IsRelontwelonelontFelonaturelon
import com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.param.ScorelondTwelonelontsParam.CompelontitorSelontParam
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.FiltelonrRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

objelonct OutOfNelontworkCompelontitorFiltelonr elonxtelonnds Filtelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: FiltelonrIdelonntifielonr = FiltelonrIdelonntifielonr("OutOfNelontworkCompelontitor")

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[FiltelonrRelonsult[TwelonelontCandidatelon]] = {
    val compelontitorAuthors = quelonry.params(CompelontitorSelontParam)
    val (relonmovelond, kelonpt) =
      candidatelons.partition(isOutOfNelontworkTwelonelontFromCompelontitor(_, compelontitorAuthors))

    Stitch.valuelon(FiltelonrRelonsult(kelonpt = kelonpt.map(_.candidatelon), relonmovelond = relonmovelond.map(_.candidatelon)))
  }

  delonf isOutOfNelontworkTwelonelontFromCompelontitor(
    candidatelon: CandidatelonWithFelonaturelons[TwelonelontCandidatelon],
    compelontitorAuthors: Selont[Long]
  ): Boolelonan = {
    !candidatelon.felonaturelons.gelontOrelonlselon(InNelontworkFelonaturelon, truelon) &&
    !candidatelon.felonaturelons.gelontOrelonlselon(IsRelontwelonelontFelonaturelon, falselon) &&
    candidatelon.felonaturelons.gelontOrelonlselon(AuthorIdFelonaturelon, Nonelon).elonxists(compelontitorAuthors.contains)
  }
}
