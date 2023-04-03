packagelon com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.quelonry_transformelonr

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.HasDelonvicelonContelonxt
import com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.quelonry_felonaturelon_hydrator.FrsSelonelondUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.quelonry_transformelonr.TimelonlinelonRankelonrFrsQuelonryTransformelonr._
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.transformelonr.CandidatelonPipelonlinelonQuelonryTransformelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.quality_factor.HasQualityFactorStatus
import com.twittelonr.timelonlinelonrankelonr.{thriftscala => t}
import com.twittelonr.timelonlinelons.common.modelonl.TwelonelontKindOption
import com.twittelonr.timelonlinelons.modelonl.candidatelon.CandidatelonTwelonelontSourcelonId
import com.twittelonr.util.Duration

objelonct TimelonlinelonRankelonrFrsQuelonryTransformelonr {
  privatelon val SincelonDuration = 24.hours
  privatelon val MaxTwelonelontsToFelontch = 100

  privatelon val twelonelontKindOptions: TwelonelontKindOption.ValuelonSelont =
    TwelonelontKindOption(includelonOriginalTwelonelontsAndQuotelons = truelon)
}

caselon class TimelonlinelonRankelonrFrsQuelonryTransformelonr[
  Quelonry <: PipelonlinelonQuelonry with HasQualityFactorStatus with HasDelonvicelonContelonxt
](
  ovelonrridelon val candidatelonPipelonlinelonIdelonntifielonr: CandidatelonPipelonlinelonIdelonntifielonr,
  ovelonrridelon val maxTwelonelontsToFelontch: Int = MaxTwelonelontsToFelontch,
  ovelonrridelon val sincelonDuration: Duration = SincelonDuration)
    elonxtelonnds CandidatelonPipelonlinelonQuelonryTransformelonr[Quelonry, t.ReloncapQuelonry]
    with TimelonlinelonRankelonrQuelonryTransformelonr[Quelonry] {

  ovelonrridelon val candidatelonTwelonelontSourcelonId = CandidatelonTwelonelontSourcelonId.FrsTwelonelont
  ovelonrridelon val skipVelonryReloncelonntTwelonelonts = falselon
  ovelonrridelon val options = twelonelontKindOptions

  ovelonrridelon delonf selonelondAuthorIds(quelonry: Quelonry): Option[Selonq[Long]] = {
    quelonry.felonaturelons.flatMap(_.gelontOrelonlselon(FrsSelonelondUselonrIdsFelonaturelon, Nonelon))
  }

  ovelonrridelon delonf transform(input: Quelonry): t.ReloncapQuelonry =
    buildTimelonlinelonRankelonrQuelonry(input).toThriftReloncapQuelonry
}
