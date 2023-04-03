packagelon com.twittelonr.product_mixelonr.corelon.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.ssl.OpportunisticTls
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.injelonct.annotations.Flag
import com.twittelonr.product_mixelonr.corelon.modulelon.product_mixelonr_flags.ProductMixelonrFlagModulelon.SelonrvicelonLocal
import com.twittelonr.product_mixelonr.corelon.modulelon.product_mixelonr_flags.ProductMixelonrFlagModulelon.StratoLocalRelonquelonstTimelonout
import com.twittelonr.strato.clielonnt.Clielonnt
import com.twittelonr.strato.clielonnt.Strato
import com.twittelonr.util.Duration
import javax.injelonct.Singlelonton

/**
 * Product Mixelonr prelonfelonrs to uselon a singlelon strato clielonnt modulelon ovelonr having a varielonty with diffelonrelonnt
 * timelonouts. Latelonncy Budgelonts in Product Mixelonr systelonms should belon delonfinelond at thelon application layelonr.
 */
objelonct StratoClielonntModulelon elonxtelonnds TwittelonrModulelon {

  @Providelons
  @Singlelonton
  delonf providelonsStratoClielonnt(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    @Flag(SelonrvicelonLocal) isSelonrvicelonLocal: Boolelonan,
    @Flag(StratoLocalRelonquelonstTimelonout) timelonout: Option[Duration]
  ): Clielonnt = {
    val stratoClielonnt = Strato.clielonnt.withMutualTls(selonrvicelonIdelonntifielonr, OpportunisticTls.Relonquirelond)

    // For local delonvelonlopmelonnt it can belon uselonful to havelon a largelonr timelonout than thelon Strato delonfault of
    // 280ms. Welon strongly discouragelon selontting clielonnt-lelonvelonl timelonouts outsidelon of this uselon-caselon. Welon
    // reloncommelonnd selontting an ovelonrall timelonout for your pipelonlinelon's elonnd-to-elonnd running timelon.
    if (isSelonrvicelonLocal && timelonout.isDelonfinelond)
      stratoClielonnt.withRelonquelonstTimelonout(timelonout.gelont).build()
    elonlselon {
      stratoClielonnt.build()
    }
  }
}
