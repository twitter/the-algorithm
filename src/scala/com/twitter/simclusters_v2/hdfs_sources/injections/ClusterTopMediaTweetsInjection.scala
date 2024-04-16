package com.tw ter.s mclusters_v2.hdfs_s ces. nject ons

 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.ScalaCompactThr ft
 mport com.tw ter.s mclusters_v2.thr ftscala.{T etsW hScore, DayPart  onedCluster d}

object ClusterTop d aT ets nject on {

  val  nject on = KeyVal nject on[DayPart  onedCluster d, T etsW hScore](
    ScalaCompactThr ft(DayPart  onedCluster d),
    ScalaCompactThr ft(T etsW hScore)
  )
}
