package com.ExTwitter.graph_feature_service.worker.util

import com.ExTwitter.graph_feature_service.thriftscala.EdgeType
import com.ExTwitter.util.Future

case class GraphContainer(
  graphs: Map[GraphKey, AutoUpdatingGraph]) {

  final val toPartialMap: Map[EdgeType, AutoUpdatingGraph] =
    graphs.collect {
      case (partialValueGraph: PartialValueGraph, graph) =>
        partialValueGraph.edgeType -> graph
    }

  // load all the graphs from constantDB format to memory
  def warmup: Future[Unit] = {
    Future.collect(graphs.mapValues(_.warmup())).unit
  }
}
