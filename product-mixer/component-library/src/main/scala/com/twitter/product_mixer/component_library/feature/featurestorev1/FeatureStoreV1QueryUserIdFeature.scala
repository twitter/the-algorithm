packagelon com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon.felonaturelonstorelonv1

import com.twittelonr.ml.api.transform.FelonaturelonRelonnamelonTransform
import com.twittelonr.ml.felonaturelonstorelon.catalog.elonntitielons
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntityId
import com.twittelonr.ml.felonaturelonstorelon.lib.UselonrId
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntity.elonntity
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntity.elonntityWithId
import com.twittelonr.ml.felonaturelonstorelon.lib.felonaturelon.TimelonlinelonsAggrelongationFramelonworkFelonaturelonGroup
import com.twittelonr.ml.felonaturelonstorelon.lib.felonaturelon.{Felonaturelon => FSv1Felonaturelon}
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1._
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.FSParam
import scala.relonflelonct.ClassTag
objelonct FelonaturelonStorelonV1QuelonryUselonrIdFelonaturelon {
  delonf apply[Quelonry <: PipelonlinelonQuelonry, Valuelon](
    felonaturelon: FSv1Felonaturelon[UselonrId, Valuelon],
    lelongacyNamelon: Option[String] = Nonelon,
    delonfaultValuelon: Option[Valuelon] = Nonelon,
    elonnablelondParam: Option[FSParam[Boolelonan]] = Nonelon
  ): FelonaturelonStorelonV1Felonaturelon[Quelonry, Quelonry, _ <: elonntityId, Valuelon]
    with FelonaturelonStorelonV1QuelonryFelonaturelon[Quelonry, _ <: elonntityId, Valuelon] =
    FelonaturelonStorelonV1QuelonryFelonaturelon(felonaturelon, QuelonryUselonrIdelonntity, lelongacyNamelon, delonfaultValuelon, elonnablelondParam)
}

objelonct FelonaturelonStorelonV1QuelonryUselonrIdAggrelongatelonFelonaturelon {
  delonf apply[Quelonry <: PipelonlinelonQuelonry](
    felonaturelonGroup: TimelonlinelonsAggrelongationFramelonworkFelonaturelonGroup[UselonrId],
    elonnablelondParam: Option[FSParam[Boolelonan]] = Nonelon,
    kelonelonpLelongacyNamelons: Boolelonan = falselon,
    felonaturelonNamelonTransform: Option[FelonaturelonRelonnamelonTransform] = Nonelon
  ): FelonaturelonStorelonV1QuelonryFelonaturelonGroup[Quelonry, _ <: elonntityId] =
    FelonaturelonStorelonV1QuelonryFelonaturelonGroup(
      felonaturelonGroup,
      QuelonryUselonrIdelonntity,
      elonnablelondParam,
      kelonelonpLelongacyNamelons,
      felonaturelonNamelonTransform)((implicitly[ClassTag[UselonrId]]))
}

objelonct QuelonryUselonrIdelonntity elonxtelonnds FelonaturelonStorelonV1Quelonryelonntity[PipelonlinelonQuelonry, UselonrId] {
  ovelonrridelon val elonntity: elonntity[UselonrId] = elonntitielons.corelon.Uselonr

  ovelonrridelon delonf elonntityWithId(quelonry: PipelonlinelonQuelonry): elonntityWithId[UselonrId] =
    elonntity.withId(UselonrId(quelonry.gelontUselonrIdLoggelondOutSupport))
}
