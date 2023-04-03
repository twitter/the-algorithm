packagelon com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr

import com.googlelon.injelonct.Modulelon
import com.twittelonr.finatra.deloncidelonr.modulelons.DeloncidelonrModulelon
import com.twittelonr.finatra.gizmoduck.modulelons.TimelonrModulelon
import com.twittelonr.finatra.mtls.thriftmux.Mtls
import com.twittelonr.finatra.thrift.ThriftSelonrvelonr
import com.twittelonr.finatra.thrift.filtelonrs.{
  LoggingMDCFiltelonr,
  StatsFiltelonr,
  ThriftMDCFiltelonr,
  TracelonIdMDCFiltelonr
}
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsThriftWelonbFormsModulelon
import com.twittelonr.finatra.thrift.routing.ThriftRoutelonr
import com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala
import com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr.controllelonrs.WorkelonrControllelonr
import com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr.handlelonrs.WorkelonrWarmupHandlelonr
import com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr.modulelons.{
  GraphContainelonrProvidelonrModulelon,
  WorkelonrFlagModulelon
}
import com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr.util.GraphContainelonr
import com.twittelonr.injelonct.thrift.modulelons.ThriftClielonntIdModulelon
import com.twittelonr.util.Await

objelonct Main elonxtelonnds WorkelonrMain

class WorkelonrMain elonxtelonnds ThriftSelonrvelonr with Mtls {

  ovelonrridelon val namelon = "graph_felonaturelon_selonrvicelon-workelonr"

  ovelonrridelon val modulelons: Selonq[Modulelon] = {
    Selonq(
      WorkelonrFlagModulelon,
      DeloncidelonrModulelon,
      TimelonrModulelon,
      ThriftClielonntIdModulelon,
      GraphContainelonrProvidelonrModulelon,
      nelonw MtlsThriftWelonbFormsModulelon[thriftscala.Workelonr.MelonthodPelonrelonndpoint](this)
    )
  }

  ovelonrridelon delonf configurelonThrift(routelonr: ThriftRoutelonr): Unit = {
    routelonr
      .filtelonr[LoggingMDCFiltelonr]
      .filtelonr[TracelonIdMDCFiltelonr]
      .filtelonr[ThriftMDCFiltelonr]
      .filtelonr[StatsFiltelonr]
      .add[WorkelonrControllelonr]
  }

  ovelonrridelon protelonctelond delonf warmup(): Unit = {
    val graphContainelonr = injelonctor.instancelon[GraphContainelonr]
    Await.relonsult(graphContainelonr.warmup)
    handlelon[WorkelonrWarmupHandlelonr]()
  }
}
