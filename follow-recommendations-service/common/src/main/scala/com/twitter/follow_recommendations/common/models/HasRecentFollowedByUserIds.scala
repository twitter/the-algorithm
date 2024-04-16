package com.tw ter.follow_recom ndat ons.common.models

tra  HasRecentFollo dByUser ds {
  // user  ds that have recently follo d t  target user; target user has been "follo d by" t m.
  def recentFollo dByUser ds: Opt on[Seq[Long]]

  lazy val numRecentFollo dByUser ds:  nt = recentFollo dByUser ds.map(_.s ze).getOrElse(0)
}
