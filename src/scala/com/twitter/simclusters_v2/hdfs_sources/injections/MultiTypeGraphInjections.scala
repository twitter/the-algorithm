package com.tw ter.s mclusters_v2.hdfs_s ces. nject ons

 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.ScalaCompactThr ft
 mport com.tw ter.s mclusters_v2.thr ftscala.LeftNode
 mport com.tw ter.s mclusters_v2.thr ftscala.NounW hFrequencyL st
 mport com.tw ter.s mclusters_v2.thr ftscala.R ghtNode
 mport com.tw ter.s mclusters_v2.thr ftscala.R ghtNodeTypeStruct
 mport com.tw ter.s mclusters_v2.thr ftscala.R ghtNodeW hEdge  ghtL st
 mport com.tw ter.s mclusters_v2.thr ftscala.S m larR ghtNodes
 mport com.tw ter.s mclusters_v2.thr ftscala.Cand dateT etsL st
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.Long2B gEnd an

object Mult TypeGraph nject ons {
  f nal val truncatedMult TypeGraph nject on =
    KeyVal nject on(ScalaCompactThr ft(LeftNode), ScalaCompactThr ft(R ghtNodeW hEdge  ghtL st))
  f nal val topKR ghtNounL st nject on =
    KeyVal nject on(
      ScalaCompactThr ft(R ghtNodeTypeStruct),
      ScalaCompactThr ft(NounW hFrequencyL st))
  f nal val s m larR ghtNodes nject on =
    KeyVal nject on[R ghtNode, S m larR ghtNodes](
      ScalaCompactThr ft(R ghtNode),
      ScalaCompactThr ft(S m larR ghtNodes)
    )
  f nal val t etRecom ndat ons nject on =
    KeyVal nject on[Long, Cand dateT etsL st](
      Long2B gEnd an,
      ScalaCompactThr ft(Cand dateT etsL st)
    )
}
