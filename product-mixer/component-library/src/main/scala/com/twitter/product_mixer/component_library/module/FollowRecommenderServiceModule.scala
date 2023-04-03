packagelon com.twittelonr.product_mixelonr.componelonnt_library.modulelon

import com.twittelonr.follow_reloncommelonndations.thriftscala.FollowReloncommelonndationsThriftSelonrvicelon
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.convelonrsions.PelonrcelonntOps._
import com.twittelonr.finaglelon.thriftmux.MelonthodBuildelonr
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.thrift.modulelons.ThriftMelonthodBuildelonrClielonntModulelon
import com.twittelonr.util.Duration

objelonct FollowReloncommelonndelonrSelonrvicelonModulelon
    elonxtelonnds ThriftMelonthodBuildelonrClielonntModulelon[
      FollowReloncommelonndationsThriftSelonrvicelon.SelonrvicelonPelonrelonndpoint,
      FollowReloncommelonndationsThriftSelonrvicelon.MelonthodPelonrelonndpoint
    ]
    with MtlsClielonnt {

  ovelonrridelon val labelonl: String = "follow-reloncommelonndations-selonrvicelon"

  ovelonrridelon val delonst: String = "/s/follow-reloncommelonndations/follow-reloncos-selonrvicelon"

  ovelonrridelon protelonctelond delonf configurelonMelonthodBuildelonr(
    injelonctor: Injelonctor,
    melonthodBuildelonr: MelonthodBuildelonr
  ): MelonthodBuildelonr = {
    melonthodBuildelonr
      .withTimelonoutPelonrRelonquelonst(400.millis)
      .withTimelonoutTotal(800.millis)
      .idelonmpotelonnt(5.pelonrcelonnt)
  }

  ovelonrridelon protelonctelond delonf selonssionAcquisitionTimelonout: Duration = 500.milliselonconds
}
