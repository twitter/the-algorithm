package com.tw ter.s mclusters_v2.common.cluster ng

 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.thr ftscala.Ne ghborW h  ghts

class  do dRepresentat veSelect on thod[T](
  producerProducerS m lar yFn: (T, T) => Double)
    extends ClusterRepresentat veSelect on thod[T] {

  /**
   *  dent fy t   do d of a cluster and return  .
   *
   * @param cluster A set of Ne ghborW h  ghts.
   * @param embedd ngs A map of producer  D -> embedd ng.
   */
  def selectClusterRepresentat ve(
    cluster: Set[Ne ghborW h  ghts],
    embedd ngs: Map[User d, T],
  ): User d = {
    val key = cluster.maxBy {
       d1 => // maxBy because   use s m lar y, wh ch gets larger as   get closer.
        val v = embedd ngs( d1.ne ghbor d)
        cluster
          .map( d2 => producerProducerS m lar yFn(v, embedd ngs( d2.ne ghbor d))).sum
    }
    key.ne ghbor d
  }
}
