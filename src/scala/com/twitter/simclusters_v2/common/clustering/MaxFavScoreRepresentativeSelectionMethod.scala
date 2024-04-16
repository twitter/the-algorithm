package com.tw ter.s mclusters_v2.common.cluster ng

 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.thr ftscala.Ne ghborW h  ghts

class MaxFavScoreRepresentat veSelect on thod[T] extends ClusterRepresentat veSelect on thod[T] {

  /**
   *  dent fy t   mber w h largest favScoreHalfL fe100Days and return  .
   *
   * @param cluster A set of Ne ghborW h  ghts.
   * @param embedd ngs A map of producer  D -> embedd ng.
   */
  def selectClusterRepresentat ve(
    cluster: Set[Ne ghborW h  ghts],
    embedd ngs: Map[User d, T],
  ): User d = {
    val key = cluster.maxBy { x: Ne ghborW h  ghts => x.favScoreHalfL fe100Days.getOrElse(0.0) }
    key.ne ghbor d
  }
}
