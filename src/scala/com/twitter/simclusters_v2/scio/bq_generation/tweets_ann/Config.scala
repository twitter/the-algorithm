package com.twitter.simclusters_v2.scio.bq_generation.tweets_ann

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
  val IIKFHL0EL15ANNOutputPath: String = "tweets_ann/iikf_hl_0_el_15"
  val IIKFHL2EL15ANNOutputPath: String = "tweets_ann/iikf_hl_2_el_15"
  val IIKFHL2EL50ANNOutputPath: String = "tweets_ann/iikf_hl_2_el_50"
  val IIKFHL8EL50ANNOutputPath: String = "tweets_ann/iikf_hl_8_el_50"
  val MTSConsumerEmbeddingsANNOutputPath: String = "tweets_ann/mts_consumer_embeddings"

  /*
   * Variables for tweet embeddings generation
   */
  val SimClustersTweetEmbeddingsGenerationHalfLife: Int = 28800000 // 8hrs in ms
  val SimClustersTweetEmbeddingsGenerationEmbeddingLength: Int = 15

  /*
   * Variables for ANN
   */
  val SimClustersANNTopNClustersPerSourceEmbedding: Int = 20
  val SimClustersANNTopMTweetsPerCluster: Int = 50
  val SimClustersANNTopKTweetsPerUserRequest: Int = 200
}
