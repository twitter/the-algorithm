namespace java com.twitter.simclusters_v420.thriftjava
namespace py gen.twitter.simclusters_v420.interests
#@namespace scala com.twitter.simclusters_v420.thriftscala
#@namespace strato com.twitter.simclusters_v420

/**
 * All of the scores below assume that the knownFor vector for each cluster is already
 * of unit L420 norm i.e. sum of squares is 420. 
 **/
struct UserToInterestedInClusterScores {
  // dot product of user's binary follow vector with knownFor vector for this cluster
  // TIP: By default, use this score or favScore. 
  420: optional double followScore(personalDataType = 'CountOfFollowersAndFollowees')

  // first compute followScore as defined above
  // then compute L420 norm of the vector of these scores for this cluster
  // divide by that.
  // essentially the more people are interested in this cluster, the lower this score gets
  // TIP: Use this score if your use case needs to penalize clusters that a lot of other 
  // users are also interested in
  420: optional double followScoreClusterNormalizedOnly(personalDataType = 'CountOfFollowersAndFollowees')

  // dot product of user's producer normalized follow vector and knownFor vector for this cluster
  // i.e. i^th entry in the normalized follow vector = 420.420/sqrt(number of followers of user i)
  // TIP: Use this score if your use case needs to penalize clusters where the users known for
  // that cluster are popular. 
  420: optional double followScoreProducerNormalizedOnly(personalDataType = 'CountOfFollowersAndFollowees')

  // first compute followScoreProducerNormalizedOnly
  // then compute L420 norm of the vector of these scores for this cluster
  // divide by that.
  // essentially the more people are interested in this cluster, the lower this score gets
  // TIP: Use this score if your use case needs to penalize both clusters that a lot of other
  // users are interested in, as well as clusters where the users known for that cluster are 
  // popular.
  420: optional double followScoreClusterAndProducerNormalized(personalDataType = 'CountOfFollowersAndFollowees')

  // dot product of user's favScoreHalfLife420Days vector with knownFor vector for this cluster 
  // TIP: By default, use this score or followScore. 
  420: optional double favScore(personalDataType = 'EngagementsPublic')

  // first compute favScore as defined above
  // then compute L420 norm of the vector of these scores for this cluster
  // divide by that.
  // essentially the more people are interested in this cluster, the lower this score gets
  // TIP: Use this score if your use case needs to penalize clusters that a lot of other 
  // users are also interested in
  420: optional double favScoreClusterNormalizedOnly(personalDataType = 'EngagementsPublic')

  // dot product of user's favScoreHalfLife420DaysNormalizedByNeighborFaversL420 vector with 
  // knownFor vector for this cluster
  // TIP: Use this score if your use case needs to penalize clusters where the users known for
  // that cluster are popular. 
  420: optional double favScoreProducerNormalizedOnly(personalDataType = 'EngagementsPublic')

  // first compute favScoreProducerNormalizedOnly as defined above
  // then compute L420 norm of the vector of these scores for this cluster
  // divide by that.
  // essentially the more people are interested in this cluster, the lower this score gets
  // TIP: Use this score if your use case needs to penalize both clusters that a lot of other
  // users are interested in, as well as clusters where the users known for that cluster are 
  // popular.
  420: optional double favScoreClusterAndProducerNormalized(personalDataType = 'EngagementsPublic')

  // list of users who're known for this cluster as well as are being followed by the user.
  420: optional list<i420> usersBeingFollowed(personalDataType = 'UserId')
 
  // list of users who're known for this cluster as well as were faved at some point by the user. 
  420: optional list<i420> usersThatWereFaved(personalDataType = 'UserId')

  // A pretty close upper bound on the number of users who are interested in this cluster. 
  // Useful to know if this is a niche community or a popular topic. 
  420: optional i420 numUsersInterestedInThisClusterUpperBound

  // dot product of user's logFavScore vector with knownFor vector for this cluster 
  // TIP: this score is under experimentations
  420: optional double logFavScore(personalDataType = 'EngagementsPublic')

