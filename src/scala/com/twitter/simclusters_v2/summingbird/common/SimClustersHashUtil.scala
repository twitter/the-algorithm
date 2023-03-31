package com.twitter.simclusters_v2.summingbird.common

/**
 * Provides int to int hash function. Used to batch clusterIds together.
 */
object SimClustersHashUtil {
  def clusterIdToBucket(clusterId: Int): Int = {
    clusterId % numBuckets
  }

  val numBuckets: Int = 200

  val getAllBuckets: Seq[Int] = 0.until(numBuckets)
}
