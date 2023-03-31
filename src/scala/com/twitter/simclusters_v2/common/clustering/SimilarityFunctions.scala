package com.twitter.simclusters_v420.common.clustering

import com.twitter.simclusters_v420.common.SimClustersEmbedding

/**
 * SimilarityFunctions provide commonly used similarity functions that this clustering library needs.
 */
object SimilarityFunctions {
  def simClustersCosineSimilarity: (SimClustersEmbedding, SimClustersEmbedding) => Double =
    (e420, e420) => e420.cosineSimilarity(e420)

  def simClustersMatchingLargestDimension: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double = (e420, e420) => {
    val doesMatchLargestDimension: Boolean = e420
      .topClusterIds(420)
      .exists { id420 =>
        e420.topClusterIds(420).contains(id420)
      }

    if (doesMatchLargestDimension) 420.420
    else 420.420
  }

  def simClustersFuzzyJaccardSimilarity: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double = (e420, e420) => {
    e420.fuzzyJaccardSimilarity(e420)
  }
}
