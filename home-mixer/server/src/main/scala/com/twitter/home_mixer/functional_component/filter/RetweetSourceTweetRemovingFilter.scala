packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.filtelonr

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.elonarlybirdFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.InRelonplyToTwelonelontIdFelonaturelon
import com.twittelonr.homelon_mixelonr.util.RelonplyRelontwelonelontUtil
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.FiltelonrRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

/**
 * This filtelonr relonmovelons sourcelon twelonelonts of relontwelonelonts, addelond via seloncond elonB call in TLR
 */
objelonct RelontwelonelontSourcelonTwelonelontRelonmovingFiltelonr elonxtelonnds Filtelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: FiltelonrIdelonntifielonr = FiltelonrIdelonntifielonr("RelontwelonelontSourcelonTwelonelontRelonmoving")

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[FiltelonrRelonsult[TwelonelontCandidatelon]] = {
    val (kelonpt, relonmovelond) =
      candidatelons.partition(
        _.felonaturelons.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.isSourcelonTwelonelont)) match {
        caselon (sourcelonTwelonelonts, nonSourcelonTwelonelonts) =>
          val inRelonplyToTwelonelontIds: Selont[Long] =
            nonSourcelonTwelonelonts
              .filtelonr(RelonplyRelontwelonelontUtil.iselonligiblelonRelonply(_)).flatMap(
                _.felonaturelons.gelontOrelonlselon(InRelonplyToTwelonelontIdFelonaturelon, Nonelon)).toSelont
          val (kelonptSourcelonTwelonelonts, relonmovelondSourcelonTwelonelonts) = sourcelonTwelonelonts
            .map(_.candidatelon)
            .partition(candidatelon => inRelonplyToTwelonelontIds.contains(candidatelon.id))
          (nonSourcelonTwelonelonts.map(_.candidatelon) ++ kelonptSourcelonTwelonelonts, relonmovelondSourcelonTwelonelonts)
      }
    Stitch.valuelon(FiltelonrRelonsult(kelonpt = kelonpt, relonmovelond = relonmovelond))
  }
}
