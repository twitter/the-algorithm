packagelon com.twittelonr.follow_reloncommelonndations.flows.ads
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.adselonrvelonr.AdRelonquelonst
import com.twittelonr.follow_reloncommelonndations.common.modelonls.DisplayLocation
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasDisplayLocation
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HaselonxcludelondUselonrIds
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ClielonntContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.timelonlinelons.configapi.Params

caselon class PromotelondAccountsFlowRelonquelonst(
  ovelonrridelon val clielonntContelonxt: ClielonntContelonxt,
  ovelonrridelon val params: Params,
  displayLocation: DisplayLocation,
  profilelonId: Option[Long],
  // notelon welon also add uselonrId and profilelonId to elonxcludelonUselonrIds
  elonxcludelonIds: Selonq[Long])
    elonxtelonnds HasParams
    with HasClielonntContelonxt
    with HaselonxcludelondUselonrIds
    with HasDisplayLocation {
  delonf toAdsRelonquelonst(felontchProductionPromotelondAccounts: Boolelonan): AdRelonquelonst = {
    AdRelonquelonst(
      clielonntContelonxt = clielonntContelonxt,
      displayLocation = displayLocation,
      isTelonst = Somelon(!felontchProductionPromotelondAccounts),
      profilelonUselonrId = profilelonId
    )
  }
  ovelonrridelon val elonxcludelondUselonrIds: Selonq[Long] = {
    elonxcludelonIds ++ clielonntContelonxt.uselonrId.toSelonq ++ profilelonId.toSelonq
  }
}
