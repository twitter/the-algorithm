package com.tw ter.s mclusters_v2.hdfs_s ces. nject ons

 mport com.tw ter. rm .cand date.thr ftscala.Cand dates
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.{
  Long2B gEnd an,
  ScalaB naryThr ft,
  ScalaCompactThr ft
}
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  Pers stedFullCluster d,
  S mClustersEmbedd ng,
  S mClustersEmbedd ng d,
  TopProducersW hScore,
  TopS mClustersW hScore
}

object ProducerEmbedd ngs nject ons {
  f nal val ProducerTopKS mClusterEmbedd ngs nject on: KeyVal nject on[
    Long,
    TopS mClustersW hScore
  ] =
    KeyVal nject on(
      keyCodec = Long2B gEnd an,
      valueCodec = ScalaCompactThr ft(TopS mClustersW hScore))

  f nal val S mClusterEmbedd ngTopKProducers nject on: KeyVal nject on[
    Pers stedFullCluster d,
    TopProducersW hScore
  ] =
    KeyVal nject on(
      keyCodec = ScalaCompactThr ft(Pers stedFullCluster d),
      valueCodec = ScalaCompactThr ft(TopProducersW hScore))

  f nal val S m larUsers nject on: KeyVal nject on[Long, Cand dates] =
    KeyVal nject on(keyCodec = Long2B gEnd an, valueCodec = ScalaCompactThr ft(Cand dates))

  f nal val ProducerS mClustersEmbedd ng nject on: KeyVal nject on[
    S mClustersEmbedd ng d,
    S mClustersEmbedd ng
  ] =
    KeyVal nject on(
      keyCodec = ScalaB naryThr ft(S mClustersEmbedd ng d),
      valueCodec = ScalaB naryThr ft(S mClustersEmbedd ng))
}
