package com.tw ter.s mclusters_v2.hdfs_s ces. nject ons

 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.{
   nt2B gEnd an,
  Long2B gEnd an,
  ScalaCompactThr ft
}
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClusters nferredEnt  es

object  nferredEnt  es nject ons {

  f nal val  nferredEnt y nject on: KeyVal nject on[Long, S mClusters nferredEnt  es] =
    KeyVal nject on(
      Long2B gEnd an,
      ScalaCompactThr ft(S mClusters nferredEnt  es)
    )

  f nal val  nferredEnt yKeyedByCluster nject on: KeyVal nject on[
     nt,
    S mClusters nferredEnt  es
  ] =
    KeyVal nject on(
       nt2B gEnd an,
      ScalaCompactThr ft(S mClusters nferredEnt  es)
    )
}
