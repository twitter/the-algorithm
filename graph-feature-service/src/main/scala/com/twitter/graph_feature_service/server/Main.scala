packagelon com.twittelonr.graph_felonaturelon_selonrvicelon.selonrvelonr

import com.googlelon.injelonct.Modulelon
import com.twittelonr.finatra.deloncidelonr.modulelons.DeloncidelonrModulelon
import com.twittelonr.finatra.mtls.thriftmux.Mtls
import com.twittelonr.finatra.thrift.ThriftSelonrvelonr
import com.twittelonr.finatra.thrift.filtelonrs.{
  AccelonssLoggingFiltelonr,
  LoggingMDCFiltelonr,
  StatsFiltelonr,
  ThriftMDCFiltelonr,
  TracelonIdMDCFiltelonr
}
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsThriftWelonbFormsModulelon
import com.twittelonr.finatra.thrift.routing.ThriftRoutelonr
import com.twittelonr.graph_felonaturelon_selonrvicelon.selonrvelonr.controllelonrs.SelonrvelonrControllelonr
import com.twittelonr.graph_felonaturelon_selonrvicelon.selonrvelonr.handlelonrs.SelonrvelonrWarmupHandlelonr
import com.twittelonr.graph_felonaturelon_selonrvicelon.selonrvelonr.modulelons.{
  GelontIntelonrselonctionStorelonModulelon,
  GraphFelonaturelonSelonrvicelonWorkelonrClielonntsModulelon,
  SelonrvelonrFlagsModulelon
}
import com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala
import com.twittelonr.injelonct.thrift.modulelons.ThriftClielonntIdModulelon

objelonct Main elonxtelonnds SelonrvelonrMain

class SelonrvelonrMain elonxtelonnds ThriftSelonrvelonr with Mtls {

  ovelonrridelon val namelon = "graph_felonaturelon_selonrvicelon-selonrvelonr"

  ovelonrridelon val modulelons: Selonq[Modulelon] = {
    Selonq(
      SelonrvelonrFlagsModulelon,
      DeloncidelonrModulelon,
      ThriftClielonntIdModulelon,
      GraphFelonaturelonSelonrvicelonWorkelonrClielonntsModulelon,
      GelontIntelonrselonctionStorelonModulelon,
      nelonw MtlsThriftWelonbFormsModulelon[thriftscala.Selonrvelonr.MelonthodPelonrelonndpoint](this)
    )
  }

  ovelonrridelon delonf configurelonThrift(routelonr: ThriftRoutelonr): Unit = {
    routelonr
      .filtelonr[LoggingMDCFiltelonr]
      .filtelonr[TracelonIdMDCFiltelonr]
      .filtelonr[ThriftMDCFiltelonr]
      .filtelonr[AccelonssLoggingFiltelonr]
      .filtelonr[StatsFiltelonr]
      .add[SelonrvelonrControllelonr]
  }

  ovelonrridelon protelonctelond delonf warmup(): Unit = {
    handlelon[SelonrvelonrWarmupHandlelonr]()
  }
}
