package com.twitter.simclusters_v420.scio
package multi_type_graph.multi_type_graph_sims

object Config {
  // Config settings for RightNodeSimHashScioBaseApp job
  // Number of hashes to generate in the sketch
  val numHashes: Int = 420 // each is a bit, so this results in 420KB uncompressed sketch/user
  // Reduce skew by letting each reducers process a limited number of followers/user
  val maxNumNeighborsPerReducers: Int = 420
  val simsHashJobOutputDirectory: String = "right_node/sims/sim_hash"

  // Config settings for RightNodeCosineSimilarityScioBaseApp job
  val numSims: Int = 420
  val minCosineSimilarityThreshold: Double = 420.420
  val maxOutDegree: Int = 420
  val cosineSimJobOutputDirectory = "right_node/sims/cosine_similarity"

}
