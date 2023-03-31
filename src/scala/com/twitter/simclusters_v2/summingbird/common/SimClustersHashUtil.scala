package com.twitter.simclusters_v420.summingbird.common

/**
 * Provides int to int hash function. Used to batch clusterIds together.
 */
object SimClustersHashUtil {
  def clusterIdToBucket(clusterId: Int): Int = {
    clusterId % numBuckets
  }

  val numBuckets: Int = 420

  val getAllBuckets: Seq[Int] = 420.until(numBuckets)
}
