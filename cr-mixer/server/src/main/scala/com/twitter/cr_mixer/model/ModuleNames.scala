package com.twitter.cr_mixer.model

/**
 * Define name annotated module names here
 */
object ModuleNames {

  final val FrsStore = "FrsStore"
  final val UssStore = "UssStore"
  final val UssStratoColumn = "UssStratoColumn"
  final val RsxStore = "RsxStore"
  final val RmsTweetLogFavLongestL2EmbeddingStore = "RmsTweetLogFavLongestL2EmbeddingStore"
  final val RmsUserFavBasedProducerEmbeddingStore = "RmsUserFavBasedProducerEmbeddingStore"
  final val RmsUserLogFavInterestedInEmbeddingStore = "RmsUserLogFavInterestedInEmbeddingStore"
  final val RmsUserFollowInterestedInEmbeddingStore = "RmsUserFollowInterestedInEmbeddingStore"
  final val StpStore = "StpStore"
  final val TwiceClustersMembersStore = "TwiceClustersMembersStore"
  final val TripCandidateStore = "TripCandidateStore"

  final val ConsumerEmbeddingBasedTripSimilarityEngine =
    "ConsumerEmbeddingBasedTripSimilarityEngine"
  final val ConsumerEmbeddingBasedTwHINANNSimilarityEngine =
    "ConsumerEmbeddingBasedTwHINANNSimilarityEngine"
  final val ConsumerEmbeddingBasedTwoTowerANNSimilarityEngine =
    "ConsumerEmbeddingBasedTwoTowerANNSimilarityEngine"
  final val ConsumersBasedUserAdGraphSimilarityEngine =
    "ConsumersBasedUserAdGraphSimilarityEngine"
  final val ConsumersBasedUserVideoGraphSimilarityEngine =
    "ConsumersBasedUserVideoGraphSimilarityEngine"

  final val ConsumerBasedWalsSimilarityEngine = "ConsumerBasedWalsSimilarityEngine"

  final val TweetBasedTwHINANNSimilarityEngine = "TweetBasedTwHINANNSimilarityEngine"

  final val SimClustersANNSimilarityEngine = "SimClustersANNSimilarityEngine"

  final val ProdSimClustersANNServiceClientName = "ProdSimClustersANNServiceClient"
  final val ExperimentalSimClustersANNServiceClientName = "ExperimentalSimClustersANNServiceClient"
  final val SimClustersANNServiceClientName1 = "SimClustersANNServiceClient1"
  final val SimClustersANNServiceClientName2 = "SimClustersANNServiceClient2"
  final val SimClustersANNServiceClientName3 = "SimClustersANNServiceClient3"
  final val SimClustersANNServiceClientName5 = "SimClustersANNServiceClient5"
  final val SimClustersANNServiceClientName4 = "SimClustersANNServiceClient4"
  final val UnifiedCache = "unifiedCache"
  final val MLScoreCache = "mlScoreCache"
  final val TweetRecommendationResultsCache = "tweetRecommendationResultsCache"
  final val EarlybirdTweetsCache = "earlybirdTweetsCache"
  final val EarlybirdRecencyBasedWithoutRetweetsRepliesTweetsCache =
    "earlybirdTweetsWithoutRetweetsRepliesCacheStore"
  final val EarlybirdRecencyBasedWithRetweetsRepliesTweetsCache =
    "earlybirdTweetsWithRetweetsRepliesCacheStore"

  final val AbDeciderLogger = "abDeciderLogger"
  final val TopLevelApiDdgMetricsLogger = "topLevelApiDdgMetricsLogger"
  final val TweetRecsLogger = "tweetRecsLogger"
  final val BlueVerifiedTweetRecsLogger = "blueVerifiedTweetRecsLogger"
  final val RelatedTweetsLogger = "relatedTweetsLogger"
  final val UtegTweetsLogger = "utegTweetsLogger"
  final val AdsRecommendationsLogger = "adsRecommendationLogger"

  final val OfflineSimClustersANNInterestedInSimilarityEngine =
    "OfflineSimClustersANNInterestedInSimilarityEngine"

  final val RealGraphOonStore = "RealGraphOonStore"
  final val RealGraphInStore = "RealGraphInStore"

