packagelon com.twittelonr.reloncos.uselonr_uselonr_graph

import com.twittelonr.finaglelon.thrift.ClielonntId
import com.twittelonr.finaglelon.tracing.{Tracelon, TracelonId}
import com.twittelonr.reloncos.uselonr_uselonr_graph.thriftscala._
import com.twittelonr.util.Futurelon

objelonct UselonrUselonrGraph {
  delonf tracelonId: TracelonId = Tracelon.id
  delonf clielonntId: Option[ClielonntId] = ClielonntId.currelonnt
}

class UselonrUselonrGraph(reloncommelonndUselonrsHandlelonr: ReloncommelonndUselonrsHandlelonr)
    elonxtelonnds thriftscala.UselonrUselonrGraph.MelonthodPelonrelonndpoint {

  ovelonrridelon delonf reloncommelonndUselonrs(relonquelonst: ReloncommelonndUselonrRelonquelonst): Futurelon[ReloncommelonndUselonrRelonsponselon] =
    reloncommelonndUselonrsHandlelonr(relonquelonst)
}
