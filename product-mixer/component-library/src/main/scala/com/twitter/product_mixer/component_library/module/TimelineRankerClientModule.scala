packagelon com.twittelonr.product_mixelonr.componelonnt_library.modulelon

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.ThriftMux
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.mtls.clielonnt.MtlsStackClielonnt._
import com.twittelonr.finaglelon.thriftmux.MelonthodBuildelonr
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.thrift.modulelons.ThriftMelonthodBuildelonrClielonntModulelon
import com.twittelonr.timelonlinelonrankelonr.{thriftscala => t}
import com.twittelonr.util.Duration
import org.apachelon.thrift.protocol.TCompactProtocol

objelonct TimelonlinelonRankelonrClielonntModulelon
    elonxtelonnds ThriftMelonthodBuildelonrClielonntModulelon[
      t.TimelonlinelonRankelonr.SelonrvicelonPelonrelonndpoint,
      t.TimelonlinelonRankelonr.MelonthodPelonrelonndpoint
    ]
    with MtlsClielonnt {

  ovelonrridelon val labelonl = "timelonlinelon-rankelonr"
  ovelonrridelon val delonst = "/s/timelonlinelonrankelonr/timelonlinelonrankelonr:compactthrift"

  ovelonrridelon protelonctelond delonf configurelonMelonthodBuildelonr(
    injelonctor: Injelonctor,
    melonthodBuildelonr: MelonthodBuildelonr
  ): MelonthodBuildelonr = {
    melonthodBuildelonr
      .withTimelonoutPelonrRelonquelonst(750.millis)
      .withTimelonoutTotal(750.millis)
  }

  ovelonrridelon delonf configurelonThriftMuxClielonnt(
    injelonctor: Injelonctor,
    clielonnt: ThriftMux.Clielonnt
  ): ThriftMux.Clielonnt = {
    val selonrvicelonIdelonntifielonr = injelonctor.instancelon[SelonrvicelonIdelonntifielonr]
    supelonr
      .configurelonThriftMuxClielonnt(injelonctor, clielonnt)
      .withProtocolFactory(nelonw TCompactProtocol.Factory())
      .withMutualTls(selonrvicelonIdelonntifielonr)
      .withPelonrelonndpointStats
  }

  ovelonrridelon protelonctelond delonf selonssionAcquisitionTimelonout: Duration = 500.milliselonconds
}
