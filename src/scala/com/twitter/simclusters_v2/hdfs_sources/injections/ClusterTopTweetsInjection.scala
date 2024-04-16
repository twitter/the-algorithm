package com.tw ter.s mclusters_v2.hdfs_s ces. nject ons

 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.ScalaCompactThr ft
 mport com.tw ter.s mclusters_v2.thr ftscala.TopKT etsW hScores
 mport com.tw ter.s mclusters_v2.thr ftscala.FullCluster d

object ClusterTopT ets nject on {

  val cluster dToTopKT ets nject on = KeyVal nject on[FullCluster d, TopKT etsW hScores](
    ScalaCompactThr ft(FullCluster d),
    ScalaCompactThr ft(TopKT etsW hScores)
  )
}
