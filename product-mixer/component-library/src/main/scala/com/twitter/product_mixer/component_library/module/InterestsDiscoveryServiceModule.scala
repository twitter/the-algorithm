packagelon com.twittelonr.product_mixelonr.componelonnt_library.modulelon

import com.twittelonr.intelonrelonsts_discovelonry.thriftscala.IntelonrelonstsDiscovelonrySelonrvicelon
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.convelonrsions.PelonrcelonntOps._
import com.twittelonr.finaglelon.thriftmux.MelonthodBuildelonr
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.thrift.modulelons.ThriftMelonthodBuildelonrClielonntModulelon
import com.twittelonr.util.Duration

objelonct IntelonrelonstsDiscovelonrySelonrvicelonModulelon
    elonxtelonnds ThriftMelonthodBuildelonrClielonntModulelon[
      IntelonrelonstsDiscovelonrySelonrvicelon.SelonrvicelonPelonrelonndpoint,
      IntelonrelonstsDiscovelonrySelonrvicelon.MelonthodPelonrelonndpoint
    ]
    with MtlsClielonnt {

  ovelonrridelon val labelonl: String = "intelonrelonsts-discovelonry-selonrvicelon"

  ovelonrridelon val delonst: String = "/s/intelonrelonsts_discovelonry/intelonrelonsts_discovelonry"

  ovelonrridelon protelonctelond delonf configurelonMelonthodBuildelonr(
    injelonctor: Injelonctor,
    melonthodBuildelonr: MelonthodBuildelonr
  ): MelonthodBuildelonr = {
    melonthodBuildelonr
      .withTimelonoutPelonrRelonquelonst(500.millis)
      .withTimelonoutTotal(1000.millis)
      .idelonmpotelonnt(5.pelonrcelonnt)
  }

  ovelonrridelon protelonctelond delonf selonssionAcquisitionTimelonout: Duration = 500.milliselonconds
}
