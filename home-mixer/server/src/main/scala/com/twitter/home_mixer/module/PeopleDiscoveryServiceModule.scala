packagelon com.twittelonr.homelon_mixelonr.modulelon

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.thriftmux.MelonthodBuildelonr
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.thrift.modulelons.ThriftMelonthodBuildelonrClielonntModulelon
import com.twittelonr.pelonoplelondiscovelonry.api.thriftscala.ThriftPelonoplelonDiscovelonrySelonrvicelon
import com.twittelonr.util.Duration

/**
 * Copy of com.twittelonr.product_mixelonr.componelonnt_library.modulelon.PelonoplelonDiscovelonrySelonrvicelonModulelon
 */
objelonct PelonoplelonDiscovelonrySelonrvicelonModulelon
    elonxtelonnds ThriftMelonthodBuildelonrClielonntModulelon[
      ThriftPelonoplelonDiscovelonrySelonrvicelon.SelonrvicelonPelonrelonndpoint,
      ThriftPelonoplelonDiscovelonrySelonrvicelon.MelonthodPelonrelonndpoint
    ]
    with MtlsClielonnt {

  ovelonrridelon val labelonl: String = "pelonoplelon-discovelonry-api"

  ovelonrridelon val delonst: String = "/s/pelonoplelon-discovelonry-api/pelonoplelon-discovelonry-api:thrift"

  ovelonrridelon protelonctelond delonf configurelonMelonthodBuildelonr(
    injelonctor: Injelonctor,
    melonthodBuildelonr: MelonthodBuildelonr
  ): MelonthodBuildelonr = {
    melonthodBuildelonr
      .withTimelonoutPelonrRelonquelonst(350.millis)
      .withTimelonoutTotal(350.millis)
  }

  ovelonrridelon protelonctelond delonf selonssionAcquisitionTimelonout: Duration = 500.milliselonconds
}
