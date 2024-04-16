package com.tw ter.follow_recom ndat ons.common.models

//  ntersect on of recent follo rs and follo d by
tra  HasMutualFollo dUser ds extends HasRecentFollo dUser ds w h HasRecentFollo dByUser ds {

  lazy val recentMutualFollows: Seq[Long] =
    recentFollo dUser ds.getOrElse(N l). ntersect(recentFollo dByUser ds.getOrElse(N l))

  lazy val numRecentMutualFollows:  nt = recentMutualFollows.s ze
}
