packagelon com.twittelonr.homelon_mixelonr.product.list_twelonelonts.modelonl

import com.twittelonr.adselonrvelonr.thriftscala.HomelonTimelonlinelonTypelon
import com.twittelonr.adselonrvelonr.thriftscala.TimelonlinelonRelonquelonstParams
import com.twittelonr.dspbiddelonr.commons.{thriftscala => dsp}
import com.twittelonr.homelon_mixelonr.modelonl.HomelonAdsQuelonry
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.DelonvicelonContelonxt
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.HasListId
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.ListTwelonelontsProduct
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtOrdelonrelondCursor
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst._
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.HasPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.Params

caselon class ListTwelonelontsQuelonry(
  ovelonrridelon val params: Params,
  ovelonrridelon val clielonntContelonxt: ClielonntContelonxt,
  ovelonrridelon val pipelonlinelonCursor: Option[UrtOrdelonrelondCursor],
  ovelonrridelon val relonquelonstelondMaxRelonsults: Option[Int],
  ovelonrridelon val delonbugOptions: Option[DelonbugOptions],
  ovelonrridelon val felonaturelons: Option[FelonaturelonMap],
  ovelonrridelon val listId: Long,
  ovelonrridelon val delonvicelonContelonxt: Option[DelonvicelonContelonxt],
  ovelonrridelon val dspClielonntContelonxt: Option[dsp.DspClielonntContelonxt])
    elonxtelonnds PipelonlinelonQuelonry
    with HasPipelonlinelonCursor[UrtOrdelonrelondCursor]
    with HasListId
    with HomelonAdsQuelonry {
  ovelonrridelon val product: Product = ListTwelonelontsProduct

  ovelonrridelon delonf withFelonaturelonMap(felonaturelons: FelonaturelonMap): ListTwelonelontsQuelonry =
    copy(felonaturelons = Somelon(felonaturelons))

  ovelonrridelon val timelonlinelonRelonquelonstParams: Option[TimelonlinelonRelonquelonstParams] =
    Somelon(TimelonlinelonRelonquelonstParams(homelonTimelonlinelonTypelon = Somelon(HomelonTimelonlinelonTypelon.HomelonLatelonst)))
}
