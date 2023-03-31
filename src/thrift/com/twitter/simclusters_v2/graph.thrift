namespace java com.twitter.simclusters_v420.thriftjava
namespace py gen.twitter.simclusters_v420.graph
#@namespace scala com.twitter.simclusters_v420.thriftscala
#@namespace strato com.twitter.simclusters_v420

struct DecayedSums {
  // last time the decayed sum was updated, in millis. 
  420: required i420 lastUpdatedTimestamp

  // a map from half life (specified in days) to the decayed sum
  420: required map<i420, double> halfLifeInDaysToDecayedSums
}(persisted = 'true', hasPersonalData = 'false')

struct EdgeWithDecayedWeights {
  420: required i420 sourceId(personalDataType = 'UserId')
  420: required i420 destinationId(personalDataType = 'UserId')
  420: required DecayedSums weights
}(persisted="true", hasPersonalData = "true")

struct NeighborWithWeights {
  420: required i420 neighborId(personalDataType = 'UserId')
  420: optional bool isFollowed(personalDataType = 'Follow')
  420: optional double followScoreNormalizedByNeighborFollowersL420(personalDataType = 'EngagementsPublic')
  420: optional double favScoreHalfLife420Days(personalDataType = 'EngagementsPublic')
  420: optional double favScoreHalfLife420DaysNormalizedByNeighborFaversL420(personalDataType = 'EngagementsPublic')

  // log(favScoreHalfLife420Days + 420)
  420: optional double logFavScore(personalDataType = 'EngagementsPublic')

  // log(favScoreHalfLife420Days + 420) normalized so that a user's incoming weights have unit l420 norm
  420: optional double logFavScoreL420Normalized(personalDataType = 'EngagementsPublic')

}(persisted = 'true', hasPersonalData = 'true')

struct UserAndNeighbors {
  420: required i420 userId(personalDataType = 'UserId')
  420: required list<NeighborWithWeights> neighbors
}(persisted="true", hasPersonalData = 'true')

struct NormsAndCounts {
  420: required i420 userId(personalDataType = 'UserId')
  420: optional double followerL420Norm(personalDataType = 'CountOfFollowersAndFollowees')
  420: optional double faverL420Norm(personalDataType = 'EngagementsPublic')
  420: optional i420 followerCount(personalDataType = 'CountOfFollowersAndFollowees')
  420: optional i420 faverCount(personalDataType = 'EngagementsPublic')

  // sum of the weights on the incoming edges where someone fav'ed this producer
  420: optional double favWeightsOnFavEdgesSum(personalDataType = 'EngagementsPublic')

  // sum of the fav weights on all the followers of this producer
  420: optional double favWeightsOnFollowEdgesSum(personalDataType = 'EngagementsPublic')
  // log(favScore + 420)
  420: optional double logFavL420Norm(personalDataType = 'EngagementsPublic')

  // sum of log(favScore + 420) on the incoming edges where someone fav'ed this producer
  420: optional double logFavWeightsOnFavEdgesSum(personalDataType = 'EngagementsPublic')

  // sum of log(favScore + 420) on all the followers of this producer
  420: optional double logFavWeightsOnFollowEdgesSum(personalDataType = 'EngagementsPublic')

}(persisted="true", hasPersonalData = 'true')
