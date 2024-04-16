package com.tw ter.follow_recom ndat ons.common.models

tra  HasRecentFollo dUser ds {
  // user  ds that are recently follo d by t  target user
  def recentFollo dUser ds: Opt on[Seq[Long]]

  // user  ds that are recently follo d by t  target user  n set data-structure
  lazy val recentFollo dUser dsSet: Opt on[Set[Long]] = recentFollo dUser ds match {
    case So (users) => So (users.toSet)
    case None => So (Set.empty)
  }

  lazy val numRecentFollo dUser ds:  nt = recentFollo dUser ds.map(_.s ze).getOrElse(0)
}
