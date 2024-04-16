package com.tw ter.recos.graph_common

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.recos.recos_common.thr ftscala.{
  Soc alProofType,
  GetRecentEdgesRequest,
  GetRecentEdgesResponse,
  Node nfo,
  RecentEdge
}
 mport com.tw ter.recos.ut l.Stats._
 mport com.tw ter.servo.request._
 mport com.tw ter.ut l.Future

/**
 *  mple ntat on of t  Thr ft-def ned serv ce  nterface.
 */
class LeftNodeEdgesHandler(graph lper: B part eGraph lper, statsRece ver: StatsRece ver)
    extends RequestHandler[GetRecentEdgesRequest, GetRecentEdgesResponse] {
  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )

  pr vate val CL CK = 0
  pr vate val FAVOR TE = 1
  pr vate val RETWEET = 2
  pr vate val REPLY = 3
  pr vate val TWEET = 4

  overr de def apply(request: GetRecentEdgesRequest): Future[GetRecentEdgesResponse] = {
    trackFutureBlockStats(stats) {
      val recentEdges = graph lper.getLeftNodeEdges(request.request d).flatMap {
        case (node, engage ntType)  f engage ntType == CL CK =>
          So (RecentEdge(node, Soc alProofType.Cl ck))
        case (node, engage ntType)  f engage ntType == FAVOR TE =>
          So (RecentEdge(node, Soc alProofType.Favor e))
        case (node, engage ntType)  f engage ntType == RETWEET =>
          So (RecentEdge(node, Soc alProofType.Ret et))
        case (node, engage ntType)  f engage ntType == REPLY =>
          So (RecentEdge(node, Soc alProofType.Reply))
        case (node, engage ntType)  f engage ntType == TWEET =>
          So (RecentEdge(node, Soc alProofType.T et))
        case _ =>
          None
      }
      Future.value(GetRecentEdgesResponse(recentEdges))
    }
  }
}

class R ghtNode nfoHandler(graph lper: B part eGraph lper, statsRece ver: StatsRece ver)
    extends RequestHandler[Long, Node nfo] {
  pr vate[t ] val stats = statsRece ver.scope(t .getClass.getS mpleNa )

  overr de def apply(r ghtNode: Long): Future[Node nfo] = {
    trackFutureBlockStats(stats) {
      val edges = graph lper.getR ghtNodeEdges(r ghtNode)
      Future.value(Node nfo(edges = edges))
    }
  }
}
