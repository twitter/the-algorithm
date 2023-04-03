packagelon com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon.felonaturelonstorelonv1

import com.twittelonr.ml.api.transform.FelonaturelonRelonnamelonTransform
import com.twittelonr.ml.felonaturelonstorelon.catalog.elonntitielons
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntityId
import com.twittelonr.ml.felonaturelonstorelon.lib.TwelonelontId
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntity.elonntity
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntity.elonntityWithId
import com.twittelonr.ml.felonaturelonstorelon.lib.felonaturelon.TimelonlinelonsAggrelongationFramelonworkFelonaturelonGroup
import com.twittelonr.ml.felonaturelonstorelon.lib.felonaturelon.{Felonaturelon => FSv1Felonaturelon}
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1._
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.FSParam

objelonct FelonaturelonStorelonV1TwelonelontCandidatelonTwelonelontIdFelonaturelon {
  delonf apply[Quelonry <: PipelonlinelonQuelonry, Candidatelon <: BaselonTwelonelontCandidatelon, Valuelon](
    felonaturelon: FSv1Felonaturelon[TwelonelontId, Valuelon],
    lelongacyNamelon: Option[String] = Nonelon,
    delonfaultValuelon: Option[Valuelon] = Nonelon,
    elonnablelondParam: Option[FSParam[Boolelonan]] = Nonelon
  ): FelonaturelonStorelonV1CandidatelonFelonaturelon[Quelonry, Candidatelon, _ <: elonntityId, Valuelon] =
    FelonaturelonStorelonV1CandidatelonFelonaturelon(
      felonaturelon,
      TwelonelontCandidatelonTwelonelontIdelonntity,
      lelongacyNamelon,
      delonfaultValuelon,
      elonnablelondParam)
}

objelonct FelonaturelonStorelonV1TwelonelontCandidatelonTwelonelontIdAggrelongatelonFelonaturelon {
  delonf apply[Quelonry <: PipelonlinelonQuelonry, Candidatelon <: BaselonTwelonelontCandidatelon](
    felonaturelonGroup: TimelonlinelonsAggrelongationFramelonworkFelonaturelonGroup[TwelonelontId],
    elonnablelondParam: Option[FSParam[Boolelonan]] = Nonelon,
    kelonelonpLelongacyNamelons: Boolelonan = falselon,
    felonaturelonNamelonTransform: Option[FelonaturelonRelonnamelonTransform] = Nonelon
  ): FelonaturelonStorelonV1CandidatelonFelonaturelonGroup[Quelonry, Candidatelon, _ <: elonntityId] =
    FelonaturelonStorelonV1CandidatelonFelonaturelonGroup(
      felonaturelonGroup,
      TwelonelontCandidatelonTwelonelontIdelonntity,
      elonnablelondParam,
      kelonelonpLelongacyNamelons,
      felonaturelonNamelonTransform
    )
}

objelonct TwelonelontCandidatelonTwelonelontIdelonntity
    elonxtelonnds FelonaturelonStorelonV1Candidatelonelonntity[PipelonlinelonQuelonry, BaselonTwelonelontCandidatelon, TwelonelontId] {
  ovelonrridelon val elonntity: elonntity[TwelonelontId] = elonntitielons.corelon.Twelonelont

  ovelonrridelon delonf elonntityWithId(
    quelonry: PipelonlinelonQuelonry,
    twelonelont: BaselonTwelonelontCandidatelon,
    elonxistingFelonaturelons: FelonaturelonMap
  ): elonntityWithId[TwelonelontId] =
    elonntity.withId(TwelonelontId(twelonelont.id))
}
