package com.twitter.simclusters_v2.scio.bq_generation.ftr_tweet

object Config {
  //   Variables for MH output path
  val FTRRootMHPath: String = "manhattan_sequence_files/ftr_tweet_embedding/"
  val FTRAdhocpath: String = "adhoc/ftr_tweet_embedding/"
  val IIKFFTRAdhocANNOutputPath: String = "ftr_tweets_test/your_ldap_test"
  val IIKFFTRAt5Pop1000ANNOutputPath: String = "ftr_tweets/ftr_at_5_pop_biased_1000"
  val IIKFFTRAt5Pop10000ANNOutputPath: String = "ftr_tweets/ftr_at_5_pop_biased_10000"
  val IIKFDecayedSumANNOutputPath: String = "ftr_tweets/decayed_sum"

  val DecayedSumClusterToTweetIndexOutputPath = "ftr_cluster_to_tweet/decayed_sum"

  val FTRPop1000RankDecay11ClusterToTweetIndexOutputPath =
    "ftr_cluster_to_tweet/ftr_pop1000_rnkdecay11"
  val FTRPop10000RankDecay11ClusterToTweetIndexOutputPath =
    "ftr_cluster_to_tweet/ftr_pop10000_rnkdecay11"
  val OONFTRPop1000RankDecayClusterToTweetIndexOutputPath =
    "oon_ftr_cluster_to_tweet/oon_ftr_pop1000_rnkdecay"

  //  Variables for tweet embeddings generation
  val TweetSampleRate = 1 // 100% sample rate
  val EngSampleRate = 1 // engagement from 50% of users
  val MinTweetFavs = 8 // min favs for tweets
  val MinTweetImps = 50 // min impressions for tweets
  val MaxTweetFTR = 0.5 // maximum tweet FTR, a way to combat spammy tweets
  val MaxUserLogNImps = 5 // maximum number of impressions 1e5 for users
  val MaxUserLogNFavs = 4 // maximum number of favs 1e4 for users
  val MaxUserFTR = 0.3 // maximum user FTR, a way to combat accounts that fav everything

  val SimClustersTweetEmbeddingsGenerationHalfLife: Int = 28800000 // 8hrs in ms
  val SimClustersTweetEmbeddingsGenerationEmbeddingLength = 15

  //  Variables for BQ ANN
  val SimClustersANNTopNClustersPerSourceEmbedding: Int = 20
  val SimClustersANNTopMTweetsPerCluster: Int = 50
  val SimClustersANNTopKTweetsPerUserRequest: Int = 200

  //  Cluster-to-tweet index configs
  val clusterTopKTweets: Int = 2000
  val maxTweetAgeHours: Int = 24
  val TweetEmbeddingHalfLife: Int = 28800000 // for usage in DecayedValue struct
}
