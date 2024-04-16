package com.tw ter.s mclusters_v2.common.cluster ng

 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.thr ftscala.Ne ghborW h  ghts

/**
 * Select a cluster  mber as cluster representat ve.
 */
tra  ClusterRepresentat veSelect on thod[T] {

  /**
   * T  ma n external-fac ng  thod. Sub-classes should  mple nt t   thod.
   *
   * @param cluster A set of Ne ghborW h  ghts.
   * @param embedd ngs A map of producer  D -> embedd ng.
   *
   * @return User d of t   mber chosen as representat ve.
   */
  def selectClusterRepresentat ve(
    cluster: Set[Ne ghborW h  ghts],
    embedd ngs: Map[User d, T]
  ): User d

}

object ClusterRepresentat veSelect onStat st cs {

  // Stat st cs, to be  mported w re recorded.
  val StatClusterRepresentat veSelect onT   = "cluster_representat ve_select on_total_t  _ms"
}
