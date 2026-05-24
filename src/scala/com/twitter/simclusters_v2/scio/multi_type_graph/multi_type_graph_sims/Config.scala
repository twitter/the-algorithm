package com.twitter.simclusters_v2.scio
package multi_type_graph.multi_type_graph_sims

object Config {
  // Config settings for RightNodeSimHashScioBaseApp job
  // Number of hashes to generate in the sketch
  val numHashes: Int = 8192 // each is a bit, so this results in 1KB uncompressed sketch/user
  // Reduce skew by letting each reducers process a limited number of followers/user
  val maxNumNeighborsPerReducers: Int = 300000
  val simsHashJobOutputDirectory: String = "right_node/sims/sim_hash"

  // Config settings for RightNodeCosineSimilarityScioBaseApp job
  val numSims: Int = 500
  val minCosineSimilarityThreshold: Double = 0.01
  val maxOutDegree: Int = 10000
  val cosineSimJobOutputDirectory = "right_node/sims/cosine_similarity"

}
