package com.tw ter.follow_recom ndat ons.common.models

tra  HasRecentFollo dUser dsW hT   {
  // user  ds that are recently follo d by t  target user
  def recentFollo dUser dsW hT  : Opt on[Seq[User dW hT  stamp]]

  lazy val numRecentFollo dUser dsW hT  :  nt =
    recentFollo dUser dsW hT  .map(_.s ze).getOrElse(0)
}
