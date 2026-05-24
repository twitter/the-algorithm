package com.twitter.recos.user_user_graph

import com.twitter.finagle.thrift.ClientId
import com.twitter.finagle.tracing.{Trace, TraceId}
import com.twitter.recos.user_user_graph.thriftscala._
import com.twitter.util.Future

object UserUserGraph {
  def traceId: TraceId = Trace.id
  def clientId: Option[ClientId] = ClientId.current
}

class UserUserGraph(recommendUsersHandler: RecommendUsersHandler)
    extends thriftscala.UserUserGraph.MethodPerEndpoint {

  override def recommendUsers(request: RecommendUserRequest): Future[RecommendUserResponse] =
    recommendUsersHandler(request)
}
