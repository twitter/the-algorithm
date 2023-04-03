packagelon com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.thriftmux.MelonthodBuildelonr
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.thrift.modulelons.ThriftMelonthodBuildelonrClielonntModulelon
import com.twittelonr.hydra.partition.{thriftscala => ht}

objelonct HydraPartitionClielonntModulelon
    elonxtelonnds ThriftMelonthodBuildelonrClielonntModulelon[
      ht.HydraPartition.SelonrvicelonPelonrelonndpoint,
      ht.HydraPartition.MelonthodPelonrelonndpoint
    ]
    with MtlsClielonnt {
  ovelonrridelon delonf labelonl: String = "hydra-partition"

  ovelonrridelon delonf delonst: String = "/s/hydra/hydra-partition"

  ovelonrridelon protelonctelond delonf configurelonMelonthodBuildelonr(
    injelonctor: Injelonctor,
    melonthodBuildelonr: MelonthodBuildelonr
  ): MelonthodBuildelonr = melonthodBuildelonr.withTimelonoutTotal(500.milliselonconds)

}
