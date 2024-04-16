package com.tw ter.s mclusters_v2.hdfs_s ces. nject ons

 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.ScalaB naryThr ft
 mport com.tw ter.s mclusters_v2.thr ftscala._
 mport com.tw ter.ml.ap .thr ftscala.Embedd ng
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.Long2B gEnd an
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.ScalaCompactThr ft

object Ent yEmbedd ngs nject ons {

  f nal val Ent yS mClustersEmbedd ng nject on: KeyVal nject on[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] =
    KeyVal nject on(
      ScalaB naryThr ft(S mClustersEmbedd ng d),
      ScalaB naryThr ft(S mClustersEmbedd ng)
    )

  f nal val  nternal dEmbedd ng nject on: KeyVal nject on[
    S mClustersEmbedd ng d,
     nternal dEmbedd ng
  ] =
    KeyVal nject on(
      ScalaB naryThr ft(S mClustersEmbedd ng d),
      ScalaB naryThr ft( nternal dEmbedd ng)
    )

  f nal val Ent yS mClustersMult Embedd ng nject on: KeyVal nject on[
    S mClustersMult Embedd ng d,
    S mClustersMult Embedd ng
  ] =
    KeyVal nject on(
      ScalaB naryThr ft(S mClustersMult Embedd ng d),
      ScalaB naryThr ft(S mClustersMult Embedd ng)
    )

  f nal val UserMbcgEmbedd ng nject on: KeyVal nject on[
    Long,
    Embedd ng
  ] =
    KeyVal nject on[Long, Embedd ng](
      Long2B gEnd an,
      ScalaCompactThr ft(Embedd ng)
    )
}
