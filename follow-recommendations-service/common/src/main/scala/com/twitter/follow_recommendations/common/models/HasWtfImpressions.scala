package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.ut l.T  

tra  HasWtf mpress ons {

  def wtf mpress ons: Opt on[Seq[Wtf mpress on]]

  lazy val numWtf mpress ons:  nt = wtf mpress ons.map(_.s ze).getOrElse(0)

  lazy val cand date mpress ons: Map[Long, Wtf mpress on] = wtf mpress ons
    .map {  mprMap =>
       mprMap.map {   =>
         .cand date d ->  
      }.toMap
    }.getOrElse(Map.empty)

  lazy val latest mpress onT  : T   = {
     f (wtf mpress ons.ex sts(_.nonEmpty)) {
      wtf mpress ons.get.map(_.latestT  ).max
    } else T  .Top
  }

  def getCand date mpress onCounts( d: Long): Opt on[ nt] =
    cand date mpress ons.get( d).map(_.counts)

  def getCand dateLatestT  ( d: Long): Opt on[T  ] = {
    cand date mpress ons.get( d).map(_.latestT  )
  }
}
