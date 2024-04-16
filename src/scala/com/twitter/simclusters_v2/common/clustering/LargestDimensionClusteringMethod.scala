package com.tw ter.s mclusters_v2.common.cluster ng

/**
 * Groups ent  es by a s ngle embedd ng d  ns on w h t  largest score.
 */
class LargestD  ns onCluster ng thod extends Cluster ng thod {

  /**
   * @param embedd ngs   map of ent y  Ds and correspond ng embedd ngs
   * @param s m lar yFn funct on that outputs d screte value (0.0 or 1.0).
   *                     1.0  f t  d  ns ons of t  h g st score (  ght) from two g ven embedd ngs match.
   *                     0.0 ot rw se.
   *                     e.g.
   *                        case 1: E1=[0.0, 0.1, 0.6, 0.2], E2=[0.1, 0.3, 0.8, 0.0]. s m lar yFn(E1, E2)=1.0
   *                        case 2: E1=[0.0, 0.1, 0.6, 0.2], E2=[0.1, 0.4, 0.2, 0.0]. s m lar yFn(E1, E2)=0.0
   * @tparam T embedd ng type. e.g. S mClustersEmbedd ng
   *
   * @return A set of sets of ent y  Ds, each set represent ng a d st nct cluster.
   */
  overr de def cluster[T](
    embedd ngs: Map[Long, T],
    s m lar yFn: (T, T) => Double,
    recordStatCallback: (Str ng, Long) => Un 
  ): Set[Set[Long]] = {

    // rely on cluster ng by connected component.
    // s m lar yThreshold=0.1 because  's larger than 0.0 (s m lar yFn returns 0.0  f two embedd ngs
    // don't share t  largest d  ns on.
    new ConnectedComponentsCluster ng thod(s m lar yThreshold = 0.1)
      .cluster(embedd ngs, s m lar yFn, recordStatCallback)
  }

}
