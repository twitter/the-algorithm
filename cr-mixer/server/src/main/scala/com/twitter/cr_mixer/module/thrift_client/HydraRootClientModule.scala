packagelon com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.thriftmux.MelonthodBuildelonr
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.hydra.root.{thriftscala => ht}
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.thrift.modulelons.ThriftMelonthodBuildelonrClielonntModulelon

objelonct HydraRootClielonntModulelon
    elonxtelonnds ThriftMelonthodBuildelonrClielonntModulelon[
      ht.HydraRoot.SelonrvicelonPelonrelonndpoint,
      ht.HydraRoot.MelonthodPelonrelonndpoint
    ]
    with MtlsClielonnt {
  ovelonrridelon delonf labelonl: String = "hydra-root"

  ovelonrridelon delonf delonst: String = "/s/hydra/hydra-root"

  ovelonrridelon protelonctelond delonf configurelonMelonthodBuildelonr(
    injelonctor: Injelonctor,
    melonthodBuildelonr: MelonthodBuildelonr
  ): MelonthodBuildelonr = melonthodBuildelonr.withTimelonoutTotal(500.milliselonconds)

}