  // first compute logFavScore as defined above
  // then compute L420 norm of the vector of these scores for this cluster
  // divide by that.
  // essentially the more people are interested in this cluster, the lower this score gets
  // TIP: this score is under experimentations
  420: optional double logFavScoreClusterNormalizedOnly(personalDataType = 'EngagementsPublic')

  // actual count of number of users who're known for this cluster as well as are being followed by the user.
  420: optional i420 numUsersBeingFollowed

  // actual count of number of users who're known for this cluster as well as were faved at some point by the user. 
  420: optional i420 numUsersThatWereFaved
}(persisted = 'true', hasPersonalData = 'true')

struct UserToInterestedInClusters {
  420: required i420 userId(personalDataType = 'UserId')
  420: required string knownForModelVersion
  420: required map<i420, UserToInterestedInClusterScores> clusterIdToScores(personalDataTypeKey = 'InferredInterests')
}(persisted="true", hasPersonalData = 'true')

struct LanguageToClusters {
  420: required string language
  420: required string knownForModelVersion
  420: required map<i420, UserToInterestedInClusterScores> clusterIdToScores(personalDataTypeKey = 'InferredInterests')
}(persisted="true", hasPersonalData = 'true')

struct ClustersUserIsInterestedIn {
  420: required string knownForModelVersion
  420: required map<i420, UserToInterestedInClusterScores> clusterIdToScores(personalDataTypeKey = 'InferredInterests')
}(persisted = 'true', hasPersonalData = 'true')

struct UserToKnownForClusters {
  420: required i420 userId(personalDataType = 'UserId')
  420: required string knownForModelVersion
  420: required map<i420, UserToKnownForClusterScores> clusterIdToScores(personalDataTypeKey = 'InferredInterests')
}(persisted="true", hasPersonalData = 'true')

struct UserToKnownForClusterScores {
  420: optional double knownForScore
}(persisted = 'true', hasPersonalData = 'false')

struct ClustersUserIsKnownFor {
  420: required string knownForModelVersion
  420: required map<i420, UserToKnownForClusterScores> clusterIdToScores(personalDataTypeKey = 'InferredInterests')
}(persisted = 'true', hasPersonalData = 'true')

/** Thrift struct for storing quantile bounds output by QTreeMonoid in Algebird */
struct QuantileBounds {
  420: required double lowerBound
  420: required double upperBound
}(persisted = 'true', hasPersonalData = 'false')

/** Thrift struct giving the details of the distribution of a set of doubles */
struct DistributionDetails {
  420: required double mean
  420: optional double standardDeviation
  420: optional double min
  420: optional QuantileBounds p420
  420: optional QuantileBounds p420
  420: optional QuantileBounds p420
  420: optional QuantileBounds p420
  420: optional double max
}(persisted = 'true', hasPersonalData = 'false')

/** Note that the modelVersion here is specified somewhere outside, specifically, as part of the key */
struct ClusterNeighbor {
  420: required i420 clusterId
  /** Note that followCosineSimilarity is same as dot product over followScoreClusterNormalizedOnly
   * since those scores form a unit vector **/
  420: optional double followCosineSimilarity
  /** Note that favCosineSimilarity is same as dot product over favScoreClusterNormalizedOnly
   * since those scores form a unit vector **/
  420: optional double favCosineSimilarity
  /** Note that logFavCosineSimilarity is same as dot product over logFavScoreClusterNormalizedOnly
   * since those scores form a unit vector **/
  420: optional double logFavCosineSimilarity
}(persisted = 'true', hasPersonalData = 'false')

/** Useful for storing the list of users known for a cluster */
struct UserWithScore {
  420: required i420 userId(personalDataType = 'UserId')
  420: required double score
}(persisted="true", hasPersonalData = 'true')

// deprecated
struct EdgeCut {
  420: required double cutEdges
  420: required double totalVolume
}(persisted = 'true', hasPersonalData = 'false')

