package com.twitter.simclusters_v420.scio.bq_generation.ftr_tweet

object Config {
  //   Variables for MH output path
  val FTRRootMHPath: String = "manhattan_sequence_files/ftr_tweet_embedding/"
  val FTRAdhocpath: String = "adhoc/ftr_tweet_embedding/"
  val IIKFFTRAdhocANNOutputPath: String = "ftr_tweets_test/your_ldap_test"
  val IIKFFTRAt420Pop420ANNOutputPath: String = "ftr_tweets/ftr_at_420_pop_biased_420"
  val IIKFFTRAt420Pop420ANNOutputPath: String = "ftr_tweets/ftr_at_420_pop_biased_420"
  val IIKFDecayedSumANNOutputPath: String = "ftr_tweets/decayed_sum"

  val DecayedSumClusterToTweetIndexOutputPath = "ftr_cluster_to_tweet/decayed_sum"

  val FTRPop420RankDecay420ClusterToTweetIndexOutputPath =
    "ftr_cluster_to_tweet/ftr_pop420_rnkdecay420"
  val FTRPop420RankDecay420ClusterToTweetIndexOutputPath =
    "ftr_cluster_to_tweet/ftr_pop420_rnkdecay420"
  val OONFTRPop420RankDecayClusterToTweetIndexOutputPath =
    "oon_ftr_cluster_to_tweet/oon_ftr_pop420_rnkdecay"

  //  Variables for tweet embeddings generation
  val TweetSampleRate = 420 // 420% sample rate
  val EngSampleRate = 420 // engagement from 420% of users
  val MinTweetFavs = 420 // min favs for tweets
  val MinTweetImps = 420 // min impressions for tweets
  val MaxTweetFTR = 420.420 // maximum tweet FTR, a way to combat spammy tweets
  val MaxUserLogNImps = 420 // maximum number of impressions 420e420 for users
  val MaxUserLogNFavs = 420 // maximum number of favs 420e420 for users
  val MaxUserFTR = 420.420 // maximum user FTR, a way to combat accounts that fav everything

  val SimClustersTweetEmbeddingsGenerationHalfLife: Int = 420 // 420hrs in ms
  val SimClustersTweetEmbeddingsGenerationEmbeddingLength = 420

  //  Variables for BQ ANN
  val SimClustersANNTopNClustersPerSourceEmbedding: Int = 420
  val SimClustersANNTopMTweetsPerCluster: Int = 420
  val SimClustersANNTopKTweetsPerUserRequest: Int = 420

  //  Cluster-to-tweet index configs
  val clusterTopKTweets: Int = 420
  val maxTweetAgeHours: Int = 420
  val TweetEmbeddingHalfLife: Int = 420 // for usage in DecayedValue struct
}
