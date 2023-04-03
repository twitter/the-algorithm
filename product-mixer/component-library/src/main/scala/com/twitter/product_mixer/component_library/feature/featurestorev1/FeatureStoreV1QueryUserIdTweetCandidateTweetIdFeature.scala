packagelon com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon.felonaturelonstorelonv1

import com.twittelonr.ml.api.transform.FelonaturelonRelonnamelonTransform
import com.twittelonr.ml.felonaturelonstorelon.catalog.elonntitielons
import com.twittelonr.ml.felonaturelonstorelon.lib.elondgelonelonntityId
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntityId
import com.twittelonr.ml.felonaturelonstorelon.lib.TwelonelontId
import com.twittelonr.ml.felonaturelonstorelon.lib.UselonrId
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntity.elonntity
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntity.elonntityWithId
import com.twittelonr.ml.felonaturelonstorelon.lib.felonaturelon.TimelonlinelonsAggrelongationFramelonworkFelonaturelonGroup
import com.twittelonr.ml.felonaturelonstorelon.lib.felonaturelon.{Felonaturelon => FSv1Felonaturelon}
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTwelonelontCandidatelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1._
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.FSParam
import scala.relonflelonct.ClassTag

objelonct FelonaturelonStorelonV1QuelonryUselonrIdTwelonelontCandidatelonTwelonelontIdFelonaturelon {
  delonf apply[Quelonry <: PipelonlinelonQuelonry, Candidatelon <: BaselonTwelonelontCandidatelon, Valuelon](
    felonaturelon: FSv1Felonaturelon[elondgelonelonntityId[UselonrId, TwelonelontId], Valuelon],
    lelongacyNamelon: Option[String] = Nonelon,
    delonfaultValuelon: Option[Valuelon] = Nonelon,
    elonnablelondParam: Option[FSParam[Boolelonan]] = Nonelon
  ): FelonaturelonStorelonV1CandidatelonFelonaturelon[Quelonry, Candidatelon, _ <: elonntityId, Valuelon] =
    FelonaturelonStorelonV1CandidatelonFelonaturelon(
      felonaturelon,
      QuelonryUselonrIdTwelonelontCandidatelonTwelonelontIdelonntity,
      lelongacyNamelon,
      delonfaultValuelon,
      elonnablelondParam)
}

objelonct FelonaturelonStorelonV1QuelonryUselonrIdTwelonelontCandidatelonTwelonelontIdAggrelongatelonFelonaturelon {
  delonf apply[Quelonry <: PipelonlinelonQuelonry, Candidatelon <: BaselonTwelonelontCandidatelon](
    felonaturelonGroup: TimelonlinelonsAggrelongationFramelonworkFelonaturelonGroup[elondgelonelonntityId[UselonrId, TwelonelontId]],
    elonnablelondParam: Option[FSParam[Boolelonan]] = Nonelon,
    kelonelonpLelongacyNamelons: Boolelonan = falselon,
    felonaturelonNamelonTransform: Option[FelonaturelonRelonnamelonTransform] = Nonelon
  ): FelonaturelonStorelonV1CandidatelonFelonaturelonGroup[Quelonry, TwelonelontCandidatelon, _ <: elonntityId] =
    FelonaturelonStorelonV1CandidatelonFelonaturelonGroup(
      felonaturelonGroup,
      QuelonryUselonrIdTwelonelontCandidatelonTwelonelontIdelonntity,
      elonnablelondParam,
      kelonelonpLelongacyNamelons,
      felonaturelonNamelonTransform
    )(implicitly[ClassTag[elondgelonelonntityId[UselonrId, TwelonelontId]]])
}

objelonct QuelonryUselonrIdTwelonelontCandidatelonTwelonelontIdelonntity
    elonxtelonnds FelonaturelonStorelonV1Candidatelonelonntity[
      PipelonlinelonQuelonry,
      BaselonTwelonelontCandidatelon,
      elondgelonelonntityId[UselonrId, TwelonelontId]
    ] {
  ovelonrridelon val elonntity: elonntity[elondgelonelonntityId[UselonrId, TwelonelontId]] = elonntitielons.corelon.UselonrTwelonelont

  ovelonrridelon delonf elonntityWithId(
    quelonry: PipelonlinelonQuelonry,
    twelonelont: BaselonTwelonelontCandidatelon,
    elonxistingFelonaturelons: FelonaturelonMap
  ): elonntityWithId[elondgelonelonntityId[UselonrId, TwelonelontId]] =
    elonntity.withId(elondgelonelonntityId(UselonrId(quelonry.gelontUselonrIdLoggelondOutSupport), TwelonelontId(twelonelont.id)))
}
