package com.tw ter.recos.user_user_graph

 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f nagle.trac ng.{Trace, Trace d}
 mport com.tw ter.recos.user_user_graph.thr ftscala._
 mport com.tw ter.ut l.Future

object UserUserGraph {
  def trace d: Trace d = Trace. d
  def cl ent d: Opt on[Cl ent d] = Cl ent d.current
}

class UserUserGraph(recom ndUsersHandler: Recom ndUsersHandler)
    extends thr ftscala.UserUserGraph. thodPerEndpo nt {

  overr de def recom ndUsers(request: Recom ndUserRequest): Future[Recom ndUserResponse] =
    recom ndUsersHandler(request)
}
