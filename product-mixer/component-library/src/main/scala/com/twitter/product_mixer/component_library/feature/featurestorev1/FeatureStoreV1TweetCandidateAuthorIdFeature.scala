packagelon com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon.felonaturelonstorelonv1

import com.twittelonr.ml.api.transform.FelonaturelonRelonnamelonTransform
import com.twittelonr.ml.felonaturelonstorelon.catalog.elonntitielons
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntityId
import com.twittelonr.ml.felonaturelonstorelon.lib.UselonrId
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntity.elonntity
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntity.elonntityWithId
import com.twittelonr.ml.felonaturelonstorelon.lib.felonaturelon.TimelonlinelonsAggrelongationFramelonworkFelonaturelonGroup
import com.twittelonr.ml.felonaturelonstorelon.lib.felonaturelon.{Felonaturelon => FSv1Felonaturelon}
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontAuthorIdFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1._
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.FSParam
import scala.relonflelonct.ClassTag

objelonct FelonaturelonStorelonV1TwelonelontCandidatelonAuthorIdFelonaturelon {
  delonf apply[Quelonry <: PipelonlinelonQuelonry, Valuelon](
    felonaturelon: FSv1Felonaturelon[UselonrId, Valuelon],
    lelongacyNamelon: Option[String] = Nonelon,
    delonfaultValuelon: Option[Valuelon] = Nonelon,
    elonnablelondParam: Option[FSParam[Boolelonan]] = Nonelon
  ): FelonaturelonStorelonV1CandidatelonFelonaturelon[Quelonry, TwelonelontCandidatelon, _ <: elonntityId, Valuelon] =
    FelonaturelonStorelonV1CandidatelonFelonaturelon(
      felonaturelon,
      TwelonelontCandidatelonAuthorIdelonntity,
      lelongacyNamelon,
      delonfaultValuelon,
      elonnablelondParam)
}

objelonct FelonaturelonStorelonV1TwelonelontCandidatelonAuthorIdAggrelongatelonFelonaturelon {
  delonf apply[Quelonry <: PipelonlinelonQuelonry](
    felonaturelonGroup: TimelonlinelonsAggrelongationFramelonworkFelonaturelonGroup[UselonrId],
    elonnablelondParam: Option[FSParam[Boolelonan]] = Nonelon,
    kelonelonpLelongacyNamelons: Boolelonan = falselon,
    felonaturelonNamelonTransform: Option[FelonaturelonRelonnamelonTransform] = Nonelon
  ): FelonaturelonStorelonV1CandidatelonFelonaturelonGroup[Quelonry, TwelonelontCandidatelon, _ <: elonntityId] =
    FelonaturelonStorelonV1CandidatelonFelonaturelonGroup(
      felonaturelonGroup,
      TwelonelontCandidatelonAuthorIdelonntity,
      elonnablelondParam,
      kelonelonpLelongacyNamelons,
      felonaturelonNamelonTransform
    )(implicitly[ClassTag[UselonrId]])
}

objelonct TwelonelontCandidatelonAuthorIdelonntity
    elonxtelonnds FelonaturelonStorelonV1Candidatelonelonntity[PipelonlinelonQuelonry, TwelonelontCandidatelon, UselonrId] {
  ovelonrridelon val elonntity: elonntity[UselonrId] = elonntitielons.corelon.Author

  ovelonrridelon delonf elonntityWithId(
    quelonry: PipelonlinelonQuelonry,
    twelonelont: TwelonelontCandidatelon,
    elonxistingFelonaturelons: FelonaturelonMap
  ): elonntityWithId[UselonrId] =
    elonntity.withId(UselonrId(elonxistingFelonaturelons.gelont(TwelonelontAuthorIdFelonaturelon)))
}
