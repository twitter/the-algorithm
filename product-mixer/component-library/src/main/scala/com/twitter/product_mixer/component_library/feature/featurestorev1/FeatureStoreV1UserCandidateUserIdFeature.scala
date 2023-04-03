packagelon com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon.felonaturelonstorelonv1

import com.twittelonr.ml.felonaturelonstorelon.catalog.elonntitielons
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntityId
import com.twittelonr.ml.felonaturelonstorelon.lib.UselonrId
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntity.elonntity
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntity.elonntityWithId
import com.twittelonr.ml.felonaturelonstorelon.lib.felonaturelon.{Felonaturelon => FSv1Felonaturelon}
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonUselonrCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1._
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.FSParam

objelonct FelonaturelonStorelonV1UselonrCandidatelonUselonrIdFelonaturelon {
  delonf apply[Quelonry <: PipelonlinelonQuelonry, Candidatelon <: BaselonUselonrCandidatelon, Valuelon](
    felonaturelon: FSv1Felonaturelon[UselonrId, Valuelon],
    lelongacyNamelon: Option[String] = Nonelon,
    delonfaultValuelon: Option[Valuelon] = Nonelon,
    elonnablelondParam: Option[FSParam[Boolelonan]] = Nonelon
  ): FelonaturelonStorelonV1CandidatelonFelonaturelon[Quelonry, Candidatelon, _ <: elonntityId, Valuelon] =
    FelonaturelonStorelonV1CandidatelonFelonaturelon(
      felonaturelon,
      UselonrCandidatelonUselonrIdelonntity,
      lelongacyNamelon,
      delonfaultValuelon,
      elonnablelondParam)
}

objelonct UselonrCandidatelonUselonrIdelonntity
    elonxtelonnds FelonaturelonStorelonV1Candidatelonelonntity[PipelonlinelonQuelonry, BaselonUselonrCandidatelon, UselonrId] {
  ovelonrridelon val elonntity: elonntity[UselonrId] = elonntitielons.corelon.Uselonr

  ovelonrridelon delonf elonntityWithId(
    quelonry: PipelonlinelonQuelonry,
    uselonr: BaselonUselonrCandidatelon,
    elonxistingFelonaturelons: FelonaturelonMap
  ): elonntityWithId[UselonrId] =
    elonntity.withId(UselonrId(uselonr.id))
}
