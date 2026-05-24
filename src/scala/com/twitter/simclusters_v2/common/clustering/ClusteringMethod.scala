package com.twitter.simclusters_v2.common.clustering

/**
 * Partitions a set of entities into clusters.
 * NOTE: The selection/construction of the cluster representatives (e.g. medoid, random, average) is implemented in ClusterRepresentativeSelectionMethod.scala
 */
trait ClusteringMethod {

  /**
   * The main external-facing method. Sub-classes should implement this method.
   *
   * @param embeddings map of entity IDs and corresponding embeddings
   * @param similarityFn function that outputs similarity (>=0, the larger, more similar), given two embeddings
   * @tparam T embedding type. e.g. SimClustersEmbedding
   *
   * @return A set of sets of entity IDs, each set representing a distinct cluster.
   */
  def cluster[T](
    embeddings: Map[Long, T],
    similarityFn: (T, T) => Double,
    recordStatCallback: (String, Long) => Unit = (_, _) => ()
  ): Set[Set[Long]]

}

object ClusteringStatistics {

  // Statistics, to be imported where recorded.
  val StatSimilarityGraphTotalBuildTime = "similarity_graph_total_build_time_ms"
  val StatClusteringAlgorithmRunTime = "clustering_algorithm_total_run_time_ms"
  val StatMedoidSelectionTime = "medoid_selection_total_time_ms"
  val StatComputedSimilarityBeforeFilter = "computed_similarity_before_filter"

}
