package com.tw ter.recos.user_t et_graph

 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.f nagle.trac ng.Trace d
 mport com.tw ter.recos.dec der.Endpo ntLoadS dder
 mport com.tw ter.recos.recos_common.thr ftscala._
 mport com.tw ter.recos.user_t et_graph.thr ftscala._
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  r
 mport scala.concurrent.durat on.M LL SECONDS
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.recos.user_t et_graph.relatedT etHandlers.T etBasedRelatedT etsHandler
 mport com.tw ter.recos.user_t et_graph.relatedT etHandlers.ProducerBasedRelatedT etsHandler
 mport com.tw ter.recos.user_t et_graph.relatedT etHandlers.Consu rsBasedRelatedT etsHandler
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.common.User d

object UserT etGraph {
  def trace d: Trace d = Trace. d
  def cl ent d: Opt on[Cl ent d] = Cl ent d.current
}

class UserT etGraph(
  t etBasedRelatedT etsHandler: T etBasedRelatedT etsHandler,
  producerBasedRelatedT etsHandler: ProducerBasedRelatedT etsHandler,
  consu rsBasedRelatedT etsHandler: Consu rsBasedRelatedT etsHandler,
  endpo ntLoadS dder: Endpo ntLoadS dder
)(
   mpl c  t  r: T  r)
    extends thr ftscala.UserT etGraph. thodPerEndpo nt {

  pr vate val defaultT  out: Durat on = Durat on(50, M LL SECONDS)
  pr vate val EmptyResponse = Future.value(RelatedT etResponse())
  pr vate val EmptyFeatureResponse = Future.value(UserT etFeatureResponse())

  pr vate val log = Logger()

  overr de def recom ndT ets(request: Recom ndT etRequest): Future[Recom ndT etResponse] =
    Future.value(Recom ndT etResponse())

  overr de def getLeftNodeEdges(request: GetRecentEdgesRequest): Future[GetRecentEdgesResponse] =
    Future.value(GetRecentEdgesResponse())

  overr de def getR ghtNode(t et: Long): Future[Node nfo] = Future.value(Node nfo())

  // deprecated
  overr de def relatedT ets(request: RelatedT etRequest): Future[RelatedT etResponse] =
    EmptyResponse

  overr de def t etBasedRelatedT ets(
    request: T etBasedRelatedT etRequest
  ): Future[RelatedT etResponse] =
    endpo ntLoadS dder("t etBasedRelatedT ets") {
      t etBasedRelatedT etsHandler(request).ra seW h n(defaultT  out)
    }.rescue {
      case Endpo ntLoadS dder.LoadS dd ngExcept on =>
        EmptyResponse
      case e =>
        log. nfo("user-t et-graph_t etBasedRelatedT ets" + e)
        EmptyResponse
    }

  overr de def producerBasedRelatedT ets(
    request: ProducerBasedRelatedT etRequest
  ): Future[RelatedT etResponse] =
    endpo ntLoadS dder("producerBasedRelatedT ets") {
      producerBasedRelatedT etsHandler(request).ra seW h n(defaultT  out)
    }.rescue {
      case Endpo ntLoadS dder.LoadS dd ngExcept on =>
        EmptyResponse
      case e =>
        log. nfo("user-t et-graph_producerBasedRelatedT ets" + e)
        EmptyResponse
    }

  overr de def consu rsBasedRelatedT ets(
    request: Consu rsBasedRelatedT etRequest
  ): Future[RelatedT etResponse] =
    endpo ntLoadS dder("consu rsBasedRelatedT ets") {
      consu rsBasedRelatedT etsHandler(request).ra seW h n(defaultT  out)
    }.rescue {
      case Endpo ntLoadS dder.LoadS dd ngExcept on =>
        EmptyResponse
      case e =>
        log. nfo("user-t et-graph_consu rsBasedRelatedT ets" + e)
        EmptyResponse
    }

  // deprecated
  overr de def userT etFeatures(
    user d: User d,
    t et d: T et d
  ): Future[UserT etFeatureResponse] =
    EmptyFeatureResponse

}
