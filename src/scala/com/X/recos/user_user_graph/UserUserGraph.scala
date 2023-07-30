package com.X.recos.user_user_graph

import com.X.finagle.thrift.ClientId
import com.X.finagle.tracing.{Trace, TraceId}
import com.X.recos.user_user_graph.thriftscala._
import com.X.util.Future

object UserUserGraph {
  def traceId: TraceId = Trace.id
  def clientId: Option[ClientId] = ClientId.current
}

class UserUserGraph(recommendUsersHandler: RecommendUsersHandler)
    extends thriftscala.UserUserGraph.MethodPerEndpoint {

  override def recommendUsers(request: RecommendUserRequest): Future[RecommendUserResponse] =
    recommendUsersHandler(request)
}
