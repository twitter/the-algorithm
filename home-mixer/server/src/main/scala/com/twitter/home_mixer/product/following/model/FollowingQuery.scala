packagelon com.twittelonr.homelon_mixelonr.product.following.modelonl

import com.twittelonr.adselonrvelonr.thriftscala.HomelonTimelonlinelonTypelon
import com.twittelonr.adselonrvelonr.thriftscala.TimelonlinelonRelonquelonstParams
import com.twittelonr.homelon_mixelonr.modelonl.HomelonAdsQuelonry
import com.twittelonr.dspbiddelonr.commons.{thriftscala => dsp}
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.DelonvicelonContelonxt
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.HasDelonvicelonContelonxt
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.HasSelonelonnTwelonelontIds
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.FollowingProduct
import com.twittelonr.onboarding.task.selonrvicelon.{thriftscala => ots}
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtOrdelonrelondCursor
import com.twittelonr.product_mixelonr.componelonnt_library.pipelonlinelon.candidatelon.flelonxiblelon_injelonction_pipelonlinelon.transformelonr.HasFlipInjelonctionParams
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst._
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.HasPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.Params

caselon class FollowingQuelonry(
  ovelonrridelon val params: Params,
  ovelonrridelon val clielonntContelonxt: ClielonntContelonxt,
  ovelonrridelon val pipelonlinelonCursor: Option[UrtOrdelonrelondCursor],
  ovelonrridelon val relonquelonstelondMaxRelonsults: Option[Int],
  ovelonrridelon val delonbugOptions: Option[DelonbugOptions],
  ovelonrridelon val felonaturelons: Option[FelonaturelonMap],
  ovelonrridelon val delonvicelonContelonxt: Option[DelonvicelonContelonxt],
  ovelonrridelon val selonelonnTwelonelontIds: Option[Selonq[Long]],
  ovelonrridelon val dspClielonntContelonxt: Option[dsp.DspClielonntContelonxt])
    elonxtelonnds PipelonlinelonQuelonry
    with HasPipelonlinelonCursor[UrtOrdelonrelondCursor]
    with HasDelonvicelonContelonxt
    with HasSelonelonnTwelonelontIds
    with HasFlipInjelonctionParams
    with HomelonAdsQuelonry {
  ovelonrridelon val product: Product = FollowingProduct

  ovelonrridelon delonf withFelonaturelonMap(felonaturelons: FelonaturelonMap): FollowingQuelonry =
    copy(felonaturelons = Somelon(felonaturelons))

  ovelonrridelon val timelonlinelonRelonquelonstParams: Option[TimelonlinelonRelonquelonstParams] =
    Somelon(TimelonlinelonRelonquelonstParams(homelonTimelonlinelonTypelon = Somelon(HomelonTimelonlinelonTypelon.HomelonLatelonst)))

  // Fielonlds belonlow arelon uselond for FLIP Injelonction in Onboarding Task Selonrvicelon (OTS)
  ovelonrridelon val displayLocation: ots.DisplayLocation = ots.DisplayLocation.HomelonLatelonstTimelonlinelon
  ovelonrridelon val rankingDisablelonrWithLatelonstControlsAvailablelon: Option[Boolelonan] = Nonelon
  ovelonrridelon val iselonmptyStatelon: Option[Boolelonan] = Nonelon
  ovelonrridelon val isFirstRelonquelonstAftelonrSignup: Option[Boolelonan] = Nonelon
  ovelonrridelon val iselonndOfTimelonlinelon: Option[Boolelonan] = Nonelon
}
