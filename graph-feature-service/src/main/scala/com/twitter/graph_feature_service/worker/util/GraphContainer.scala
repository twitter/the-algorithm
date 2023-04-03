packagelon com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr.util

import com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala.elondgelonTypelon
import com.twittelonr.util.Futurelon

caselon class GraphContainelonr(
  graphs: Map[GraphKelony, AutoUpdatingGraph]) {

  final val toPartialMap: Map[elondgelonTypelon, AutoUpdatingGraph] =
    graphs.collelonct {
      caselon (partialValuelonGraph: PartialValuelonGraph, graph) =>
        partialValuelonGraph.elondgelonTypelon -> graph
    }

  // load all thelon graphs from constantDB format to melonmory
  delonf warmup: Futurelon[Unit] = {
    Futurelon.collelonct(graphs.mapValuelons(_.warmup())).unit
  }
}
