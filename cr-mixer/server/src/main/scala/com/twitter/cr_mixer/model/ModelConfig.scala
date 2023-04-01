package com.twitter.cr_mixer.model

/**
 * A Configuration class for all Model Based Candidate Sources.
 *
 * The Model Name Guideline. Please your modelId as "Algorithm_Product_Date"
 * If your model is used for multiple product surfaces, name it as all
 * Don't name your algorithm as MBCG. All the algorithms here are MBCG =.=
 *
 * Don't forgot to add your new models into allHnswANNSimilarityEngineModelIds list.
 */
object ModelConfig {
  // Offline SimClusters CG Experiment related Model Ids
  val OfflineInterestedInFromKnownFor2020: String = "OfflineIIKF_ALL_20220414"
  val OfflineInterestedInFromKnownFor2020Hl0El15: String = "OfflineIIKF_ALL_20220414_Hl0_El15"
  val OfflineInterestedInFromKnownFor2020Hl2El15: String = "OfflineIIKF_ALL_20220414_Hl2_El15"
  val OfflineInterestedInFromKnownFor2020Hl2El50: String = "OfflineIIKF_ALL_20220414_Hl2_El50"
  val OfflineInterestedInFromKnownFor2020Hl8El50: String = "OfflineIIKF_ALL_20220414_Hl8_El50"
  val OfflineMTSConsumerEmbeddingsFav90P20M: String =
    "OfflineMTSConsumerEmbeddingsFav90P20M_ALL_20220414"

  // Twhin Model Ids
  val ConsumerBasedTwHINRegularUpdateAll20221024: String =
    "ConsumerBasedTwHINRegularUpdate_All_20221024"

  // Averaged Twhin Model Ids
  val TweetBasedTwHINRegularUpdateAll20221024: String =
    "TweetBasedTwHINRegularUpdate_All_20221024"

  // Collaborative Filtering Twhin Model Ids
  val TwhinCollabFilterForFollow: String =
    "TwhinCollabFilterForFollow"
  val TwhinCollabFilterForEngagement: String =
    "TwhinCollabFilterForEngagement"
  val TwhinMultiClusterForFollow: String =
    "TwhinMultiClusterForFollow"
  val TwhinMultiClusterForEngagement: String =
    "TwhinMultiClusterForEngagement"

  // Two Tower model Ids
  val TwoTowerFavALL20220808: String =
    "TwoTowerFav_ALL_20220808"

  // Debugger Demo-Only Model Ids
  val DebuggerDemo: String = "DebuggerDemo"

  // ColdStartLookalike - this is not really a model name, it is as a placeholder to
  // indicate ColdStartLookalike candidate source, which is currently being pluged into
  // CustomizedRetrievalCandidateGeneration temporarily.
  val ColdStartLookalikeModelName: String = "ConsumersBasedUtgColdStartLookalike20220707"

  // consumersBasedUTG-RealGraphOon Model Id
  val ConsumersBasedUtgRealGraphOon20220705: String = "ConsumersBasedUtgRealGraphOon_All_20220705"
  // consumersBasedUAG-RealGraphOon Model Id
  val ConsumersBasedUagRealGraphOon20221205: String = "ConsumersBasedUagRealGraphOon_All_20221205"

  // FTR
  val OfflineFavDecayedSum: String = "OfflineFavDecayedSum"
  val OfflineFtrAt5Pop1000RnkDcy11: String = "OfflineFtrAt5Pop1000RnkDcy11"
  val OfflineFtrAt5Pop10000RnkDcy11: String = "OfflineFtrAt5Pop10000RnkDcy11"

  // All Model Ids of HnswANNSimilarityEngines
  val allHnswANNSimilarityEngineModelIds = Seq(
    ConsumerBasedTwHINRegularUpdateAll20221024,
    TwoTowerFavALL20220808,
    DebuggerDemo
  )

  val ConsumerLogFavBasedInterestedInEmbedding: String =
    "ConsumerLogFavBasedInterestedIn_ALL_20221228"
  val ConsumerFollowBasedInterestedInEmbedding: String =
    "ConsumerFollowBasedInterestedIn_ALL_20221228"

  val RetweetBasedDiffusion: String =
    "RetweetBasedDiffusion"

}
