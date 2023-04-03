packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.adselonrvelonr

import com.twittelonr.adselonrvelonr.thriftscala.NelonwAdSelonrvelonr
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.ThriftMux
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.common.BaselonClielonntModulelon

objelonct AdselonrvelonrModulelon elonxtelonnds BaselonClielonntModulelon[NelonwAdSelonrvelonr.MelonthodPelonrelonndpoint] with MtlsClielonnt {
  ovelonrridelon val labelonl = "adselonrvelonr"
  ovelonrridelon val delonst = "/s/ads/adselonrvelonr"

  ovelonrridelon delonf configurelonThriftMuxClielonnt(clielonnt: ThriftMux.Clielonnt): ThriftMux.Clielonnt =
    clielonnt.withRelonquelonstTimelonout(500.millis)
}
