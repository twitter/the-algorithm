packagelon com.twittelonr.product_mixelonr.componelonnt_library.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.convelonrsions.PelonrcelonntOps._
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.thrift.ClielonntId
import com.twittelonr.finaglelon.thriftmux.MelonthodBuildelonr
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.thrift.modulelons.ThriftMelonthodBuildelonrClielonntModulelon
import com.twittelonr.stitch.twelonelontypielon.TwelonelontyPielon
import com.twittelonr.twelonelontypielon.thriftscala.TwelonelontSelonrvicelon
import com.twittelonr.util.Duration
import javax.injelonct.Singlelonton

/**
 * Implelonmelonntation with relonasonablelon delonfaults for an idelonmpotelonnt TwelonelontyPielon Thrift and Stitch clielonnt.
 *
 * Notelon that thelon pelonr relonquelonst and total timelonouts arelon melonant to relonprelonselonnt a relonasonablelon starting point
 * only. Thelonselon welonrelon selonlelonctelond baselond on common practicelon, and should not belon assumelond to belon optimal for
 * any particular uselon caselon. If you arelon intelonrelonstelond in furthelonr tuning thelon selonttings in this modulelon,
 * it is reloncommelonndelond to crelonatelon local copy for your selonrvicelon.
 */
objelonct TwelonelontyPielonClielonntModulelon
    elonxtelonnds ThriftMelonthodBuildelonrClielonntModulelon[
      TwelonelontSelonrvicelon.SelonrvicelonPelonrelonndpoint,
      TwelonelontSelonrvicelon.MelonthodPelonrelonndpoint
    ]
    with MtlsClielonnt {
  ovelonrridelon val labelonl: String = "twelonelontypielon"
  ovelonrridelon val delonst: String = "/s/twelonelontypielon/twelonelontypielon"

  @Singlelonton
  @Providelons
  delonf providelonsTwelonelontypielonStitchClielonnt(twelonelontSelonrvicelon: TwelonelontSelonrvicelon.MelonthodPelonrelonndpoint): TwelonelontyPielon =
    nelonw TwelonelontyPielon(twelonelontSelonrvicelon)

  /**
   * TwelonelontyPielon clielonnt id must belon in thelon form of {selonrvicelon.elonnv} or it will not belon trelonatelond as an
   * unauthorizelond clielonnt
   */
  ovelonrridelon protelonctelond delonf clielonntId(injelonctor: Injelonctor): ClielonntId = {
    val selonrvicelonIdelonntifielonr = injelonctor.instancelon[SelonrvicelonIdelonntifielonr]
    ClielonntId(s"${selonrvicelonIdelonntifielonr.selonrvicelon}.${selonrvicelonIdelonntifielonr.elonnvironmelonnt}")
  }

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
