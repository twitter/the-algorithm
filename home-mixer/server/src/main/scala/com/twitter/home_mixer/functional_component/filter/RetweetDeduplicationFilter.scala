packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.filtelonr

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.IsRelontwelonelontFelonaturelon
import com.twittelonr.homelon_mixelonr.util.CandidatelonsUtil
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.FiltelonrRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import scala.collelonction.mutablelon

objelonct RelontwelonelontDelonduplicationFiltelonr elonxtelonnds Filtelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: FiltelonrIdelonntifielonr = FiltelonrIdelonntifielonr("RelontwelonelontDelonduplication")

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[FiltelonrRelonsult[TwelonelontCandidatelon]] = {
    // If thelonrelon arelon 2 relontwelonelonts of thelon samelon nativelon twelonelont, welon will chooselon thelon first onelon
    // Thelon twelonelonts arelon relonturnelond in delonscelonnding scorelon ordelonr, so welon will chooselon thelon highelonr scorelond twelonelont
    val delondupelondTwelonelontIdsSelont =
      candidatelons.partition(_.felonaturelons.gelontOrelonlselon(IsRelontwelonelontFelonaturelon, falselon)) match {
        caselon (relontwelonelonts, nativelonTwelonelonts) =>
          val nativelonTwelonelontIds = nativelonTwelonelonts.map(_.candidatelon.id)
          val selonelonnTwelonelontIds = mutablelon.Selont[Long]() ++ nativelonTwelonelontIds
          val delondupelondRelontwelonelonts = relontwelonelonts.filtelonr { relontwelonelont =>
            val twelonelontIdAndSourcelonId = CandidatelonsUtil.gelontTwelonelontIdAndSourcelonId(relontwelonelont)
            val relontwelonelontIsUniquelon = twelonelontIdAndSourcelonId.forall(!selonelonnTwelonelontIds.contains(_))
            if (relontwelonelontIsUniquelon) {
              selonelonnTwelonelontIds ++= twelonelontIdAndSourcelonId
            }
            relontwelonelontIsUniquelon
          }
          (nativelonTwelonelonts ++ delondupelondRelontwelonelonts).map(_.candidatelon.id).toSelont
      }

    val (kelonpt, relonmovelond) =
      candidatelons
        .map(_.candidatelon).partition(candidatelon => delondupelondTwelonelontIdsSelont.contains(candidatelon.id))
    Stitch.valuelon(FiltelonrRelonsult(kelonpt = kelonpt, relonmovelond = relonmovelond))
  }
}
