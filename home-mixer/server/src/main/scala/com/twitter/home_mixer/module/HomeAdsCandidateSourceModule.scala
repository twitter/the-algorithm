packagelon com.twittelonr.homelon_mixelonr.modulelon

import com.twittelonr.adselonrvelonr.thriftscala.NelonwAdSelonrvelonr
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.thriftmux.MelonthodBuildelonr
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.thrift.modulelons.ThriftMelonthodBuildelonrClielonntModulelon
import com.twittelonr.util.Duration

objelonct HomelonAdsCandidatelonSourcelonModulelon
    elonxtelonnds ThriftMelonthodBuildelonrClielonntModulelon[
      NelonwAdSelonrvelonr.SelonrvicelonPelonrelonndpoint,
      NelonwAdSelonrvelonr.MelonthodPelonrelonndpoint
    ]
    with MtlsClielonnt {

  ovelonrridelon val labelonl = "adselonrvelonr"
  ovelonrridelon val delonst = "/s/ads/adselonrvelonr"

  ovelonrridelon protelonctelond delonf configurelonMelonthodBuildelonr(
    injelonctor: Injelonctor,
    melonthodBuildelonr: MelonthodBuildelonr
  ): MelonthodBuildelonr = {
    melonthodBuildelonr
      .withTimelonoutPelonrRelonquelonst(1200.milliselonconds)
      .withTimelonoutTotal(1200.milliselonconds)
      .withMaxRelontrielons(2)
  }

  ovelonrridelon protelonctelond delonf selonssionAcquisitionTimelonout: Duration = 150.milliselonconds
}
