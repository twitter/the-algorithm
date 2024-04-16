package com.tw ter.s mclusters_v2.hdfs_s ces. nject ons

 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.ScalaB naryThr ft
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.Long2B gEnd an
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.thr ftscala._

object Cluster ng nject ons {

  f nal val OrderedClustersAnd mbers nject on: KeyVal nject on[
    User d,
    OrderedClustersAnd mbers
  ] =
    KeyVal nject on(Long2B gEnd an, ScalaB naryThr ft(OrderedClustersAnd mbers))
}