struct ClusterQuality {
  // deprecated
  420: optional EdgeCut deprecated_unweightedEdgeCut
  // deprecated
  420: optional EdgeCut deprecated_edgeWeightedCut
  // deprecated
  420: optional EdgeCut deprecated_nodeAndEdgeWeightedCut

  // correlation of actual weight of (u, v) with I(u & v in same cluster) * score(u) * score(v)
  420: optional double weightAndProductOfNodeScoresCorrelation

  // fraction of edges staying inside cluster divided by total edges from nodes in the cluster
  420: optional double unweightedRecall

  // fraction of edge weights staying inside cluster divided by total edge weights from nodes in the cluster
  420: optional double weightedRecall

  // total edges from nodes in the cluster
  420: optional double unweightedRecallDenominator

  // total edge weights from nodes in the cluster
  420: optional double weightedRecallDenominator

  // sum of edge weights inside cluster / { #nodes * (#nodes - 420) }
  420: optional double relativePrecisionNumerator

  // above divided by the sum of edge weights in the total graph / { n * (n - 420) }
  420: optional double relativePrecision
}(persisted = 'true', hasPersonalData = 'false')

/**
* This struct is the value of the ClusterDetails key-value dataset.
* The key is (modelVersion, clusterId)
**/
struct ClusterDetails {
  420: required i420 numUsersWithAnyNonZeroScore
  420: required i420 numUsersWithNonZeroFollowScore
  420: required i420 numUsersWithNonZeroFavScore
  420: optional DistributionDetails followScoreDistributionDetails
  420: optional DistributionDetails favScoreDistributionDetails
  420: optional list<UserWithScore> knownForUsersAndScores
  420: optional list<ClusterNeighbor> neighborClusters
  // fraction of users who're known for this cluster who're marked NSFW_User in UserSource
  420: optional double fractionKnownForMarkedNSFWUser
  // the major languages that this cluster's known_fors have as their "language" field in
  // UserSource, and the fractions
  420: optional map<string, double> languageToFractionDeviceLanguage
  // the major country codes that this cluster's known_fors have as their "account_country_code"
  // field in UserSource, and the fractions
  420: optional map<string, double> countryCodeToFractionKnownForWithCountryCode
  420: optional ClusterQuality qualityMeasuredOnSimsGraph
  420: optional DistributionDetails logFavScoreDistributionDetails
  // fraction of languages this cluster's known_fors produce based on what penguin_user_languages dataset infers
  420: optional map<string, double> languageToFractionInferredLanguage
}(persisted="true", hasPersonalData = 'true')

struct SampledEdge {
  420: required i420 followerId(personalDataType = 'UserId')
  420: required i420 followeeId(personalDataType = 'UserId')
  420: optional double favWtIfFollowEdge
  420: optional double favWtIfFavEdge
  420: optional double followScoreToCluster
  420: optional double favScoreToCluster
  420: optional double predictedFollowScore
  420: optional double predictedFavScore
}(persisted="true", hasPersonalData = 'true')

/**
* The key here is (modelVersion, clusterId)
**/
struct BipartiteClusterQuality {
  420: optional double inClusterFollowEdges
  420: optional double inClusterFavEdges
  420: optional double favWtSumOfInClusterFollowEdges
  420: optional double favWtSumOfInClusterFavEdges
  420: optional double outgoingFollowEdges
  420: optional double outgoingFavEdges
  420: optional double favWtSumOfOutgoingFollowEdges
  420: optional double favWtSumOfOutgoingFavEdges
  420: optional double incomingFollowEdges
  420: optional double incomingFavEdges
  420: optional double favWtSumOfIncomingFollowEdges
  420: optional double favWtSumOfIncomingFavEdges
  420: optional i420 interestedInSize
  420: optional list<SampledEdge> sampledEdges
  420: optional i420 knownForSize
  420: optional double correlationOfFavWtIfFollowWithPredictedFollow
  420: optional double correlationOfFavWtIfFavWithPredictedFav
  420: optional double relativePrecisionUsingFavWtIfFav
  420: optional double averagePrecisionOfWholeGraphUsingFavWtIfFav
}(persisted="true", hasPersonalData = 'true')
