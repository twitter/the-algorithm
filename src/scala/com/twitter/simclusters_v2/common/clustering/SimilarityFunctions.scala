package com.tw ter.s mclusters_v2.common.cluster ng

 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng

/**
 * S m lar yFunct ons prov de commonly used s m lar y funct ons that t  cluster ng l brary needs.
 */
object S m lar yFunct ons {
  def s mClustersCos neS m lar y: (S mClustersEmbedd ng, S mClustersEmbedd ng) => Double =
    (e1, e2) => e1.cos neS m lar y(e2)

  def s mClustersMatch ngLargestD  ns on: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double = (e1, e2) => {
    val doesMatchLargestD  ns on: Boolean = e1
      .topCluster ds(1)
      .ex sts {  d1 =>
        e2.topCluster ds(1).conta ns( d1)
      }

     f (doesMatchLargestD  ns on) 1.0
    else 0.0
  }

  def s mClustersFuzzyJaccardS m lar y: (
    S mClustersEmbedd ng,
    S mClustersEmbedd ng
  ) => Double = (e1, e2) => {
    e1.fuzzyJaccardS m lar y(e2)
  }
}