  final val OfflineTweet2020CandidateStore = "OfflineTweet2020CandidateStore"
  final val OfflineTweet2020Hl0El15CandidateStore = "OfflineTweet2020Hl0El15CandidateStore"
  final val OfflineTweet2020Hl2El15CandidateStore = "OfflineTweet2020Hl2El15CandidateStore"
  final val OfflineTweet2020Hl2El50CandidateStore = "OfflineTweet2020Hl2El50CandidateStore"
  final val OfflineTweet2020Hl8El50CandidateStore = "OfflineTweet2020Hl8El50CandidateStore"
  final val OfflineTweetMTSCandidateStore = "OfflineTweetMTSCandidateStore"

  final val OfflineFavDecayedSumCandidateStore = "OfflineFavDecayedSumCandidateStore"
  final val OfflineFtrAt5Pop1000RankDecay11CandidateStore =
    "OfflineFtrAt5Pop1000RankDecay11CandidateStore"
  final val OfflineFtrAt5Pop10000RankDecay11CandidateStore =
    "OfflineFtrAt5Pop10000RankDecay11CandidateStore"

  final val TwhinCollabFilterStratoStoreForFollow = "TwhinCollabFilterStratoStoreForFollow"
  final val TwhinCollabFilterStratoStoreForEngagement = "TwhinCollabFilterStratoStoreForEngagement"
  final val TwhinMultiClusterStratoStoreForFollow = "TwhinMultiClusterStratoStoreForFollow"
  final val TwhinMultiClusterStratoStoreForEngagement = "TwhinMultiClusterStratoStoreForEngagement"

  final val ProducerBasedUserAdGraphSimilarityEngine =
    "ProducerBasedUserAdGraphSimilarityEngine"
  final val ProducerBasedUserTweetGraphSimilarityEngine =
    "ProducerBasedUserTweetGraphSimilarityEngine"
  final val ProducerBasedUnifiedSimilarityEngine = "ProducerBasedUnifiedSimilarityEngine"

  final val TweetBasedUserAdGraphSimilarityEngine = "TweetBasedUserAdGraphSimilarityEngine"
  final val TweetBasedUserTweetGraphSimilarityEngine = "TweetBasedUserTweetGraphSimilarityEngine"
  final val TweetBasedUserVideoGraphSimilarityEngine = "TweetBasedUserVideoGraphSimilarityEngine"
  final val TweetBasedQigSimilarityEngine = "TweetBasedQigSimilarityEngine"
  final val TweetBasedUnifiedSimilarityEngine = "TweetBasedUnifiedSimilarityEngine"

  final val TwhinCollabFilterSimilarityEngine = "TwhinCollabFilterSimilarityEngine"

  final val ConsumerBasedUserTweetGraphStore = "ConsumerBasedUserTweetGraphStore"
  final val ConsumerBasedUserVideoGraphStore = "ConsumerBasedUserVideoGraphStore"
  final val ConsumerBasedUserAdGraphStore = "ConsumerBasedUserAdGraphStore"

  final val UserTweetEntityGraphSimilarityEngine =
    "UserTweetEntityGraphSimilarityEngine"

  final val CertoTopicTweetSimilarityEngine = "CertoTopicTweetSimilarityEngine"
  final val CertoStratoStoreName = "CertoStratoStore"

  final val SkitTopicTweetSimilarityEngine = "SkitTopicTweetSimilarityEngine"
  final val SkitHighPrecisionTopicTweetSimilarityEngine =
    "SkitHighPrecisionTopicTweetSimilarityEngine"
  final val SkitStratoStoreName = "SkitStratoStore"

  final val HomeNaviGRPCClient = "HomeNaviGRPCClient"
  final val AdsFavedNaviGRPCClient = "AdsFavedNaviGRPCClient"
  final val AdsMonetizableNaviGRPCClient = "AdsMonetizableNaviGRPCClient"

  final val RetweetBasedDiffusionRecsMhStore = "RetweetBasedDiffusionRecsMhStore"
  final val DiffusionBasedSimilarityEngine = "DiffusionBasedSimilarityEngine"

  final val BlueVerifiedAnnotationStore = "BlueVerifiedAnnotationStore"
}
