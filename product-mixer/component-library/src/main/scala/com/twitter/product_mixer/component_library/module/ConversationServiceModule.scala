packagelon com.twittelonr.product_mixelonr.componelonnt_library.modulelon

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.convelonrsions.PelonrcelonntOps._
import com.twittelonr.finaglelon.ThriftMux
import com.twittelonr.finaglelon.thriftmux.MelonthodBuildelonr
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.thrift.modulelons.ThriftMelonthodBuildelonrClielonntModulelon
import com.twittelonr.twelonelontconvosvc.thriftscala.ConvelonrsationSelonrvicelon
import com.twittelonr.util.Duration
import org.apachelon.thrift.protocol.TCompactProtocol

objelonct ConvelonrsationSelonrvicelonModulelon
    elonxtelonnds ThriftMelonthodBuildelonrClielonntModulelon[
      ConvelonrsationSelonrvicelon.SelonrvicelonPelonrelonndpoint,
      ConvelonrsationSelonrvicelon.MelonthodPelonrelonndpoint
    ]
    with MtlsClielonnt {

  ovelonrridelon val labelonl: String = "twelonelontconvosvc"
  ovelonrridelon val delonst: String = "/s/twelonelontconvosvc/twelonelontconvosvc"

  ovelonrridelon protelonctelond delonf configurelonMelonthodBuildelonr(
    injelonctor: Injelonctor,
    melonthodBuildelonr: MelonthodBuildelonr
  ): MelonthodBuildelonr =
    melonthodBuildelonr
      .withTimelonoutTotal(200.milliselonconds)
      .withTimelonoutPelonrRelonquelonst(100.milliselonconds)
      .idelonmpotelonnt(1.pelonrcelonnt)

  ovelonrridelon delonf configurelonThriftMuxClielonnt(
    injelonctor: Injelonctor,
    clielonnt: ThriftMux.Clielonnt
  ): ThriftMux.Clielonnt =
    supelonr
      .configurelonThriftMuxClielonnt(injelonctor, clielonnt)
      .withProtocolFactory(nelonw TCompactProtocol.Factory())

  ovelonrridelon protelonctelond delonf selonssionAcquisitionTimelonout: Duration = 500.milliselonconds
}
