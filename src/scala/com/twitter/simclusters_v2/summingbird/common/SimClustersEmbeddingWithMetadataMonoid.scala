package com.tw ter.s mclusters_v2.summ ngb rd.common

 mport com.tw ter.algeb rd.{Mono d, Opt onMono d}
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.summ ngb rd.common.Mono ds.TopKScoresUt ls
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  S mClustersEmbedd ng tadata,
  S mClustersEmbedd ngW h tadata,
  S mClustersEmbedd ng => Thr ftS mClustersEmbedd ng
}

/**
 * Decayed aggregat on of embedd ngs.
 *
 * W n  rg ng 2 embedd ngs, t  older embedd ng's scores are scaled by t  .  f a cluster  s
 * present  n both embedd ngs, t  h g st score (after scal ng)  s used  n t  result.
 *
 * @halfL feMs - def nes how qu ckly a score decays
 * @topK - only t  topk clusters w h t  h g st scores are reta ned  n t  result
 * @threshold - any clusters w h   ghts below threshold are excluded from t  result
 */
class S mClustersEmbedd ngW h tadataMono d(
  halfL feMs: Long,
  topK:  nt,
  threshold: Double)
    extends Mono d[S mClustersEmbedd ngW h tadata] {

  overr de val zero: S mClustersEmbedd ngW h tadata = S mClustersEmbedd ngW h tadata(
    Thr ftS mClustersEmbedd ng(),
    S mClustersEmbedd ng tadata()
  )

  pr vate val opt onLongMono d = new Opt onMono d[Long]()
  pr vate val opt onMaxMono d =
    new Opt onMono d[Long]()(com.tw ter.algeb rd.Max.maxSem group[Long])

  overr de def plus(
    x: S mClustersEmbedd ngW h tadata,
    y: S mClustersEmbedd ngW h tadata
  ): S mClustersEmbedd ngW h tadata = {

    val  rgedClusterScores = TopKScoresUt ls. rgeClusterScoresW hUpdateT  s(
      x = S mClustersEmbedd ng(x.embedd ng).embedd ng,
      xUpdatedAtMs = x. tadata.updatedAtMs.getOrElse(0),
      y = S mClustersEmbedd ng(y.embedd ng).embedd ng,
      yUpdatedAtMs = y. tadata.updatedAtMs.getOrElse(0),
      halfL feMs = halfL feMs,
      topK = topK,
      threshold = threshold
    )
    S mClustersEmbedd ngW h tadata(
      embedd ng = S mClustersEmbedd ng( rgedClusterScores).toThr ft,
       tadata = S mClustersEmbedd ng tadata(
        updatedAtMs = opt onMaxMono d.plus(x. tadata.updatedAtMs, y. tadata.updatedAtMs),
        updatedCount = opt onLongMono d.plus(x. tadata.updatedCount, y. tadata.updatedCount)
      )
    )
  }
}
