packagelon com.twittelonr.product_mixelonr.componelonnt_library.modulelon

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.convelonrsions.PelonrcelonntOps._
import com.twittelonr.finaglelon.thriftmux.MelonthodBuildelonr
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.thrift.modulelons.ThriftMelonthodBuildelonrClielonntModulelon
import com.twittelonr.pelonoplelondiscovelonry.api.thriftscala.ThriftPelonoplelonDiscovelonrySelonrvicelon
import com.twittelonr.util.Duration

/**
 * Implelonmelonntation with relonasonablelon delonfaults for an idelonmpotelonnt Pelonoplelon Discovelonry Thrift clielonnt.
 *
 * Notelon that thelon pelonr relonquelonst and total timelonouts configurelond in this modulelon arelon melonant to relonprelonselonnt a
 * relonasonablelon starting point only. Thelonselon welonrelon selonlelonctelond baselond on common practicelon, and should not belon
 * assumelond to belon optimal for any particular uselon caselon. If you arelon intelonrelonstelond in furthelonr tuning thelon
 * selonttings in this modulelon, it is reloncommelonndelond to crelonatelon local copy for your selonrvicelon.
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
      .withTimelonoutPelonrRelonquelonst(800.millis)
      .withTimelonoutTotal(1200.millis)
      .idelonmpotelonnt(5.pelonrcelonnt)
  }

  ovelonrridelon protelonctelond delonf selonssionAcquisitionTimelonout: Duration = 500.milliselonconds
}
