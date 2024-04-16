package com.tw ter.graph_feature_serv ce.worker.ut l

 mport com.tw ter.graph_feature_serv ce.thr ftscala.EdgeType
 mport com.tw ter.ut l.Future

case class GraphConta ner(
  graphs: Map[GraphKey, AutoUpdat ngGraph]) {

  f nal val toPart alMap: Map[EdgeType, AutoUpdat ngGraph] =
    graphs.collect {
      case (part alValueGraph: Part alValueGraph, graph) =>
        part alValueGraph.edgeType -> graph
    }

  // load all t  graphs from constantDB format to  mory
  def warmup: Future[Un ] = {
    Future.collect(graphs.mapValues(_.warmup())).un 
  }
}
