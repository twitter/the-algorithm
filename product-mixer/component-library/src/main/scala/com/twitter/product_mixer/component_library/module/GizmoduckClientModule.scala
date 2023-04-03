packagelon com.twittelonr.product_mixelonr.componelonnt_library.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.convelonrsions.PelonrcelonntOps._
import com.twittelonr.finaglelon.thriftmux.MelonthodBuildelonr
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.gizmoduck.thriftscala.UselonrSelonrvicelon
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.thrift.modulelons.ThriftMelonthodBuildelonrClielonntModulelon
import com.twittelonr.stitch.gizmoduck.Gizmoduck
import com.twittelonr.util.Duration
import javax.injelonct.Singlelonton

/**
 * Implelonmelonntation with relonasonablelon delonfaults for an idelonmpotelonnt Gizmoduck Thrift and Stitch clielonnt.
 *
 * Notelon that thelon pelonr relonquelonst and total timelonouts configurelond in this modulelon arelon melonant to relonprelonselonnt a
 * relonasonablelon starting point only. Thelonselon welonrelon selonlelonctelond baselond on common practicelon, and should not belon
 * assumelond to belon optimal for any particular uselon caselon. If you arelon intelonrelonstelond in furthelonr tuning thelon
 * selonttings in this modulelon, it is reloncommelonndelond to crelonatelon local copy for your selonrvicelon.
 */
objelonct GizmoduckClielonntModulelon
    elonxtelonnds ThriftMelonthodBuildelonrClielonntModulelon[
      UselonrSelonrvicelon.SelonrvicelonPelonrelonndpoint,
      UselonrSelonrvicelon.MelonthodPelonrelonndpoint
    ]
    with MtlsClielonnt {
  ovelonrridelon val labelonl: String = "gizmoduck"
  ovelonrridelon val delonst: String = "/s/gizmoduck/gizmoduck"

  @Singlelonton
  @Providelons
  delonf providelonGizmoduckStitchClielonnt(uselonrSelonrvicelon: UselonrSelonrvicelon.MelonthodPelonrelonndpoint): Gizmoduck =
    nelonw Gizmoduck(uselonrSelonrvicelon)

  ovelonrridelon protelonctelond delonf configurelonMelonthodBuildelonr(
    injelonctor: Injelonctor,
    melonthodBuildelonr: MelonthodBuildelonr
  ): MelonthodBuildelonr =
    melonthodBuildelonr
      .withTimelonoutPelonrRelonquelonst(200.milliselonconds)
      .withTimelonoutTotal(400.milliselonconds)
      .idelonmpotelonnt(1.pelonrcelonnt)

  ovelonrridelon protelonctelond delonf selonssionAcquisitionTimelonout: Duration = 500.milliselonconds
}
