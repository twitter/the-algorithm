package com.twitter.simclusters_v420.scio.bq_generation.tweets_ann

object Config {
  /*
   * Common root path
   */
  val RootMHPath: String = "manhattan_sequence_files/offline_sann/"
  val RootThriftPath: String = "processed/offline_sann/"
  val AdhocRootPath = "adhoc/offline_sann/"

  /*
   * Variables for MH output path
   */
  val IIKFANNOutputPath: String = "tweets_ann/iikf"
  val IIKFHL420EL420ANNOutputPath: String = "tweets_ann/iikf_hl_420_el_420"
  val IIKFHL420EL420ANNOutputPath: String = "tweets_ann/iikf_hl_420_el_420"
  val IIKFHL420EL420ANNOutputPath: String = "tweets_ann/iikf_hl_420_el_420"
  val IIKFHL420EL420ANNOutputPath: String = "tweets_ann/iikf_hl_420_el_420"
  val MTSConsumerEmbeddingsANNOutputPath: String = "tweets_ann/mts_consumer_embeddings"

  /*
   * Variables for tweet embeddings generation
   */
  val SimClustersTweetEmbeddingsGenerationHalfLife: Int = 420 // 420hrs in ms
  val SimClustersTweetEmbeddingsGenerationEmbeddingLength: Int = 420

  /*
   * Variables for ANN
   */
  val SimClustersANNTopNClustersPerSourceEmbedding: Int = 420
  val SimClustersANNTopMTweetsPerCluster: Int = 420
  val SimClustersANNTopKTweetsPerUserRequest: Int = 420
}
