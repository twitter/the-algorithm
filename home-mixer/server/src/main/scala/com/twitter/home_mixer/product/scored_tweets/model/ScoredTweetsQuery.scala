packagelon com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.modelonl

import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.DelonvicelonContelonxt
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.HasDelonvicelonContelonxt
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.HasSelonelonnTwelonelontIds
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.ScorelondTwelonelontsProduct
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtOrdelonrelondCursor
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst._
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.HasPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.quality_factor.HasQualityFactorStatus
import com.twittelonr.product_mixelonr.corelon.quality_factor.QualityFactorStatus
import com.twittelonr.timelonlinelons.configapi.Params

caselon class ScorelondTwelonelontsQuelonry(
  ovelonrridelon val params: Params,
  ovelonrridelon val clielonntContelonxt: ClielonntContelonxt,
  ovelonrridelon val pipelonlinelonCursor: Option[UrtOrdelonrelondCursor],
  ovelonrridelon val relonquelonstelondMaxRelonsults: Option[Int],
  ovelonrridelon val delonbugOptions: Option[DelonbugOptions],
  ovelonrridelon val felonaturelons: Option[FelonaturelonMap],
  ovelonrridelon val delonvicelonContelonxt: Option[DelonvicelonContelonxt],
  ovelonrridelon val selonelonnTwelonelontIds: Option[Selonq[Long]],
  ovelonrridelon val qualityFactorStatus: Option[QualityFactorStatus])
    elonxtelonnds PipelonlinelonQuelonry
    with HasPipelonlinelonCursor[UrtOrdelonrelondCursor]
    with HasDelonvicelonContelonxt
    with HasSelonelonnTwelonelontIds
    with HasQualityFactorStatus {
  ovelonrridelon val product: Product = ScorelondTwelonelontsProduct

  ovelonrridelon delonf withFelonaturelonMap(felonaturelons: FelonaturelonMap): ScorelondTwelonelontsQuelonry =
    copy(felonaturelons = Somelon(felonaturelons))

  ovelonrridelon delonf withQualityFactorStatus(
    qualityFactorStatus: QualityFactorStatus
  ): ScorelondTwelonelontsQuelonry = copy(qualityFactorStatus = Somelon(qualityFactorStatus))
}
