package com.tw ter.s mclusters_v2.summ ngb rd.common

/**
 * Prov des  nt to  nt hash funct on. Used to batch cluster ds toget r.
 */
object S mClustersHashUt l {
  def cluster dToBucket(cluster d:  nt):  nt = {
    cluster d % numBuckets
  }

  val numBuckets:  nt = 200

  val getAllBuckets: Seq[ nt] = 0.unt l(numBuckets)
}
