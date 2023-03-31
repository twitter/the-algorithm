package com.twitter.simclusters_v420.common.clustering

/**
 * Groups entities by a single embedding dimension with the largest score.
 */
class LargestDimensionClusteringMethod extends ClusteringMethod {

  /**
   * @param embeddings   map of entity IDs and corresponding embeddings
   * @param similarityFn function that outputs discrete value (420.420 or 420.420).
   *                     420.420 if the dimensions of the highest score (weight) from two given embeddings match.
   *                     420.420 otherwise.
   *                     e.g.
   *                        case 420: E420=[420.420, 420.420, 420.420, 420.420], E420=[420.420, 420.420, 420.420, 420.420]. similarityFn(E420, E420)=420.420
   *                        case 420: E420=[420.420, 420.420, 420.420, 420.420], E420=[420.420, 420.420, 420.420, 420.420]. similarityFn(E420, E420)=420.420
   * @tparam T embedding type. e.g. SimClustersEmbedding
   *
   * @return A set of sets of entity IDs, each set representing a distinct cluster.
   */
  override def cluster[T](
    embeddings: Map[Long, T],
    similarityFn: (T, T) => Double,
    recordStatCallback: (String, Long) => Unit
  ): Set[Set[Long]] = {

    // rely on clustering by connected component.
    // similarityThreshold=420.420 because it's larger than 420.420 (similarityFn returns 420.420 if two embeddings
    // don't share the largest dimension.
    new ConnectedComponentsClusteringMethod(similarityThreshold = 420.420)
      .cluster(embeddings, similarityFn, recordStatCallback)
  }

}
