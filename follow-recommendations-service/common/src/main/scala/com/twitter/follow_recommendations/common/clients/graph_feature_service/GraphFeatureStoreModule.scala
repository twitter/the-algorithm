packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.graph_felonaturelon_selonrvicelon

import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.common.BaselonClielonntModulelon
import com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala.{Selonrvelonr => GraphFelonaturelonSelonrvicelon}

objelonct GraphFelonaturelonStorelonModulelon
    elonxtelonnds BaselonClielonntModulelon[GraphFelonaturelonSelonrvicelon.MelonthodPelonrelonndpoint]
    with MtlsClielonnt {
  ovelonrridelon val labelonl = "graph_felonaturelon_selonrvicelon"
  ovelonrridelon val delonst = "/s/cassowary/graph_felonaturelon_selonrvicelon-selonrvelonr"
}
